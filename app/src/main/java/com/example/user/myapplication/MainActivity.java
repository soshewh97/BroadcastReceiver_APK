package com.example.user.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private static  MainActivity ins;
    private ListView listView;
    private SQLiteOpenHelper myDBHelper;
    private Button button1,button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ins=this;
        myDBHelper= new MyDBHelper(this, "expense.db", null, 1);

//        textView = (TextView)  findViewById(R.id.textView);
        listView = (ListView)findViewById(R.id.listView);
        button1 = (Button)findViewById(R.id.button);
        button2 = (Button)findViewById(R.id.button2);
        selectData();

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectData();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myDBHelper.getWritableDatabase().execSQL("DELETE from main.exp");
                selectData();
            }
        });

    }

    private void selectData() {
        Cursor c = myDBHelper.getReadableDatabase().query("exp",null,null,null,null,null,null);
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                this,R.layout.row,c,
                new String[]{"_id","packageName","version","Timestamp"},
                new int[]{R.id.item_id,R.id.item_date,R.id.item_info,R.id.item_amount
                }
        );
        listView.setAdapter(adapter);
    }

    public static MainActivity getInstace(){
        return ins;
    }

    public void updateTheTextView(){
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                selectData();
            }
        });
    }

    @Override
    protected void onResume() {
        selectData();
        super.onResume();
    }
}
