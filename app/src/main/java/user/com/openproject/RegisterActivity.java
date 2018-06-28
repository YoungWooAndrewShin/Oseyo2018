package user.com.openproject;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
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

    EditText mNickNameEdit;
    EditText mEmailEdit;
    EditText mPasswordEdit;
    EditText mPasswordConfirmEdit;

    RadioButton mMaleRadio;
    RadioButton mFemaleRadio;

    ImageView mRegisterBtn;


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
