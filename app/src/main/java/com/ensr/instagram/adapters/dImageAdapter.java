package com.ensr.instagram.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import java.util.ArrayList;

/**
 * Created by ensr on 22.12.2014.
 */
public class dImageAdapter  extends BaseAdapter {

    ArrayList<String> f = new ArrayList<String>();// list of file paths
   // File[] listFile;
    Context mContext;
    //private LayoutInflater mInflater;


    public dImageAdapter(ArrayList<String> f, Context mContext) {
        this.mContext = mContext;
        this.f = f;
      //  mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return f.size();
    }

    @Override
    public Object getItem(int position) {
        return f.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Bitmap myBitmap = BitmapFactory.decodeFile(f.get(position));
        ImageView imageView = new ImageView(mContext);
        imageView.setImageBitmap(myBitmap);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
        return imageView;
    }
}
