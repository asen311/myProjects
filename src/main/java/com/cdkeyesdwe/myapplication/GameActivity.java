package com.cdkeyesdwe.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by asen on 09/10/2016.
 */
public class GameActivity extends AppCompatActivity implements ItemPressed, View.OnClickListener ,View.OnLongClickListener,
        GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener{

    private ArrayList<String> myDataset;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private final Context ctx = this;
    private  TextView mTextView,mWordsTextView,mLastFindWord;

    private ImageView mHangmanView,mMusicImageView;
    char [] mask;//mask for word - with length of current word

    private int level=0,wrongAnswers=0;
    private int song=1;

    private boolean nextLevel;

    public static boolean bg = false;

    private  Intent musicService;

   private  StringBuilder builder;

    private RelativeLayout mLayout;

    private GestureDetectorCompat mDetector;
   //
    private DataBaseManagerLoad dataBaseForLoadRecords = new DataBaseManagerLoad(ctx);

    private  String[]enWords = {"elephant","dentist","monkey","rabbit","student","fireman"};
    private  String[]bgWords = {"хипопотам","таралеж","антрактида","лисица","пожарникар","зъболекар"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pregovor_layout);

        //this intent is if the user wants to follow me on Facebook and came when the user click notification after last word finded
        if(getIntent().hasExtra("FOLLOW_ON_FACEBOOK")){
            try {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/asen.nikolaev.3"));
                startActivity(myIntent);
            } catch (ActivityNotFoundException e) {

                CustomNotification notification = new CustomNotification();
                notification.showNotify(ctx,getString(R.string.noAplicationtohandle).toString(),getString(R.string.installbrowser).toString(),"ERROR_FOLLOW_FACEBOOK");
            }
        }

         if(getIntent().hasExtra("INTENT_FROM_LOADER")){
             level = getIntent().getIntExtra("INTENT_FROM_LOADER",-1);

         }

        mLayout = (RelativeLayout)findViewById(R.id.GameLayout);

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new GridLayoutManager(this, 10);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mTextView = (TextView)findViewById(R.id.hangmanTextView);
        mWordsTextView = (TextView)findViewById(R.id.wordsView);

        mLastFindWord = (TextView)findViewById(R.id.lastWordTextView);


        mHangmanView = (ImageView)findViewById(R.id.imageHangMan);

        mMusicImageView= (ImageView)findViewById(R.id.musicImageView);
        mMusicImageView.setOnClickListener(this);
        mMusicImageView.setOnLongClickListener(this);

        dataBaseForLoadRecords = new DataBaseManagerLoad(this);

        mDetector = new GestureDetectorCompat(this,this);
        mDetector.setOnDoubleTapListener(this);



if(bg) {
    game(level,bgWords);
}else{
    game(level,enWords);
}
    }



    private void game(int level,String[] words) {

        if(level>5){
            finishTheGame();
             startActivity(new Intent(GameActivity.this,Menu_Activity.class));
            finish();
            return;
        }

        mWordsTextView.setText(getString(R.string.Words)+ String.valueOf(level));
        mHangmanView.setBackgroundResource(R.mipmap.begining);

        if(!bg) {
            myDataset = Alphabet.getEnglishAlphabet();

        }else{
            myDataset = Alphabet.getBulgarianAlphabet();
        }
        mAdapter = new MyAdapter(myDataset, this);
        mRecyclerView.setAdapter(mAdapter);

nextLevel=false;

 builder=new StringBuilder();

   mask = new char[words[level].length()];
        for(int i=0;i<words[level].length();i++){
                builder.append(" _ ");
               mask[i]='*';
        }

        mTextView.setText(builder.toString());


    }




    @Override
    public void itemSelected(String itemtext) {
        char c = itemtext.charAt(0);

        String words[];
        if(bg){
            words=bgWords;
        }else{
            words=enWords;
        }

      int index= words[level].indexOf(c);

if(index!=-1) {

    while (true) {

        if (index == -1) {
            break;
        }
        mask[index] = c;
        index = words[level].indexOf(c, index + 1);

    }


    builder = new StringBuilder();
    for (int i = 0; i < mask.length; i++) {
        if (mask[i] == '*') {
            builder.append(" _ ");
        } else {
            builder.append(" " + mask[i] + " ");
        }
    }
    String build = builder.toString();
    mTextView.setText(builder.toString());
}else{
    update(++wrongAnswers);
}

        int position = myDataset.indexOf(itemtext); //position from adapter to remove
        myDataset.remove(position);
        mAdapter.notifyDataSetChanged();

        nextLevel=true;


        for(int i=0;i<mask.length;i++){
    if(mask[i]=='*'){
        nextLevel=false;
        break;
    }
}
        if(nextLevel){
            Toast.makeText(this,words[level] + " +1" ,Toast.LENGTH_LONG).show();
            wrongAnswers=0;
            mLastFindWord.setText(getString(R.string.lastword) + words[level]);
          level++;
            game(level,words);
        }

}

    private void update(int wrongAnswers) {
        switch (wrongAnswers){
            case 1:{
                mHangmanView.setBackgroundResource(R.mipmap.etap1);
                break;
            }
            case 2:{
                mHangmanView.setBackgroundResource(R.mipmap.etap2);
                break;
            }
            case 3:{
                mHangmanView.setBackgroundResource(R.mipmap.etap3);
                break;
            }
            case 4:{
                mHangmanView.setBackgroundResource(R.mipmap.etap4);
                break;
            }
            case 5:{
                mHangmanView.setBackgroundResource(R.mipmap.etap5);
                break;
            }
            case 6:{
                mHangmanView.setBackgroundResource(R.mipmap.etap6);
                break;
            }
            case 7:{
                mHangmanView.setBackgroundResource(R.mipmap.etap6);
                break;
            }
            case 8:{
                mHangmanView.setBackgroundResource(R.mipmap.etap8);
                break;
            }
            case 9:{
                mHangmanView.setBackgroundResource(R.mipmap.etap9);
                break;
            }
            case 10:{
                mHangmanView.setBackgroundResource(R.mipmap.etap10);
                break;
            }
            case 11:{
                mHangmanView.setBackgroundResource(R.mipmap.etap11);
                break;
            }
            case 12:{
                mHangmanView.setBackgroundResource(R.mipmap.etap12);

                 level =0;
                AlertDialog.Builder dialog =
     CustomAlertDialog.recieveBuilder(ctx,getString(R.string.GameOver) +" " + getString(R.string.words) + " " + level,"");

                dialog.setNegativeButton(R.string.returnToMainMenu, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(GameActivity.this,Menu_Activity.class));
                        finish();
                    }
                });

                AlertDialog d = dialog.create();
                d.show();

                break;
            }
        }

    }

    private void finishTheGame() {
        CustomNotification notification = new CustomNotification();

        notification.showNotify(this,getString(R.string.findAllTheWords).toString(),getString(R.string.followMeOnFacebook).toString(),"FOLLOW_ON_FACEBOOK");
    }

//<MENU-OPTIONS
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_man,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.TakeAPicture){
          Intent takeAPic = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takeAPic,0);
        }

        if(item.getItemId()==R.id.GetGalleryPicture){

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,
                    "Select Picture"), 1);//1 - how many picture to choose

        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            if (resultCode == RESULT_OK) {
                if (data != null) {

                        if(data.hasExtra("data")) { //from camera
                            Bitmap m = (Bitmap) data.getExtras().get("data");
                            Drawable drawable = new BitmapDrawable(getResources(), m);
                            mLayout.setBackground(drawable);
                        }else {

                            final Uri uri = data.getData();
                            try {
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                                Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                                mLayout.setBackground(drawable);

                            } catch (IOException e) {
                                 CustomNotification notification = new CustomNotification();
                                notification.showNotify(ctx,getString(R.string.gotWrong).toString(),getString(R.string.cantresolve).toString(),"");
                            }
                        }

                }
            }
    }


    @Override
    public void onBackPressed() {


        AlertDialog.Builder dialog =
                CustomAlertDialog.recieveBuilder(ctx,getString(R.string.exitGame),getString(R.string.doyousure));


        dialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if(level>0) {
             CustomNotification notification = new CustomNotification();

     notification.showNotify(ctx, getString(R.string.congratulations).toString(), getString(R.string.youFind).toString() +
            level + getString(R.string.words).toString(), "dd");

}
                if(musicService!=null) {
                    stopService(musicService);
                }
                startActivity(new Intent(GameActivity.this,Menu_Activity.class));
                finish();

            }
        });

        dialog.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

            }
        });

        dialog.setNeutralButton(R.string.SaveAndExit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               if(level>0) {
                   dataBaseForLoadRecords.getWritableDatabase();
                   dataBaseForLoadRecords.onSaveNewLoadInformation(level);
                   dataBaseForLoadRecords.close();
               }
                 if(musicService!=null) {
                     stopService(musicService);
                 }
                dialogInterface.dismiss();
                finish();

            }
        });

        AlertDialog d= dialog.create();
        d.show();
    }


    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.musicImageView){

            musicService = new Intent(GameActivity.this,ServiceMusic.class);
             if(song>4){
                 song=1;
             }
            musicService.putExtra("PLAY_SONG",song);
            startService(musicService);
          song++;
        }
    }

    @Override
    public boolean onLongClick(View view) {
        if(musicService!=null){
            stopService(musicService);
        }
        return true;
    }


    @Override
    protected void onPause() {
        super.onPause();
        if(musicService!=null){
            stopService(musicService);
        }
    }

//GESTURES

    @Override
    protected void onResume(){
        super.onResume();
        if(musicService!=null){
            startService(musicService);
        }
    }

   //GESTURES
    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {

        changeColor();

        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return false;
    }
    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        changeColor();
        return true;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
            if(musicService!=null){
                stopService(musicService);
            }
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {

        if(musicService==null)musicService = new Intent(GameActivity.this,ServiceMusic.class);

        if(song>4){
            song=1;
        }
        musicService.putExtra("PLAY_SONG",song);
        startService(musicService);
        song++;
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


    private void changeColor(){
        Random rand = new Random();
        switch (rand.nextInt(4)){
            case 1:{
                mLayout.setBackgroundColor(getResources().getColor(R.color.Gray));
                break;
            } case 2:{
                mLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                break;
            }
            case 3:{
                mLayout.setBackgroundColor(getResources().getColor(R.color.Orange));
                break;
            }
            case 4:{
                mLayout.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                break;
            }

        }
    }
}
