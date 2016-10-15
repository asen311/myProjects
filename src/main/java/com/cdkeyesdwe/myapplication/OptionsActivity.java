package com.cdkeyesdwe.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Locale;

/**
 * Created by asen on 12/10/2016.
 */
public class OptionsActivity extends Activity implements View.OnClickListener{

 private Button bChangeLanguage,mLocation;


   private final Context ctx=this;
   private LocationManager locationManager ;
   private Location location;
    private ImageView creatorImageView;
    private TextView textViewForClick;

    private CustomTimeZone timezone = new CustomTimeZone();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_layout);


        bChangeLanguage=  (Button)findViewById(R.id.changeLanguage);
        bChangeLanguage.setOnClickListener(this);



        creatorImageView = (ImageView) findViewById(R.id.creatorImage);

        textViewForClick =(TextView) findViewById(R.id.TextForCreator);
        textViewForClick.setOnClickListener(this);


        mLocation = (Button)findViewById(R.id.btnForLocation);
        mLocation.setOnClickListener(this);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        try {
            location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            location.getLatitude();
        }catch(SecurityException e){
            CustomNotification notification = new CustomNotification();
            notification.showNotify(this,"Something Wrong with GPS","You don't have permission to use this","");
        }



        if(GameActivity.bg){
            bChangeLanguage.setBackgroundResource(R.mipmap.america);
        }else{
            bChangeLanguage.setBackgroundResource(R.mipmap.bulgaria);
        }



    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        if(viewId==R.id.changeLanguage){ // firstButton - for change Language

            if(GameActivity.bg){
                changeLanguage("en_US");
                  GameActivity.bg =false;

            }else {
                changeLanguage("bg");
                GameActivity.bg = true;

            }
            startActivity(new Intent(OptionsActivity.this,Menu_Activity.class));
            finish();
        }

        if(viewId==R.id.btnForLocation){ //second button - for you to see where you are on the map

            double latitude,longitude;
               if(location!=null){

                   latitude=location.getLatitude();
                   longitude = location.getLongitude();

                   Uri gmmIntentUri = Uri.parse("geo:"+latitude+","+longitude);

                   Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

                   mapIntent.setPackage("com.google.android.apps.maps");

                   startActivity(mapIntent);

               }else{
                   AlertDialog.Builder builder = CustomAlertDialog.recieveBuilder(this,"Error","Something is not wrong with location");
                   builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           dialogInterface.dismiss();
                       }
                   });
                   AlertDialog dialog = builder.create();
                   dialog.show();
               }






        }
        

        if(viewId == R.id.TextForCreator){

            Picasso.with(this)
                    .load("https://scontent.fsof3-1.fna.fbcdn.net/v/t1.0-9/12798864_1077873775596525_2443483923633814783_n.jpg?oh=f1250f0fe1819a8c8c42c5f622e73212&oe=58AB285B")
                    .into(creatorImageView);
            textViewForClick.setText(getString(R.string.Click_on_me).toString());
            creatorImageView.setOnClickListener(this);
        }

        if(viewId == R.id.creatorImage){
            startActivity(new Intent(OptionsActivity.this,CreatorPage.class));
        }
    }


    private void changeLanguage(String language){
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

    }

    public void  checkTimeZone(View view){//implicit button
                 CustomTimeZone.ctx = this;
        startActivity(new Intent(OptionsActivity.this,ViewForTime.class));
    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();


        mLocation.setOnClickListener(null);
        creatorImageView.setOnClickListener(null);
        bChangeLanguage.setOnClickListener(null);
        textViewForClick.setOnClickListener(null);
        finish();
    }

}
