package jp.senchan.gcmsample;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService {

	public GCMIntentService() {
		super(GCMSample.GCM_SENDER_ID);
	}

	@Override
	public void onRegistered(Context context, String registrationId) {
		Log.w("registration id:", registrationId);
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet("http://dev.tkch.in/register/"
				+ registrationId);
		try {
			HttpResponse res = client.execute(get);
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				sendMessage("Push通知を登録しました");
				return;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sendMessage("Push通知の登録に失敗しました");
	}

	@Override
	protected void onUnregistered(Context context, String registrationId) {
		sendMessage("Push通知の登録を解除しました");
	}

	@Override
	public void onError(Context context, String errorId) {
		sendMessage("Push通知の登録に失敗しました");
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		String title = intent.getStringExtra("title");
		String message = intent.getStringExtra("message");
 		NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Intent touchIntent = new Intent(getApplicationContext(),
				MainActivity.class);
		PendingIntent pi = PendingIntent.getActivity(this, 0, touchIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				getApplicationContext());
		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setContentTitle(title);
		builder.setContentText(message);
		builder.setContentIntent(pi);
		builder.setTicker(title);
		nm.notify(1, builder.getNotification());
	}

	private void sendMessage(String str) {
		Intent broadcastIntent = new Intent();
		broadcastIntent.putExtra("message", str);
		broadcastIntent.setAction(GCMSample.BROADCAST_ACTION);
		getBaseContext().sendBroadcast(broadcastIntent);
	}
}
