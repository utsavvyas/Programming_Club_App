package programmingclub.daiict;

import android.app.IntentService;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by omkar13 on 9/25/2015.
 */
public class PCWorldRssService extends IntentService{

    private static final String RSS_LINK = "http://www.pcworld.com/index.rss";
    public static final String ITEMS = "items";
    public static final String RECEIVER = "receiver";

    public PCWorldRssService() {
        super("PCWorldRssService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
      //  Log.d("PCWorldRssService:onHandleIntent method ", "Service started");
         List<RssItem> rssItems = null;

        try {
            RssParser parser = new RssParser();

            if (getInputStream(RSS_LINK)!=null)
            {
                rssItems = parser.parse(getInputStream(RSS_LINK)); //this gives a list of stirngs

                //now create a database and add items to it.
               // FeedReaderDbHelper mDbHelper=new FeedReaderDbHelper(this);
               // SQLiteDatabase db = mDbHelper.getWritableDatabase(); //this is writable database hopefully for this context

                //now add  each row
               // ListIterator<String> listIterator= rssItems.listIterator();

            }

            //new trial code, check what methods are available, we will create database for each activity, HERE

        } catch (XmlPullParserException e) {
            Log.w(e.getMessage(), e);
        } catch (IOException e) {
            Log.w(e.getMessage(), e);
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(ITEMS, (Serializable) rssItems);
        ResultReceiver receiver = intent.getParcelableExtra(RECEIVER);
        receiver.send(0, bundle); //sends strings to our receiver
    }

    public InputStream getInputStream(String link) { //gets the raw rss feed
        try {
            URL url = new URL(link);
            return url.openConnection().getInputStream();
        } catch (IOException e) {
        //    Log.w("Exception while retrieving the input stream",e);
            return null;
        }
    }
}
