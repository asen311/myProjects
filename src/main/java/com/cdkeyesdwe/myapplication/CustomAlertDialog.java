package com.cdkeyesdwe.myapplication;

import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * Created by asen on 09/10/2016.
 */
public class CustomAlertDialog {

    public static AlertDialog.Builder recieveBuilder(Context ctx, String title, String description){

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setTitle(title);
        builder.setMessage(description);
        builder.setIcon(R.mipmap.besenica);
        builder.setCancelable(false);

        return builder;
    }
}
