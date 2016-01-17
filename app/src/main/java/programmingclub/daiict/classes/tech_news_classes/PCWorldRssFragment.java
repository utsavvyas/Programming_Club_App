package programmingclub.daiict.classes.tech_news_classes;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.Fragment;
//import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import programmingclub.daiict.R;

/**
 * Created by omkar13 on 9/26/2015.
 */
public class PCWorldRssFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ProgressBar progressBar;
    private ListView listView;
    private View view;

    //database open helper
    FeedReaderDbHelper mDbHelper;
    SQLiteDatabase db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); //even if activity is destroyed and recreated when con is changed, the frag will be retained
        mDbHelper = new FeedReaderDbHelper(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {//method used by fragment to set up its ui
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_layout, container, false); //contains the fragment layout and os renders it.
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
            listView = (ListView) view.findViewById(R.id.listView);
            listView.setOnItemClickListener(this); //inbuilt listener for listview

            //pointers to ui elements of fragment's layout obtained then start the fragment service(why service?becauee network ops can be long running)
            startService();
        } else {
            // If we are returning from a configuration change:
            // "view" is still attached to the previous view hierarchy
            // so we need to remove it and re-attach it to the current one
            ViewGroup parent = (ViewGroup) view.getParent();
            parent.removeView(view);
        }
        return view;
    }

    private void startService() {
        Intent intent = new Intent(getActivity(), PCWorldRssService.class);
        intent.putExtra(PCWorldRssService.RECEIVER, resultReceiver);
        getActivity().startService(intent);
    }

    /**
     * Once the {@link PCWorldRssService} finishes its task, the result is sent to this ResultReceiver.
     */
    private final ResultReceiver resultReceiver = new ResultReceiver(new Handler()) { //anonymous class of  a concrete class inherits from it
        @SuppressWarnings("unchecked")
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData)
        {
            List<RssItem> items = (List<RssItem>) resultData.getSerializable(PCWorldRssService.ITEMS);
            if (items != null)
            {
                RssAdapter adapter = new RssAdapter(getActivity(), items);
                listView.setAdapter(adapter);  //get the rss feed and populate the local list view with it.

                //if the rssitems list is available then use it to create the database
                db = mDbHelper.getWritableDatabase();
                ContentValues values = new ContentValues(); //contentValues to hold the rssitem info when it is being put in database

                //now fill in the table till list has items

                ListIterator<RssItem> li=items.listIterator(); //this is the li which we will use

                //when info is available, then fill in the database
                //before that delete previous data
                db.delete(DatabaseContract.tableDefinition.TABLE_NAME,null,null);

                while (li.hasNext())
                {
                    RssItem temp=li.next();
                    values.put(DatabaseContract.tableDefinition.COLUMN_NAME_TITLE,temp.getTitle());
                    values.put(DatabaseContract.tableDefinition.COLUMN_NAME_LINK, temp.getLink());

                    db.insert(DatabaseContract.tableDefinition.TABLE_NAME, null, values); //row added
                    values.clear();
                }

            }
            else
            {
                  items=new ArrayList<RssItem>();

                //rss list is empty then retrieve information from the database.
                //we will try to get cursor if cursor is empty then db is empty

                db=mDbHelper.getWritableDatabase();
                String query="SELECT * FROM "+DatabaseContract.tableDefinition.TABLE_NAME+" WHERE 1;"; //query syntax to retrive the entire database.

                Cursor c =db.rawQuery(query,null);

                if(c.moveToFirst()==false)
                {Toast.makeText(getActivity(), "An error occured while downloading the rss feed.",
                            Toast.LENGTH_LONG).show();}
                else
                {
                   String title=null;
                    String link=null;
                    while(!c.isAfterLast())
                    {

                        if (c.getString(c.getColumnIndex("title"))!=null)
                        {
                            title=c.getString(c.getColumnIndex("title"));
                        }

                        if (c.getString(c.getColumnIndex("link"))!=null)
                        {
                            link=c.getString(c.getColumnIndex("link"));
                        }

                        items.add(new RssItem(title, link));
                        c.moveToNext();
                    }

                    RssAdapter adapter = new RssAdapter(getActivity(), items);
                    listView.setAdapter(adapter);
                }
            }
            progressBar.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
        };
    }; //when service finishes it returns to this fragment and we can populate our listview.

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RssAdapter adapter = (RssAdapter) parent.getAdapter();
        RssItem item = (RssItem) adapter.getItem(position);
        String url = item.getLink();
        Intent intent=new Intent(getActivity(),WebViewActivity.class );   //start the webview activity
        intent.putExtra("url",url);
        startActivity(intent);

    }


}