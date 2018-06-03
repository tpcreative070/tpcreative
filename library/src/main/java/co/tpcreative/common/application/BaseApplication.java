package co.tpcreative.common.application;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

public class BaseApplication extends MultiDexApplication {

    private static BaseApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        MultiDex.install(this);
    }

    public static synchronized BaseApplication getInstance() {
        return mInstance;
    }
}
