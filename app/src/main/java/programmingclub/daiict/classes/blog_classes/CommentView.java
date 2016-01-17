package programmingclub.daiict.classes.blog_classes;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import programmingclub.daiict.R;

/**
 * Created by spock on 31/7/15.
 */
public class CommentView extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        Bundle b=getIntent().getExtras();
        String comm=b.getString("comments");
        if(comm!=null && !comm.equals("")) {
            TextView listView = (TextView) findViewById(R.id.listOfComment);
            listView.setMovementMethod(new ScrollingMovementMethod());
            listView.setMovementMethod(LinkMovementMethod.getInstance());
            listView.setText(comm);
            //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.comment,android.R.id.text1, comm);
            //listView.setAdapter(adapter);
        }
        else{
            TextView listView = (TextView) findViewById(R.id.listOfComment);
            listView.setText("No Comments");
        }

    }
}
