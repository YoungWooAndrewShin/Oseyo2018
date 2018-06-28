package user.com.openproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

public class AuthorizationActivity extends AppCompatActivity {

    Spinner mContrySpinner;
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

        mContrySpinner = (Spinner)findViewById(R.id.AuthorizationContrySpinner);
        mPhoneEdit = (EditText)findViewById(R.id.AuthorizationPhoneEdit);
        mAuthBtn = (ImageView)findViewById(R.id.AuthorizationAuthBtn);

        mContryAdapter = ArrayAdapter.createFromResource(this, R.array.contry_code, R.layout.support_simple_spinner_dropdown_item);
        mContrySpinner.setAdapter(mContryAdapter);
        mContrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                Toast.makeText(AuthorizationActivity.this, mContryAdapter.getItem(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mAuthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AuthorizationActivity.this, "휴대폰 본인인증", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
