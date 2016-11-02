package test.qbw.annotation.go;

import java.io.Serializable;

/**
 * Created by Bond on 2016/10/28 11:53
 * you can contact me at qbaowei@qq.com
 */


public class ModelS implements Serializable {
    public int i = 101;
    public String s = "101";

    @Override
    public String toString() {
        return String.format("i=%d, s=%s", i, s);
    }
}
