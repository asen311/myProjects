package com.cdkeyesdwe.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * Created by asen on 13/10/2016.
 */
public class CreatorPage extends Activity implements View.OnClickListener{

    private TextView mInformation,mTelephone;
    private ImageView creatorImage,mFacebookImage;


    private boolean singleLine=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creator_page);

         creatorImage = (ImageView)findViewById(R.id.creatorPageImage);
        Picasso.with(this)
                .load("https://scontent.fsof3-1.fna.fbcdn.net/v/t1.0-9/12798864_1077873775596525_2443483923633814783_n.jpg?oh=f1250f0fe1819a8c8c42c5f622e73212&oe=58AB285B")
                .into(creatorImage);

        mInformation  = (TextView)findViewById(R.id.informationText);
        mInformation.setSingleLine(true);
        mInformation.setOnClickListener(this);

        mTelephone = (TextView)findViewById(R.id.telephone);
        mTelephone.setOnClickListener(this);

        mFacebookImage = (ImageView)findViewById(R.id.faceBookImage);
        Picasso.with(this)
                .load("https://lh3.googleusercontent.com/ZZPdzvlpK9r_Df9C3M7j1rNRi7hhHRvPhlklJ3lfi5jk86Jd1s0Y5wcQ1QgbVaAP5Q=w170")
                .into(mFacebookImage);

         mFacebookImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        int viewId=view.getId();

        if(viewId== R.id.informationText){
            if(singleLine){

                singleLine=false;
            }else{
               singleLine=true;
            }
            mInformation.setSingleLine(singleLine);
        }


        if(viewId == R.id.telephone){
           String uri= "tel:" + mTelephone.getText().toString() ;
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse(uri));
            try {
                startActivity(intent);
            }catch (SecurityException e){
                Toast.makeText(this,"can't call him right now.",Toast.LENGTH_LONG).show();
                startActivity(new Intent(CreatorPage.this,CreatorPage.class));
            }
        }

         if(viewId == R.id.faceBookImage){
             startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/asen.nikolaev.3")));
         }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

