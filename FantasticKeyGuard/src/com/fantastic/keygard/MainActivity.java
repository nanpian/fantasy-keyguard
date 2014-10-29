package com.fantastic.keygard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	private static final boolean DBG = true;
	private static final String TAG = "Fantastic";
	public static StatusViewManager mStatusViewManager;
	private VelocityTracker mVelocityTracker;
	 
	@Override
	public void onAttachedToWindow() {
		// TODO Auto-generated method stub
		super.onAttachedToWindow();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
        if(DBG) Log.d(TAG, "onCreate()");
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				   WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mStatusViewManager = new StatusViewManager(this, this.getApplicationContext());
        setContentView(R.layout.main);
        startService(new Intent(MainActivity.this, FantasticLockService.class));

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	public void onDetachedFromWindow() {
		// TODO Auto-generated method stub
		super.onDetachedFromWindow();
		if(DBG) Log.d(TAG, "onDetachedFromWindow()");
	    //解除监听
	    mStatusViewManager.unregisterComponent();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
    private void addVelocityTrackerEvent(MotionEvent event) {  
        if (mVelocityTracker == null) {  
            mVelocityTracker = VelocityTracker.obtain();  
        }  
  
        mVelocityTracker.addMovement(event);  
        
    }  
  
    // 获得横向的手速  
    private int getTouchVelocityX() {  
        if (mVelocityTracker == null)  
            return 0;  
        mVelocityTracker.computeCurrentVelocity(1000);  
        int velocity = (int) mVelocityTracker.getXVelocity();  
        return Math.abs(velocity);  
    }  
	@Override
	public boolean onTouchEvent(MotionEvent event) {

	      addVelocityTrackerEvent(event);  
	      if(getTouchVelocityX()>0)this.finish();
	      return false;  
		}

}
