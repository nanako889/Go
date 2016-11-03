package com.qbw.annotation.go.compiler.common;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import javax.lang.model.type.TypeMirror;

/**
 * @author QBW
 * @createtime 2016/08/15 18:47
 * @company 9zhitx.com
 * @description
 */


public class VariableEnity {

    private String mName;//变量名
    private TypeMirror mTypeMirror;//类型信息

    private String mType;//经典的包名类名组合(getCanonicalName)

    private String mSourceType;//不会经过转化

    public VariableEnity(String name, TypeMirror typeMirror) {
        mName = name;
        mTypeMirror = typeMirror;
        mType = mTypeMirror.toString();
        mSourceType = mType;
        convertType();
        //Log.i(String.format("name[%s] type[%s]", mName, mType));
    }

    /**
     * @return 变量名
     */
    public String getName() {
        return mName;
    }

    /**
     * @return 变量类型简单类名
     */
    public String getSimpleClassName() {
        return mType.substring(mType.lastIndexOf(".") + 1);
    }

    /**
     * @return JavaPoet格式的变量类型
     */
    public TypeName getPoetTypeName() {
        return ClassName.get(mTypeMirror);
    }

    private void convertType() {
        if ("int".equals(mType)) {
            mType = "java.lang.Integer";
        } else if ("long".equals(mType)) {
            mType = "java.lang.Long";
        } else if ("float".equals(mType)) {
            mType = "java.lang.Float";
        } else if ("boolean".equals(mType)) {
            mType = "java.lang.Boolean";
        } else if ("byte".equals(mType)) {
            mType = "java.lang.Byte";
        } else if ("short".equals(mType)) {
            mType = "java.lang.Short";
        } else if ("char".equals(mType)) {
            mType = "java.lang.Character";
        } else if ("double".equals(mType)) {
            mType = "java.lang.Double";
        }
    }

    public String getType() {
        return mType;
    }

    public boolean nullAble() {
        if ("int".equals(mSourceType)) {
            return false;
        } else if ("long".equals(mSourceType)) {
            return false;
        } else if ("float".equals(mSourceType)) {
            return false;
        } else if ("boolean".equals(mSourceType)) {
            return false;
        } else if ("byte".equals(mSourceType)) {
            return false;
        } else if ("short".equals(mSourceType)) {
            return false;
        } else if ("char".equals(mSourceType)) {
            return false;
        } else if ("double".equals(mSourceType)) {
            return false;
        }
        return true;
    }

    public boolean nullUnableBool() {
        if ("boolean".equals(mSourceType)) {
            return true;
        } else {
            return false;
        }
    }
}
