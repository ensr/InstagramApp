package com.ensr.instagram;

import java.util.ArrayList;

import org.json.JSONObject;
import com.ensr.instagram.R;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ensr.instagram.adapters.ImageAdapter;
import com.ensr.instagram.alert.SweetAlertDialog;
import com.ensr.instagram.constants.TContants;
import com.ensr.instagram.customcompanent.Custom;
import com.ensr.instagram.response.TagsResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class MainActivity extends ActionBarActivity implements OnClickListener {

	private String url;
	private Button btnSearch;
	private TextView tvFromThePicture;
	private ArrayList<String> pictureUrl;
	private EditText etTagName;
	private ToggleButton tbTagAndUserName;
	private Activity context = MainActivity.this;
	private GridView gridView;
	private ImageAdapter imageAdapter;
	private Gson gson;
	private GsonBuilder builder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		/*
		 * Views
		 */
		
		etTagName = (EditText) findViewById(R.id.etTagsName);
		gridView = (GridView) findViewById(R.id.grid_view);
		tvFromThePicture = (TextView) findViewById(R.id.tvReponseData);
		tbTagAndUserName = (ToggleButton) findViewById(R.id.togglebutton);
		btnSearch = (Button) findViewById(R.id.btnSearch);
		btnSearch.setOnClickListener(this);

		/*
		 * library
		 */
		builder = new GsonBuilder();
		gson = builder.create();

	}

	@Override
	public void onClick(View v) {
		if (v == btnSearch) {
			getJsonData();
		}

	}
	
	public void onToggleClicked(View view) {
	    boolean on = ((ToggleButton) view).isChecked();
	    
	    if (on) {
	        Toast.makeText(context, "True", Toast.LENGTH_LONG).show();
	    } else {
	    	Toast.makeText(context, "False", Toast.LENGTH_LONG).show();
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

	private void getJsonData() {
		final ProgressDialog pDialog = new ProgressDialog(this);

		url = TContants.urlBeforeTag + etTagName.getText().toString()
				+ TContants.urlAfterTag;
		JsonObjectRequest jsonObjReq = new JsonObjectRequest(Method.GET, url,
				null, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						pDialog.hide();
						Custom.hideKeyboard(context);
						getResponseParseWithGson(response);
						tvFromThePicture.setVisibility(View.GONE);

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

						imageAdapter = new ImageAdapter(pictureUrl, context);
						gridView.setAdapter(imageAdapter);

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
		for (int i = 0; i < gsonData.data.length; i++) {

			pictureUrl.add(gsonData.data[i].images.standard_resolution.url
					.toString());

		}

	}

}
