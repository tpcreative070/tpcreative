package co.tpcreative.common.component;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.util.Log;
import co.tpcreative.common.application.BaseApplication;


public class TPApplication extends BaseApplication implements Application.ActivityLifecycleCallbacks{

    private static int resumed;
    private static int paused;
    private static int started;
    private static int stopped;
    private static String TAG = TPApplication.class.getSimpleName();
    private static TPApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        mInstance = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        ++resumed;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        ++paused;
        Log.d(TAG, "application is in foreground: " + (resumed > paused));
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {

    }

    @Override
    public void onActivityStarted(Activity activity) {
        ++started;
    }

    @Override
    public void onActivityStopped(Activity activity) {
        ++stopped;
        Log.d(TAG, "application is visible: " + (started > stopped));
    }

    // If you want a static function you can use to check if your application is
    // foreground/background, you can use the following:
    //
    // Replace the four variables above with these four
    // And these two public static functions

    public boolean isApplicationVisible() {
        return started > stopped;
    }

    public boolean isApplicationInForeground() {
        return resumed > paused;
    }

    private static TPApplication sInstance;


    public TPApplication() {
        sInstance = this;
    }

    public static synchronized TPApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(TPReceiver.ConnectivityReceiverListener listener) {
        TPReceiver.connectivityReceiverListener = listener;
    }

    /*
    *
    *
    *     IntentFilter intentFilter = new IntentFilter();
          intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
          androidReceiver = new AndroidReceiver();
          registerReceiver(androidReceiver, intentFilter);
          AndroidApplication.getInstance().setConnectivityListener(this);

    *
    *
    *
    * */


}

