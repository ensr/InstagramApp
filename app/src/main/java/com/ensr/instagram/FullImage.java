package com.ensr.instagram;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
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
    
    Button download, back;
    ImageView imageView;


    int savedValue;
    static int m = 0;
    static SharedPreferences sharedPreferences;
    String url;
    String path = Environment
            .getExternalStorageDirectory()
            .toString()+"/InstaStorage";

    private Activity context = FullImage.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image);

        imageView = (ImageView) findViewById(R.id.ivFullImage);
        download = (Button) findViewById(R.id.bDownload);
        back = (Button) findViewById(R.id.bGetBack);
        Intent i = getIntent();
        
        //Resim url'si alınarak picasso ile ekrana basıldı
        url = i.getExtras().getString("id");
        Picasso.with(this).load(url).into(imageView);

        download.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DownloadImage().execute(url);
                refresh();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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

            createDirIfNotExists();
            refresh();

            try {
                m = loadInt();
                FileOutputStream fos = new FileOutputStream(path + "/instaStorage" + m + ".jpg");
                saveInt("key", savedValue + 1);
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

    public void saveInt(String key, int value){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }
    public int loadInt(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        savedValue = sharedPreferences.getInt("key", 0);
        return savedValue;
    }

    public void refresh(){
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {*/
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            //File f = new File("folderPATH", "fileName"); //OR  File f = new File(YourCurrentPhotoPath);
            File f = new File(path);
            Uri contentUri = Uri.fromFile(f);
            mediaScanIntent.setData(contentUri);
            context.sendBroadcast(mediaScanIntent);
     /*   } else {
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory() + "/" + "FOLDER_TO_REFRESH")));
        }*/
    }
}
