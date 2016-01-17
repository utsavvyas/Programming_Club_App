package programmingclub.daiict.classes.event_classes;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import programmingclub.daiict.R;
import programmingclub.daiict.classes.event_classes.EventListView;

/**
 * Created by NEHAL on 6/2/2015.
 */
public class notifyService {

    private NotificationManager mManager;


    public void createNotification(Context context,String contest)
    {
        mManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        Intent intent1 = new Intent(context,EventListView.class);
        Notification notification = new Notification(R.mipmap.event_f, contest , System.currentTimeMillis());
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.flags |= Notification.FLAG_SHOW_LIGHTS;
        notification.ledARGB = 0xff00ff00;
        notification.ledOnMS = 300;
        notification.ledOffMS = 1000;

        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingNotificationIntent = PendingIntent.getActivity(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        notification.setLatestEventInfo(context, contest, "Coding time!!!", pendingNotificationIntent);

        mManager.notify(0, notification);
    }

}
