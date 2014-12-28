package com.ensr.instagram.tabs;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.ensr.instagram.R;
import com.ensr.instagram.adapters.dImageAdapter;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by ensr on 26.11.2014.
 */
public class Downloaded extends Activity {

    private Activity context = Downloaded.this;
    ArrayList<String> f = new ArrayList<String>();
    File[] listFile;
    GridView indirilenler;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.downloaded);

        indirilenler = (GridView)findViewById(R.id.gvDownloaded);
        getFromSdcard();

        dImageAdapter dImageA = new dImageAdapter(f,context);
        indirilenler.setAdapter(dImageA);


    }

    public void getFromSdcard()
    {
        File file= new File(android.os.Environment.getExternalStorageDirectory(),"InstaStorage");

        if (file.isDirectory())
        {
            listFile = file.listFiles();
            for (int i = 0; i < listFile.length; i++) {
                f.add(listFile[i].getAbsolutePath());
            }
        }
    }




}

