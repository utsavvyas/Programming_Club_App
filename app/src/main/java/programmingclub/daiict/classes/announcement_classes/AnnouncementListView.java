package programmingclub.daiict.classes.announcement_classes;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import programmingclub.daiict.classes.my_card.Card;
import programmingclub.daiict.classes.my_card.CardArrayAdapter;
import programmingclub.daiict.MainActivity;
import programmingclub.daiict.classes.database_management_class.MySQLiteHelper;
import programmingclub.daiict.R;
import programmingclub.daiict.classes.blog_classes.Blog;

/**
 * Created by spock on 31/7/15.
 */
public class AnnouncementListView extends MainActivity {

    SQLiteDatabase myDB= null;
    JSONArray jArray = null;
    ListView listView;
    ArrayList<String> values;
    ArrayList<Integer> arr;


    public class asyncex extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... arg0) {
            // TODO Auto-generated method stub.
            String response = "";
            String url = "https://public-api.wordpress.com/rest/v1.1/sites/proclubannouncementdaiict.wordpress.com/posts?number=30";
            try{
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
                    response+=inputLine;
                }
                in.close();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return response;
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_list_view);

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

        final MySQLiteHelper mySQLiteHelper = new MySQLiteHelper(this);
        final asyncex as = new asyncex();
        JSONArray jArray = null;
        String param[] = new String[3];
        CardArrayAdapter adapter = new CardArrayAdapter(getApplicationContext(),R.layout.list_item_card);
        try {
            String result = as.execute("https://public-api.wordpress.com/rest/v1.1/sites/proclubannouncementdaiict.wordpress.com/posts?number=100").get();
            JSONObject jobj = new JSONObject(result);
            jArray = jobj.getJSONArray("posts");
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            Toast.makeText(getApplicationContext(), "No Intenet Connection Please connect to Internet", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        arr=new ArrayList<Integer>();
        values=new ArrayList<String>();
        listView = (ListView) findViewById(R.id.card_listView);

        //taking all blogs from sqlite database
        List<Blog> list = mySQLiteHelper.getAllAnnouncMent();

        String Title = "";
        String Content = "";

        for(int i=0;i<list.size();i++)
        {
            Title = list.get(i).getTitle();
            Content = list.get(i).getContent();
            //System.out.println(Title);
            values.add(0,Title);
            Card c=new Card(Title,Content);
            //arr.add(0,0);
            adapter.add(c);
        }

        //convert to string format from json array
        if(jArray!=null)
        {
            for(int i=0;i<jArray.length();i++)
            {
                JSONObject author;
                try {
                    String content = (Html.fromHtml(((JSONObject) jArray.get(i)).getString("content")).toString());
                    String title = ((JSONObject)jArray.get(i)).getString("title");
                    boolean eq=false;
                    for(int i1=0;i1<values.size();i1++){
                        if(values.get(i1).equals(title)){
                            eq=true;
                            break;
                        }
                    }
                    if(!eq){

                        values.add(0, title);
                        Card c=new Card(title,content);
                        //arr.add(0, 1);
                        adapter.add(c);
                        Blog blog = new Blog();
                        blog.setContent(content);
                        blog.setTitle(title);
                        mySQLiteHelper.createAnnouncement(blog);
                    }
                }
                catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        //adapter.getData(arr);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;
                List<Blog> list = mySQLiteHelper.getAllAnnouncMent();
                Card  itemValue = (Card) listView.getItemAtPosition(position);
                String Title = "";
                String Content = null;

                int i=0;
                while(!Title.equals(itemValue.getLine1()) )
                {

                    if(itemValue.getLine1().equals(list.get(i).getTitle())) {
                        Title = list.get(i).getTitle();
                        Content = list.get(i).getContent();
                        break;
                    }
                    i++;
                }
                Bundle bundle = new Bundle();
                bundle.putString("Title", Title);
                bundle.putString("Content", Content);


                Intent AnnView=new Intent("programmingclub.daiict.ANNOUNCEMENTVIEW");
                AnnView.putExtras(bundle);
                startActivity(AnnView);

            }

        });


    }

    @Override
    public void onResume() {
        super.onResume();
        getActionBar().setTitle(listArray[2]);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        getActionBar().setTitle(listArray[2]);
    }
}
