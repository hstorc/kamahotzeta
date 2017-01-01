package com.grey.hstorc.dropdownandprice;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by hstorc on 10/21/16.
 */
public class Transaction implements Parcelable {
    private int id;
    private String owner;
    public Date transactionDate;
    public Float transactionValue;
    public String product;
    public long timestamp;
    public boolean deleted;
    public String share;



    public Transaction(){

    }
    public Transaction(Parcel in){
        this.product = in.readString();
        this.transactionValue=in.readFloat();
        DateFormat formatter = new SimpleDateFormat(UtilitiesHelper.myDateFormat, Locale.US);
        try {
            this.transactionDate = (Date)formatter.parse(in.readString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Transaction(int id,Date date, float value,
                       String item){
        this.id =id;
        this.transactionDate = date;
        this.product=item;
        this.transactionValue = value;

    }

    public Transaction(int id,String owner,Date date, float value,
                       String item, long timestamp, boolean delete, String share){
        this.id =id;
        this.owner = owner;
        this.transactionDate = date;
        this.product=item;
        this.transactionValue = value;
        this.timestamp = timestamp;
        this.share = share;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(product);
        dest.writeFloat(transactionValue);
        dest.writeString(transactionDate.toString());
    }

    public static final Parcelable.Creator<Transaction> CREATOR = new Parcelable.Creator<Transaction>() {

        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };
}
