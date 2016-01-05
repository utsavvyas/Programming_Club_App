package programmingclub.daiict;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by omkar13 on 12/20/2015.
 */
public class FragmentActivity extends android.support.v4.app.FragmentActivity {


    public void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); //an empty frame layout

        Bundle b = getIntent().getExtras();

        int no=b.getInt("feedNo"); //tells us which freagment to load

        switch(no){
            case 1: addPCWorldFragment();
                break;
            case 2: addTechFragment();
                break;
            case 3: addAndroidFragment();
                break;
            case 4: addStartUpFragment();
                break;
            case 5: addGamingFragment();
                break;
            case 6: addOpenSourceFragment();
                break;
            case 7: addInternetFragment();
                break;
            case 8: addSoftwareFragment();
                break;
        }
}

    public void addPCWorldFragment(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        PCWorldRssFragment fragment = new PCWorldRssFragment();
        //TechnologyAppRssFragment fragment = new TechnologyAppRssFragment();     //lets see if this works!
        transaction.add(R.id.fragment_container, fragment);

        transaction.commit();
    }

    public void addTechFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //PCWorldRssFragment fragment = new PCWorldRssFragment();
        TechnologyAppRssFragment fragment = new TechnologyAppRssFragment();     //lets see if this works!
        transaction.add(R.id.fragment_container, fragment);

        transaction.commit();
    }

    public void addAndroidFragment() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //PCWorldRssFragment fragment = new PCWorldRssFragment();
        AndroidRssFragment fragment = new AndroidRssFragment();     //lets see if this works!
        transaction.add(R.id.fragment_container, fragment);

        transaction.commit();
    }


    public void addStartUpFragment(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        StartUpRssFragment fragment=new StartUpRssFragment();
        //TechnologyAppRssFragment fragment = new TechnologyAppRssFragment();     //lets see if this works!
        transaction.add(R.id.fragment_container, fragment);

        transaction.commit();


    }


    public void addGamingFragment(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        GamingRssFragment fragment=new GamingRssFragment();
        //TechnologyAppRssFragment fragment = new TechnologyAppRssFragment();     //lets see if this works!
        transaction.add(R.id.fragment_container, fragment);

        transaction.commit();


    }

    public void addOpenSourceFragment(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        OpenSourceRssFragment fragment=new OpenSourceRssFragment();
        //TechnologyAppRssFragment fragment = new TechnologyAppRssFragment();     //lets see if this works!
        transaction.add(R.id.fragment_container, fragment);

        transaction.commit();


    }
    public void addInternetFragment(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        InternetRssFragment fragment=new InternetRssFragment();
        //TechnologyAppRssFragment fragment = new TechnologyAppRssFragment();     //lets see if this works!
        transaction.add(R.id.fragment_container, fragment);

        transaction.commit();


    }
    public void addSoftwareFragment(){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        SoftwareRssFragment fragment=new SoftwareRssFragment();
        //TechnologyAppRssFragment fragment = new TechnologyAppRssFragment();     //lets see if this works!
        transaction.add(R.id.fragment_container, fragment);

        transaction.commit();


    }

}
