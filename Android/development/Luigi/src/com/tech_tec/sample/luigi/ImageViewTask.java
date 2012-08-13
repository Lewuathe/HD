package com.tech_tec.sample.luigi;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.GridView;

public class ImageViewTask extends AsyncTask<String, Void, ArrayList<Bitmap>> {
	Context conText;
	GridView gridView;
	private ArrayList<Bitmap> viewBitMaps;
	private Bitmap bitMap;
	public ImageViewTask(ArrayList<Bitmap> viewBitmaps, GridView gridview, Context context){
		super();
		viewBitMaps = viewBitmaps;
		gridView = gridview;
		conText = context;
	}
	
	protected ArrayList<Bitmap> doInBackground(String... url) {
		//URLÇ©ÇÁBitmap
		try{
			InputStream in = new URL(url[0]).openStream();
			bitMap = BitmapFactory.decodeStream(in);
			viewBitMaps.add(bitMap);
			return viewBitMaps;
		} catch (Exception e){
			e.printStackTrace();
		}
		return viewBitMaps;
	}

	protected void onPostExecute(ArrayList<Bitmap> result) {
		gridView.setAdapter(new BitmapAdapter(conText, R.layout.imageview, result));
	}
}