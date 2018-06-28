package user.com.openproject;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class RegisterActivity extends AppCompatActivity {

    EditText mIdEdit;
    EditText mNameEdit;
    EditText mNickNameEdit;
    EditText mEmailEdit;
    EditText mPhoneEdit;
    EditText mAddressEdit;
    EditText mCompanyNumberEdit;
    Button mRegisterBtn;


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
        mIdEdit = (EditText)findViewById(R.id.RegisterIdEdit);
        mNameEdit = (EditText)findViewById(R.id.RegisterNameEdit);
        mNickNameEdit = (EditText)findViewById(R.id.RegisterNicknameEdit);
        mEmailEdit = (EditText)findViewById(R.id.RegisterEmailEdit);
        mPhoneEdit = (EditText) findViewById(R.id.RegisterPhoneEdit);
        mAddressEdit = (EditText)findViewById(R.id.RegisterAddressEdit);
        mCompanyNumberEdit = (EditText)findViewById(R.id.RegisterCompanyNumberEdit);
        mRegisterBtn = (Button)findViewById(R.id.RegisterRegisterBtn);
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    saveFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void loadFile() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(getFilesDir() + "buffer.txt"));
        String readStr = "";
        String str = null;

        if((str = br.readLine()) != null)
            mIdEdit.setText(str);
        if((str = br.readLine()) != null)
            mNameEdit.setText(str);
        if((str = br.readLine()) != null)
            mNickNameEdit.setText(str);
        if((str = br.readLine()) != null)
            mEmailEdit.setText(str);
        if((str = br.readLine()) != null)
            mPhoneEdit.setText(str);
        if((str = br.readLine()) != null)
            mAddressEdit.setText(str);
        if((str = br.readLine()) != null)
            mCompanyNumberEdit.setText(str);;

        br.close();
    }

    private void saveFile() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(getFilesDir() + "buffer.txt",false));
        bw.write(mIdEdit.getText().toString()); bw.newLine();
        bw.write(mNameEdit.getText().toString()); bw.newLine();
        bw.write(mNickNameEdit.getText().toString()); bw.newLine();
        bw.write(mEmailEdit.getText().toString()); bw.newLine();
        bw.write(mPhoneEdit.getText().toString()); bw.newLine();
        bw.write(mAddressEdit.getText().toString()); bw.newLine();
        bw.write(mCompanyNumberEdit.getText().toString()); bw.newLine();

        bw.close();
        //Toast.makeText(this,"저장완료",Toast.LENGTH_SHORT).show();

    }
}
