package programmingclub.daiict.classes.About_Developers;

/**
 * Created by omkar on 17/1/16.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import programmingclub.daiict.R;

public class CustomList extends ArrayAdapter<String> {

private final Activity context;
private final String[] name , email;
private final Integer[] imageId;
public CustomList(Activity context,
        String[] name, Integer[] imageId, String[] email) {
        super(context, R.layout.list_single, name);
        this.context = context;
        this.name = name;
        this.imageId = imageId;
        this.email = email;
        }
@Override
public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);
        TextView nameView = (TextView) rowView.findViewById(R.id.name);

        TextView emailView = (TextView) rowView.findViewById(R.id.name);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);

        nameView.setText(name[position]);
        emailView.setText(email[position]);

        imageView.setImageResource(imageId[position]);
        return rowView;
        }
        }
