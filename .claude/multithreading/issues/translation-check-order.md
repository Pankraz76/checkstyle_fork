# Issue draft: order of `TranslationCheck` violations

For `checkstyle/checkstyle`, bug report template. Before filing, replace the output with a run
of the latest release jar (the output below is from a build of `master` at `9d82118`, with
paths shortened to `...`) and add the output of `java -version`.

## Text

Title: TranslationCheck reports violations in an order that depends on JVM identity hashes

I have read check documentation: <https://checkstyle.org/checks/misc/translation.html>
I have downloaded the latest checkstyle from:
<https://checkstyle.org/cmdline.html#Download_and_Run>
I have executed the cli and showed it below, as cli describes the problem better than 1,000
words

```text
$ cat config.xml
<?xml version="1.0"?>
<!DOCTYPE module PUBLIC
    "-//Checkstyle//DTD Checkstyle Configuration 1.3//EN"
    "https://checkstyle.org/dtds/configuration_1_3.dtd">
<module name="Checker">
  <property name="fileExtensions" value="properties"/>
  <module name="Translation">
    <property name="requiredTranslations" value="sq"/>
  </module>
</module>

$ java -jar checkstyle-all.jar -c config.xml src/main/resources | head -4
Starting audit...
[ERROR] .../checks/regexp:1: Properties file 'messages_sq.properties' is missing.
[ERROR] .../checks/design:1: Properties file 'messages_sq.properties' is missing.
[ERROR] .../checks/javadoc:1: Properties file 'messages_sq.properties' is missing.

$ java -XX:+UnlockExperimentalVMOptions -XX:hashCode=2 -jar checkstyle-all.jar \
    -c config.xml src/main/resources | head -4
Starting audit...
[ERROR] .../checks/metrics:1: Properties file 'messages_sq.properties' is missing.
[ERROR] .../checks/modules:1: Properties file 'messages_sq.properties' is missing.
[ERROR] .../checks/whitespace:1: Properties file 'messages_sq.properties' is missing.
```

`src/main/resources` is the folder of the Checkstyle repository. Both runs report the same 19
violations; only their order differs.

Expected: the order of violations is defined by the input, for example the order in which the
files were given.

Actual: `TranslationCheck.groupFilesIntoBundles` collects bundles in a `HashSet`. The private
class `ResourceBundle` does not override `equals` and `hashCode`, so the iteration order follows
identity hash codes. It is stable only as long as the JVM hands out the same identity hashes,
which is not guaranteed between JVM versions, flags or embedding applications.
