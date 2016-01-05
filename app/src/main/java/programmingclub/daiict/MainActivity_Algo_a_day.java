package programmingclub.daiict;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity_Algo_a_day extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView w= (WebView) findViewById(R.id.webView);


        final asyncex as = new asyncex();
       // String param[] = new String[3];
        try {
            String result = as.execute("https://public-api.wordpress.com/rest/v1.1/sites/omkarsiteblog.wordpress.com/posts/5").get();
            JSONObject jobj = new JSONObject(result);
              //     jArray = jobj.getJSONArray("posts");

            String s = (String)jobj.get("content");
            String parts[]=s.split("\"");
            String url=parts[1];
            w.loadUrl(url);

            //Log.d("url",url);

        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            Toast.makeText(this.getApplicationContext(), "No Internet Connection.Please connect to Internet", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }





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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
