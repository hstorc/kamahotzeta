package com.grey.hstorc.dropdownandprice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DisplayAllTransactions extends AppCompatActivity {

    TableLayout table;
    TableRow row;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_display_all_transactions);
            Intent intent = getIntent();
            TransactionDB db = new TransactionDB(this);
            List<Transaction> transactions = db.getValues(TransactionDB.KamaHotzeta.COLUMN_NAME_TRANSACTION_DATE);
            float total = 0.0f, totalPerDay = 0.0f;
            table = (TableLayout) findViewById(R.id.alltransactions);
            //text
            for (Transaction trans : transactions) {

                row = new TableRow(this);
                row.setLayoutParams(new TableLayout.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                // inner for loop
                TextView tv = new TextView(this);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv.setBackgroundResource(R.drawable.cell_shape);
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(18);
                tv.setPadding(0, 5, 0, 5);

                tv.setText(trans.product);
                row.addView(tv);
                tv = new TextView(this);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                //tv.setBackgroundResource(R.drawable.cell_shape);
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(18);
                tv.setPadding(0, 5, 0, 5);
                tv.setText(trans.transactionValue.toString());
                row.addView(tv);

                tv = new TextView(this);
                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT));
                tv.setBackgroundResource(R.drawable.cell_shape);
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(18);
                tv.setPadding(0, 5, 0, 5);
                DateFormat df3 = new SimpleDateFormat("dd-MMM-yyyy");
                tv.setText(df3.format(trans.transactionDate));
                row.addView(tv);

                table.addView(row);
                total += trans.transactionValue;

            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        }

    public void AddTranation(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void DeleteAll(View view) {
        TransactionDB db = new TransactionDB(this);
        db.deleteAll();
        db.close();
    }


}

