package com.cdkeyesdwe.myapplication;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by asen on 14/10/2016.
 */
public class CustomTimeZone extends Fragment {

    public static Context ctx;

    DataBaseManagerLoad database;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.custom_time_zone_layout,container,false);
        TextView fragmentText= (TextView)view.findViewById(R.id.fragmentTextView);

        database=new DataBaseManagerLoad(ctx);
        database.getWritableDatabase();
       int level = database.getFinalSaveRecord();
        database.close();
        if(level>0){
            fragmentText.setText(ctx.getString(R.string.lastSavedGame).toString() + level);
        }else{
            fragmentText.setText(ctx.getString(R.string.noSavedGames).toString());
        }


        return view;
    }

    public CustomTimeZone() {
        super();
    }
}
