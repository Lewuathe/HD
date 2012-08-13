package com.tech_tec.sample.camera;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class GridViewTestActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.gridviewtest);
		
		GridView grid = (GridView)this.findViewById(R.id.gridview);
		ArrayList<String> viewTexts = new ArrayList<String>();
		for(int i = 0; i < 100; i++) {
			viewTexts.add("hello" + i);
		}
		grid.setAdapter(new ArrayAdapter<String>(this, R.layout.textview, viewTexts));
	}
}
