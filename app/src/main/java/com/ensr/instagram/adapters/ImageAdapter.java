package com.ensr.instagram.adapters;

import java.util.ArrayList;
import com.squareup.picasso.Picasso;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import static android.widget.GridView.*;

public class ImageAdapter extends BaseAdapter {
	ArrayList<String> pictureUrl;
	Context mContext;
	
	public ImageAdapter(ArrayList<String> pictureUrl, Context mContext) {
		this.pictureUrl = pictureUrl;
		this.mContext = mContext;
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
        imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
        return imageView;
	}

}
