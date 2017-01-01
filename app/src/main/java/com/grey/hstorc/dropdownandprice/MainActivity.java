package com.grey.hstorc.dropdownandprice;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    AutoCompleteTextView spProducts;
    String products[] = {"fruits", "groceries", "clothes", "books", "fast food"};
    ArrayAdapter<String> adapterProducts;
    private android.widget.TextView transactionDate;
    private SimpleDateFormat dateFormatter;
    public final static Transaction newTransaction= new Transaction();
    private DatePickerDialog transactionDatePickerDialog;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spProducts = (AutoCompleteTextView) findViewById(R.id.spProducts);
        adapterProducts = new ArrayAdapter<String>( this,android.R.layout.simple_spinner_item,
               products);
        adapterProducts.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProducts.setAdapter(adapterProducts);
        dateFormatter = new SimpleDateFormat(UtilitiesHelper.myDateFormat, Locale.US);
        transactionDate = (TextView)findViewById(R.id.transactionDate);
        transactionDate.setText(UtilitiesHelper.getCurrentTimeStamp());
        setDateTimeField();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.grey.hstorc.dropdownandprice/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.grey.hstorc.dropdownandprice/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    private void setDateTimeField() {
        try {

            transactionDate = (android.widget.TextView) findViewById(R.id.transactionDate);
            transactionDate.setOnClickListener(this);

            Calendar newCalendar = Calendar.getInstance();
            transactionDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    transactionDate.setText(dateFormatter.format(newDate.getTime()));
                }

            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        }
        catch(Exception x){
            Log.e("ddp, setdatetimefield",x.getMessage());

        }
    }

    @Override
    public void onClick(View view) {
        if(view == transactionDate) {
            transactionDatePickerDialog.show();
        }
    }

    public void ShowAll(View view) {
        Intent intent = new Intent(this, DisplayAllTransactions.class);
        startActivity(intent);
    }

    public void NextActivity(View view){
        Intent intent = new Intent(this, DisplayAllTransactions.class);
        AutoCompleteTextView products = (AutoCompleteTextView) findViewById(R.id.spProducts);
        String transactiondate= transactionDate.getText().toString();
        EditText priceedit = (EditText) findViewById(R.id.priceEditText);
        String price = priceedit.getText().toString();
        TransactionDB db = new TransactionDB(this);
        Transaction currenttransaction = new Transaction();
        currenttransaction.product=products.getText().toString();
        currenttransaction.transactionDate=db.convertToDate(transactiondate,null);
        currenttransaction.transactionValue=Float.parseFloat(priceedit.getText().toString());
        long id = db.Insert(currenttransaction);
        intent.putExtra("newTransaction", currenttransaction);
        startActivity(intent);


    }

}
