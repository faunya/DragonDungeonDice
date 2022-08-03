package edu.neu.madcourse.team20_finalproject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteManager extends SQLiteOpenHelper {
    private static SQLiteManager sqLiteManager;

    private static final String DBNAME = "Game.db";
    private static final int DBVER = 1;

    private static final String ID = "ID";
    private static final String NAME = "NAME";

    //entity table
    private static final String ENTITYTABLE = "Entities";
    public static final String MAXHP = "MAX_HP";
    public static final String HP = "HP";
    public static final String MAXSP = "MAX_SP";
    public static final String SP = "SP";
    public static final String STR = "STR";
    public static final String DEX = "DEX";
    public static final String VIT = "VIT";
    public static final String WIS = "WIS";
    public static final String INT = "INT";
    public static final String SPD = "SPD";
    public static final String AC = "AC";

    //inventory table
    private static final String INVTABLE = "Inventory";
    private static final String STAT = "STAT";
    private static final String EQP  = "EQUIPPED";
    private static final String SLOT = "SLOT";

    public SQLiteManager(Context context) {
        super(context, DBNAME, null, DBVER);
    }

    /*
    public static instanceDB(Context context) {
        if (sqLiteManager == null) {
            sqLiteManager = new SQLiteManager(context);
        }
        return sqLiteManager;
    }

     */

    @Override
    public void onCreate(SQLiteDatabase db) {
        //entitiy table created
        db.execSQL("create table " + ENTITYTABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT" +
                ", NAME TEXT, MAX_HP INTEGER, HP INTEGER, MAX_SP INTEGER, SP INTEGER" +
                ", STR INTEGER, DEX INTEGER, VIT INTEGER, WIS INTEGER, INT INTEGER, SPD INTEGER, AC INTEGER)");

        //inventory table created
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
