package user.com.openproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class StoreCategoryActivity extends AppCompatActivity {

    String mCategory;
    ListView mList;

    final String MAIN = "main";
    final String MIDDLE = "middle";
    final String SMALL = "small";
    final String CATEGORY = "category";
    final String POSITION = "position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_category);

        setRes();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void setRes() {
        Intent i = getIntent();
        mCategory = i.getStringExtra(CATEGORY);
        mList = findViewById(R.id.StoreCategoryListview);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        switch(mCategory) {
            case MAIN:
                adapter.addAll(getResources().getStringArray(R.array.store_main_category));
                break;
            case MIDDLE:
                adapter.addAll(getResources().getStringArray(R.array.store_middle_category));
                break;
            case SMALL:
                adapter.addAll(getResources().getStringArray(R.array.store_small_category));
                break;
        }

        mList.setAdapter(adapter);
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent();
                intent.putExtra(POSITION, i);
                setResult(GlobalVariable.REGISTER_STORE_CATEGORY_RESULT_CODE, intent);
                finish();
            }
        });
    }
}
