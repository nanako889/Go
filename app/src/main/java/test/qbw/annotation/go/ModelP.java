package test.qbw.annotation.go;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bond on 2016/10/28 15:38
 * you can contact me at qbaowei@qq.com
 */


public class ModelP implements Parcelable {
    private float f = 3.1415926f;
    private char c = '!';

    private List<Test> mTests;
    private ModelS mModelS = new ModelS();


    @Override
    public String toString() {
        String str = String.format("f=%f, c=%c,(%s)", f, c, mModelS.toString());
        if (null != mTests && !mTests.isEmpty()) {
            for (Test t : mTests) {
                str = str + "," + t.toString();
            }
        }
        return str;
    }

    public ModelP() {
        mTests = new ArrayList<Test>();
        mTests.add(new Test());
        mTests.add(new Test());
    }

    public static class Test implements Parcelable {
        private int i;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.i);
        }

        public Test() {
            i = 1;
        }

        @Override
        public String toString() {
            return String.format("i=%d", i);
        }

        protected Test(Parcel in) {
            this.i = in.readInt();
        }

        public static final Creator<Test> CREATOR = new Creator<Test>() {
            @Override
            public Test createFromParcel(Parcel source) {
                return new Test(source);
            }

            @Override
            public Test[] newArray(int size) {
                return new Test[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.f);
        dest.writeInt(this.c);
        dest.writeTypedList(this.mTests);
        dest.writeSerializable(this.mModelS);
    }

    protected ModelP(Parcel in) {
        this.f = in.readFloat();
        this.c = (char) in.readInt();
        this.mTests = in.createTypedArrayList(Test.CREATOR);
        this.mModelS = (ModelS) in.readSerializable();
    }

    public static final Creator<ModelP> CREATOR = new Creator<ModelP>() {
        @Override
        public ModelP createFromParcel(Parcel source) {
            return new ModelP(source);
        }

        @Override
        public ModelP[] newArray(int size) {
            return new ModelP[size];
        }
    };
}
