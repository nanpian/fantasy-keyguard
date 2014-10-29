package com.fantastic.keygard;

import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class FantasticLockService extends Service {

	
	protected static final boolean DBG = false;
	private static final String TAG="FantasticLockService";
	private Intent mFxLockIntent;
	 public static StatusViewManager mStatusViewManager;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		registerComponent();
		mFxLockIntent = new Intent(FantasticLockService.this, MainActivity.class);
		mFxLockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterComponent();
		//被销毁时启动自身，保持自身在后台存活
		startService(new Intent(FantasticLockService.this, FantasticLockService.class));
	}

	//监听来自用户按Power键点亮点暗屏幕的广播
	private BroadcastReceiver mScreenOnOrOffReceiver = new BroadcastReceiver() {
		
		private KeyguardManager mKeyguardManager;
		private KeyguardLock mKeyguardLock;
		private Intent mFxLockIntent;

		@Override
		public void onReceive(Context context, Intent intent)
		{

			if(DBG) Log.d(TAG, "mScreenOffReceiver-->" + intent.getAction());
			
			if (intent.getAction().equals("android.intent.action.SCREEN_ON")
					|| intent.getAction().equals("android.intent.action.SCREEN_OFF"))
			{
				mKeyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
				mKeyguardLock = mKeyguardManager.newKeyguardLock("FxLock");
				//屏蔽手机内置的锁屏
				mKeyguardLock.disableKeyguard();
				//启动该第三方锁屏
				mFxLockIntent = new Intent(FantasticLockService.this, MainActivity.class);
				mFxLockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(mFxLockIntent);
			}	
			
			if(intent.getAction().equals("com.phicomm.hu.action.music"))
			{
			}
		}
	};
	
    //注册广播监听
	public void registerComponent(){
		if(DBG) Log.d(TAG, "registerComponent()");
		IntentFilter mScreenOnOrOffFilter = new IntentFilter();
		mScreenOnOrOffFilter.addAction("android.intent.action.SCREEN_ON");
		mScreenOnOrOffFilter.addAction("android.intent.action.SCREEN_OFF");
		FantasticLockService.this.registerReceiver(mScreenOnOrOffReceiver, mScreenOnOrOffFilter);
	}
	
	public void unregisterComponent(){
		if(DBG) Log.d(TAG, "unregisterComponent()");
		if (mScreenOnOrOffReceiver != null)
		{
			FantasticLockService.this.unregisterReceiver(mScreenOnOrOffReceiver);
		}
		
	}
	
	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		return super.onUnbind(intent);
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
