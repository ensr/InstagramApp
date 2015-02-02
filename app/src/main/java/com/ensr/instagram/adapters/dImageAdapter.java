package com.ensr.instagram.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.ensr.instagram.tabs.Url;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 * Created by ensr on 22.12.2014.
 */
public class dImageAdapter extends BaseAdapter {

    ArrayList<String> f = new ArrayList<String>();
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
        ImageView imageView = new ImageView(mContext);

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        Bitmap myBitmap = BitmapFactory.decodeFile(f.get(position),options);

        imageView.setImageBitmap(myBitmap);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
        return imageView;
    }


        /*
        Bitmap myBitmap = null;
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        //Bitmap myBitmap = BitmapFactory.decodeFile(f.get(position),options);
        ImageView imageView = new ImageView(mContext);

        //myBitmap.recycle();
        try {
            myBitmap = BitmapFactory.decodeFile(f.get(position));
            imageView.setImageBitmap(myBitmap);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
            return imageView;
        } catch (OutOfMemoryError ooM) {

            System.gc();
        }
*/
        /*
        imageView.setImageBitmap(myBitmap);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
        return imageView;
        */



    private static Bitmap getResizedBitmap(Bitmap bitmap, float maxWidth, float maxHeight) {

        float width = bitmap.getWidth();
        float height = bitmap.getHeight();
        if (width > maxWidth) {
            height = (maxWidth / width) * height;
            width = maxWidth;
        }
        if (height > maxHeight) {
            width = (maxHeight / height) * width;
            height = maxHeight;
        }
        return Bitmap.createScaledBitmap(bitmap, (int) width, (int) height, true);

    }

    public static Bitmap decodeFile(String pathName) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        for (options.inSampleSize = 1; options.inSampleSize <= 32; options.inSampleSize++) {
            try {
                bitmap = BitmapFactory.decodeFile(pathName, options);
                Log.d("TravellerLog ::", "Decoded successfully for sampleSize " + options.inSampleSize);
                break;
            } catch (OutOfMemoryError outOfMemoryError) {
// If an OutOfMemoryError occurred, we continue with for loop and next inSampleSize value
                Log.e("TravellerLog ::", "outOfMemoryError while reading file for sampleSize " + options.inSampleSize
                        + " retrying with higher value");
            }
        }
        return bitmap;
    }
}
