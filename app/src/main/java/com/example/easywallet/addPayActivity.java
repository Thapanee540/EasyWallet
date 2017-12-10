package com.example.easywallet;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.easywallet.adapter.PayListAdapter;
import com.example.easywallet.db.PayDbHelper;
import com.example.easywallet.model.PayItem;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by TO_MANG on 10/12/2560.
 */

public class addPayActivity extends AppCompatActivity implements View.OnClickListener{

    private  static final String tag = addPayActivity.class.getName();

    private EditText mPayTitleEditText, mPayNumberEditText;
    private ImageView mPayImageView;
    private Button mSaveButton;

    private File mSelectedPictureFile;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.add_activity);

        mPayTitleEditText = findViewById(R.id.editTextDetail);
        mPayNumberEditText = findViewById(R.id.editTextMoney);
        mPayImageView = findViewById(R.id.payImageV);
        mSaveButton = findViewById(R.id.saveButton);

        // กำหนดให้ Activity เป็น Listener ของ ImageView
//        mPayImageView.setOnClickListener(this);
        // กำหนดให้ Activity เป็น Listener ของ Button
        mSaveButton.setOnClickListener(this);
    }
    public void onClick(View view) {

            saveDataToDb();
            setResult(RESULT_OK);
            finish();

    }

private void saveDataToDb() {
    String payTitle = mPayTitleEditText.getText().toString();
    String payNumber = mPayNumberEditText.getText().toString();
    String pictureFileName = "ic_income.png";

    ContentValues cv = new ContentValues();
    cv.put(PayDbHelper.COL_TITLE, payTitle);
    cv.put(PayDbHelper.COL_NUMBER, payNumber);
    cv.put(PayDbHelper.COL_PICTURE, pictureFileName);

    PayDbHelper dbHelper = new PayDbHelper(this);
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    long result = db.insert(PayDbHelper.TABLE_NAME, null, cv);
    if (result == -1) {
        //
    }
}
    /*
    private PayDbHelper mHelper;
    private SQLiteDatabase mDb;

    private ArrayList<PayItem> mPayItem = new ArrayList<>();
    private PayListAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.add_activity);

        Button insertButon = findViewById(R.id.button_in);
        insertButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText detailEditText = findViewById(R.id.editTextDetail);
                EditText moneyEditText = findViewById(R.id.editTextMoney);

                String phoneTitle = detailEditText.getText().toString();
                String phoneNumber = moneyEditText.getText().toString();

                ContentValues cv = new ContentValues();
                cv.put(PayDbHelper.COL_TITLE, phoneTitle);
                cv.put(PayDbHelper.COL_NUMBER, phoneNumber);
                cv.put(PayDbHelper.COL_PICTURE, "ic_income.png");

                mDb.insert(PayDbHelper.TABLE_NAME, null, cv);
                loadDataFromDb();
                mAdapter.notifyDataSetChanged();

            }
        });
    }

    private void loadDataFromDb() {
        Cursor cursor = mDb.query(
                PayDbHelper.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        mPayItem.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(PayDbHelper.COL_ID));
            String title = cursor.getString(cursor.getColumnIndex(PayDbHelper.COL_TITLE));
            String number = cursor.getString(cursor.getColumnIndex(PayDbHelper.COL_NUMBER));
            String picture = cursor.getString(cursor.getColumnIndex(PayDbHelper.COL_PICTURE));

            PayItem item = new PayItem(id, title, number, picture);
            mPayItem.add(item);
        }*/
    }

