package user.com.openproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PoliceActivity extends AppCompatActivity {

    ImageView mNextBtn;
    TextView mServiceAgreementTv;
    TextView mDataPoliceTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police);
        setRes();
    }

    private void setRes() {
        mNextBtn = (ImageView)findViewById(R.id.PoliceNextBtn);
        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PoliceActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        mServiceAgreementTv = findViewById(R.id.PoliceServiceAgreementTv);
        mServiceAgreementTv.setMovementMethod(new ScrollingMovementMethod());
        mDataPoliceTv = findViewById(R.id.PoliceDataTv);
        mDataPoliceTv.setMovementMethod(new ScrollingMovementMethod());
    }
}
