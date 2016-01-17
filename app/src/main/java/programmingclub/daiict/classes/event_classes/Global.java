package programmingclub.daiict.classes.event_classes;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by spock on 31/7/15.
 */
public class Global {

    public static ArrayList<String> listEvents = new ArrayList<>();
    public static ArrayList<Calendar> timeList = new ArrayList<>();

    private static Global single;

    public static Global getInstance()
    {
        return single;
    }

    public static void addListEvents(String s)
    {
        listEvents.add(s);
    }
    public static void addtimeList(Calendar c)
    {
        timeList.add(c);
    }


}
