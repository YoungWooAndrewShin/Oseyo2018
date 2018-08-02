package user.com.openproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class StoreInformationActivity extends AppCompatActivity implements View.OnFocusChangeListener, View.OnClickListener{

    EditText mName;
    EditText mMainCategory;
    EditText mMiddleCategory;
    EditText mSmallCategory;
    EditText mAddress;
    EditText mAddressDetail;
    Button mSearchBtn;

    enum CATEGORY_KIND { MAIN, MIDDLE, SMALL };
    CATEGORY_KIND mCurrentCategory;

    final String MAIN = "main";
    final String MIDDLE = "middle";
    final String SMALL = "small";
    final String CATEGORY = "category";
    final String POSITION = "position";
    final String ADDRESS = "address";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_information);
        setRes();
    }

    private void setRes() {

        mName = findViewById(R.id.StoreInformationNameEdit);
        mMainCategory = findViewById(R.id.StoreInformationMainCategoryEdit);
        mMainCategory.setInputType(InputType.TYPE_NULL);
        mMainCategory.setOnFocusChangeListener(this);
        mMainCategory.setOnClickListener(this);

        mMiddleCategory = findViewById(R.id.StoreInformationMiddleCategoryEdit);
        mMiddleCategory.setInputType(InputType.TYPE_NULL);
        mMiddleCategory.setOnFocusChangeListener(this);
        mMiddleCategory.setOnClickListener(this);

        mSmallCategory = findViewById(R.id.StoreInformationSmallCategoryEdit);
        mSmallCategory.setInputType(InputType.TYPE_NULL);
        mSmallCategory.setOnFocusChangeListener(this);
        mSmallCategory.setOnClickListener(this);

        mAddress = findViewById(R.id.StoreInformationAddress);
        mSearchBtn = findViewById(R.id.StoreInformationAddressSearch);
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(StoreInformationActivity.this, WebViewActivity.class);
                i.putExtra("address", mAddress.getText().toString());
                startActivityForResult(i, GlobalVariable.REGISTER_ADDRESS_REQUEST_CODE);
            }
        });
        //mAddress.setInputType(InputType.TYPE_NULL);
        //mAddress.setOnFocusChangeListener(this);
        //mAddress.setOnClickListener(this);

        mAddressDetail = findViewById(R.id.StoreInformationAddressDetail);
    }

    @Override
    public void onFocusChange(View view, boolean b) {

        int id = view.getId();
        if(id != R.id.StoreInformationMainCategoryEdit
                && id != R.id.StoreInformationMiddleCategoryEdit
                && id != R.id.StoreInformationSmallCategoryEdit
                && id != R.id.StoreInformationAddress)
            return;

        if(!b)
            return;

        Intent i;

        switch (id) {
            case R.id.StoreInformationMainCategoryEdit:
            case R.id.StoreInformationMiddleCategoryEdit:
            case R.id.StoreInformationSmallCategoryEdit:
                i = new Intent(StoreInformationActivity.this, StoreCategoryActivity.class);
                break;
            case R.id.StoreInformationAddress:
//                i = new Intent(StoreInformationActivity.this, InputAddressActivity.class);
                i = new Intent(StoreInformationActivity.this, WebViewActivity.class);
                break;
            default:
                i = new Intent();
        }

        switch (id) {
            case R.id.StoreInformationMainCategoryEdit:
                i.putExtra(CATEGORY, MAIN);
                mCurrentCategory = CATEGORY_KIND.MAIN;
                break;
            case R.id.StoreInformationMiddleCategoryEdit:
                i.putExtra(CATEGORY,MIDDLE);
                mCurrentCategory = CATEGORY_KIND.MIDDLE;
                break;
            case R.id.StoreInformationSmallCategoryEdit:
                i.putExtra(CATEGORY,SMALL);
                mCurrentCategory = CATEGORY_KIND.SMALL;
                break;
            case R.id.StoreInformationAddress:

                break;
        }

        switch (id) {
            case R.id.StoreInformationMainCategoryEdit:
            case R.id.StoreInformationMiddleCategoryEdit:
            case R.id.StoreInformationSmallCategoryEdit:
                startActivityForResult(i, GlobalVariable.REGISTER_STORE_CATEGORY_REQUEST_CODE);
                break;
            case R.id.StoreInformationAddress:
                startActivityForResult(i, GlobalVariable.REGISTER_ADDRESS_REQUEST_CODE);
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GlobalVariable.REGISTER_STORE_CATEGORY_REQUEST_CODE) {
            if(resultCode == GlobalVariable.REGISTER_STORE_CATEGORY_RESULT_CODE) {
                int position = data.getIntExtra(POSITION, 0);

                switch (mCurrentCategory) {
                    case MAIN:
                        mMainCategory.setText(getResources().getStringArray(R.array.store_main_category)[position]);
                        break;
                    case MIDDLE:
                        mMiddleCategory.setText(getResources().getStringArray(R.array.store_middle_category)[position]);
                        break;
                    case SMALL:
                        mSmallCategory.setText(getResources().getStringArray(R.array.store_small_category)[position]);
                        break;
                }
            }
        }

        else if(requestCode == GlobalVariable.REGISTER_ADDRESS_REQUEST_CODE) {
            if(resultCode == GlobalVariable.REGISTER_ADDRESS_RESULT_CODE) {
                String address = data.getExtras().getString(ADDRESS);
                mAddress.setText(address);
            }
        }


    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        if(id != R.id.StoreInformationMainCategoryEdit
                && id != R.id.StoreInformationMiddleCategoryEdit
                && id != R.id.StoreInformationSmallCategoryEdit
                && id != R.id.StoreInformationAddress)
            return;

        Intent i = new Intent(StoreInformationActivity.this, StoreCategoryActivity.class);
        switch (id) {
            case R.id.StoreInformationMainCategoryEdit:
                i.putExtra(CATEGORY,MAIN);
                mCurrentCategory = CATEGORY_KIND.MAIN;
                break;
            case R.id.StoreInformationMiddleCategoryEdit:
                i.putExtra(CATEGORY,MIDDLE);
                mCurrentCategory = CATEGORY_KIND.MIDDLE;
                break;
            case R.id.StoreInformationSmallCategoryEdit:
                i.putExtra(CATEGORY,SMALL);
                mCurrentCategory = CATEGORY_KIND.SMALL;
                break;
        }

        startActivityForResult(i, GlobalVariable.REGISTER_STORE_CATEGORY_REQUEST_CODE);
    }
}
