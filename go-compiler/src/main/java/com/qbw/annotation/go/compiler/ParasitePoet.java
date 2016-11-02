package com.qbw.annotation.go.compiler;

import com.qbw.annotation.go.Constant;
import com.qbw.annotation.go.compiler.common.AbstractPoet;
import com.qbw.annotation.go.compiler.common.ClassNames;
import com.qbw.annotation.go.compiler.common.Log;
import com.qbw.annotation.go.compiler.common.VariableEnity;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;

import static com.qbw.annotation.go.compiler.common.ClassNames.BUNDLE;
import static com.qbw.annotation.go.compiler.common.ClassNames.BUNDLE_UTIL;
import static com.qbw.annotation.go.compiler.common.ClassNames.XLOG;

/**
 * @author qbw
 * @createtime 2016/08/29 16:32
 */


public class ParasitePoet extends AbstractPoet {

    //public static final String M_EMPTY_BUNDLE = "emptyBundle";
    public static final String M_FROM = "from";

    public static final String F_INTENT = "mIntent";
    public static final String F_BUNDLE_UTIL = "mBU";
    public static final String F_FROM = "mFromAct";

    public static final String P_BUNDLE = "bundle";
    public static final String P_SCN = "simpleClassName";
    public static final String P_KEY = "key";
    public static final String P_HOST = "host";

    private List<VariableEnity> mVariableNames;

    private ClassName mHostClassName;

    public ParasitePoet(Filer filer, String packageName, String parasiteComplexClassName, String parasiteSimpleClassName, String hostComplexClassName) {
        super(filer, packageName, parasiteComplexClassName, parasiteSimpleClassName);
        mVariableNames = new ArrayList<>();
        mHostClassName = ClassName.get(packageName, hostComplexClassName);
    }


    @Override
    protected List<FieldSpec> getFields() {
        List<FieldSpec> fieldSpecs = new ArrayList<>();

        FieldSpec fieldSpec = FieldSpec.builder(BUNDLE_UTIL, F_BUNDLE_UTIL).addModifiers(Modifier.PRIVATE).
                build();
        fieldSpecs.add(fieldSpec);

        return fieldSpecs;
    }


    @Override
    protected List<MethodSpec> getMethods() {
        List<MethodSpec> methodSpecs = new ArrayList<>();

        //构造函数
        MethodSpec.Builder mb = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC);
        mb.addStatement("$L = $T.$L()", F_BUNDLE_UTIL, BUNDLE_UTIL, M_G_INST);
        mb.addStatement("$L = $T.class", M_GOTO_CLS, mHostClassName);
        methodSpecs.add(mb.build());

        //from函数
        mb = MethodSpec.methodBuilder(M_FROM);
        mb.addModifiers(Modifier.PUBLIC);
        mb.addParameter(ParameterSpec.builder(ClassNames.ACTIVITY, P_FROM_ACT).build());
        mb.returns(mTargetClassName);
        mb.addStatement("$L = $L", F_FROM, P_FROM_ACT);
        mb.addStatement("$L = new $T()", F_BUNDLE, BUNDLE);
        mb.addStatement("return this");
        methodSpecs.add(mb.build());

        //变量pack函数
        for (VariableEnity ve : mVariableNames) {
            Log.i("generate set method:" + convertMethodName(ve.getName()));
            mb = MethodSpec.methodBuilder(convertMethodName(ve.getName())).addModifiers(Modifier.PUBLIC).returns(mTargetClassName);
            mb.addParameter(ParameterSpec.builder(ve.getPoetTypeName(), P_VALUE).build());
            mb.beginControlFlow("if (null == $L)", F_BUNDLE);
            mb.addStatement("$L.e($S)", XLOG, "null == mBundle, you should call method [from] first");
            mb.addStatement("return this");
            mb.endControlFlow();
            mb.addStatement("$L.$L($L, $S, $S, $L)", F_BUNDLE_UTIL, M_SAVE, F_BUNDLE, ve.getType(), Constant.getKey(ve.getName()), P_VALUE);
            mb.addStatement("return this");
            methodSpecs.add(mb.build());
        }

        //pack函数
        mb = MethodSpec.methodBuilder(M_SAVE).addModifiers(Modifier.PUBLIC).addParameter(ParameterSpec.builder(mHostClassName, P_HOST).build()).addParameter(ParameterSpec.builder(BUNDLE, P_BUNDLE).build());
        mb.beginControlFlow("if (null == $L)", P_HOST);
        mb.addStatement("$L.e($S)", XLOG, "null == host");
        mb.addStatement("return");
        mb.endControlFlow();
        mb.beginControlFlow("if (null == $L)", P_BUNDLE);
        mb.addStatement("$L.e($S)", XLOG, "null == bundle");
        mb.addStatement("return");
        mb.endControlFlow();
        mb.addStatement("$L = $L", F_BUNDLE, P_BUNDLE);
        for (VariableEnity ve : mVariableNames) {
            mb.addStatement("$L($L.$L)", convertMethodName(ve.getName()), P_HOST, ve.getName());
        }
        methodSpecs.add(mb.build());

        //变量unpack函数
        for (VariableEnity ve : mVariableNames) {
            Log.i("generate get method:" + convertMethodName(ve.getName()));
            mb = MethodSpec.methodBuilder(convertMethodName(ve.getName())).addModifiers(Modifier.PUBLIC).returns(ve.getPoetTypeName());
            mb.addParameter(ParameterSpec.builder(mHostClassName, P_HOST).build());
            mb.addParameter(ParameterSpec.builder(ClassNames.BUNDLE, P_BUNDLE).build());
            mb.beginControlFlow("if (null == $L)", P_HOST);
            mb.addStatement("$L.e($S)", XLOG, "null == host");
            if (ve.nullAble()) {
                mb.addStatement("return null");
            } else {
                if (ve.nullUnableBool()) {
                    mb.addStatement("return false");
                } else {
                    mb.addStatement("return 0");
                }
            }
            mb.endControlFlow();
            mb.beginControlFlow("if (null == $L)", P_BUNDLE);
            mb.addStatement("$L.e($S)", XLOG, "null == bundle");
            if (ve.nullAble()) {
                mb.addStatement("return null");
            } else {
                if (ve.nullUnableBool()) {
                    mb.addStatement("return false");
                } else {
                    mb.addStatement("return 0");
                }
            }
            mb.endControlFlow();
            mb.addStatement("$T $L = null", ClassName.OBJECT, P_VALUE);
            mb.addStatement("$L = $L.$L($L, $S, $S)", P_VALUE, F_BUNDLE_UTIL, M_RESTORE, P_BUNDLE, ve.getType(), Constant.getKey(ve.getName()));
            if (ve.nullAble()) {
                mb.beginControlFlow("if (null == $L)", P_VALUE);
                mb.addStatement("return null");
                mb.endControlFlow();
            }
            mb.addStatement("$L.$L = ($T)$L", P_HOST, ve.getName(), ve.getPoetTypeName(), P_VALUE);
            mb.addStatement("return $L.$L", P_HOST, ve.getName());
            methodSpecs.add(mb.build());
        }

        //unpack函数
        mb = MethodSpec.methodBuilder(M_RESTORE).addModifiers(Modifier.PUBLIC).addParameter(ParameterSpec.builder(mHostClassName, P_HOST).build()).addParameter(ParameterSpec.builder(BUNDLE, P_BUNDLE).build());
        mb.beginControlFlow("if (null == $L)", P_HOST);
        mb.addStatement("$L.e($S)", XLOG, "null == host");
        mb.addStatement("return");
        mb.endControlFlow();
        mb.beginControlFlow("if (null == $L)", P_BUNDLE);
        mb.addStatement("$L.e($S)", XLOG, "null == bundle");
        mb.addStatement("return");
        mb.endControlFlow();
        for (VariableEnity ve : mVariableNames) {
            mb.addStatement("$L($L, $L)", convertMethodName(ve.getName()), P_HOST, P_BUNDLE);
        }
        methodSpecs.add(mb.build());

        return methodSpecs;
    }

    public ClassName getHostClassName() {
        return mHostClassName;
    }

    @Override
    protected ClassName getParent() {
        return ClassNames.ABSTRACT_GO;
    }

    public List<VariableEnity> getVariableNames() {
        return mVariableNames;
    }

    protected String convertMethodName(String valName) {
        if (valName.length() > 1 && valName.startsWith("m")) {
            valName = valName.substring(1);
            char c = valName.charAt(0);
            if (Character.isUpperCase(c)) {
                c = Character.toLowerCase(c);
                if (valName.length() > 1) {
                    valName = c + valName.substring(1);
                } else {
                    valName = "" + c;
                }
            }
        }
        return valName;
    }
}
