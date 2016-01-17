package programmingclub.daiict.classes.tech_news_classes;

import android.provider.BaseColumns;

/**
 * Created by Aditya on 29-12-2015.
 */

//the standard way is to give it a inner class which will define the columns of the table
public final class DatabaseContract
{
    public DatabaseContract()
    {

    }

    //inner table defining class
    public static abstract class tableDefinition implements BaseColumns
    {
        //give the structure of the table
        //primary key is automatically inherited
        // our db has 8 tables to store the rssitems

        public static final String TABLE_NAME = "news";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_LINK = "link";

        public static final String TABLE_NAME2 = "news2";
        public static final String COLUMN_NAME_TITLE2 = "title2";
        public static final String COLUMN_NAME_LINK2 = "link2";


        public static final String TABLE_NAME3 = "news3";
        public static final String COLUMN_NAME_TITLE3 = "title3";
        public static final String COLUMN_NAME_LINK3 = "link3";

        public static final String TABLE_NAME4 = "news4";
        public static final String COLUMN_NAME_TITLE4 = "title4";
        public static final String COLUMN_NAME_LINK4 = "link4";

        public static final String TABLE_NAME5 = "news5";
        public static final String COLUMN_NAME_TITLE5 = "title5";
        public static final String COLUMN_NAME_LINK5 = "link5";

        public static final String TABLE_NAME6 = "news6";
        public static final String COLUMN_NAME_TITLE6 = "title6";
        public static final String COLUMN_NAME_LINK6 = "link6";

        public static final String TABLE_NAME7 = "news7";
        public static final String COLUMN_NAME_TITLE7 = "title7";
        public static final String COLUMN_NAME_LINK7 = "link7";

        public static final String TABLE_NAME8 = "news8";
        public static final String COLUMN_NAME_TITLE8 = "title8";
        public static final String COLUMN_NAME_LINK8 = "link8";
    }
}
