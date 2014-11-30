package com.ensr.instagram.tabs;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;

import com.ensr.instagram.R;

/**
 * Created by ensr on 26.11.2014.
 */
public class Downloaded extends Activity {

    GridView indirilenler;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.downloaded);

        indirilenler = (GridView)findViewById(R.id.gvDownloaded);


    }
}
