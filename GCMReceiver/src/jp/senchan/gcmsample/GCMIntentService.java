package jp.senchan.gcmsample;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {
	
	public GCMIntentService() {
		super(GCMSaple.GCM_SENDER_ID);
	}

    @Override
    public void onRegistered(Context context, String registrationId) {
        Log.w("registration id:", registrationId);
        sendMessage("id:" + registrationId);
    }
 
    @Override
    protected void onUnregistered(Context context, String registrationId) {
        sendMessage("C2DM Unregistered");
    }
 
    @Override
    public void onError(Context context, String errorId) {
        sendMessage("err:" + errorId);
    }
 
    @Override
    protected void onMessage(Context context, Intent intent) {
        String str = intent.getStringExtra("message");
        Log.w("message:", str);
        sendMessage(str);
    }
   
    private void sendMessage(String str) {
    }
}
