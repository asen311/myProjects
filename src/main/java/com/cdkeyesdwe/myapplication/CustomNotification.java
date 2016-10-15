package com.cdkeyesdwe.myapplication;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

/**
 * Created by asen on 11/10/2016.
 */
public class CustomNotification  {


    public void showNotify(Context ctx,String titledata, String bodydata,String extra){

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(ctx);
        mBuilder.setSmallIcon(R.mipmap.besenica);
        mBuilder.setContentTitle(titledata);
        mBuilder.setContentText(bodydata);

        Intent intent = new Intent(ctx,GameActivity.class);
       intent.putExtra(extra,"");
        PendingIntent pendingIntent = PendingIntent.getActivity(ctx,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(pendingIntent);

        int mNotificationID = 001;

        NotificationManager mNotifyManager= (NotificationManager)ctx.getSystemService(ctx.NOTIFICATION_SERVICE);

        mNotifyManager.notify(mNotificationID,mBuilder.build());

    }
}
