package com.qbw.annotation.go.core;

import android.os.Bundle;

/**
 * @author qbw
 * @createtime 2016/08/29 17:43
 */


public interface IGo {
    void go();

    void go(int requestCode);

    void go(int requestCode, Bundle bundle);

    void go(Bundle bundle);
}
