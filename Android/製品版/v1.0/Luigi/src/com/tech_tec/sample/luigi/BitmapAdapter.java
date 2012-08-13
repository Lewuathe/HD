package com.tech_tec.sample.luigi;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class BitmapAdapter extends ArrayAdapter<Bitmap> {

	public BitmapAdapter(Context context, int resource, List<Bitmap> bitMaps) {
		super(context, resource, bitMaps);
	}	
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = new ImageView(getContext());
		}
		ImageView view = (ImageView) convertView;
		view.setImageBitmap((Bitmap) getItem(position));
		return view;
	}
}