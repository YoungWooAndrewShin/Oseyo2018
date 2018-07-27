package user.com.openproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;

public class AuthorizationActivity extends AppCompatActivity {

    EditText mPhoneEdit;
    ImageView mAuthBtn;

    ArrayAdapter<CharSequence> mContryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        setRes();
    }

    private void setRes() {

        mPhoneEdit = (EditText)findViewById(R.id.AuthorizationPhoneEdit);
        mAuthBtn = (ImageView)findViewById(R.id.AuthorizationAuthBtn);

        mContryAdapter = ArrayAdapter.createFromResource(this, R.array.contry_code, R.layout.support_simple_spinner_dropdown_item);
        mAuthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AuthorizationActivity.this, CompanyAuthorizationActivity.class);
                startActivity(i);
            }
        });
    }

}
