package com.ensr.instagram;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ensr.instagram.alert.SweetAlertDialog;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by ensr on 29.11.2014.
 */
public class FullImage extends Activity {
    
    Button download;
    String url;
    ImageView imageView;
    private Activity context = FullImage.this;
    String path = Environment
            .getExternalStorageDirectory()
            .toString()+"/InstaStorage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image);

        imageView = (ImageView) findViewById(R.id.ivFullImage);
        download = (Button) findViewById(R.id.bDownload);

        Intent i = getIntent();
        
        //Resim url'si alınarak picasso ile ekrana basıldı
        url = i.getExtras().getString("id");
        Picasso.with(this).load(url).into(imageView);

        download.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DownloadImage().execute(url);
            }
        });

    }

    private class DownloadImage extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            new SweetAlertDialog(context,
                    SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Resim İndirme.")
                    .setContentText("Resim İndirildi.")
                    .show();

        }

        @Override
        protected Bitmap doInBackground(String... URL) {

            String imageURL = URL[0];

            Bitmap bitmap = null;
            try {
                // Download Image from URL
                InputStream input = new java.net.URL(imageURL).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            // Set the bitmap into ImageView
            imageView.setImageBitmap(result);

            BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
            Bitmap bitmap = drawable.getBitmap();

            /*
            MediaStore.Images.Media.insertImage(getContentResolver(), bitmap,
                    "InstagramImage", "instagramDownload");
            */

            createDirIfNotExists();

            try {
                FileOutputStream fos = new FileOutputStream(path + "deneme.jpg");

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

                fos.flush();
                fos.close();

            } catch (Exception e) {
                Log.e("MyLog", e.toString());
            }

        }

        public void createDirIfNotExists() {

            File file = new File(path);
            if (!file.exists()) {
                if (!file.mkdir()) {
                    Log.e("TravellerLog :: ", "Problem creating Image folder");

                }else {
                    Log.e("TravellerLog :: ", "Dosya olusturuldu");
                }
            }else {
                Log.e("TravellerLog :: ", "Dosya zaten var");
            }
        }

    }

}
