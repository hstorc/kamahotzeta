package com.grey.hstorc.dropdownandprice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.support.annotation.StringDef;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


/**
 * Created by hstorc on 10/21/16.
 */
public class TransactionDB extends SQLiteOpenHelper {

    public void deleteAll() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(KamaHotzeta.TABLE_NAME,KamaHotzeta._ID + " > ?", new String[] { String.valueOf(0) });
        db.close();
    }

    public static class KamaHotzeta implements BaseColumns {

        private static final String DATABASE_NAME = "db";
        private static final int DATABASE_VERSION = 1;
        public static final String TABLE_NAME = "transactions";
        private static final String TEXT_TYPE = " TEXT";
        private static final String FLOAT_TYPE = " FLOAT";
        private static final String DATE_TYPE = " DATETIME";
        private static final String TIMESTAMP_TYPE = " TIMESTAMP";
        private static final String BOOL_TYPE = " BOOLEAN";
        private static final String COMMA_SEP = " ,";
       // public static final String COLUMN_NAME_TRANSACTION_ID = "Id";
        public static final String COLUMN_NAME_TRANSACTION_OWNER = "Owner";
        public static final String COLUMN_NAME_TRANSACTION_DATE = "transactionDate";
        public static final String COLUMN_NAME_TRANSACTION_VALUE = "Value";
        public static final String COLUMN_NAME_TRANSACTION_ITEM = "Item";
        public static final String COLUMN_NAME_TRANSACTION_TIMESTAMP = "timestamp";
        public static final String COLUMN_NAME_TRANSACTION_DELETED = "deleted";
        public static final String COLUMN_NAME_TRANSACTION_SHARE = "Share";
        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_NAME_TRANSACTION_OWNER + TEXT_TYPE + COMMA_SEP +
                        COLUMN_NAME_TRANSACTION_DATE + DATE_TYPE + COMMA_SEP +
                        COLUMN_NAME_TRANSACTION_VALUE + FLOAT_TYPE + COMMA_SEP +
                        COLUMN_NAME_TRANSACTION_ITEM + TEXT_TYPE + COMMA_SEP +
                        COLUMN_NAME_TRANSACTION_TIMESTAMP + TIMESTAMP_TYPE + " DEFAULT CURRENT_TIMESTAMP"+ COMMA_SEP +
                        COLUMN_NAME_TRANSACTION_DELETED + BOOL_TYPE + COMMA_SEP +
                        COLUMN_NAME_TRANSACTION_SHARE + TEXT_TYPE+" )";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + KamaHotzeta.TABLE_NAME;
    }


    public TransactionDB(Context context){
        super(context,KamaHotzeta.DATABASE_NAME,null,KamaHotzeta.DATABASE_VERSION);
    }


    public TransactionDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(KamaHotzeta.SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long Insert(Transaction transaction){
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(KamaHotzeta.COLUMN_NAME_TRANSACTION_VALUE, transaction.transactionValue);
        values.put(KamaHotzeta.COLUMN_NAME_TRANSACTION_DATE, transaction.transactionDate.toString());
        values.put(KamaHotzeta.COLUMN_NAME_TRANSACTION_ITEM, transaction.product);

// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(KamaHotzeta.TABLE_NAME, null, values);
        return newRowId;
    }

    public ArrayList<Transaction> getValues(String orderBy) {
        ArrayList<Transaction>	ret = new ArrayList<Transaction>();
        int x = 0;

        String selectQuery = "SELECT  * FROM " + KamaHotzeta.TABLE_NAME+(orderBy==null?"":" ORDER BY "+orderBy);


        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                ret.add(new Transaction(
                        Integer.valueOf(cursor.getString(0)),
                        cursor.getString(1),
                        convertToDate(cursor.getString(2),UtilitiesHelper.DbReadDateFormat),
                        cursor.getFloat(3),
                        cursor.getString(4),
                        cursor.getLong(5),
                        Boolean.valueOf(cursor.getString(6)),
                        cursor.getString(7)));
            } while (cursor.moveToNext());
        }
        return ret;
    }

    public Transaction	getValue(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(KamaHotzeta.TABLE_NAME,
                new String[] { KamaHotzeta._ID,
                        KamaHotzeta.COLUMN_NAME_TRANSACTION_OWNER,
                        KamaHotzeta.COLUMN_NAME_TRANSACTION_DATE,
                        KamaHotzeta.COLUMN_NAME_TRANSACTION_VALUE,
                        KamaHotzeta.COLUMN_NAME_TRANSACTION_ITEM,
                        KamaHotzeta.COLUMN_NAME_TRANSACTION_TIMESTAMP,
                        KamaHotzeta.COLUMN_NAME_TRANSACTION_DELETED,
                        KamaHotzeta.COLUMN_NAME_TRANSACTION_SHARE},
                KamaHotzeta._ID + "=?", new String[] { String.valueOf(id) },
                null, null, null, null);
        if (cursor == null)
            return null;

        Transaction data = null;
       while (cursor.moveToNext()){

            data = new Transaction(Integer.valueOf(cursor.getString(0)),
                    cursor.getString(1),
                    convertToDate(cursor.getString(2),UtilitiesHelper.DbReadDateFormat),
                    cursor.getFloat(3),
                    cursor.getString(4),
                    cursor.getLong(5),
                    Boolean.valueOf(cursor.getString(6)),
                    cursor.getString(7));
        }
        return data;
    }

    public void	deleteData(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(KamaHotzeta.TABLE_NAME,KamaHotzeta._ID + " = ?", new String[] { String.valueOf(id) });
        db.close();
    }

    public void Open(SQLiteDatabase db ){

    }

    public Date convertToDate(String val, String dateFormatter){
        if (dateFormatter==null) dateFormatter = UtilitiesHelper.myDateFormat;
        DateFormat formatter = new SimpleDateFormat(dateFormatter, Locale.US);
        Date date = null;
        try {
            date = (Date)formatter.parse(val);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
