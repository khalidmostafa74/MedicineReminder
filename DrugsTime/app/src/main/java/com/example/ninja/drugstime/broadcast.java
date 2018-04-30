package com.example.ninja.drugstime;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by ninja on 28/04/2018.
 */

public class broadcast extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        Notification.Builder builder = new Notification.Builder(context);
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.alert);
        builder.setTicker("alert")
                .setContentTitle("New Horizons")
                .setContentText("Next Session will be cancelled")
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher);
//                .setLargeIcon(bitmap)
        Notification notification = builder.build();
        NotificationManager mngr = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        mngr.notify(10,notification);
    }
}