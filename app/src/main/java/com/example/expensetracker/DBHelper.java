package com.example.expensetracker;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Hashtable;

public class DBHelper extends SQLiteOpenHelper {

public static final String database="Expense.db";
    public DBHelper(@Nullable Context context) {
        super(context, database,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table myexpense (reason text , income double,expense double )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
    db.execSQL("Drop table if exists myexpense");
    onCreate(db);
    }

    public boolean insertData(String reason,double income,double expense)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("reason",reason);
        contentValues.put("income",income);
        contentValues.put("expense",expense);
        db.insert("myexpense",null,contentValues);
        return true;
    }

    @SuppressLint("Range")
    public double returnbalance()    //to calculate balance and show it in balance textview
    {
        double total=0.0,totalincome=0.0,totalexpense=0.0;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res1=db.rawQuery("Select (SUM(income)) as totalincome from myexpense",null);
        Cursor res2=db.rawQuery("Select (SUM(expense)) as totalexpense from myexpense",null);
        res1.moveToFirst();res2.moveToFirst();
        while(res1.isAfterLast()==false)
        {
            totalincome=Double.parseDouble(res1.getString(res1.getColumnIndex("totalincome")));
            res1.moveToNext();
        }
        while(res2.isAfterLast()==false)
        {
            totalexpense=Double.parseDouble(res2.getString(res2.getColumnIndex("totalexpense")));
            res2.moveToNext();
        }
        total=totalincome-totalexpense;
        return total;
    }

    @SuppressLint("Range")
    public double getIncomeTotal()
    {
        double total=0.0;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("Select (SUM(income)) as total from myexpense",null);
        res.moveToFirst();
        while(res.isAfterLast()==false)
        {
            total=Double.parseDouble(res.getString(res.getColumnIndex("total")));
            res.moveToNext();
        }
        return total;
    }
    @SuppressLint("Range")
    public double getExpenseTotal()
    {
        double total=0.0;
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("Select (SUM(expense)) as total from myexpense",null);
        res.moveToFirst();
        while(res.isAfterLast()==false)
        {
            total=Double.parseDouble(res.getString(res.getColumnIndex("total")));
            res.moveToNext();
        }
        return total;
    }
    public Hashtable getHistory()
    {
        Hashtable<String, String> ht
                = new Hashtable<String, String>();
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("Select * from myexpense",null);
        res.moveToLast();
        ht.put("text",res.getString(0));
        ht.put("income",String.valueOf(res.getDouble(1)));
        ht.put("expense", String.valueOf(res.getDouble(2)));
        return ht;
    }

    public Cursor displayAll()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor res=db.rawQuery("Select * from myexpense",null);
        res.moveToFirst();
        return res;

    }
    
}
