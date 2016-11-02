package com.qbw.annotation.go.compiler.common;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.processing.Filer;
import javax.annotation.processing.FilerException;
import javax.lang.model.element.Modifier;

import static com.qbw.annotation.go.Constant.INNER_LINK;

/**
 * Created by Bond on 2016/8/14.
 */

public abstract class AbstractPoet {

    protected final String M_G_INST = "getInstance";
    protected final String M_G_CONTEXT = "getContext";
    protected final String M_SAVE = "pack";
    protected final String M_RESTORE = "unpack";
    protected final String M_CLEAR = "clear";
    protected final String M_GO = "Go";
    protected final String M_GOTO_CLS = "mGoToCls";

    protected final String F_TARGET = "mTarget";
    protected final String F_CONTEXT = "mContext";
    protected final String F_BUNDLE = "mBundle";

    protected final String P_FROM_ACT = "fromAct";
    protected final String P_GOTO_CLS = "gotoCls";
    protected final String P_HOST = "host";
    protected final String P_CONTEXT = "context";
    protected final String P_VALUE = "value";



    protected final String FILE_HEADER = "\nThis file is generated and should not be edited!\n\nAuthor:qbaowei@qq.com\n";


    protected Filer mFiler;

    protected String mPackageName;
    /**
     * inner class(If 'Test' is a innerclass, so com.test.MainActivity.Test, for'Test' its SimpleClassName is 'Test', ComplexClassName is 'MainActivity.Test')
     */
    protected String mComplexClassName;
    protected String mSimpleClassName;

    protected String mFileClassName;

    protected ClassName mTargetClassName;

    public AbstractPoet(Filer filer, String packageName, String complexClassName, String simpleClassName) {
        mFiler = filer;
        mPackageName = packageName;
        mComplexClassName = complexClassName;
        mSimpleClassName = simpleClassName;
        mFileClassName = mComplexClassName.replace(".", INNER_LINK);
        mTargetClassName = ClassName.get(mPackageName, mFileClassName);
    }

    public void generate() {

        Log.i("Start to generate code for " + mTargetClassName.toString());
        TypeSpec.Builder bTypeSpec;
        if (TypeSpec.Kind.CLASS == getKind()) {
            bTypeSpec = TypeSpec.classBuilder(mTargetClassName);
        } else {
            bTypeSpec = TypeSpec.interfaceBuilder(mTargetClassName);
        }
        bTypeSpec.addModifiers(Modifier.PUBLIC);
        if (null != getParent()) {
            if (TypeSpec.Kind.INTERFACE == getKind()) {
                bTypeSpec.addSuperinterface(getParent());
            } else {
                bTypeSpec.superclass(getParent());
            }
        }

        CodeBlock staticBlock = getStaticBlock();
        if (null != staticBlock) {
            bTypeSpec.addStaticBlock(staticBlock);
        }

        for (FieldSpec fieldSpec : getFields()) {
            bTypeSpec.addField(fieldSpec);
        }
        for (MethodSpec methodSpec : getMethods()) {
            bTypeSpec.addMethod(methodSpec);
        }
        JavaFile javaFile = JavaFile.builder(mPackageName, bTypeSpec.build()).addFileComment(FILE_HEADER).
                build();
        try {
            javaFile.writeTo(mFiler);
        } catch (FilerException e) {
            Log.w(e);
        } catch (IOException e) {
            Log.w(e);
        }
    }

    protected ClassName getParent() {
        return null;
    }

    public ClassName getTargetClassName() {
        return mTargetClassName;
    }

    public String getFileClassName() {
        return mFileClassName;
    }

    protected List<FieldSpec> getFields() {
        return new ArrayList<>();
    }

    protected List<MethodSpec> getMethods() {
        return new ArrayList<>();
    }

    protected CodeBlock getStaticBlock() {
        return null;
    }

    protected TypeSpec.Kind getKind() {
        return TypeSpec.Kind.CLASS;
    }

    protected TypeSpec.Kind getParentKind() {
        return TypeSpec.Kind.INTERFACE;
    }
}
