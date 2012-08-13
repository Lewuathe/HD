package com.tech_tec.sample.luigi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraPreview extends SurfaceView {
	private CameraCallback callback = new CameraCallback();
	public CameraPreview(Context context) {
		super(context);
		this.getHolder().addCallback(this.callback);
		this.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		this.callback.camera.takePicture(
			new Camera.ShutterCallback() { @Override public void onShutter() { } },
			new Camera.PictureCallback() { @Override public void onPictureTaken(byte[] d, Camera c) { } },
			new Camera.PictureCallback() {
				@Override
				public void onPictureTaken(byte[] data, Camera camera) {
					this.writeToSdcard(this.lotateBitmap(data));
					goToPictureListActivity();
				}
				private Bitmap lotateBitmap(byte[] data) {
					Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
					Matrix matrix = new Matrix();
					matrix.postRotate(90);
					
					return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
				}
				private void writeToSdcard(Bitmap bitmap) {
					try {
						FileOutputStream output = new FileOutputStream(new File("/sdcard/test.jpg"));
						bitmap.compress(CompressFormat.JPEG, 100, output);
						output.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				private void goToPictureListActivity() {
					getContext().startActivity(new Intent(getContext(), PictureListActivity.class));
				}
			}
		);
		return super.onTouchEvent(event);
	}
	
	public class CameraCallback implements SurfaceHolder.Callback {
		private Camera camera;
		
		public Camera getCamera() {
			return this.camera;
		}
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			this.camera = Camera.open();
			try {
				this.camera.setPreviewDisplay(holder);
			} catch (IOException e) {
				e.printStackTrace();
			}
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
		public void surfaceDestroyed(SurfaceHolder holder) {
			this.camera.stopPreview();
			camera.release();
		}
	}
}
