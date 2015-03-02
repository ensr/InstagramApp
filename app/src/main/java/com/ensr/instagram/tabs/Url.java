package com.ensr.instagram.tabs;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ensr.instagram.FullImage;
import com.ensr.instagram.R;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by ensr on 26.11.2014.
 */

public class Url extends Activity{

    private EditText etUrl;
    private ImageView ivUrl;
    private Button bUrlSearch;
    public String sharedPicUrl;
    public String sharedPicPageUrl;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.url);

        etUrl = (EditText) findViewById(R.id.etUrl);
        ivUrl = (ImageView) findViewById(R.id.ivUrl);
        bUrlSearch = (Button) findViewById(R.id.bUrlSearch);

        bUrlSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPicPageUrl = etUrl.getText().toString();

                try {
                    getPicFromUrl(sharedPicPageUrl);
                    displayImage();

                } catch (IOException e) {
                    e.printStackTrace();

                }


            }
        });
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private void getPicFromUrl(String picUrl) throws IOException {

        //network işlemlerinin çalışabilmesi için eklendi.
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        org.jsoup.nodes.Document doc = Jsoup.connect(picUrl).get();
        Elements a = doc.select("div[class=ResponsiveBlock lfFrame Frame UserTaggedImage]");
        Elements newsHeadlines = a.select("div[class=utiImage Image]");

        sharedPicUrl = newsHeadlines.attr("src").toString();
    }

    private void displayImage(){
        Intent i = new Intent(getApplicationContext(), FullImage.class);
        String a = "https://igcdn-photos-h-a.akamaihd.net/hphotos-ak-xfa1/t51.2885-15/10932035_1518102041790199_1001074739_n.jpg";
        i.putExtra("id", sharedPicUrl);
        startActivity(i);
    }

}


