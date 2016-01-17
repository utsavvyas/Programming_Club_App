package programmingclub.daiict.classes.tech_news_classes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Aditya on 29-12-2015.
 */
public class FeedReaderDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) //imp- this is called only when database is first created,
            //it is not called when helper is created. If db exists this is ignored
    {
        //sql query to create table
        db.execSQL( "CREATE TABLE " + DatabaseContract.tableDefinition.TABLE_NAME + " (" +
                DatabaseContract.tableDefinition._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DatabaseContract.tableDefinition.COLUMN_NAME_TITLE + " TEXT, "+
                        DatabaseContract.tableDefinition.COLUMN_NAME_LINK + " TEXT"+
                " );"
        );

        //I have made title prim key the primary key
        db.execSQL( "CREATE TABLE " + DatabaseContract.tableDefinition.TABLE_NAME2 + " (" +
                        //DatabaseContract.tableDefinition._ID + " INTEGER  AUTOINCREMENT, " +
                        DatabaseContract.tableDefinition.COLUMN_NAME_TITLE2 + " TEXT PRIMARY KEY, "+
                        DatabaseContract.tableDefinition.COLUMN_NAME_LINK2 + " TEXT"+
                        " );"
        );




        db.execSQL( "CREATE TABLE " + DatabaseContract.tableDefinition.TABLE_NAME3 + " (" +
                    //    DatabaseContract.tableDefinition._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        DatabaseContract.tableDefinition.COLUMN_NAME_TITLE3 + " TEXT PRIMARY KEY, "+
                        DatabaseContract.tableDefinition.COLUMN_NAME_LINK3 + " TEXT"+
                        " );"
        );

        db.execSQL( "CREATE TABLE " + DatabaseContract.tableDefinition.TABLE_NAME4 + " (" +
                        //DatabaseContract.tableDefinition._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        DatabaseContract.tableDefinition.COLUMN_NAME_TITLE4 + " TEXT PRIMARY KEY, "+
                        DatabaseContract.tableDefinition.COLUMN_NAME_LINK4 + " TEXT"+
                        " );"
        );

        db.execSQL( "CREATE TABLE " + DatabaseContract.tableDefinition.TABLE_NAME5 + " (" +
                        //DatabaseContract.tableDefinition._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        DatabaseContract.tableDefinition.COLUMN_NAME_TITLE5 + " TEXT PRIMARY KEY, "+
                        DatabaseContract.tableDefinition.COLUMN_NAME_LINK5 + " TEXT"+
                        " );"
        );

        db.execSQL( "CREATE TABLE " + DatabaseContract.tableDefinition.TABLE_NAME6 + " (" +
                       // DatabaseContract.tableDefinition._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        DatabaseContract.tableDefinition.COLUMN_NAME_TITLE6 + " TEXT PRIMARY KEY, "+
                        DatabaseContract.tableDefinition.COLUMN_NAME_LINK6 + " TEXT"+
                        " );"
        );

        db.execSQL( "CREATE TABLE " + DatabaseContract.tableDefinition.TABLE_NAME7 + " (" +
                        //DatabaseContract.tableDefinition._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        DatabaseContract.tableDefinition.COLUMN_NAME_TITLE7 + " TEXT PRIMARY KEY, "+
                        DatabaseContract.tableDefinition.COLUMN_NAME_LINK7 + " TEXT"+
                        " );"
        );

        db.execSQL( "CREATE TABLE " + DatabaseContract.tableDefinition.TABLE_NAME8 + " (" +
                      //  DatabaseContract.tableDefinition._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        DatabaseContract.tableDefinition.COLUMN_NAME_TITLE8 + " TEXT PRIMARY KEY, "+
                        DatabaseContract.tableDefinition.COLUMN_NAME_LINK8 + " TEXT"+
                        " );"
        );

    }

    //for upgrade
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.tableDefinition.TABLE_NAME);
        onCreate(db);
    }

}