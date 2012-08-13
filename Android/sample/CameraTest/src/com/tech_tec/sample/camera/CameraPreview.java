package com.tech_tec.sample.camera;

import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
	private SurfaceHolder holder;
	private Camera camera;
	
	public CameraPreview(Context context) {
		super(context);
		this.holder = super.getHolder();
		this.holder.addCallback(this);
		this.holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		this.camera.stopPreview();
		Camera.Parameters params = this.camera.getParameters();
		params.setPreviewSize(width, height);
		this.camera.setParameters(params);
		this.camera.startPreview();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		this.camera.takePicture(
			new Camera.ShutterCallback() {
				@Override
				public void onShutter() {
				}
			},
			new Camera.PictureCallback() {
				@Override
				public void onPictureTaken(byte[] data, Camera camera) {
				}
			}, new Camera.PictureCallback() {
				@Override
				public void onPictureTaken(byte[] data, Camera camera) {
					Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
					Matrix matrix = new Matrix();
					matrix.postRotate(90);
					
					Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
					
					try {
						String path = "/sdcard/test.jpg";
						FileOutputStream output = new FileOutputStream(path);
						//output.write(data);
						newBitmap.compress(CompressFormat.JPEG, 100, output);
						output.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					Toast.makeText(getContext(), "end", Toast.LENGTH_LONG).show();
					//camera.startPreview();
					
					getContext().startActivity(new Intent(getContext(), ListActivity.class));
				}
			}
		);
			
		return super.onTouchEvent(event);
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		this.camera = Camera.open();
		try {
			Parameters params = this.camera.getParameters();
			params.setFlashMode(Parameters.FLASH_MODE_ON);
			this.camera.setParameters(params);
			
			this.camera.setPreviewDisplay(this.holder);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		this.camera.stopPreview();
		camera.release();
	}
	
}
