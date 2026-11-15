#!/usr/bin/env python3
"""Writes a Checkstyle configuration that enables every module with its defaults.

Usage: all-modules-config.py <repository root> <output file>

Modules are read from the generated metadata. Filters that need a mandatory property
(a suppressions file, a query, a severity) are left out.
"""
import glob
import re
import sys

SKIPPED = {
    "BeforeExecutionExclusionFileFilter",
    "SeverityMatchFilter",
    "SuppressionFilter",
    "SuppressionSingleFilter",
    "SuppressionXpathFilter",
    "SuppressionXpathSingleFilter",
}
META = "/src/main/resources/com/puppycrawl/tools/checkstyle/meta/**/*.xml"
MODULE = re.compile(
    r'<(?:check|filter|file-filter)\s+fully-qualified-name="[^"]+"\s+'
    r'name="([^"]+)"\s+parent="([^"]+)"', re.S)


def main(root, output):
    tree_walker = []
    checker = []
    for path in sorted(glob.glob(root + META, recursive=True)):
        with open(path, encoding="utf-8") as meta:
            match = MODULE.search(meta.read())
        if match and match.group(1) not in SKIPPED:
            target = tree_walker if match.group(2).endswith("TreeWalker") else checker
            target.append(match.group(1))
    lines = [
        '<?xml version="1.0"?>',
        '<!DOCTYPE module PUBLIC',
        '    "-//Checkstyle//DTD Checkstyle Configuration 1.3//EN"',
        '    "https://checkstyle.org/dtds/configuration_1_3.dtd">',
        '<module name="Checker">',
        '  <property name="haltOnException" value="false"/>',
    ]
    lines += ['  <module name="%s"/>' % name for name in checker]
    lines += [
        '  <module name="TreeWalker">',
        '    <property name="skipFileOnJavaParseException" value="true"/>',
    ]
    lines += ['    <module name="%s"/>' % name for name in tree_walker]
    lines += ['  </module>', '</module>', '']
    with open(output, "w", encoding="utf-8") as config:
        config.write("\n".join(lines))
    print("%d modules" % (len(checker) + len(tree_walker)))


if __name__ == "__main__":
    main(sys.argv[1], sys.argv[2])
