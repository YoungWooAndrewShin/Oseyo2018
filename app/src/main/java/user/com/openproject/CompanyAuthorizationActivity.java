package user.com.openproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class CompanyAuthorizationActivity extends AppCompatActivity {

    EditText mCompanyNumberEdit;
    Button mSearchBtn;
    ImageView mNextBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_authorization);
    }

    private void setRes() {
        mCompanyNumberEdit = findViewById(R.id.CompanyAuthorizationCompanyNumberEdit);
        mSearchBtn = findViewById(R.id.CompanyAuthorizationSearchBtn);
        mNextBtn = findViewById(R.id.CompanyAuthorizationNextBtn);
        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent i = new Intent(CompanyAuthorizationActivity.this, )
                //startActivity(i);
            }
        });
    }
}
