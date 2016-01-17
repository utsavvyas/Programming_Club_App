package programmingclub.daiict.classes.tech_news_classes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import programmingclub.daiict.MainActivity;
import programmingclub.daiict.R;

/**
 * Created by omkar13 on 12/17/2015.
 */
public class MainActivity_RSS extends MainActivity implements AdapterView.OnItemClickListener{

    private TextView stickyView;
    private ListView listView;
    private View heroImageView;
    private View stickyViewSpacer;

    private int MAX_ROWS = 20;

    public void onCreate(Bundle savedInstanceState){


        super.onCreate(savedInstanceState);
        setContentView(R.layout.various_rss_list);

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

        listView = (ListView) findViewById(R.id.rssListView);
        heroImageView = findViewById(R.id.heroImageView); //containing the photo
        stickyView = (TextView) findViewById(R.id.stickyView);


        /* Inflate list header layout */
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listHeader = inflater.inflate(R.layout.list_header_layout, null); //this just contains spaces, inflator method generates a view object from the layout xml file
        stickyViewSpacer = listHeader.findViewById(R.id.stickyViewPlaceholder); //findViewById can also be uesd to find views within inflated layouts contained in a view like above
                                                                                //stickyViewSpacer is a just space of 50dp.
         /* Add list view header */
        listView.addHeaderView(listHeader); //this gives the listview a header which currently only contains spaces



        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                /* Check if the first item is already reached to top.*/
                if (listView.getFirstVisiblePosition() == 0) { //if the first list item seen on screen is indeed the first item of the list(this might not happen always)
                    View firstChild = listView.getChildAt(0);
                    int topY = 0;
                    if (firstChild != null) {
                        topY = firstChild.getTop(); //getTop gives the Y coordinate of the view
                    }

                    int heroTopY = stickyViewSpacer.getTop();
                    stickyView.setY(Math.max(0,heroTopY+topY));

                    /* Set the image to scroll half of the amount that of ListView */
                    heroImageView.setY(topY * 0.5f);
                }
            }
        }); //defines how the layout will change when a scroll happens



        listView.setOnItemClickListener(this); //this is the use of the inbult listener

        List<String> l =new ArrayList<String>();

        l.add("PCWorld News");
        l.add("Technology News");
        l.add("Android News");
        l.add("StartUp News");
        l.add("Gaming News");
        l.add("Open Source News");
        l.add("Internet News");
        l.add("Software News");

        RssListAdapter a=new RssListAdapter(this,l); //the list adapter has a method which gives you a view containing an embedded text view or a tag
        listView.setAdapter(a); // set adapter to populate the listView
    }


    @Override //call back method for the inbult listView listerer
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        // start the Fragment activity
        Intent i=new Intent(MainActivity_RSS.this, FragmentActivity.class);
        boolean listItemClick=false;

        switch (position){

            case 1:  i.putExtra("feedNo", 1);
                    listItemClick=true;
                    break;
            case 2:  i.putExtra("feedNo", 2);
                    listItemClick=true;
                    break;
            case 3: i.putExtra("feedNo", 3);
                     listItemClick=true;
                    break;
            case 4: i.putExtra("feedNo", 4);
                    listItemClick=true;
                    break;
            case 5: i.putExtra("feedNo", 5);
                     listItemClick=true;
                     break;
            case 6: i.putExtra("feedNo", 6);
                listItemClick=true;
                break;
            case 7: i.putExtra("feedNo", 7);
                listItemClick=true;
                break;
            case 8: i.putExtra("feedNo", 8);
                listItemClick=true;
                break;

        }

        if(listItemClick)
            startActivity(i);

    }

    @Override
    public void onResume() {
        super.onResume();
        getActionBar().setTitle(listArray[4]);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        getActionBar().setTitle(listArray[4]);
    }
}
