package programmingclub.daiict.classes.blog_classes;

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
import java.util.concurrent.ExecutionException;

import programmingclub.daiict.classes.my_card.Card;
import programmingclub.daiict.classes.my_card.CardArrayAdapter;
import programmingclub.daiict.MainActivity;
import programmingclub.daiict.classes.database_management_class.MySQLiteHelper;
import programmingclub.daiict.R;


public class BlogListView extends MainActivity {

    SQLiteDatabase myDB= null;
    JSONArray jArray = null;
    int u=0;
    ListView listView;
    ArrayList<Card> values;
    ArrayList<Integer> arr;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_list_view);

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
        try {
            String result = as.execute("https://public-api.wordpress.com/rest/v1.1/sites/proclubdaiict.wordpress.com/posts?number=100").get();
            JSONObject jobj = new JSONObject(result);
            jArray = jobj.getJSONArray("posts");
        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            Toast.makeText(this.getApplicationContext(), "No Intenet Connection.Please connect to Internet", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        arr=new ArrayList<Integer>();
        values=new ArrayList<Card>();
        listView = (ListView) findViewById(R.id.card_listView);
        CardArrayAdapter cardArrayAdapter = new CardArrayAdapter(this.getApplicationContext(), R.layout.list_item_card);
        //taking all blogs from sqlite database
        List<Blog> list = mySQLiteHelper.getAllBlog();

        String UserName="";
        String Title = "";
        String Tag = "";
        String Content = "";
        String Comment = "";
        int id=0;

        for(int i=0;i<list.size();i++)
        {
            UserName = list.get(i).getUsrername();
            Title = list.get(i).getTitle();
            id = list.get(i).getId();
            Content = list.get(i).getContent();
            Comment = list.get(i).getComment();
            Tag = list.get(i).getTag();
            //System.out.println(Title);
            Card card = new Card(Title,Content);
            values.add(0,card);
            cardArrayAdapter.add(card);
            arr.add(0,0);
        }

        //convert to string format from json array
        if(jArray!=null)
        {
            for(int i=0;i<jArray.length();i++)
            {
                JSONObject author;
                try {
                    author = (JSONObject)((JSONObject)jArray.get(i)).get("author");
                    String aut = author.getString("name");
                    String content = (Html.fromHtml(((JSONObject) jArray.get(i)).getString("content")).toString());
                    if(content.contains("<img"))
                        content.concat("asadsdsdd");
                    String title = ((JSONObject)jArray.get(i)).getString("title");
                    String category = ((JSONObject)jArray.get(i)).getString("categories");
                    category=category.split("\"")[1];
                    int blogid =  Integer.parseInt(((JSONObject) jArray.get(i)).getString("ID"));
                    boolean eq=false;
                    for(int i1=0;i1<values.size();i1++){
                        if(values.get(i1).getLine1().equals(title)){
                            eq=true;
                            break;
                        }
                    }
                    if(!eq){
                        Card card = new Card(title,content);
                        cardArrayAdapter.add(card);
                        values.add(0, card);
                        arr.add(0, 1);
                        Blog blog = new Blog();
                        blog.setUsrername(aut);
                        blog.setContent(content);
                        blog.setTitle(title);
                        blog.setId(blogid);
                        blog.setTag(category);
                        mySQLiteHelper.createBlog(blog);
                    }
                }
                catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }


        listView.setAdapter(cardArrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index

                List<Blog> list = mySQLiteHelper.getAllBlog();
                Card  itemValue    = (Card) listView.getItemAtPosition(position);
                String UserName="";
                String Title = "";
                String Tag = null;
                String Content = null;
                String Comment = null;
                int bid = 0;

                int i=0;
                while(!Title.equals(itemValue.getLine1()))
                {

                    if(itemValue.getLine1().equals(list.get(i).getTitle())) {
                        UserName = list.get(i).getUsrername();
                        Title = list.get(i).getTitle();
                        bid = list.get(i).getId();
                        Content = list.get(i).getContent();
                        Comment = list.get(i).getComment();
                        Tag = list.get(i).getTag();
                        break;
                    }
                    i++;
                }
                Bundle bundle = new Bundle();
                bundle.putString("Author", UserName);
                bundle.putString("Title", Title);
                bundle.putString("Content", Content);


                try {
                    asyncex as1=new asyncex();
                    String s = as1.execute("https://public-api.wordpress.com/rest/v1.1/sites/proclubdaiict.wordpress.com/posts/" + bid + "/replies/").get();
                    bundle.putString("comment",s);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                Intent BlogView=new Intent("programmingclub.daiict.BLOGVIEW");
                BlogView.putExtras(bundle);
                startActivity(BlogView);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getActionBar().setTitle(listArray[0]);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        getActionBar().setTitle(listArray[0]);
    }
}
