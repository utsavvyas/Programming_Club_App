package programmingclub.daiict.classes.About_Developers;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import programmingclub.daiict.R;

/**
 * Created by spock on 1/8/15.
 */
public class AboutDevelopers extends Activity {


    ListView list;
    String[] developers = {
            "utsav",
            "parth",
            "omkar",
            "aditya",
            "rajdeep"
    } ;
    Integer[] imageId = {
            R.drawable.utsav,
            R.drawable.parth,
            R.drawable.omkardp,
            R.drawable.omkardp,
            R.drawable.omkardp,

    };

    String[] email = {
            "utsavvyas16@gmail.com",
            "parthpanchal12196@gmail.com",
            "omkarvdamle13@gmail.com",
            "joglekara1@gmail.com",
            "rajpinge@gmail.com"
    }
            ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.developers);

        CustomList adapter = new
                CustomList(AboutDevelopers.this, developers, imageId,email);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
    }
}
