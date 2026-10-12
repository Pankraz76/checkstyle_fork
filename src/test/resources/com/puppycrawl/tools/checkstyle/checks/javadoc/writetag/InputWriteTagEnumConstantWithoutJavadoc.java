/*
WriteTag
tag = @incomplete
tagFormat = .*
tagSeverity = (default)info
tokens = ENUM_CONSTANT_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.writetag;

public enum InputWriteTagEnumConstantWithoutJavadoc {
    FIRST,
    // violation 2 lines below 'Javadoc tag @incomplete=This enum constant needs more code...'
    /**
     * @incomplete This enum constant needs more code...
     */
    SECOND,
    // single line comment
    THIRD
}
