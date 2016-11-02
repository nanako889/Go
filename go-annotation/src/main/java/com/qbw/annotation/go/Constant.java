package com.qbw.annotation.go;

/**
 * @author QBW
 * @createtime 2016/08/15 16:05
 * @company 9zhitx.com
 * @description
 */


public class Constant {

    public static final String REFERENCE_CLASS_PACKAGE = "com.qbw.annotation.go.core";
    public static final String POET_CLASS_PACKAGE = "com.qbw.annotation";
    public static final String LINK = "";
    public static final String SUFFIX = "GO";
    public static final String SPLIT = "<_#$;_>";
    public static final String INNER_LINK = "_";
    public static final String SN_STRING = "String";
    public static final String SN_INTEGER = "Integer";
    public static final String SN_LONG = "Long";
    public static final String SN_FLOAT = "Float";
    public static final String SN_BOOLEAN = "Boolean";
    public static final String SN_STRING_ARR = "String[]";
    public static final String SN_LIST = "List";
    public static final String SN_CHARSEQUENCE_LIST = "CharSequence>";
    public static final String SN_INTEGER_LIST = "Integer>";
    public static final String SN_STRING_LIST = "String>";
    //public static final String SN_OB_BYTE = "ObservableByte";

    public static final Byte DEFAULT_BYTE = (byte) 0;
    public static final Short DEFAULT_SHORT = (short) 0;
    public static final Integer DEFAULT_INT = 0;
    public static final Long DEFAULT_LONG = 0l;
    @Deprecated
    public static final String DEFAULT_STR = "";
    public static final Boolean DEFAULT_BOOL = false;
    public static final Character DEFAULT_CHAR = '0';
    public static final String STR_NULL = "null";
    public static final Float DEFAULT_FLOAT = 0.0f;
    public static final Double DEFAULT_DOUBLE = 0.0;

    public static String appendSuffix(String className) {
        return className + LINK + SUFFIX;
    }

    public static String getKey(String sKey) {
        return SPLIT + sKey;
    }
}
