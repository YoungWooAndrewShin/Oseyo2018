package user.com.openproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class StoreInformationPreActivity extends AppCompatActivity implements View.OnFocusChangeListener, View.OnClickListener {

    enum CATEGORY_KIND { MAIN1, MAIN2, MAIN3, MIDDLE1, MIDDLE2, MIDDLE3, SMALL1, SMALL2, SMALL3 }
    CATEGORY_KIND mCurrentCategory;

    final String MAIN = "main";
    final String MIDDLE = "middle";
    final String SMALL = "small";
    final String CATEGORY = "category";
    final String POSITION = "position";
    final String ADDRESS = "address";

    final int CATEGORY_MAX_COUNT = 2;
    int mCurrentCategoryCount = 0;
    ImageView mAddbtn;
    LinearLayout[] mCategoryLayout = new LinearLayout[3];
    EditText[] mCategoryMain = new EditText[3];
    EditText[] mCategoryMiddle = new EditText[3];
    EditText[] mCategorySmall = new EditText[3];
    EditText mAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_information_pre);
        setRes();
    }

    private void setRes() {
        mCategoryLayout[0] = findViewById(R.id.StoreInformationPreCategoryLayout1);
        mCategoryLayout[1] = findViewById(R.id.StoreInformationPreCategoryLayout2);
        mCategoryLayout[2] = findViewById(R.id.StoreInformationPreCategoryLayout3);

        mCategoryMain[0] = findViewById(R.id.StoreInformationPreMainCategoryEdit1);
        mCategoryMain[1] = findViewById(R.id.StoreInformationPreMainCategoryEdit2);
        mCategoryMain[2] = findViewById(R.id.StoreInformationPreMainCategoryEdit3);

        mCategoryMiddle[0] = findViewById(R.id.StoreInformationPreMiddleCategoryEdit1);
        mCategoryMiddle[1] = findViewById(R.id.StoreInformationPreMiddleCategoryEdit2);
        mCategoryMiddle[2] = findViewById(R.id.StoreInformationPreMiddleCategoryEdit3);

        mCategorySmall[0] = findViewById(R.id.StoreInformationPreSmallCategoryEdit1);
        mCategorySmall[1] = findViewById(R.id.StoreInformationPreSmallCategoryEdit2);
        mCategorySmall[2] = findViewById(R.id.StoreInformationPreSmallCategoryEdit3);

        for(int i = 0; i < CATEGORY_MAX_COUNT + 1; i++) {
            mCategoryMain[i].setInputType(InputType.TYPE_NULL);
            mCategoryMain[i].setOnFocusChangeListener(this);
            mCategoryMain[i].setOnClickListener(this);

            mCategoryMiddle[i].setInputType(InputType.TYPE_NULL);
            mCategoryMiddle[i].setOnFocusChangeListener(this);
            mCategoryMiddle[i].setOnClickListener(this);

            mCategorySmall[i].setInputType(InputType.TYPE_NULL);
            mCategorySmall[i].setOnFocusChangeListener(this);
            mCategorySmall[i].setOnClickListener(this);
        }

        mAddbtn = findViewById(R.id.StoreInformationPreCategoryAddBtn);
        mAddbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mCurrentCategoryCount < CATEGORY_MAX_COUNT) {
                    mCurrentCategoryCount++;
                    mCategoryLayout[mCurrentCategoryCount].setVisibility(View.VISIBLE);
                }
            }
        });

        mAddress = findViewById(R.id.StoreInformationPreAddress);
        mAddress.setInputType(InputType.TYPE_NULL);
        mAddress.setOnFocusChangeListener(this);
        mAddress.setOnClickListener(this);
        mAddress.setFocusable(false);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GlobalVariable.REGISTER_STORE_CATEGORY_REQUEST_CODE) {
            if(resultCode == GlobalVariable.REGISTER_STORE_CATEGORY_RESULT_CODE) {
                int position = data.getIntExtra(POSITION, 0);

                Intent i;
                switch (mCurrentCategory) {
                    case MAIN1:
                        mCategoryMain[0].setText(getResources().getStringArray(R.array.store_main_category)[position]);
                        i = new Intent(StoreInformationPreActivity.this, StoreCategoryActivity.class);
                        i.putExtra(CATEGORY,MIDDLE);
                        mCurrentCategory = CATEGORY_KIND.MIDDLE1;
                        startActivityForResult(i, GlobalVariable.REGISTER_STORE_CATEGORY_REQUEST_CODE);
                        break;
                    case MAIN2:
                        mCategoryMain[1].setText(getResources().getStringArray(R.array.store_main_category)[position]);
                        i = new Intent(StoreInformationPreActivity.this, StoreCategoryActivity.class);
                        i.putExtra(CATEGORY,MIDDLE);
                        mCurrentCategory = CATEGORY_KIND.MIDDLE2;
                        startActivityForResult(i, GlobalVariable.REGISTER_STORE_CATEGORY_REQUEST_CODE);
                        break;
                    case MAIN3:
                        mCategoryMain[2].setText(getResources().getStringArray(R.array.store_main_category)[position]);
                        i = new Intent(StoreInformationPreActivity.this, StoreCategoryActivity.class);
                        i.putExtra(CATEGORY,MIDDLE);
                        mCurrentCategory = CATEGORY_KIND.MIDDLE3;
                        startActivityForResult(i, GlobalVariable.REGISTER_STORE_CATEGORY_REQUEST_CODE);
                        break;
                    case MIDDLE1:
                        mCategoryMiddle[0].setText(getResources().getStringArray(R.array.store_middle_category)[position]);
                        i = new Intent(StoreInformationPreActivity.this, StoreCategoryActivity.class);
                        i.putExtra(CATEGORY,SMALL);
                        mCurrentCategory = CATEGORY_KIND.SMALL1;
                        startActivityForResult(i, GlobalVariable.REGISTER_STORE_CATEGORY_REQUEST_CODE);
                        break;
                    case MIDDLE2:
                        mCategoryMiddle[1].setText(getResources().getStringArray(R.array.store_middle_category)[position]);
                        i = new Intent(StoreInformationPreActivity.this, StoreCategoryActivity.class);
                        i.putExtra(CATEGORY,SMALL);
                        mCurrentCategory = CATEGORY_KIND.SMALL2;
                        startActivityForResult(i, GlobalVariable.REGISTER_STORE_CATEGORY_REQUEST_CODE);
                        break;
                    case MIDDLE3:
                        mCategoryMiddle[2].setText(getResources().getStringArray(R.array.store_middle_category)[position]);
                        i = new Intent(StoreInformationPreActivity.this, StoreCategoryActivity.class);
                        i.putExtra(CATEGORY,SMALL);
                        mCurrentCategory = CATEGORY_KIND.SMALL3;
                        startActivityForResult(i, GlobalVariable.REGISTER_STORE_CATEGORY_REQUEST_CODE);
                        break;
                    case SMALL1:
                        mCategorySmall[0].setText(getResources().getStringArray(R.array.store_small_category)[position]);
                        break;
                    case SMALL2:
                        mCategorySmall[1].setText(getResources().getStringArray(R.array.store_small_category)[position]);
                        break;
                    case SMALL3:
                        mCategorySmall[2].setText(getResources().getStringArray(R.array.store_small_category)[position]);
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
        boolean ret1 = isCategorySelected(id, R.id.StoreInformationPreMainCategoryEdit1,
                R.id.StoreInformationPreMiddleCategoryEdit1, R.id.StoreInformationPreSmallCategoryEdit1);
        boolean ret2 = isCategorySelected(id, R.id.StoreInformationPreMainCategoryEdit2,
                R.id.StoreInformationPreMiddleCategoryEdit2, R.id.StoreInformationPreSmallCategoryEdit2);
        boolean ret3 = isCategorySelected(id, R.id.StoreInformationPreMainCategoryEdit3,
                R.id.StoreInformationPreMiddleCategoryEdit3, R.id.StoreInformationPreSmallCategoryEdit3);
        boolean ret4 = isViewSelected(id, R.id.StoreInformationPreAddress);

        if(!ret1 && !ret2 && !ret3 && !ret4)
            return;

        Intent i;

        if(isViewSelected(id, R.id.StoreInformationPreAddress)) {
            i = new Intent(StoreInformationPreActivity.this, WebViewActivity.class);
        }
        else {
            i = new Intent(StoreInformationPreActivity.this, StoreCategoryActivity.class);
        }

        switch(id) {
            case R.id.StoreInformationPreMainCategoryEdit1:
                mCurrentCategory = CATEGORY_KIND.MAIN1;
                i.putExtra(CATEGORY, MAIN);
                break;
            case R.id.StoreInformationPreMainCategoryEdit2:
                mCurrentCategory = CATEGORY_KIND.MAIN2;
                i.putExtra(CATEGORY, MAIN);
                break;
            case R.id.StoreInformationPreMainCategoryEdit3:
                mCurrentCategory = CATEGORY_KIND.MAIN3;
                i.putExtra(CATEGORY, MAIN);
                break;
            case R.id.StoreInformationPreMiddleCategoryEdit1:
                mCurrentCategory = CATEGORY_KIND.MIDDLE1;
                i.putExtra(CATEGORY, MIDDLE);
                break;
            case R.id.StoreInformationPreMiddleCategoryEdit2:
                mCurrentCategory = CATEGORY_KIND.MIDDLE2;
                i.putExtra(CATEGORY, MIDDLE);
                break;
            case R.id.StoreInformationPreMiddleCategoryEdit3:
                mCurrentCategory = CATEGORY_KIND.MIDDLE3;
                i.putExtra(CATEGORY, MIDDLE);
                break;
            case R.id.StoreInformationPreSmallCategoryEdit1:
                mCurrentCategory = CATEGORY_KIND.SMALL1;
                i.putExtra(CATEGORY, SMALL);
                break;
            case R.id.StoreInformationPreSmallCategoryEdit2:
                mCurrentCategory = CATEGORY_KIND.SMALL2;
                i.putExtra(CATEGORY, SMALL);
                break;
            case R.id.StoreInformationPreSmallCategoryEdit3:
                mCurrentCategory = CATEGORY_KIND.SMALL3;
                i.putExtra(CATEGORY, SMALL);
                break;
            case R.id.StoreInformationPreAddress:
                break;
        }

        if(isViewSelected(id, R.id.StoreInformationPreAddress)) {
            startActivityForResult(i, GlobalVariable.REGISTER_ADDRESS_REQUEST_CODE);
        }
        else {
            startActivityForResult(i, GlobalVariable.REGISTER_STORE_CATEGORY_REQUEST_CODE);
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {

        int id = view.getId();
        boolean ret1 = isCategorySelected(id, R.id.StoreInformationPreMainCategoryEdit1,
                R.id.StoreInformationPreMiddleCategoryEdit1, R.id.StoreInformationPreSmallCategoryEdit1);
        boolean ret2 = isCategorySelected(id, R.id.StoreInformationPreMainCategoryEdit2,
                R.id.StoreInformationPreMiddleCategoryEdit2, R.id.StoreInformationPreSmallCategoryEdit2);
        boolean ret3 = isCategorySelected(id, R.id.StoreInformationPreMainCategoryEdit3,
                R.id.StoreInformationPreMiddleCategoryEdit3, R.id.StoreInformationPreSmallCategoryEdit3);
        boolean ret4 = isViewSelected(id, R.id.StoreInformationPreAddress);

        if(!ret1 && !ret2 && !ret3 && !ret4)
            return;

        if(!b)
            return;

        Intent i;

        if(isViewSelected(id, R.id.StoreInformationPreAddress)) {
            i = new Intent(StoreInformationPreActivity.this, WebViewActivity.class);
        }
        else {
            i = new Intent(StoreInformationPreActivity.this, StoreCategoryActivity.class);
        }

        switch(id) {
            case R.id.StoreInformationPreMainCategoryEdit1:
                mCurrentCategory = CATEGORY_KIND.MAIN1;
                i.putExtra(CATEGORY, MAIN);
                break;
            case R.id.StoreInformationPreMainCategoryEdit2:
                mCurrentCategory = CATEGORY_KIND.MAIN2;
                i.putExtra(CATEGORY, MAIN);
                break;
            case R.id.StoreInformationPreMainCategoryEdit3:
                mCurrentCategory = CATEGORY_KIND.MAIN3;
                i.putExtra(CATEGORY, MAIN);
                break;
            case R.id.StoreInformationPreMiddleCategoryEdit1:
                mCurrentCategory = CATEGORY_KIND.MIDDLE1;
                i.putExtra(CATEGORY, MIDDLE);
                break;
            case R.id.StoreInformationPreMiddleCategoryEdit2:
                mCurrentCategory = CATEGORY_KIND.MIDDLE2;
                i.putExtra(CATEGORY, MIDDLE);
                break;
            case R.id.StoreInformationPreMiddleCategoryEdit3:
                mCurrentCategory = CATEGORY_KIND.MIDDLE3;
                i.putExtra(CATEGORY, MIDDLE);
                break;
            case R.id.StoreInformationPreSmallCategoryEdit1:
                mCurrentCategory = CATEGORY_KIND.SMALL1;
                i.putExtra(CATEGORY, SMALL);
                break;
            case R.id.StoreInformationPreSmallCategoryEdit2:
                mCurrentCategory = CATEGORY_KIND.SMALL2;
                i.putExtra(CATEGORY, SMALL);
                break;
            case R.id.StoreInformationPreSmallCategoryEdit3:
                mCurrentCategory = CATEGORY_KIND.SMALL3;
                i.putExtra(CATEGORY, SMALL);
                break;
            case R.id.StoreInformationPreAddress:
                break;
        }

        if(isViewSelected(id, R.id.StoreInformationPreAddress)) {
            startActivityForResult(i, GlobalVariable.REGISTER_ADDRESS_REQUEST_CODE);
        }
        else {
            startActivityForResult(i, GlobalVariable.REGISTER_STORE_CATEGORY_REQUEST_CODE);
        }
    }

    private boolean isCategorySelected(int id, int main, int middle, int small) {
        boolean ret = id != main && id != middle && id != small;
        return !ret;
    }

    private boolean isViewSelected(int id, int viewId) {
        return id == viewId;
    }
}
