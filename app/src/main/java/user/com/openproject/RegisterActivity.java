package user.com.openproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class RegisterActivity extends AppCompatActivity {

    EditText mNickNameEdit;
    EditText mEmailEdit;
    EditText mPasswordEdit;
    EditText mPasswordConfirmEdit;

    RadioButton mMaleRadio;
    RadioButton mFemaleRadio;

    ImageView mRegisterBtn;
    ImageView mRegisterPreBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setRes();
        try {
            loadFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setRes() {
        mNickNameEdit = (EditText)findViewById(R.id.RegisterNicknameEdit);
        mEmailEdit = (EditText)findViewById(R.id.RegisterEmailEdit);
        mPasswordEdit = (EditText)findViewById(R.id.RegisterPasswordEdit);
        mPasswordConfirmEdit = (EditText)findViewById(R.id.RegisterPasswordConfirmEdit);
        mRegisterBtn = (ImageView) findViewById(R.id.RegisterRegisterBtn);
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    saveFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Intent i = new Intent(RegisterActivity.this, AuthorizationActivity.class);
                startActivity(i);
            }
        });

        mRegisterPreBtn = findViewById(R.id.RegisterRegisterPreBtn);
        mRegisterPreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    saveFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Intent i = new Intent(RegisterActivity.this, StoreInformationPreActivity.class);
                startActivity(i);
            }
        });
    }

    private void loadFile() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(getFilesDir() + "buffer.txt"));
        String readStr = "";
        String str = null;

        if((str = br.readLine()) != null)
            mNickNameEdit.setText(str);
        if((str = br.readLine()) != null)
            mEmailEdit.setText(str);

        br.close();
    }

    private void saveFile() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + "buffer.txt",false));
        bw.write(mNickNameEdit.getText().toString()); bw.newLine();
        bw.write(mEmailEdit.getText().toString()); bw.newLine();

        bw.close();
        //Toast.makeText(this,"저장완료",Toast.LENGTH_SHORT).show();

    }
}
