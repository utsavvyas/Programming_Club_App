package programmingclub.daiict.classes.event_classes;

/**
 * Created by NEHAL on 6/1/2015.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.Calendar;

public class MyReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent)
    {
        Bundle b = intent.getExtras();
        notifyService service;
        service = new notifyService();
        System.out.print(b.getString("contest_name"));
        Calendar c = Calendar.getInstance();

        for(int i=0;i< Global.listEvents.size();i++){
            if(Global.timeList.get(i).compareTo(c)<=0) {
                service.createNotification(context,Global.listEvents.get(i));
                Global.listEvents.remove(i);
                Global.timeList.remove(i);
                break;
            }
        }

    }

}