package tpcreative.co.tpcreative;
import android.os.Bundle;
import android.widget.TextView;
import butterknife.BindView;
import co.tpcreative.common.activity.BaseActivity;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tvHello)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView.setText("Welcome To TPCreative");
    }

}
