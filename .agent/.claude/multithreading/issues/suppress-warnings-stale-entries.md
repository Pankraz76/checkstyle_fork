# Issue draft: stale `@SuppressWarnings` entries

For `checkstyle/checkstyle`, bug report template. Before filing, replace the output with a run
of the latest release jar (the output below is from a build of `master` at `9d82118`) and add
the output of `java -version`.

## Text

Title: SuppressWarningsFilter suppresses violations in a file with suppressions of a
previously processed Java file

I have read check documentation: <https://checkstyle.org/filters/suppresswarningsfilter.html>
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
  <property name="fileExtensions" value="java, txt"/>
  <module name="SuppressWarningsFilter"/>
  <module name="FileTabCharacter">
    <property name="eachLine" value="true"/>
  </module>
  <module name="TreeWalker">
    <module name="SuppressWarningsHolder"/>
  </module>
</module>

$ cat A.java
@SuppressWarnings("all")
class A {
    int a;
    int b;
    int c;
}

$ cat -A b.txt
line one$
^Iline two with tab$
line three$

$ java -jar checkstyle-all.jar -c config.xml b.txt A.java
Starting audit...
[ERROR] b.txt:2:1: Line contains a tab character. [FileTabCharacter]
Audit done.
Checkstyle ends with 1 errors.

$ java -jar checkstyle-all.jar -c config.xml A.java b.txt
Starting audit...
Audit done.
```

Expected: the violation in `b.txt` is reported in both runs. `b.txt` contains no annotation.

Actual: the violation is suppressed when `A.java` is processed before `b.txt`.

Cause: `SuppressWarningsHolder` keeps the collected entries in a static `ThreadLocal` and
clears them only in `beginTree`. `beginTree` is not called for files that `TreeWalker` does not
walk (other extensions, or Java files skipped by `skipFileOnJavaParseException`), so
`SuppressWarningsFilter` still sees the entries of the last walked file and matches them by
line and column only.
