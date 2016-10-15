package com.cdkeyesdwe.myapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by asen on 08/10/2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<String> mDataset;

  private static Context ctx;
   private static ItemPressed callback;

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public TextView mTextView;
        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView)v.findViewById(R.id.RecyclerViewRowText);

            mTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    callback.itemSelected(mTextView.getText().toString());
                }
            });
        }
    }

    public MyAdapter(ArrayList<String> myDataset, Context ctx) {
        mDataset = myDataset;
        this.ctx = ctx;
        callback = (ItemPressed)ctx;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);

        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextView.setText(mDataset.get(position));
    }


    @Override
    public int getItemCount() {
        return mDataset.size();

    }


}
