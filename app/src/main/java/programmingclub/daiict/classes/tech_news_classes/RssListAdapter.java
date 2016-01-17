package programmingclub.daiict.classes.tech_news_classes;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import programmingclub.daiict.R;

/**
 * Created by omkar13 on 12/24/2015.
 */
public class RssListAdapter extends BaseAdapter { //baseAdapter is abstract, we extend it to define how we want to handle our list


    private final List<String> items;
    private final Context context;

    public RssListAdapter(Context context, List<String> items) {
        this.items = items;
        this.context = context;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override //strange method takes in int and returns the same as long
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) { //baseadapter does not give this methods implementation, we wrote the
        //method in such a way that the method returns a textview with the approppriate text
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.list_item, null); //we inflate a layout in convertview
            holder = new ViewHolder();
            holder.itemTitle = (TextView) convertView.findViewById(R.id.rssItemTitle); //points to textview in layout held in convertView
            convertView.setTag(holder); //that text view is then encapsulated within covnertView and we have tied it to a concrete layout
        }

        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.itemTitle.setText(items.get(position));
        return convertView;
    }

    static class ViewHolder {
        TextView itemTitle; //reference to a textView
    }
}
