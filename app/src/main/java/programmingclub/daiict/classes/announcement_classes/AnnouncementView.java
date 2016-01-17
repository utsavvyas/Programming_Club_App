package programmingclub.daiict.classes.announcement_classes;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import programmingclub.daiict.R;

/**
 * Created by spock on 31/7/15.
 */
public class AnnouncementView extends Activity{

    String array  = "";
    @Override
    protected void onCreate(Bundle savedInstanceState1) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState1);
        setContentView(R.layout.announcement_viewer);
        //ListView listView = (ListView)findViewById(R.id.list_comment);
        if(getIntent().getExtras()!=null)
            System.out.println("hi");
        Bundle savedInstanceState = getIntent().getExtras();
        String title = (String) savedInstanceState.get("Title");
        String Content = (String) savedInstanceState.get("Content");
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.comment, android.R.id.text1, array);
        //listView.setAdapter(adapter);


        TextView t1,t2;
        t1 = (TextView)(findViewById(R.id.textViewAnn1));
        t2 = (TextView)(findViewById(R.id.textViewAnn2));
        t2.setMovementMethod(new ScrollingMovementMethod());
        t2.setMovementMethod(LinkMovementMethod.getInstance());
        //t1.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);





        t1.setTextColor(Color.BLACK);
        t2.setTextColor(Color.BLACK);

        t1.setText(title);
        t2.setText(Content);


    }


}
