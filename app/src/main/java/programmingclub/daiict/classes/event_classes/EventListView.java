package programmingclub.daiict.classes.event_classes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import programmingclub.daiict.MainActivity;
import programmingclub.daiict.classes.database_management_class.MySQLiteHelper;
import programmingclub.daiict.R;
import programmingclub.daiict.classes.service_classes.ScheduleClient;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;


public class EventListView extends MainActivity {

    ListView listView;
    ArrayList<String> values;
    private PendingIntent pendingIntent;
    private XmlPullParserFactory xmlFactoryObject;
    private XmlPullParser myparser;
    public ArrayList<Event> arr;
    MySQLiteHelper mySQLiteHelper = null;
    Button tv;
    TextView tv2;

    public class asyncex extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            try {
                arr = new ArrayList<Event>();
                URL url = new URL("https://www.hackerrank.com/calendar/feed.rss");
                xmlFactoryObject = XmlPullParserFactory.newInstance();
                myparser = xmlFactoryObject.newPullParser();
                myparser.setInput(url.openConnection().getInputStream(), "UTF_8");
                int eventType = myparser.getEventType();
                boolean inside = false;
                Event event = null;
                while (eventType != myparser.END_DOCUMENT) {
                    if (eventType == myparser.START_TAG) {
                        if (myparser.getName().equalsIgnoreCase("item")) {
                            inside = true;
                            event = new Event();
                        } else if (myparser.getName().equalsIgnoreCase("title")) {
                            if (inside)
                                event.setTitle(myparser.nextText());
                        } else if (myparser.getName().equalsIgnoreCase("description")) {
                            if (inside)
                                event.setDescription(myparser.nextText());
                        } else if (myparser.getName().equalsIgnoreCase("url")) {
                            if (inside)
                                event.setUrl(myparser.nextText());
                        } else if (myparser.getName().equalsIgnoreCase("startTime")) {
                            if (inside)
                                event.setStartTime(myparser.nextText());
                        } else if (myparser.getName().equalsIgnoreCase("endTime")) {
                            if (inside)
                                event.setEndTime(myparser.nextText());
                        }
                    } else if (eventType == XmlPullParser.END_TAG && myparser.getName().equalsIgnoreCase("item")) {
                        inside = false;
                        arr.add(0, event);
                    }
                    eventType = myparser.next();
                }


            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public int getWeekNo(int date, int month, int year) {
        Date d = new Date(year, month - 1, date);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.WEEK_OF_YEAR);

    }

    public void setWeekInstance(int weekNo, int year) {
        String ans = "      ";

        calendar.set(Calendar.WEEK_OF_YEAR, weekNo);
        calendar.set(Calendar.YEAR, year);
        Format formatter1 = new SimpleDateFormat("MMMM");
        String month = formatter1.format(calendar.getTime());
        Format formatter2 = new SimpleDateFormat("yyyy");
        month += " " + formatter2.format(calendar.getTime());


        SimpleDateFormat formatter = new SimpleDateFormat("dd"); // PST`
        Date startDate = calendar.getTime();
        String startDateInStr = formatter.format(startDate);
        ans += startDateInStr;

        for (int i = 1; i < 7; i++) {

            calendar.add(Calendar.DATE, 1);
            Date enddate = calendar.getTime();
            ans += "   " + formatter.format(enddate);
        }
        System.out.println(ans);
        tv.setText(month);

        tv2.setText(ans);
    }

    int weekOfYear = 0, year = 0;
    Calendar calendar = null;
    EventCardArrayAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list_view);


        //getLayoutInflater().inflate(R.layout.main_layout, frameLayout);

        /**
         * Setting title and itemChecked
         */

        //trying to get the drawer here as well
        frameLayout = (FrameLayout)findViewById(R.id.content_frame);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);

        // set a custom shadow that overlays the main content when the drawer opens
        //mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, listArray));
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                openActivity(position);
            }
        });

        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);

        initializeActionBarDrawerToggle();

        adapter = new EventCardArrayAdapter(this, R.layout.eventlayout);
        mySQLiteHelper = new MySQLiteHelper(this);
        Toast.makeText(getApplicationContext(), "Click on the event to set notification", LENGTH_LONG).show();
        values = new ArrayList<String>();
        listView = (ListView) findViewById(R.id.list);
        //CalendarView cal = (CalendarView)findViewById(R.id.calendar);
        tv = (Button) findViewById(R.id.currWeek);
        tv2 = (TextView) findViewById(R.id.currWeekDates);
        /*Finding current date*/
        Date d = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        weekOfYear = c.get(Calendar.WEEK_OF_YEAR);
        year = c.get(Calendar.YEAR);

        String ans = "      ";

        calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.WEEK_OF_YEAR, weekOfYear);
        calendar.set(Calendar.YEAR, year);
        Format formatter1 = new SimpleDateFormat("MMMM");
        String month = formatter1.format(new Date());
        Format formatter2 = new SimpleDateFormat("yyyy");
        month += " " + formatter2.format(new Date());


        SimpleDateFormat formatter = new SimpleDateFormat("dd"); // PST`
        Date startDate = calendar.getTime();
        String startDateInStr = formatter.format(startDate);
        ans += startDateInStr;

        for (int i = 1; i < 7; i++) {

            calendar.add(Calendar.DATE, 1);
            Date enddate = calendar.getTime();
            ans += "   " + formatter.format(enddate);
        }

        Button tv = (Button) findViewById(R.id.currWeek);
        tv.setText(month);
        TextView tv2 = (TextView) findViewById(R.id.currWeekDates);
        tv2.setText(ans);

        Button left = (Button) findViewById(R.id.left);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.add(Calendar.DATE, -13);
                weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
                year = calendar.get(Calendar.YEAR);
                setWeekInstance(calendar.get(Calendar.WEEK_OF_YEAR), calendar.get(Calendar.YEAR));
                List<Event> list = mySQLiteHelper.getAllEvent();
                adapter.remove();
                for (int i = 0; i < list.size(); i++) {
                    String temp[] = list.get(i).getStartTime().split(" ")[0].split("-");
                    String temp1[] = list.get(i).getEndTime().split(" ")[0].split("-");

                    if (weekOfYear == getWeekNo(Integer.parseInt(temp[2]), Integer.parseInt(temp[1]), Integer.parseInt(temp[0])))
                        adapter.add(list.get(i));
                    else if (year > Integer.parseInt(temp[0]) && year < Integer.parseInt(temp1[0]))
                        adapter.add(list.get(i));
                    else if (weekOfYear >= getWeekNo(Integer.parseInt(temp[2]), Integer.parseInt(temp[1]), Integer.parseInt(temp[0])) && year >= Integer.parseInt(temp[0]) && weekOfYear < getWeekNo(Integer.parseInt(temp1[2]), Integer.parseInt(temp1[1]), Integer.parseInt(temp1[0])) && year <= Integer.parseInt(temp1[0]))
                        adapter.add(list.get(i));

                }
                listView.setAdapter(adapter);
            }

        });
        Button right = (Button) findViewById(R.id.right);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("hiiiiiiiiiiii");
                calendar.add(Calendar.DATE, 1);
                weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);
                year = calendar.get(Calendar.YEAR);
                setWeekInstance(calendar.get(Calendar.WEEK_OF_YEAR), calendar.get(Calendar.YEAR));
                List<Event> list = mySQLiteHelper.getAllEvent();
                adapter.remove();
                for (int i = 0; i < list.size(); i++) {
                    String temp[] = list.get(i).getStartTime().split(" ")[0].split("-");
                    String temp1[] = list.get(i).getEndTime().split(" ")[0].split("-");

                    if (weekOfYear == getWeekNo(Integer.parseInt(temp[2]), Integer.parseInt(temp[1]), Integer.parseInt(temp[0])))
                        adapter.add(list.get(i));
                    else if (year > Integer.parseInt(temp[0]) && year < Integer.parseInt(temp1[0]))
                        adapter.add(list.get(i));
                    else if (weekOfYear >= getWeekNo(Integer.parseInt(temp[2]), Integer.parseInt(temp[1]), Integer.parseInt(temp[0])) && year >= Integer.parseInt(temp[0]) && weekOfYear < getWeekNo(Integer.parseInt(temp1[2]), Integer.parseInt(temp1[1]), Integer.parseInt(temp1[0])) && year <= Integer.parseInt(temp1[0])) {
                        adapter.add(list.get(i));
                    }

                }
                listView.setAdapter(adapter);
            }

        });


        asyncex as = new asyncex();
        try {
            String s = as.execute().get();
            for (int i = 0; i < arr.size(); i++) {
                System.out.println(arr.get(i).getTitle());
            }
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (ExecutionException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        String Title = null;
        String desc = null;
        String link = null;
        String start = null;
        String end = null;
        try {
            List<Event> e = mySQLiteHelper.getAllEvent();
            for (int i = 0; i < e.size(); i++) {
                Title = e.get(i).getTitle();
                desc = e.get(i).getDescription();
                link = e.get(i).getUrl();
                start = e.get(i).getStartTime();
                end = e.get(i).getEndTime();
                String temp[] = start.split(" ")[0].split("-");
                String temp1[] = end.split(" ")[0].split("-");

                if (weekOfYear == getWeekNo(Integer.parseInt(temp[2]), Integer.parseInt(temp[1]), Integer.parseInt(temp[0])))
                    adapter.add(e.get(i));
                else if (year > Integer.parseInt(temp[0]) && year < Integer.parseInt(temp1[0]))
                    adapter.add(e.get(i));
                else if (weekOfYear >= getWeekNo(Integer.parseInt(temp[2]), Integer.parseInt(temp[1]), Integer.parseInt(temp[0])) && year >= Integer.parseInt(temp[0]) && weekOfYear < getWeekNo(Integer.parseInt(temp1[2]), Integer.parseInt(temp1[1]), Integer.parseInt(temp1[0])) && year <= Integer.parseInt(temp1[0]))
                    adapter.add(e.get(i));

                values.add(Title + "\n" + desc + "\n" + link + "\nStart Time: " + start + "\nEnd Time: " + end);
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        for (int i = 0; i < arr.size(); i++) {
            boolean eq = false;
            for (int i1 = 0; i1 < values.size(); i1++) {
                if (values.get(i1).split("\n")[0].equals(arr.get(i).getTitle())) {
                    eq = true;
                    break;
                }
            }
            if (!eq) {
                values.add(arr.get(i).getTitle() + "\n" + arr.get(i).getDescription() + "\n" + arr.get(i).getUrl() + "\nStart Time: " + arr.get(i).getStartTime() + "\nEnd Time: " + arr.get(i).getEndTime());
                mySQLiteHelper.createEvent(arr.get(i));
                String start1 = arr.get(i).getStartTime();
                String temp[] = start1.split(" ")[0].split("-");
                String temp1[] = arr.get(i).getEndTime().split(" ")[0].split("-");

                if (weekOfYear == getWeekNo(Integer.parseInt(temp[2]), Integer.parseInt(temp[1]), Integer.parseInt(temp[0])))
                    adapter.add(arr.get(i));
                else if (year > Integer.parseInt(temp[0]) && year < Integer.parseInt(temp1[0]))
                    adapter.add(arr.get(i));
                else if (weekOfYear >= getWeekNo(Integer.parseInt(temp[2]), Integer.parseInt(temp[1]), Integer.parseInt(temp[0])) && year >= Integer.parseInt(temp[0]) && weekOfYear < getWeekNo(Integer.parseInt(temp1[2]), Integer.parseInt(temp1[1]), Integer.parseInt(temp1[0])) && year <= Integer.parseInt(temp1[0]))
                    adapter.add(arr.get(i));

            }
        }


        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {

                ScheduleClient scheduleClient = new ScheduleClient(EventListView.this);
                scheduleClient.doBindService();

                Event details = (Event) listView.getItemAtPosition(arg2);
                //String temp[] = split("\n");
                String a[] = details.getStartTime().split(" ");
                // Create a new calendar set to the date chosen
                // we set the time to midnight (i.e. the first minute of that day)
                Calendar c = Calendar.getInstance();
                String aa[] = a[0].split("-");
                c.set(Integer.parseInt(aa[0]), Integer.parseInt(aa[1]) - 1, Integer.parseInt(aa[2]));
                String aaa[] = a[1].split(":");
                c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(aaa[0]));
                c.set(Calendar.MINUTE, Integer.parseInt(aaa[1]));
                c.set(Calendar.SECOND, Integer.parseInt(aaa[2]));

                Global.timeList.add(c);
                Global.listEvents.add(details.getTitle());
                //Bundle bundle = new Bundle();
                //System.out.print(temp[1]+" bundle ");
                //bundle.putString("contest_name", temp[1]);
                Intent myIntent = new Intent(EventListView.this, MyReceiver.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //myIntent.putExtras(bundle);
                PendingIntent p;
                p = PendingIntent.getBroadcast(EventListView.this, 0, myIntent, 0);

                AlarmManager alarmManager = (AlarmManager) EventListView.this.getSystemService(EventListView.this.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), p);

                // Ask our service to set an alarm for that date, this activity talks to the client that talks to the service
                // if(scheduleClient!=null)
                // scheduleClient.setAlarmForNotification(c,temp[1]);
                // Notify the user what they just did
                Toast.makeText(getApplicationContext(), "Notification set for: " + details.getStartTime(), LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getActionBar().setTitle(listArray[1]);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        mDrawerList.setItemChecked(position, true);
        getActionBar().setTitle(listArray[1]);
    }
}
