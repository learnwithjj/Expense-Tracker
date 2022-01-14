package com.example.expensetracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Transaction extends AppCompatActivity {
        DBHelper db;
       TextView displaytext;
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);


        displaytext=findViewById(R.id.displaytext);
        db=new DBHelper(this);
        StringBuffer buffer=new StringBuffer();
        Cursor res=db.displayAll();
            while(res.moveToNext()) {
                buffer.append(res.getString(0)+"\n");
                double income=Double.parseDouble(res.getString(1));
                double expense=Double.parseDouble(res.getString(2));
                if(income>expense)
                {
                    buffer.append("+"+income+"\n");
                }
                else
                {
                    buffer.append("-"+expense+"\n");
                }
                buffer.append("---------------------------------------------------------\n");
                displaytext.setText(buffer);

            }


    }
}


