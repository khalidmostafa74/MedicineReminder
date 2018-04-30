package com.example.ninja.drugstime;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.ninja.drugstime.DBHelper.DRUGCOUNT;
import static com.example.ninja.drugstime.DBHelper.DRUGID;
import static com.example.ninja.drugstime.DBHelper.DRUGIMAGE;
import static com.example.ninja.drugstime.DBHelper.DRUGNAME;
import static com.example.ninja.drugstime.DBHelper.DRUGPERIOD;

import static com.example.ninja.drugstime.DBHelper.TABLENAME;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private DBHelper helper;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter ;
    RecyclerView.LayoutManager layoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpDB();
        setupRecycle();
    }

    private void setupRecycle() {
        recyclerView = findViewById(R.id.recycle);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

    }

    private void setUpDB() {
        helper = new DBHelper(this);
        db = helper.getWritableDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_add,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add:goToAdd();break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToAdd() {
        Intent i = new Intent(MainActivity.this,AddActivity.class);
        startActivity(i);
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewDrugs();
    }

    private void viewDrugs() {
        ArrayList<Drug> drugs = new ArrayList<>();
        String cols[]={DRUGID,DRUGNAME,DRUGCOUNT,DRUGIMAGE,DRUGPERIOD};
        Cursor c = db.query(true, TABLENAME, cols, null, null, null, null, DRUGNAME, null);
        if (c.moveToFirst()){
            do{
                int id = c.getInt(c.getColumnIndex(DRUGID));
                String name = c.getString(c.getColumnIndex(DRUGNAME));
                byte[] image = c.getBlob(c.getColumnIndex(DRUGIMAGE));
                int count = c.getInt(c.getColumnIndex(DRUGCOUNT));
                int period = c.getInt(c.getColumnIndex(DRUGPERIOD));
                drugs.add(new Drug(id,name,image,period,count));
            }while (c.moveToNext());
            adapter = new DrugAdapter(drugs , this);
            recyclerView.setAdapter(adapter);
        }

    }
}
