package com.cdkeyesdwe.myapplication;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by asen on 12/10/2016.
 */
public class ServiceMusic extends Service {

    private MediaPlayer player;
    private boolean firstplay=true;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(intent.hasExtra("PLAY_SONG")){
            int song = intent.getIntExtra("PLAY_SONG",-1);

            if(firstplay){}
                else {
                player.stop();
            }


            if(song!=-1){
             switch (song){
                 case 1:{
                     player=MediaPlayer.create(this,R.raw.song3);
                     break;
                 }
                 case 2:{
                     player=MediaPlayer.create(this,R.raw.song4);
                     break;
                 }
                 case 3:{
                     player=MediaPlayer.create(this,R.raw.song5);
                     break;
                 } case 4:{
                     player=MediaPlayer.create(this,R.raw.floridae);
                     break;
                 }
             }
                firstplay=false;
                player.start();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }



    @Override
    public void onDestroy() {

        super.onDestroy();

        player.stop();
    }
}
