package com.tech_tec.sample.mario;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridView;

public class MarioActivity extends Activity {
	ImageViewTask imagetask;
	String url = "http://k.yimg.jp/images/mht/2012/0616_dadday.png";
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		try{
			super.onCreate(savedInstanceState);
			this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
			this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			this.setContentView(R.layout.gridviewtest);

			GridView grid = (GridView)this.findViewById(R.id.gridview);
			ArrayList<Bitmap> viewBitmaps = new ArrayList<Bitmap>();

			for(int i = 0; i < 100; i++) {
				imagetask = new ImageViewTask(viewBitmaps, grid, this);
				imagetask.execute(url);
				//InputStream in = new URL(url).openStream();
				//viewBitmaps.add(BitmapFactory.decodeStream(in));
			}

			grid.setAdapter(new BitmapAdapter(this, R.layout.imageview, viewBitmaps));
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}

