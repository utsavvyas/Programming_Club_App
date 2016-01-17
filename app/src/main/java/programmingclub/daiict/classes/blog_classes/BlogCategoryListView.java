package programmingclub.daiict.classes.blog_classes;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

import programmingclub.daiict.classes.my_card.Card;
import programmingclub.daiict.classes.my_card.CardArrayAdapter;
import programmingclub.daiict.MainActivity;
import programmingclub.daiict.classes.database_management_class.MySQLiteHelper;
import programmingclub.daiict.R;

/**
 * Created by spock on 31/7/15.
 */
public class BlogCategoryListView extends MainActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "BlogCategoryListView Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://programmingclub.daiict/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "BlogCategoryListView Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://programmingclub.daiict/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_blog_list_view);
        // getLayoutInflater().inflate(R.layout.main_layout, frameLayout);

        /**
         * Setting title and itemChecked
         */
        mDrawerList.setItemChecked(position, true);
        setTitle(listArray[position]);

        Bundle b = getIntent().getExtras();
        String cat = b.getString("category");

        final ListView listView = (ListView) findViewById(R.id.card_listView);
        final MySQLiteHelper mySQLiteHelper = new MySQLiteHelper(this);
        List<String> list = mySQLiteHelper.getBlogCategory(cat);
        CardArrayAdapter cardArrayAdapter = new CardArrayAdapter(getApplicationContext(), R.layout.list_item_card);
        for (int i = 0; i < list.size(); i++)
            cardArrayAdapter.add(new Card(list.get(i), ""));
        listView.setAdapter(cardArrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in = new Intent("programmingclub.daiict.BLOGVIEW");
                Blog b = mySQLiteHelper.getBlog(((Card) listView.getItemAtPosition(position)).getLine1());
                Bundle bundle = new Bundle();
                bundle.putString("Author", b.getUsrername());
                bundle.putString("Title", b.getTitle());
                bundle.putString("Content", b.getContent());
                try {

                    asyncex as1 = new asyncex();
                    String s = as1.execute("https://public-api.wordpress.com/rest/v1.1/sites/proclubdaiict.wordpress.com/posts/" + b.getId() + "/replies/").get();
                    bundle.putString("comment", s);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                in.putExtras(bundle);
                startActivity(in);
            }
        });


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

}
