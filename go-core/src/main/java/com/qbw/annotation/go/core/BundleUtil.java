package com.qbw.annotation.go.core;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;

import com.qbw.log.XLog;

import java.io.Serializable;
import java.util.Arrays;

import static com.qbw.annotation.go.Constant.DEFAULT_BOOL;
import static com.qbw.annotation.go.Constant.DEFAULT_BYTE;
import static com.qbw.annotation.go.Constant.DEFAULT_CHAR;
import static com.qbw.annotation.go.Constant.DEFAULT_DOUBLE;
import static com.qbw.annotation.go.Constant.DEFAULT_FLOAT;
import static com.qbw.annotation.go.Constant.DEFAULT_INT;
import static com.qbw.annotation.go.Constant.DEFAULT_LONG;
import static com.qbw.annotation.go.Constant.DEFAULT_SHORT;
import static com.qbw.annotation.go.Constant.SPLIT;

/**
 * @author qbw
 * @createtime 2016/08/30 16:28
 */


public class BundleUtil {

    private static BundleUtil sInst;

    private BundleUtil() {
    }

    public static BundleUtil getInstance() {
        if (null == sInst) {
            synchronized (BundleUtil.class) {
                if (null == sInst) {
                    sInst = new BundleUtil();
                }
            }
        }
        return sInst;
    }

    public void pack(Bundle b, String classname, String key, Object value) {
        if (casePackNormal(b, classname, key, value)) {
        } else if (casePackArray(b, classname, key, value)) {
        } else if (casePS(b, classname, key, value)) {
        } else {
            throw new RuntimeException("Go, Unsupport " + classname);
        }
    }

    public Object unpack(Bundle b, String classname, String key) {
        Object value = null;
        if (null != (value = caseUnpackNormal(b, classname, key))) {
        } else if (null != (value = caseUnpackArray(b, classname, key))) {
        } else if (null != (value = caseUnpackPs(b, classname, key))) {
        }
        return value;
    }

    private boolean casePackNormal(Bundle b, String classname, String key, Object value) {
        boolean normal = true;
        if (Byte.class.getCanonicalName().equals(classname)) {
            if (null == value) {
                value = DEFAULT_BYTE;
            }
            b.putByte(key, (byte) value);
        } else if (Short.class.getCanonicalName().equals(classname)) {
            if (null == value) {
                value = DEFAULT_SHORT;
            }
            b.putShort(key, (Short) value);
        } else if (Integer.class.getCanonicalName().equals(classname)) {
            if (null == value) {
                value = DEFAULT_INT;
            }
            b.putInt(key, (Integer) value);
        } else if (Long.class.getCanonicalName().equals(classname)) {
            if (null == value) {
                value = DEFAULT_LONG;
            }
            b.putLong(key, (Long) value);
        } else if (String.class.getCanonicalName().equals(classname)) {
            if (null == value) {
                //value = DEFAULT_STR;
                b.putString(key, null);
            } else {
                b.putString(key, (String) value);
            }
        } else if (Boolean.class.getCanonicalName().equals(classname)) {
            if (null == value) {
                value = DEFAULT_BOOL;
            }
            b.putBoolean(key, (Boolean) value);
        } else if (Character.class.getCanonicalName().equals(classname)) {
            if (null == value) {
                value = DEFAULT_CHAR;
            }
            b.putChar(key, (Character) value);
        } else if (Float.class.getCanonicalName().equals(classname)) {
            if (null == value) {
                value = DEFAULT_FLOAT;
            }
            b.putFloat(key, (Float) value);
        } else if (Double.class.getCanonicalName().equals(classname)) {
            if (null == value) {
                value = DEFAULT_DOUBLE;
            }
            b.putDouble(key, (Double) value);
        } else {
            normal = false;
        }
        if (normal) {
            XLog.d("%s %s = %s", classname, key.replace(SPLIT, ""), value);
        }
        return normal;
    }

    private boolean casePackArray(Bundle b, String classname, String key, Object value) {
        boolean array = true;
        if (String[].class.getCanonicalName().equals(classname)) {
            if (null == value) {
                b.putStringArray(key, null);
            } else {
                b.putStringArray(key, (String[]) value);
            }
        } else if (int[].class.getCanonicalName().equals(classname)) {
            if (null == value) {
                b.putIntArray(key, null);
            } else {
                b.putIntArray(key, (int[]) value);
            }
        } else if (long[].class.getCanonicalName().equals(classname)) {
            if (null == value) {
                b.putLongArray(key, null);
            } else {
                b.putLongArray(key, (long[]) value);
            }
        } else if (float[].class.getCanonicalName().equals(classname)) {
            if (null == value) {
                b.putFloatArray(key, null);
            } else {
                b.putFloatArray(key, (float[]) value);
            }
        } else if (double[].class.getCanonicalName().equals(classname)) {
            if (null == value) {
                b.putDoubleArray(key, null);
            } else {
                b.putDoubleArray(key, (double[]) value);
            }
        } else {
            array = false;
        }
        if (array) {
            XLog.d("%s %s = %s", classname, key.replace(SPLIT, ""), value);
        }
        return array;
    }


    private boolean casePS(Bundle b, String classname, String key, Object value) {
        boolean ps = true;
        if (Serializable.class.getCanonicalName().equals(classname)) {
            if (null == value) {
                b.putSerializable(key, null);
            } else {
                b.putSerializable(key, (Serializable) value);
            }
        } else if (Parcelable.class.getCanonicalName().equals(classname)) {
            if (null == value) {
                b.putParcelable(key, null);
            } else {
                b.putParcelable(key, (Parcelable) value);
            }
        } else if (Parcelable[].class.getCanonicalName().equals(classname)) {
            if (null == value) {
                b.putParcelableArray(key, null);
            } else {
                b.putParcelableArray(key, (Parcelable[]) value);
            }
        } else {
            ps = false;
        }
        return ps;
    }

    private Object caseUnpackNormal(Bundle b, String classname, String key) {
        Object value = null;
        if (Byte.class.getCanonicalName().equals(classname)) {
            value = b.getByte(key, DEFAULT_BYTE);
        } else if (Short.class.getCanonicalName().equals(classname)) {
            value = b.getShort(key, DEFAULT_SHORT);
        } else if (Integer.class.getCanonicalName().equals(classname)) {
            value = b.getInt(key, DEFAULT_INT);
        } else if (Long.class.getCanonicalName().equals(classname)) {
            value = b.getLong(key, DEFAULT_LONG);
        } else if (String.class.getCanonicalName().equals(classname)) {
            value = b.getString(key);//getString(key,defval) 最小sdk是12
        } else if (Boolean.class.getCanonicalName().equals(classname)) {
            value = b.getBoolean(key, DEFAULT_BOOL);
        } else if (Character.class.getCanonicalName().equals(classname)) {
            value = b.getChar(key, DEFAULT_CHAR);
        } else if (Float.class.getCanonicalName().equals(classname)) {
            value = b.getFloat(key, DEFAULT_FLOAT);
        } else if (Double.class.getCanonicalName().equals(classname)) {
            value = b.getDouble(key, DEFAULT_DOUBLE);
        }
        if (XLog.isDebug() && null != value) {
            XLog.d("%s %s = %s", classname, key.replace(SPLIT, ""), value);
        }
        return value;
    }

    private Object caseUnpackArray(Bundle b, String classname, String key) {
        Object value = null;
        String strValue = null;
        if (String[].class.getCanonicalName().equals(classname)) {
            value = b.getStringArray(key);
            if (XLog.isDebug() && null != value) {
                strValue = Arrays.toString((String[]) value);
            }
        } else if (int[].class.getCanonicalName().equals(classname)) {
            value = b.getIntArray(key);
            if (XLog.isDebug() && null != value) {
                strValue = Arrays.toString((int[]) value);
            }
        } else if (long[].class.getCanonicalName().equals(classname)) {
            value = b.getLongArray(key);
            if (XLog.isDebug() && null != value) {
                strValue = Arrays.toString((long[]) value);
            }
        } else if (float[].class.getCanonicalName().equals(classname)) {
            value = b.getFloatArray(key);
            if (XLog.isDebug() && null != value) {
                strValue = Arrays.toString((float[]) value);
            }
        } else if (double[].class.getCanonicalName().equals(classname)) {
            value = b.getDoubleArray(key);
            if (XLog.isDebug() && null != value) {
                strValue = Arrays.toString((double[]) value);
            }
        }
        if (XLog.isDebug() && !TextUtils.isEmpty(strValue)) {
            XLog.d("%s %s = %s", classname, key.replace(SPLIT, ""), strValue);
        }
        return value;
    }

    private Object caseUnpackPs(Bundle b, String classname, String key) {
        Object value = null;
        if (Serializable.class.getCanonicalName().equals(classname)) {
            value = b.getSerializable(key);
        } else if (Parcelable.class.getCanonicalName().equals(classname)) {
            value = b.getParcelable(key);
        } else if (Parcelable[].class.getCanonicalName().equals(classname)) {
            value = b.getParcelableArray(key);
        }
        return value;
    }
}
