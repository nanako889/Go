package com.qbw.annotation.go.core;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.qbw.log.XLog;

/**
 * @author qbw
 * @createtime 2016/08/29 17:43
 */


public abstract class AbstractGo implements IGo {

    protected Activity mFromAct;
    protected Class mGoToCls;

    protected Bundle mBundle;

//    public AbstractGo from(Activity fromAct) {
//        mFromAct = fromAct;
//        return this;
//    }

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
        if (null != mBundle) {
            intent.putExtras(mBundle);
        }
        mFromAct.startActivity(intent);
    }

    public Bundle extract() {
        return mBundle;
    }

//    protected Bundle emptyBundle() {
//        if (null == mBundle) {
//            mBundle = new Bundle();
//        }
//        mBundle.clear();
//        return mBundle;
//    }
}
