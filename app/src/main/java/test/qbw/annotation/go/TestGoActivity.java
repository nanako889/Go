package test.qbw.annotation.go;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableField;
import android.os.Bundle;
import android.os.Parcelable;

import com.qbw.annotation.Go;
import com.qbw.annotation.go.IntentValue;
import com.qbw.log.XLog;

import java.io.Serializable;
import java.util.Arrays;

import test.qbw.annotation.go.databinding.ActivityTestBinding;

/**
 * @author QBW
 * @createtime 2016/09/01 10:32
 * @company 9zhitx.com
 * @description
 */


public class TestGoActivity extends Activity {

    /**
     * IntentValue限制条件如下
     * 1.修饰符不能是‘Private’
     * 2.如果变量是’mX‘类型，生成函数的时候会去掉’m‘并将第一个字符改为小写，比如‘mByte’->‘byte’,然而byte是关键字不能使用，所以你写变量的时候要注意一下
     * 3.没有列出来的类型不支持
     * 4.有时候需要build一下才会生成对应文件
     * 5.会自动生成与Activity名字对应的文件名
     * 6.不要在类名相同的类中使用，这样Go.java里面会生成函数名一样的函数
     */


    @IntentValue
    byte mTb;
    @IntentValue
    Byte tb1;
    @IntentValue
    char mTc;
    @IntentValue
    Character mTc1;
    @IntentValue
    short mTs;
    @IntentValue
    Short mTs1;
    @IntentValue
    int mTi;
    @IntentValue
    Integer mTi1;
    @IntentValue
    long mTl;
    @IntentValue
    Long tl1;
    @IntentValue
    float mTf;
    @IntentValue
    Float mTf1;
    @IntentValue
    double mTd;
    @IntentValue
    Double mTd1;
    @IntentValue
    String mTstr;
    @IntentValue
    boolean mTbn;
    @IntentValue
    Boolean mTbn1;

    @IntentValue
    String[] mTsarr;
    @IntentValue
    int[] mTiarr;
    @IntentValue
    long[] mTlarr;
    @IntentValue
    float[] mTfarr;
    @IntentValue
    double[] mTdarr;


    @IntentValue
    Serializable mTsl;
    @IntentValue
    Parcelable mTpl;
    @IntentValue
    Parcelable[] mTplarr;

    private ObservableField<String> mInfo = new ObservableField<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityTestBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_test);
        binding.setInfo(mInfo);
        Go.TestGoActivity().unpack(this, getIntent().getExtras());
        mInfo.set(strInfo());
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        XLog.d("in");
        Go.TestGoActivity().unpack(this, savedInstanceState);
        XLog.d(strInfo());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        XLog.d("in");
        Go.TestGoActivity().pack(this, outState);
    }

    private String strInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("mTb=%d, tb1=%d\n", mTb, tb1));
        stringBuilder.append(String.format("mTc=%c, mTc1=%c\n", mTc, mTc1));
        stringBuilder.append(String.format("mTs=%d, mTs1=%d\n", mTs, mTs1));
        stringBuilder.append(String.format("mTl=%d, tl1=%d\n", mTl, tl1));
        stringBuilder.append(String.format("mTi=%d, mTi1=%d\n", mTi, mTi1));
        stringBuilder.append(String.format("mTf=%f, mTf1=%f\n", mTf, mTf1));
        stringBuilder.append(String.format("mTd=%f, mTd1=%f\n", mTd, mTd1));
        stringBuilder.append(String.format("mTstr=%s\n", mTstr));
        stringBuilder.append(String.format("mTbn=%b, mTbn1=%b\n", mTbn, mTbn1));
        stringBuilder.append(String.format("mTsarr=%s\n", Arrays.toString(mTsarr)));
        stringBuilder.append(String.format("mTiarr=%s\n", Arrays.toString(mTiarr)));
        stringBuilder.append(String.format("mTlarr=%s\n", Arrays.toString(mTlarr)));
        stringBuilder.append(String.format("mTfarr=%s\n", Arrays.toString(mTfarr)));
        stringBuilder.append(String.format("mTdarr=%s\n", Arrays.toString(mTdarr)));
        stringBuilder.append(String.format("mTsl=%s\n", null == mTsl ? "null" : mTsl.toString()));
        stringBuilder.append(String.format("mTpl=%s\n", null == mTpl ? "null" : mTpl.toString()));
        StringBuilder sb = new StringBuilder();
        if (null != mTplarr) {
            for (Parcelable parcelable : mTplarr) {
                sb.append(String.format("{%s}", parcelable.toString()));
            }
        } else {
            sb.append("null");
        }
        stringBuilder.append(String.format("mTplarr=%s", sb.toString()));
        return stringBuilder.toString();
    }
}
