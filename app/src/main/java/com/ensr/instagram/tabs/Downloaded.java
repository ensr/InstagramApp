package com.ensr.instagram.tabs;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.ensr.instagram.R;

import java.io.File;

/**
 * Created by ensr on 26.11.2014.
 */
public class Downloaded extends Activity {

    private static int RESULT_LOAD_IMAGE = 1;
    GridView indirilenler;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.downloaded);

        indirilenler = (GridView)findViewById(R.id.gvDownloaded);

        //Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),Uri.fromFile() );
    }
}

