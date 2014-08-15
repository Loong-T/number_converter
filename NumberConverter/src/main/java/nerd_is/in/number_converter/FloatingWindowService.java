package nerd_is.in.number_converter;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

/**
 * Created by Nerd on 2014/6/11 0011.
 */
public class FloatingWindowService extends Service {
    public static final String TAG = "FloatingWindowService";
    public static final int TEMP_NOTI_ID = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand startId: " + startId);

        FloatingWindowManager.createFloatingWindow(this);

        if (PreferenceUtils.isFirstTimeFloating(this)) {
            Intent resultIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(this)
                    .setContentTitle(getString(R.string.noti_title))
                    .setContentText(getString(R.string.noti_content_text))
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentIntent(pendingIntent);
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE))
                    .notify(TEMP_NOTI_ID, notiBuilder.build());

            PreferenceUtils.getCommonPref(this).edit()
                    .putBoolean(PreferenceUtils.PREF_VALUE_FIRST_FLOATING, false)
                    .apply();
        }

        return super.onStartCommand(intent, flags, startId);
    }
}
