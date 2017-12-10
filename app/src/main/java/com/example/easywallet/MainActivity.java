package com.example.easywallet;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.easywallet.adapter.PayListAdapter;
import com.example.easywallet.db.PayDbHelper;
import com.example.easywallet.model.PayItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private PayDbHelper mHelper;
    private SQLiteDatabase mDb;

    private ArrayList<PayItem> mPayItem = new ArrayList<>();
    private PayListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button_in = findViewById(R.id.button_in);
        button_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,addPayActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Button button_ex = findViewById(R.id.button_ex);
        button_ex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,addPayActivity_ex.class);
                startActivity(intent);
                finish();
            }
        });

        mHelper = new PayDbHelper(this);
        mDb = mHelper.getReadableDatabase();

        loadDataFromDb();

        mAdapter = new PayListAdapter(
                this, R.layout.item_activity,mPayItem
        );

        ListView lv = findViewById(R.id.list_view);
        lv.setAdapter(mAdapter);

//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                PayItem item = mPayItem.get(position);
//                String payNumber = item.number;
//
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.s
//            }
//        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {

                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                 String[] item = new String[]{"NO","YES"};
                 dialog.setItems(item, new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialogInterface, int i) {
                         if (i == 0){
                             closeContextMenu();
                         } else if (i == 1){
                             PayItem item = mPayItem.get(position);
                             int phoneId = item.id;

                             mDb.delete(
                                     PayDbHelper.TABLE_NAME,
                                     PayDbHelper.COL_ID + "=?",
                                     new String[]{String.valueOf(phoneId)}
                             );
                             loadDataFromDb();
                             mAdapter.notifyDataSetChanged();
                         }
                     }
                 });
                 dialog.show();

                return true;
            }


        });}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if (resultCode == RESULT_OK) {
                loadDataFromDb();
                mAdapter.notifyDataSetChanged();
            }
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
        }
}
}