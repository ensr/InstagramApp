package com.ensr.instagram.tabs;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ensr.instagram.AppController;
import com.ensr.instagram.FullImage;
import com.ensr.instagram.R;
import com.ensr.instagram.adapters.ImageAdapter;
import com.ensr.instagram.alert.SweetAlertDialog;
import com.ensr.instagram.constants.TContants;
import com.ensr.instagram.customcompanent.Custom;
import com.ensr.instagram.response.TagsResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;

public class Export extends ActionBarActivity implements OnClickListener {

    private String url;
    private String url2;
    private String userId;
    private Button btnSearch;
    private ArrayList<String> pictureUrl;
    private EditText etTagName;
    private ToggleButton tbTagAndUserName;
    private Activity context = Export.this;
    private GridView gridView;
    private ImageAdapter imageAdapter;
    private Gson gson;
    private GsonBuilder builder;
    private int check;
    public int screenSize;

    public Export() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.export);

        etTagName = (EditText) findViewById(R.id.etTagsName);
        gridView = (GridView) findViewById(R.id.grid_view);
        tbTagAndUserName = (ToggleButton) findViewById(R.id.togglebutton);

        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this);

        builder = new GsonBuilder();
        gson = builder.create();

        Display display = getWindowManager().getDefaultDisplay();

        screenSize = display.getWidth();
        screenSize = ((screenSize / 3) - 15);

        getExportJsonData();
    }

    @Override
    public void onClick(View v) {
        if (v == btnSearch) {
            onToggleClicked(tbTagAndUserName);
            if (check == 1) {
                getUserJsonData();
            } else {
                getTagJsonData();
            }
        }

    }

    public void onToggleClicked(View view) {
        boolean on = ((ToggleButton) view).isChecked();

        if (on) {
            check = 1;
        } else {
            check = 0;
        }
    }

    @Override
    protected void onResume() {
        Custom.hideKeyboard(context);
        super.onResume();
    }

    @Override
    protected void onStart() {
        Custom.hideKeyboard(context);
        super.onStart();
    }

    private void getTagJsonData() {
        final ProgressDialog pDialog = new ProgressDialog(this);

        url = TContants.urlBeforeTag + etTagName.getText().toString()
                + TContants.urlAfterTag;
        JsonObjectRequest jsonObjReq;
        jsonObjReq = new JsonObjectRequest(Method.GET, url,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                pDialog.hide();
                Custom.hideKeyboard(context);
                getResponseParseWithGson(response);
                //tvFromThePicture.setVisibility(View.GONE);

                if (pictureUrl.size() == 0) {

                    new SweetAlertDialog(context,
                            SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Ne yazik ki.")
                            .setContentText("Fotograf bulunamadi.")
                            .show();
                } else {

                    new SweetAlertDialog(context,
                            SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Resimler Yuklendi")
                            .setContentText(
                                    "Resim bulma islemi tamamlandi.")
                            .show();
                }

                imageAdapter = new ImageAdapter(screenSize, pictureUrl, context);
                gridView.setAdapter(imageAdapter);
                getFullScreanByClick(gridView);

            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq,
                TContants.tag_json_obj);

    }

    private void getUserJsonData() {
        final ProgressDialog pDialog = new ProgressDialog(this);

        //userId responsu alınıyor
        url2 = TContants.urlBeforeUserId + etTagName.getText().toString() + TContants.urlAfterUser;

        JsonObjectRequest jsonObjReqForUser;
        jsonObjReqForUser = new JsonObjectRequest(Method.GET, url2,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                pDialog.hide();
                Custom.hideKeyboard(context);

                TagsResponse gsonData = gson.fromJson(response.toString(),
                        TagsResponse.class);

                userId = gsonData.data[0].id.toString();

            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReqForUser,
                TContants.tag_json_obj);


        ///////////////////////////////////////////////////////////////////
        url = TContants.urlBeforeUser + userId + TContants.urlAfterTag;

        JsonObjectRequest jsonObjReq;
        jsonObjReq = new JsonObjectRequest(Method.GET, url,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                pDialog.hide();
                Custom.hideKeyboard(context);
                getResponseParseWithGson(response);

                if (pictureUrl.size() == 0) {

                    new SweetAlertDialog(context,
                            SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Ne yazik ki.")
                            .setContentText("Fotograf bulunamadi.")
                            .show();
                } else {

                    new SweetAlertDialog(context,
                            SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Resimler Yuklendi")
                            .setContentText(
                                    "Resim bulma islemi tamamlandi.")
                            .show();
                }

                imageAdapter = new ImageAdapter(screenSize, pictureUrl, context);
                gridView.setAdapter(imageAdapter);
                getFullScreanByClick(gridView);

            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq,
                TContants.tag_json_obj);


    }

    private String getUserIdJsonData() {
        final ProgressDialog pDialog = new ProgressDialog(this);

        url = TContants.urlBeforeUserId + etTagName.getText().toString()
                + TContants.urlAfterUser;

        JsonObjectRequest jsonObjReq;
        jsonObjReq = new JsonObjectRequest(Method.GET, url,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                pDialog.hide();
                Custom.hideKeyboard(context);

                TagsResponse gsonData = gson.fromJson(response.toString(),
                        TagsResponse.class);
                userId = gsonData.data[0].id.toString();
            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq,
                TContants.tag_json_obj);
        return userId;
    }

    private void getExportJsonData() {

        final ProgressDialog pDialog = new ProgressDialog(this);
        JsonObjectRequest jsonObjReq;
        jsonObjReq = new JsonObjectRequest(Method.GET, TContants.urlPopuler,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                pDialog.hide();

                Custom.hideKeyboard(context);
                getResponseParseWithGson(response);

                imageAdapter = new ImageAdapter(screenSize, pictureUrl, context);
                gridView.setAdapter(imageAdapter);
                getFullScreanByClick(gridView);

            }

        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq,
                TContants.tag_json_obj);
    }

    private void getResponseParseWithGson(JSONObject response) {

        TagsResponse gsonData = gson.fromJson(response.toString(),
                TagsResponse.class);

        pictureUrl = new ArrayList<String>();
        for (int i = 0; i < gsonData.data.length; i++)
            pictureUrl.add(gsonData.data[i].images.standard_resolution.url);

    }

    private void getFullScreanByClick(GridView gw) {
        gw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), FullImage.class);
                String url = (String) imageAdapter.getItem(position);
                i.putExtra("id", url);
                startActivity(i);
            }
        });
    }

}
