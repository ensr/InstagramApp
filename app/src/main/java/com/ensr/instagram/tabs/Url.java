package com.ensr.instagram.tabs;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.ensr.instagram.R;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.IOException;

/**
 * Created by ensr on 26.11.2014.
 */

public class Url extends Activity {

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

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getPicFromUrl(String picUrl) throws IOException {

        org.jsoup.nodes.Document doc = Jsoup.connect(picUrl).get();
        Elements newsHeadlines = doc.select("div.utiImage Image");
        sharedPicUrl = newsHeadlines.attr("src");
/*
        for (org.jsoup.nodes.Element el : newsHeadlines) {
            sharedPicUrl = el.absUrl("src");

        }
        */
            //sharedPicUrl = newsHeadlines.attr("src").toString();
        Picasso.with(context).load(sharedPicUrl).into(ivUrl);

    }
}
