package user.com.openproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class PoliceActivity extends AppCompatActivity implements View.OnClickListener{

    final static int AGREE = 0;
    final static int DISAGREE = 1;

    ImageView mNextBtn;
    TextView mServiceAgreementTv;
    TextView mDataPoliceTv;
    TextView mLocationTv;
    TextView mThirdPartyTv;

    TextView mViewServiceAgreementTv;
    TextView mViewDataAgreementTv;
    TextView mViewLocationAgreementTv;
    TextView mViewThirdPartyAgreementTv;

    CheckBox[] mServiceCheck;
    CheckBox[] mDataCheck;
    CheckBox[] mLocationCheck;
    CheckBox[] mThirdPartyCheck;
    CheckBox[] mAllCheck;


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
        mDataPoliceTv = findViewById(R.id.PoliceDataTv);
        mLocationTv = findViewById(R.id.PoliceLocationTv);
        mThirdPartyTv = findViewById(R.id.PoliceThirdPartyTv);

        mViewServiceAgreementTv = findViewById(R.id.PoliceViewServiceAgreementTv);
        mViewDataAgreementTv = findViewById(R.id.PoliceViewDataAgreementTv);
        mViewLocationAgreementTv = findViewById(R.id.PoliceViewLocationAgreementTv);
        mViewThirdPartyAgreementTv = findViewById(R.id.PoliceViewThirdPartyAgreementTv);

        mViewServiceAgreementTv.setOnClickListener(this);
        mViewDataAgreementTv.setOnClickListener(this);
        mViewLocationAgreementTv.setOnClickListener(this);
        mViewThirdPartyAgreementTv.setOnClickListener(this);

        mServiceCheck = new CheckBox[2];
        mDataCheck = new CheckBox[2];
        mLocationCheck = new CheckBox[2];
        mThirdPartyCheck = new CheckBox[2];
        mAllCheck = new CheckBox[2];

        mServiceCheck[AGREE] = findViewById(R.id.PoliceServiceAgreeCheck);
        mDataCheck[AGREE] = findViewById(R.id.PoliceDataAgreeCheck);
        mLocationCheck[AGREE] = findViewById(R.id.PoliceLocationAgreeCheck);
        mThirdPartyCheck[AGREE] = findViewById(R.id.PoliceThirdPartyAgreeCheck);
        mAllCheck[AGREE] = findViewById(R.id.PoliceAllAgreeCheck);

        mServiceCheck[DISAGREE] = findViewById(R.id.PoliceServiceDisagreeCheck);
        mDataCheck[DISAGREE] = findViewById(R.id.PoliceDataDisagreeCheck);
        mLocationCheck[DISAGREE] = findViewById(R.id.PoliceLocationDisagreeCheck);
        mThirdPartyCheck[DISAGREE] = findViewById(R.id.PoliceThirdPartyDisagreeCheck);
        mAllCheck[DISAGREE] = findViewById(R.id.PoliceAllDisagreeCheck);

        mServiceCheck[AGREE].setOnClickListener(this);
        mServiceCheck[DISAGREE].setOnClickListener(this);

        mDataCheck[AGREE].setOnClickListener(this);
        mDataCheck[DISAGREE].setOnClickListener(this);

        mLocationCheck[AGREE].setOnClickListener(this);
        mLocationCheck[DISAGREE].setOnClickListener(this);

        mThirdPartyCheck[AGREE].setOnClickListener(this);
        mThirdPartyCheck[DISAGREE].setOnClickListener(this);

        mAllCheck[AGREE].setOnClickListener(this);
        mAllCheck[DISAGREE].setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        int visible = View.VISIBLE;
        switch(id) {
            case R.id.PoliceViewServiceAgreementTv:
                visible = mServiceAgreementTv.getVisibility();
                if(visible == View.VISIBLE) {
                    mServiceAgreementTv.setVisibility(View.GONE);
                } else {
                    mServiceAgreementTv.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.PoliceViewDataAgreementTv:
                visible = mDataPoliceTv.getVisibility();
                if(visible == View.VISIBLE) {
                    mDataPoliceTv.setVisibility(View.GONE);
                } else {
                    mDataPoliceTv.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.PoliceViewLocationAgreementTv:
                visible = mLocationTv.getVisibility();
                if(visible == View.VISIBLE) {
                    mLocationTv.setVisibility(View.GONE);
                } else {
                    mLocationTv.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.PoliceViewThirdPartyAgreementTv:
                visible = mThirdPartyTv.getVisibility();
                if (visible == View.VISIBLE) {
                    mThirdPartyTv.setVisibility(View.GONE);
                } else {
                    mThirdPartyTv.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.PoliceServiceAgreeCheck:
                changeCheckBox(mServiceCheck, AGREE);
                break;
            case R.id.PoliceServiceDisagreeCheck:
                changeCheckBox(mServiceCheck, DISAGREE);
                break;
            case R.id.PoliceDataAgreeCheck:
                changeCheckBox(mDataCheck, AGREE);
                break;
            case R.id.PoliceDataDisagreeCheck:
                changeCheckBox(mDataCheck, DISAGREE);
                break;
            case R.id.PoliceLocationAgreeCheck:
                changeCheckBox(mLocationCheck, AGREE);
                break;
            case R.id.PoliceLocationDisagreeCheck:
                changeCheckBox(mLocationCheck, DISAGREE);
                break;
            case R.id.PoliceThirdPartyAgreeCheck:
                changeCheckBox(mThirdPartyCheck, AGREE);
                break;
            case R.id.PoliceThirdPartyDisagreeCheck:
                changeCheckBox(mThirdPartyCheck, DISAGREE);
                break;
            case R.id.PoliceAllAgreeCheck:
                changeCheckBox(mServiceCheck, AGREE);
                changeCheckBox(mDataCheck, AGREE);
                changeCheckBox(mLocationCheck, AGREE);
                changeCheckBox(mThirdPartyCheck, AGREE);
                changeCheckBox(mAllCheck, AGREE);
                break;
            case R.id.PoliceAllDisagreeCheck:
                changeCheckBox(mServiceCheck, DISAGREE);
                changeCheckBox(mDataCheck, DISAGREE);
                changeCheckBox(mLocationCheck, DISAGREE);
                changeCheckBox(mThirdPartyCheck, DISAGREE);
                changeCheckBox(mAllCheck, DISAGREE);
                break;
            }
        }

        private void changeCheckBox(CheckBox[] checkBoxes, int agreement) {

            if(checkBoxes == null)
                return;

            if(agreement == AGREE) {
                checkBoxes[AGREE].setChecked(true);
                checkBoxes[DISAGREE].setChecked(false);
            } else {
                checkBoxes[AGREE].setChecked(false);
                checkBoxes[DISAGREE].setChecked(true);
            }

        }
    }
