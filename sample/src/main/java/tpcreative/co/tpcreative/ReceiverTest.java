package tpcreative.co.tpcreative;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import co.tpcreative.common.component.TPReceiver;
public class ReceiverTest extends TPReceiver{

    private static final String TAG = ReceiverTest.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        Log.d(TAG,"onReceive");
    }
}
