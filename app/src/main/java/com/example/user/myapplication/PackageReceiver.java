package com.example.user.myapplication;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by user on 2018/2/2.
 */

public class PackageReceiver extends BroadcastReceiver{
    private SQLiteOpenHelper helper;
    private String packageName;


     @Override
    public void onReceive(Context context, Intent intent) {

         helper = new MyDBHelper(context,"expense.db",null,1);
         PackageManager manager = context.getPackageManager();

        if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
            packageName = intent.getData().getSchemeSpecificPart();
            Log.i("PackageReceiver_TAG","Add"+packageName);
            Toast.makeText(context, "安装成功"+packageName, Toast.LENGTH_LONG).show();
            add(packageName,1);
        }
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REMOVED)) {
            packageName = intent.getData().getSchemeSpecificPart();
            Log.i("PackageReceiver_TAG","Remove"+packageName);
            Toast.makeText(context, "卸载成功"+packageName, Toast.LENGTH_LONG).show();
            add(packageName,2);
        }
        if (intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED)) {
            packageName = intent.getData().getSchemeSpecificPart();
            Log.i("PackageReceiver_TAG","Replaced"+packageName);
            Toast.makeText(context, "替换成功"+packageName, Toast.LENGTH_LONG).show();
            add(packageName,3);
        }
        
        try {
            MainActivity.getInstace().updateTheTextView();
        }catch (Exception e){
            
        }

    }
    
    private void add(String s,Integer ver){
        ContentValues values = new ContentValues();
        values.put("packageName",s);
        values.put("version",ver);
        long id = helper.getWritableDatabase().insert("exp",null,values);
        Log.i("ADD","新增資料"+id);
    }
    
}
