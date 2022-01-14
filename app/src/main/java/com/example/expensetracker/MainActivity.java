package com.example.expensetracker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.util.Hashtable;

public class MainActivity extends AppCompatActivity {

    TextView balanceText,incomeText,expenseText,history1,historyamt1;
    View historyview1;
    Button add,show;
    EditText entertext,enteramount;
    DBHelper db;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        show=findViewById(R.id.show);
        balanceText=findViewById(R.id.balanceText);
        incomeText=findViewById(R.id.incomeText);
        expenseText=findViewById(R.id.expenseText);
        history1=findViewById(R.id.history1);
        add=findViewById(R.id.add);
        historyamt1=findViewById(R.id.historyamt1);
        historyview1=findViewById(R.id.historyview1);
        entertext=findViewById(R.id.text);
        enteramount=findViewById(R.id.amount);
        db=new DBHelper(this);
        db.insertData("null",0.0,0.0);
        revise();


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(entertext.getText().toString().isEmpty() || enteramount.getText().toString().isEmpty())
                {
                    Toast.makeText(MainActivity.this, "Enter all the fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                String text=entertext.getText().toString().trim();
                String amount=enteramount.getText().toString().trim();
                double income,expense;
                double balance=0.0;
                balanceText.setText("₹"+balance);
                if(amount.charAt(0)=='+')
                {
                    income=Double.parseDouble(amount.substring(1));
                    expense=0.0;
                    db.insertData(text,income,expense);
                    revise();
                }
                else if(amount.charAt(0)=='-')
                {
                    expense=Double.parseDouble(amount.substring(1));
                    income=0.0;
                    db.insertData(text,income,expense);
                    revise();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Invalid amount,add + for income, - for expense", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(MainActivity.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                getHistory();
            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,com.example.expensetracker.Transaction.class);
                startActivity(intent);
            }
        });
    }
    private void revise()
    {
        getBalanceText();
        getIncomeText();
        getExpenseText();
        getHistory();
        entertext.setText("");
        enteramount.setText("");
    }
    private void getIncomeText()
    {
        double result=db.getIncomeTotal();
        incomeText.setText("₹"+result+"");
    }

    private  void getExpenseText()
    {
        double result=db.getExpenseTotal();
        expenseText.setText("₹"+result+"");
    }
    private  void getBalanceText()
    {
        double result=db.returnbalance();
        balanceText.setText("₹"+result+"");
    }


    @SuppressLint("ResourceAsColor")
    private void getHistory()
    {
        Hashtable<String,String> ht =db.getHistory();
        double income=Double.parseDouble(ht.get("income"));
        double expense=Double.parseDouble(ht.get("expense"));
            history1.setText(ht.get("text") + "");
            if (income != 0.0) {
                historyamt1.setText("₹" + income);
                historyview1.setBackgroundResource(R.color.green);
            }
            if (expense != 0.0) {
                historyamt1.setText("₹" + expense);
                historyview1.setBackgroundResource(R.color.red);
            }
            else
            {
                historyamt1.setText("₹" + income);
            }

    }

}