package com.qbw.annotation.go.core;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.qbw.log.XLog;

/**
 * @author qbw
 * @createtime 2016/08/29 17:43
 */


public abstract class AbstractGo implements IGo {

    protected Context mFromAct;
    protected Class mGoToCls;

    protected Bundle mBundle;

    @Override
    public void go() {
        if (null == mFromAct) {
            XLog.e("method 'from' is not be called!");
            return;
        }
        if (null == mGoToCls) {
            XLog.e("method 'to' is not be called");
            return;
        }
        Intent intent = new Intent(mFromAct, mGoToCls);
        if (!(mFromAct instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (null != mBundle) {
            intent.putExtras(mBundle);
        }
        mFromAct.startActivity(intent);
    }

    public Bundle extract() {
        return mBundle;
    }
}
