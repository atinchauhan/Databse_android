package com.example.atinchauhan.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ContactsDB {
    public static final String ROW_ID="_id";
    public static final String KEY_NAME="person_name";
    public static final String KEY_CELL="_cell";

    private final String DATABASE_NAME="ContactsDB";
    private final String DATABASE_TABLE="ContactsTable";
    private final int DATABASE_VERSION=1;

    private DBHelper ourHelper;
    public final Context ourcontext;
    private SQLiteDatabase ourDatabase;

    public ContactsDB(Context context){
        ourcontext=context;

    }
    private class DBHelper extends SQLiteOpenHelper {
        public DBHelper(Context context){
            super(context, DATABASE_NAME , null , DATABASE_VERSION);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            //int older i and new version i1
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
            onCreate(db);

        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            String sqlCode="CREATE TABLE " + DATABASE_TABLE + " ("+
                    ROW_ID+" INTEGER PRIMARY KEY AUTOINCREMENT , "+
                    KEY_NAME + " TEXT NOT NULL, "+
                    KEY_CELL + " TEXT NOT NULL);";
            db.execSQL(sqlCode);

        }

    }
    public ContactsDB open() throws SQLException
    {
        ourHelper=new DBHelper(ourcontext);
        ourDatabase=ourHelper.getWritableDatabase();
        return this;
    }
    public void close(){
        ourHelper.close();
    }
    public long createEntry(String name,String cell){
        ContentValues cv=new ContentValues();
        cv.put(KEY_NAME,name);
        cv.put(KEY_CELL,cell);
        return ourDatabase.insert(DATABASE_TABLE,null,cv);

    }
    public String getData(){
        String [] columns=new String[] {ROW_ID,KEY_NAME,KEY_CELL};
        Cursor c=ourDatabase.query(DATABASE_TABLE,columns,null,null,null,null,null);
        String result="";
        int iRow=c.getColumnIndex(ROW_ID);
        int iName=c.getColumnIndex(KEY_NAME);
        int iCell=c.getColumnIndex(KEY_CELL);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
            result=result+c.getString(iRow)+": " +c.getString(iName)+ " "+c.getString(iCell)+ "\n";
        }
        c.close();
        return result;
    }
    public long deleteEntry(String rowId){
        return ourDatabase.delete(DATABASE_TABLE,ROW_ID+"=?",new String[]{rowId});
    }
    public long updateEntry(String rowId,String name,String cell){
        ContentValues cv=new ContentValues();
        cv.put(KEY_NAME,name);
        cv.put(KEY_CELL,cell);
        return ourDatabase.update(DATABASE_TABLE,cv,ROW_ID+"=?",new String[]{rowId});

    }

}
