package com.tech_tec.sample.multipart;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Entity;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tech_tec.sample.android.R;

public class MultipartTestActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ((Button)this.findViewById(R.id.button)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
		        final String url = ((EditText)findViewById(R.id.urltext)).getText().toString();
				new AsyncTask<Void, Void, Boolean>() {
					@Override
					protected Boolean doInBackground(Void... params) {
						try {
							MultipartEntity entity = new MultipartEntity();
							entity.addPart("upload-file", new FileBody(new File("/sdcard/test.jpg")));
							
							HttpPost post = new HttpPost(url);
							post.addHeader("enctype", "multipart/form-data");
							post.setEntity(entity);
							
							AndroidHttpClient client = AndroidHttpClient.newInstance("Android UserAgent");
							String result = client.execute(post, new ResponseHandler<String>() {
								@Override
								public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
									return EntityUtils.toString(response.getEntity());
								}
							});
							
							Log.d("multipart_test", result);
							return true;
						} catch (Exception e) {
							e.printStackTrace();
						}
						return false;
					}	
				}.execute();
			}
		});
    }
}