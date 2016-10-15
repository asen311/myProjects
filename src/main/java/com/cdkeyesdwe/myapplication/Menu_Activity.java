package com.cdkeyesdwe.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by asen on 09/10/2016.
 */
public class Menu_Activity extends AppCompatActivity implements View.OnClickListener{

    private  ImageView image;
    private final Context ctx = this;
    private Button btnStart,btnLoad,btnOptions,btnExit;
    private DataBaseManagerLoad database= new DataBaseManagerLoad(ctx);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);
        setTitle(getString(R.string.version));

        image = (ImageView)findViewById(R.id.imageView);

        image.setBackgroundResource(R.mipmap.unnamed); //load main hangman picture

        btnStart = (Button)findViewById(R.id.start);
        btnLoad = (Button)findViewById(R.id.load);
        btnOptions = (Button)findViewById(R.id.options);
        btnExit = (Button)findViewById(R.id.exit);

        btnStart.setOnClickListener(this);
        btnLoad.setOnClickListener(this);
        btnOptions.setOnClickListener(this);
        btnExit.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

int viewId = view.getId();

        if(viewId==R.id.start){
            Intent intent = new Intent(Menu_Activity.this,GameActivity.class);
            startActivity(intent);
            finish();
        }

if(viewId == R.id.load){
 database = new DataBaseManagerLoad(ctx);

    database.getWritableDatabase();

    int level = database.getFinalSaveRecord();
    if(level>0){
        Intent intent = new Intent(Menu_Activity.this,GameActivity.class);
        intent.putExtra("INTENT_FROM_LOADER",level);
        startActivity(intent);
    }else{
        Toast.makeText(ctx, getString(R.string.notSavedRecords).toString() , Toast.LENGTH_LONG).show();
        CustomNotification notification = new CustomNotification();
        notification.showNotify(ctx,getString(R.string.app_name).toString(),getString(R.string.donthaveSave).toString(),"key");
    }
    database.close();
}

if(viewId==R.id.options){
    startActivity(new Intent(Menu_Activity.this,OptionsActivity.class));

    finish();
}

if(viewId==R.id.exit){

   AlertDialog.Builder builder =
          CustomAlertDialog.recieveBuilder(ctx,getString(R.string.exitGame).toString(),getString(R.string.doyousure).toString());

                    builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            finish();

                        }
                    });

                    builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();

                        }
                    });

                    builder.setNeutralButton(R.string.maybe, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            finish();
                        }
                    });
                    AlertDialog dialog=builder.create();
                    dialog.show();

        }
    }//end click listener


}
