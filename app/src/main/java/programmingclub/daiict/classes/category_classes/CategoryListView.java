package programmingclub.daiict.classes.category_classes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.List;

import programmingclub.daiict.classes.my_card.Card;
import programmingclub.daiict.MainActivity;
import programmingclub.daiict.classes.database_management_class.MySQLiteHelper;
import programmingclub.daiict.R;

public class CategoryListView extends MainActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

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
        
        initializeActionBarDrawerToggle();

        final MySQLiteHelper mySQLiteHelper = new MySQLiteHelper(this);
        final ListView list = (ListView)findViewById(R.id.card_listViewCategory);
        List<String> category = mySQLiteHelper.getCategories();
        System.out.print(category.size());
        CategoryCardAdapter cardArrayAdapter = new CategoryCardAdapter(this.getApplicationContext(), R.layout.category_card_layout);
        for(int i=0;i<category.size();i++) {
            Card c = new Card(category.get(i), "");
            System.out.println(c.getLine1() + "dfdfdsfff");
            cardArrayAdapter.add(c);
        }
        list.setAdapter(cardArrayAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent in=new Intent("programmingclub.daiict.BLOGCATEGORYLISTVIEW");
                Bundle b = new Bundle();
                b.putString("category", ((Card) list.getItemAtPosition(position)).getLine1());
                in.putExtras(b);
                startActivity(in);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        getActionBar().setTitle(listArray[3]);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        getActionBar().setTitle(listArray[3]);
    }
}
