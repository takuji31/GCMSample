package jp.senchan.gcmsample;

import jp.senchan.gcmkey.GCMKey;
import android.app.Application;

public class GCMSample extends Application {
	//Please change this
	public static final String GCM_SENDER_ID = GCMKey.GCM_KEY;
	public static final String BROADCAST_ACTION = "ACTION_GCM_SAMPLE_IntentService";
}
