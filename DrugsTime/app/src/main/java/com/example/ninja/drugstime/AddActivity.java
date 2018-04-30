package com.example.ninja.drugstime;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Random;

import static com.example.ninja.drugstime.DBHelper.DRUGCOUNT;
import static com.example.ninja.drugstime.DBHelper.DRUGIMAGE;
import static com.example.ninja.drugstime.DBHelper.DRUGNAME;
import static com.example.ninja.drugstime.DBHelper.DRUGPERIOD;
import static com.example.ninja.drugstime.DBHelper.DRUGPERIOD;
import static com.example.ninja.drugstime.DBHelper.TABLENAME;

public class AddActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private DBHelper helper;
    ImageView iv;
    TextInputLayout drugname , drugcount , drugperiod;
    private Bitmap bm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setUpDB();
        iv = findViewById(R.id.imageView2);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        drugname = findViewById(R.id.name);
        drugcount = findViewById(R.id.count);
        drugperiod = findViewById(R.id.period);
        Random rand = new Random();
        int value = rand.nextInt(50);
        AddAlarm(value);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addNew()){
                    //Create Notification
//                    Random rand = new Random();
//                    int value = rand.nextInt(50);
//                    Notification.Builder builder = new Notification.Builder(AddActivity.this);
//                    builder.setTicker("Treatment Time")
//                            .setAutoCancel(true)
//                            .setSmallIcon(R.drawable.mr)
//                            .setContentTitle(drugname.getEditText().getText().toString())
//                            .setContentText("you should take this medicine now : "+drugcount.getEditText().getText().toString()+" and every "+drugperiod+" hour");
//                    Notification notification = builder.build();
//                    NotificationManager mngr = new NotificationManager();
//                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//
//                    Intent notificationIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
//                    notificationIntent.addCategory("android.intent.category.DEFAULT");
//                    int period = Integer.parseInt(drugperiod.getEditText().getText().toString());
//                    PendingIntent broadcast = PendingIntent.getBroadcast(AddActivity.this, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+3000,3000, broadcast);
                    Snackbar.make(view, "Data Saved ", Snackbar.LENGTH_LONG)
                            .setAction("Done", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    finish();
                                }
                            }).show();
                }

            }
        });
    }

    private void AddAlarm(int req) {
            Intent i = new Intent(AddActivity.this,broadcast.class);
            PendingIntent pin = PendingIntent.getBroadcast(AddActivity.this,req,i,PendingIntent.FLAG_UPDATE_CURRENT);
            long trigger = System.currentTimeMillis()+30*1000;
            AlarmManager mngr = (AlarmManager) getSystemService(ALARM_SERVICE);
            mngr.setRepeating(AlarmManager.RTC_WAKEUP,trigger,10000,pin);
    }

    private void selectImage() {

            final CharSequence[] items = { "Take Photo", "Choose from Library",
                    "Cancel" };
            AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
            builder.setTitle("Add Photo!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {
                    if (items[item].equals("Take Photo")) {
                            cameraIntent();
                    } else if (items[item].equals("Choose from Library")) {
                            galleryIntent();
                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();

    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),102);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 101);
    }

    private void setUpDB() {
        helper = new DBHelper(this);
        db = helper.getWritableDatabase();
    }

    private boolean addNew() {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        ContentValues values = new ContentValues();
        values.put(DRUGIMAGE,bytes.toByteArray());
        values.put(DRUGNAME,drugname.getEditText().getText().toString());
        values.put(DRUGPERIOD,drugperiod.getEditText().getText().toString());
        values.put(DRUGCOUNT,drugcount.getEditText().getText().toString());
        long insert = db.insert(TABLENAME, null, values);
        if (insert>0){
            return true;
        }
        return false;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 102)
                onSelectFromGalleryResult(data);
            else if (requestCode == 101)
                onCaptureImageResult(data);
        }
    }

    private void onSelectFromGalleryResult(Intent data) {
        bm=null;
        if (data != null) {
            try {
                bm =  MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        iv.setImageBitmap(bm);
    }

    private void onCaptureImageResult(Intent data) {
        bm = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        iv.setImageBitmap(bm);
    }
}
