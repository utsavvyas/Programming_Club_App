package programmingclub.daiict.classes.event_classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import programmingclub.daiict.R;

/**
 * Created by spock on 28/7/15.
 */
public class EventCardArrayAdapter extends ArrayAdapter<Event> {


    private List<Event> cardList = new ArrayList<Event>();

    static class CardViewHolder {
        ImageView img1;
        ImageView img2;
        TextView line1;
        TextView line2;
        TextView line3;
        TextView line4;

    }

    public EventCardArrayAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    @Override
    public void add(Event object) {
        if(object==null){
            System.out.println("Nullll");
        }
        cardList.add(0,object);
        //super.add(0,object);
    }

    public void remove()
    {
        cardList.removeAll(cardList);
    }
    @Override
    public int getCount() {
        return this.cardList.size();
    }

    @Override
    public Event getItem(int index) {
        return this.cardList.get(index);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        CardViewHolder viewHolder;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.eventlayout, parent, false);
            viewHolder = new CardViewHolder();
            viewHolder.line1 = (TextView) row.findViewById(R.id.ContestName);
            viewHolder.line2 = (TextView) row.findViewById(R.id.contestLink);
            viewHolder.line3 = (TextView) row.findViewById(R.id.contestStartTime);
            viewHolder.line4 = (TextView) row.findViewById(R.id.contestEndTime);
            viewHolder.img1 = (ImageView) row.findViewById(R.id.contestHost);
            viewHolder.img2 = (ImageView) row.findViewById(R.id.contestHosts);
            row.setTag(viewHolder);
        } else {
            viewHolder = (CardViewHolder)row.getTag();
        }
        Event card = getItem(position);
        viewHolder.line1.setTextColor(Color.BLACK);
        viewHolder.line3.setTextColor(Color.BLACK);
        viewHolder.line4.setTextColor(Color.BLACK);
        //viewHolder.line1.setAllCaps(true);
        //viewHolder.line1.setTypeface(Typeface.DEFAULT_BOLD);

        viewHolder.line1.setText(card.getTitle());
        viewHolder.line2.setText(card.getUrl());
        viewHolder.line3.setText(card.getStartTime());
        viewHolder.line4.setText(card.getEndTime());
        if(card.getTitle().contains("Codeforces"))
        {
            viewHolder.img2.setImageResource(R.mipmap.codeforces);
        }
        else if(card.getTitle().contains("Topcoder"))
        {
            viewHolder.img2.setImageResource(R.mipmap.topcoder);
        }
        else if(card.getTitle().contains("Codechef")) {

            viewHolder.img2.setImageResource(R.mipmap.codechef);
        }
        else if(card.getUrl().contains("hackerrank"))
        {
            viewHolder.img2.setImageResource(R.mipmap.hackerrank);
        }
        else
        {
            viewHolder.img2.setImageResource(R.mipmap.other);
        }

        //viewHolder.img1.setImageDrawable();
        return row;
    }

    public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }
}
