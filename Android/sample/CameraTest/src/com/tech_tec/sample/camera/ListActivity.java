package com.tech_tec.sample.camera;

import java.io.InputStream;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ListActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			
			super.onCreate(savedInstanceState);
			this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
			this.setContentView(R.layout.list);
			
			LinearLayout layout = (LinearLayout)this.findViewById(R.id.top_layout);
			
			//SD�J�[�h�ɂ����ۂ���ʂɕ\��
			ImageView view = new ImageView(this);
			view.setImageBitmap(BitmapFactory.decodeFile("/sdcard/test.jpg"));//�{���̓p�����[�^�Ŏ󂯎����Path
			layout.addView(view);
			
			//HTTP�o�R�ŉ�ۂ�\��
			InputStream in = new URL("http://k.yimg.jp/images/mht/2012/0616_dadday.png").openStream();
			ImageView y = new ImageView(this);
			y.setImageBitmap(BitmapFactory.decodeStream(in));
			layout.addView(y);
			
			//�B�e�����ʐ^�������Ǝ��ɍs��
			view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivity(new Intent(ListActivity.this, GridViewTestActivity.class));
				}
			});
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
