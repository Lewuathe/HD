package com.tech_tec.sample.luigi;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class PictureListActivity extends Activity {
	//private static final String URL = "http://192.168.1.12/~mazda/search.php";
	private static final String URL = "http://192.168.100.103/hacknewday/search.php";
	private LinearLayout topLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		this.topLayout = new LinearLayout(this);
		this.topLayout.setGravity(Gravity.CENTER);
		this.topLayout.setOrientation(LinearLayout.VERTICAL);
		//this.topLayout.setBackgroundColor(Color.YELLOW);
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.header_back);
		this.topLayout.setBackgroundDrawable(new BitmapDrawable(bitmap));
		
		pictureAddToLayout(this.topLayout);
		this.setContentView(this.topLayout);
		
		asyncSendPicturesAndGetLuigiPictures();
	}
	private void pictureAddToLayout(ViewGroup group) {
		ImageView view = new ImageView(this);
		view.setLayoutParams(new ViewGroup.LayoutParams(200, 200));
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
				
				GridView grid = new GridView(PictureListActivity.this);
				grid.setNumColumns(1);
				grid.setGravity(Gravity.CENTER);
				//grid.setColumnWidth(60);
				grid.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
				grid.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
				grid.setBackgroundColor(Color.WHITE);
				
				PictureListActivity.this.topLayout.addView(grid);
				
				ArrayList<Bitmap> bitMaps = new ArrayList<Bitmap>();
				asyncGetAndViewPictures(result, grid, bitMaps);
				grid.setAdapter(new BitmapAdapter(PictureListActivity.this, R.layout.imageview, bitMaps));
			}
		}.execute();
	}
	private void asyncGetAndViewPictures(List<String> urls, GridView grid, ArrayList<Bitmap> bitMaps) {
		for (String url : urls) {
			new ImageViewTask(bitMaps, grid, PictureListActivity.this).execute(url);
		}
	}
}