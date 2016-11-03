package test.qbw.annotation.go.test;


import com.qbw.annotation.go.BundleValue;

/**
 * Created by Bond on 2016/11/03 15:32
 * you can contact me at qbaowei@qq.com
 */


public class TestGoActivity1 {
    @BundleValue
    long id;

    public static class TestGoActivity {
        @BundleValue
        String name;
    }
}
