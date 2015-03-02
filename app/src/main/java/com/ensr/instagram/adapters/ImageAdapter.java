package com.ensr.instagram.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.ensr.instagram.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ImageAdapter extends BaseAdapter {
    ArrayList<String> pictureUrl;
    Context mContext;
    int size;

    public ImageAdapter(int screenSize, ArrayList<String> pictureUrl, Context mContext) {
        this.pictureUrl = pictureUrl;
        this.mContext = mContext;
        this.size = screenSize;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return pictureUrl.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return pictureUrl.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView = new ImageView(mContext);
        Picasso.with(mContext).load(pictureUrl.get(position)).into(imageView);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        imageView.setLayoutParams(new GridView.LayoutParams(size, size));
        return imageView;
    }
/*
    private int getSize() {
        Intent byt = new Intent(mContext, MainActivity.class);
        mContext.startActivity(byt);
        boyut = byt.getExtras().getInt("boyut");
        return boyut;
    }
*/
}
