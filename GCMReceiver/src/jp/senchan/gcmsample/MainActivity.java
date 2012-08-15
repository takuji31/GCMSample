package jp.senchan.gcmsample;

import com.google.android.gcm.GCMRegistrar;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	private IntentFilter mIntentFilter;
	private MyBroadcastReceiver mReceiver;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		final String regId = GCMRegistrar.getRegistrationId(this);
		if (regId.equals("")) {
			GCMRegistrar.register(this, GCMSample.GCM_SENDER_ID);
		} else {
			Log.v("GCMSample", "Already registered");
		}

		Button buttonUnregister = (Button) findViewById(R.id.buttonUnregister);
		buttonUnregister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				GCMRegistrar.unregister(MainActivity.this);
			}
		});

	    mReceiver = new MyBroadcastReceiver();
	    mIntentFilter = new IntentFilter();
	    mIntentFilter.addAction(GCMSample.BROADCAST_ACTION);
	    registerReceiver(mReceiver, mIntentFilter);
		
		// Notificationを消す
		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		nm.cancelAll();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public class MyBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			Bundle bundle = intent.getExtras();
			String message = bundle.getString("message");

			Toast.makeText(context, message, Toast.LENGTH_LONG)
					.show();
		}

	}
}
