package tpcreative.co.tpcreative;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import butterknife.BindView;
import co.tpcreative.common.activity.BaseActivity;

public class MainActivity extends BaseActivity implements AndroidReceiver.ConnectivityReceiverListener{

    @BindView(R.id.tvHello)
    TextView textView;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView.setText("Welcome To TPCreative");
        Application.getInstance().setConnectivityListener(this);

        if (AndroidReceiver.isConnected()){
            textView.setText("Connected network");
            textView.setTextColor(getResources().getColor(R.color.contact_background_button_confirm_send));
        }
        else{
            textView.setText("Disconnected network");
            textView.setTextColor(getResources().getColor(R.color.option_photo_delete));

        }

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        Log.d(TAG,"Changed network :" +isConnected);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isConnected){
                    textView.setText("Connected network");
                    textView.setTextColor(getResources().getColor(R.color.contact_background_button_confirm_send));
                }
                else{
                    textView.setText("Disconnected network");
                    textView.setTextColor(getResources().getColor(R.color.option_photo_delete));

                }
            }
        });

    }
}
