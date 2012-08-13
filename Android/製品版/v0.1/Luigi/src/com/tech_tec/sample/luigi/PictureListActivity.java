package com.tech_tec.sample.luigi;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class PictureListActivity extends Activity {
	private static final String URL = "http://192.168.1.12/~mazda/search.php";
	private LinearLayout topLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		ScrollView scrollView = new ScrollView(this);
		this.topLayout = new LinearLayout(this);
		this.topLayout.setOrientation(LinearLayout.VERTICAL);
		scrollView.addView(this.topLayout);
		
		pictureAddToLayout(topLayout);
		this.setContentView(scrollView);
		
		asyncSendPicturesAndGetLuigiPictures();
	}
	private void pictureAddToLayout(ViewGroup group) {
		ImageView view = new ImageView(this);
		view.setImageBitmap(BitmapFactory.decodeFile("/sdcard/test.jpg"));
		group.addView(view);	
	}
	private void asyncSendPicturesAndGetLuigiPictures() {
		new AsyncTask<Void, Void, ArrayList<String>>() {
			@Override
			protected ArrayList<String> doInBackground(Void... params) {
				ArrayList<String> urls = sendRequest();
				Log.d("luigi", "************************************");
				for (String url : urls) {
					Log.d("luigi", url);
				}
				Log.d("luigi", "************************************");
				return urls;
			}
			private ArrayList<String> sendRequest() {
				MultipartEntity entity = new MultipartEntity();
				entity.addPart("upload-file", new FileBody(new File("/sdcard/test.jpg")));
				
				HttpPost post = new HttpPost(URL);
				post.addHeader("enctype", "multipart/form-data");
				post.setEntity(entity);
				
				AndroidHttpClient client = AndroidHttpClient.newInstance("Android UserAgent");
				ArrayList<String> result = new ArrayList<String>();
				try {
					result = client.execute(post, new ResponseHandler<ArrayList<String>>() {
						@Override
						public ArrayList<String> handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
							return this.parseToUrls(response.getEntity().getContent());
						}
						private ArrayList<String> parseToUrls(InputStream input) {
							ArrayList<String> urls = new ArrayList<String>();
							
							try {
								DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
								DocumentBuilder builder = factory.newDocumentBuilder();
	
								Document document = builder.parse(input);
								Element root = document.getDocumentElement();
								NodeList children = root.getChildNodes();
	
								for(int i = 0; i < children.getLength(); i++) {
									Node child = children.item(i);
									if (child instanceof Element) {
										String url = child.getAttributes().getNamedItem("uri").getNodeValue();
										urls.add(url);
									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}

							return urls;
						}
					});
					client.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				return result;
			}
			@Override
			protected void onPostExecute(ArrayList<String> result) {
				super.onPostExecute(result);
				asyncGetAndViewPictures(result);
			}
		}.execute();
	}
	private void asyncGetAndViewPictures(List<String> urls) {
		for (String url : urls) {
			new AsyncTask<String, Void, View>() {
				@Override
				protected View doInBackground(String... urls) {
					try {
						InputStream in = new URL(urls[0]).openStream();
						ImageView view = new ImageView(PictureListActivity.this);
						view.setImageBitmap(BitmapFactory.decodeStream(in));
						
						return view;
					} catch (Exception e) {
						e.printStackTrace();
					}
					return null;//new ImageView(PictureListActivity.this);
				}
				
				@Override
				protected void onPostExecute(final View result) {
					super.onPostExecute(result);
					Log.d("luigi", "add new view...");
					PictureListActivity.this.topLayout.addView(result);
				}
			}.execute(url);
		}
	}
}