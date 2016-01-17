package programmingclub.daiict;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import programmingclub.daiict.classes.About_Developers.AboutDevelopers;
import programmingclub.daiict.classes.announcement_classes.AnnouncementListView;
import programmingclub.daiict.classes.blog_classes.BlogListView;
import programmingclub.daiict.classes.category_classes.CategoryListView;
import programmingclub.daiict.classes.event_classes.EventListView;
import programmingclub.daiict.classes.tech_news_classes.MainActivity_RSS;

/**
 * @author dipenp
 *         <p/>
 *         This activity will add Navigation Drawer for our application and all the code related to navigation drawer.
 *         We are going to extend all our other activites from this BaseActivity so that every activity will have Navigation Drawer in it.
 *         This activity layout contain one frame layout in which we will add our child activity layout.
 */
public class MainActivity extends Activity {

    /**
     * Frame layout: Which is going to be used as parent layout for child activity layout.
     * This layout is protected so that child activity can access this
     */
    protected FrameLayout frameLayout;

    /**
     * ListView to add navigation drawer item in it.
     * We have made it protected to access it in child class. We will just use it in child class to make item selected according to activity opened.
     */

    protected ListView mDrawerList;

    /**
     * List item array for navigation drawer items.
     */
    protected String[] listArray = {"Blog", "Events", "Announcements", "Editorial", "Tech News"};

    /**
     * Static variable for selected item position. Which can be used in child activity to know which item is selected from the list.
     */
    protected static int position;

    /**
     * This flag is used just to check that launcher activity is called first time
     * so that we can open appropriate Activity on launch and make list item position selected accordingly.
     */
    protected static boolean isLaunch = true;

    /**
<<<<<<< HEAD
     *  Base layout node of this Activity.
     * */
    public  DrawerLayout mDrawerLayout;

    /**
     * Drawer listner class for drawer open, close etc.
     */
    public ActionBarDrawerToggle actionBarDrawerToggle;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private com.google.android.gms.common.api.GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        WebView w = (WebView) findViewById(R.id.webView);

        final asyncex as = new asyncex();
        // String param[] = new String[3];
        try {
            String result = as.execute("https://public-api.wordpress.com/rest/v1.1/sites/omkarsiteblog.wordpress.com/posts/5").get();
            JSONObject jobj = new JSONObject(result);
            //     jArray = jobj.getJSONArray("posts");

            String s = (String) jobj.get("content");
            String parts[] = s.split("\"");
            String url = parts[1];
            w.loadUrl(url);

            //Log.d("url",url);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            Toast.makeText(this.getApplicationContext(), "No Internet Connection.Please connect to Internet", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // set a custom shadow that overlays the main content when the drawer opens
        //mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        // set up the drawer's list view with items and click listener
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, listArray));
        mDrawerList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                openActivity(position);
            }
        });

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        initializeActionBarDrawerToggle();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new com.google.android.gms.common.api.GoogleApiClient.Builder(this).addApi(com.google.android.gms.appindexing.AppIndex.API).build();
    }

    public void initializeActionBarDrawerToggle() {
        // ActionBarDrawerToggle ties together the the proper interactions between the sliding drawer and the action bar app icon
        actionBarDrawerToggle = new ActionBarDrawerToggle(

                this,						/* host Activity */
                mDrawerLayout, 				/* DrawerLayout object */
                //android.support.v7.widget.Toolbar,
                R.drawable.ic_navigation_drawer,     /* nav drawer image to replace 'Up' caret */
                R.string.open_drawer,         /* "open drawer" description for accessibility */
                R.string.close_drawer)
                 /* "close drawer" description for accessibility */
        {
            @Override
            public void onDrawerClosed(View drawerView) {

                if (position >= 0)
                    getActionBar().setTitle(listArray[position]);

                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(getString(R.string.app_name));
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        };

        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);


        /**
         * As we are calling BaseActivity from manifest file and this base activity is intended just to add navigation drawer in our app.
         * We have to open some activity with layout on launch. So we are checking if this BaseActivity is called first time then we are opening our first activity.
         * */
        if (isLaunch) {
            /**
             *Setting this flag false so that next time it will not open our first activity.
             *We have to use this flag because we are using this BaseActivity as parent activity to our other activity.
             *In this case this base activity will always be call when any child activity will launch.
             */
            isLaunch = false;
            openActivity(-5);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        com.google.android.gms.appindexing.Action viewAction = com.google.android.gms.appindexing.Action.newAction(
                com.google.android.gms.appindexing.Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://programmingclub.daiict/http/host/path")
        );
        com.google.android.gms.appindexing.AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActionBar().setTitle(getString(R.string.app_name));
    }

    @Override
    public void onRestart() {
        super.onRestart();
        getActionBar().setTitle(getString(R.string.app_name));
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        com.google.android.gms.appindexing.Action viewAction = com.google.android.gms.appindexing.Action.newAction(
                com.google.android.gms.appindexing.Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://programmingclub.daiict/http/host/path")
        );
        com.google.android.gms.appindexing.AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    //inner class for retrive data from wordpress rest api
    public class asyncex extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub.
            String response = "";
            String url = arg0[0];

            try {
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                // optional default is GET
                con.setRequestMethod("GET");

                //add request header
                con.setRequestProperty("Accept", "text/json");

                int responseCode = con.getResponseCode();
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response += inputLine;
                }
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }
    }

    /**
     * @param position Launching activity when any list item is clicked.
     */
    protected void openActivity(int position) {

        /**
         * We can set title & itemChecked here but as this BaseActivity is parent for other activity,
         * So whenever any activity is going to launch this BaseActivity is also going to be called and
         * it will reset this value because of initialization in onCreate method.
         * So that we are setting this in child activity.
         */
//		mDrawerList.setItemChecked(position, true);
//		setTitle(listArray[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
        MainActivity.position = position; //Setting currently selected position in this field so that it will be available in our child activities.

        switch (position) {
            case 0:
                startActivity(new Intent(this, BlogListView.class));
                break;
            case 1:
                startActivity(new Intent(this, EventListView.class));
                break;
            case 2:
                startActivity(new Intent(this, AnnouncementListView.class));
                break;
            case 3:
                startActivity(new Intent(this, CategoryListView.class)); //where is the editorial
                break;
            case 4:
                startActivity(new Intent(this, MainActivity_RSS.class));
                break;

            default:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }

        // Toast.makeText(this, "Selected Item Position::"+position, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_splash, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }

        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent i =new Intent(this, AboutDevelopers.class);
                startActivity(i);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    /* We can override onBackPressed method to toggle navigation drawer*/
    @Override
    public void onBackPressed() {

        // Intent intent = new Intent(this,MainActivity.class); //I explicity instruct the activity to launch main acitivity and close itself
        //startActivity(intent);
        //position=-5;


        if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
            mDrawerLayout.closeDrawer(mDrawerList);

            //    finish();

        }/*else {
            mDrawerLayout.openDrawer(mDrawerList);

        }*/

        finish();
        //getActionBar().setTitle(getString(R.string.app_name));
    }
}