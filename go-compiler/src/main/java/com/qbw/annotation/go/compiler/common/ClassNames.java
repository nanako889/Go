package com.qbw.annotation.go.compiler.common;

import com.squareup.javapoet.ClassName;

import static com.qbw.annotation.go.Constant.POET_CLASS_PACKAGE;
import static com.qbw.annotation.go.Constant.REFERENCE_CLASS_PACKAGE;

/**
 * Created by Bond on 2016/8/14.
 */

public class ClassNames {

    //custom(extern class)
    public static final ClassName ABSTRACT_GO = ClassName.get(REFERENCE_CLASS_PACKAGE, "AbstractGo");
    public static final ClassName BUNDLE_UTIL = ClassName.get(REFERENCE_CLASS_PACKAGE, "BundleUtil");
    public static final ClassName XLOG = ClassName.get("com.qbw.log", "XLog");
    //public static final ClassName XLOG = ClassName.get("com.qbw.log", "XLog");

    //android
    public static final ClassName CONTEXT = ClassName.get("android.content", "Context");
    public static final ClassName ACTIVITY = ClassName.get("android.app", "Activity");
    public static final ClassName INTENT = ClassName.get("android.content", "Intent");
    public static final ClassName BUNDLE = ClassName.get("android.os", "Bundle");

    //java
    public static final ClassName STRING = ClassName.get(String.class);
    public static final ClassName CLASS = ClassName.get(Class.class);
    //public static final ClassName SERIALIZABLE = ClassName.get(Serializable.class);


    //to generate
    public static final ClassName GO = ClassName.get(POET_CLASS_PACKAGE, "Go");
    public static final ClassName I_GO = ClassName.get(POET_CLASS_PACKAGE, "IGo");


}
