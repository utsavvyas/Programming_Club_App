package programmingclub.daiict.classes.blog_classes;

/**
 * Created by parth panchal on 08-04-2015.
 */

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import programmingclub.daiict.R;

public class BlogView extends Activity {

    String array  = "";
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState1) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState1);
        setContentView(R.layout.blog_viewer);
        //ListView listView = (ListView)AnnouncementView(R.id.list_comment);
        if(getIntent().getExtras()!=null)
            System.out.println("hi");
        Bundle savedInstanceState = getIntent().getExtras();
        String title = (String) savedInstanceState.get("Title");
        String author = (String) savedInstanceState.get("Author");
        String Content = (String) savedInstanceState.get("Content");
        JSONObject json;

        try {
            json = new JSONObject((String)savedInstanceState.get("comment"));
            JSONArray arr = json.getJSONArray("comments");
            for(int i=0;i<arr.length();i++)
            {
                array+=(Html.fromHtml(arr.getJSONObject(i).getJSONObject("author").getString("nice_name") + ": " + arr.getJSONObject(i).getString("content")).toString() +"\n");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.comment, android.R.id.text1, array);
        //listView.setAdapter(adapter);


        TextView t1,t2,t3;
        t1 = (TextView)(findViewById(R.id.textView1));
        t2 = (TextView)(findViewById(R.id.textView2));
        t3 = (TextView)(findViewById(R.id.textView3));
        t3.setMovementMethod(new ScrollingMovementMethod());
        t3.setMovementMethod(LinkMovementMethod.getInstance());
        t1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        t2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

        Button comm=(Button)(findViewById(R.id.list_comment));
        comm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent("programmingclub.daiict.COMMENTVIEW");
                Bundle b=new Bundle();
                b.putString("comments", array);
                in.putExtras(b);
                startActivity(in);
            }
        });
        if(t1==null)
            System.out.println("1");
        if(t2==null)
            System.out.println("2");
        if(t3==null)
            System.out.println("3");




        t1.setTextColor(Color.BLACK);
        t2.setTextColor(Color.BLACK);
        t3.setTextColor(Color.BLACK);

        t1.setText(title);
        t2.setText(author);
        t3.setText(Content);


    }


}
