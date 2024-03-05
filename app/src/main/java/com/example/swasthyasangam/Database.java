package com.example.swasthyasangam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String qry1 = "create table users (username text, email text , password text)";
        db.execSQL(qry1);

        String qry2 = "create table cart(username text, product text, price float, otype text)";
        db.execSQL(qry2);

        String qry3 = "create table orderplace(username text,fullname text, address text, contactno text, pincode int, date text, time text,amount float,otype text)";
        db.execSQL(qry3);

        String qry4 = "create table profile(username text, image blob)";
        db.execSQL(qry4);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void register (String username, String email , String password){
        ContentValues cv = new ContentValues();
        cv.put("username" , username);
        cv.put("email" , email);
        cv.put("password" , password);
        SQLiteDatabase database = getWritableDatabase();
        database.insert("users", null, cv);
        database.close();

    }
    public int login(String username , String password){
        int result = 0;
        String str[] = new String[2];
        str[0] = username;
        str[1] = password;
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery(" select * from users where username = ? and password = ?",str);
        if (c.moveToFirst()){
            result = 1;
        }
        return result;
    }
    public void addCart(String username, String product, float price, String otype){
        ContentValues cv = new ContentValues();
        cv.put("username",username);
        cv.put("product",product);
        cv.put("price",price);
        cv.put("otype",otype);
        SQLiteDatabase database=getWritableDatabase();
        database.insert("cart",null,cv);
        database.close();
    }
    public int checkCart(String username,String product){
        int result=0;
        String str[] = new String[2];
        str[0] = username;
        str[1] = product;
        SQLiteDatabase database= getReadableDatabase();
        Cursor c = database.rawQuery("select * from cart where username =? and product=?",str);
        if(c.moveToFirst()){
            result=1;
        }
        database.close();
        return result;
    }
    public void removePackage(String username, String product) {
        SQLiteDatabase database = getWritableDatabase();
        database.delete("cart", "username = ? AND product = ?", new String[]{username, product});
        database.close();
    }

    public void removeCart(String username,String otype){
        String str[]=new String[2];
        str[0]=username;
        str[1]=otype;
        SQLiteDatabase database = getWritableDatabase();
        database.delete("cart","username= ? and otype=?",str);
        database.close();
    }

    public ArrayList getCartData(String username,String otype){
        ArrayList<String> arr = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        String str[] = new String[2];
        str[0] = username;
        str[1] = otype;
        Cursor c = database.rawQuery("select * from cart where username = ? and otype=?",str);
        if (c.moveToFirst()){
            do{
                String product = c.getString(1);
                String price = c.getString(2);
                arr.add(product +"$"+ price);
            }while (c.moveToNext());
        }
        database.close();
        return arr;
    }

    public void addOrder(String username, String fullname, String address, String contact, int pincode, String date, String time, float price, String otype){
        ContentValues cv = new ContentValues();
        cv.put("username",username);
        cv.put("fullname",fullname);
        cv.put("address",address);
        cv.put("contactno",contact);
        cv.put("pincode",pincode);
        cv.put("date",date);
        cv.put("time",time);
        cv.put("amount",price);
        cv.put("otype",otype);
        SQLiteDatabase database = getWritableDatabase();
        database.insert("orderplace",null,cv);
        database.close();
    }

    public ArrayList getOrderData(String username){
        ArrayList<String> arr = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        String str[] = new String[1];
        str[0] = username;
        Cursor c = database.rawQuery("select * from orderplace where username= ?",str);
        if (c.moveToFirst()){
            do{
                arr.add(c.getString(1)+"$"+c.getString(2)+"$"+c.getString(3)+"$"+c.getString(4)+"$"+c.getString(5)+"$"+c.getString(6)+"$"+c.getString(7)+"$"+c.getString(8));
            }while (c.moveToNext());
        }
        database.close();
        return arr;
    }
    public int checkAppointmentExists(String username, String fullname, String address, String contact, String date,String time){
        int result = 0;
        String str[] = new String[6];
        str[0]=username;
        str[1]=fullname;
        str[2]=address;
        str[3]=contact;
        str[4]=date;
        str[5]=time;
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery("select * from orderplace where username=? and fullname = ? and address = ? and contactno = ? and date = ? and time = ?",str);
        if(c.moveToFirst()){
            result=1;
        }
        database.close();
        return result;
    }


                                                   //Profile Section
//    public void addProfile(String username, byte[] image) {
//        ContentValues cv = new ContentValues();
//        cv.put("username", username);
//        cv.put("image", image);
//        SQLiteDatabase database = getWritableDatabase();
//        database.insert("profile", null, cv);
//        database.close();
//    }
    // New method to get profile image by username
    public byte[] getProfileImage(String username) {
        byte[] image = null;
        SQLiteDatabase database = null;
        Cursor cursor = null;

        try {
            database = getReadableDatabase();
            String[] selectionArgs = {username};
            cursor = database.rawQuery("SELECT image FROM profile WHERE username = ?", selectionArgs);
            if (cursor.moveToFirst()) {
                image = cursor.getBlob(0);
            }
        } catch (Exception e) {
            // Handle exceptions, log or show error message
            e.printStackTrace();
        } finally {
            // Close cursor and database
            if (cursor != null) {
                cursor.close();
            }
            if (database != null) {
                database.close();
            }
        }
        return image;
    }

    public boolean addOrUpdateProfile(String username, byte[] image) {
        SQLiteDatabase database = null;
        Cursor cursor = null;
        boolean updatedSuccessfully = false;

        try {
            database = getWritableDatabase();
            cursor = database.rawQuery("SELECT * FROM profile WHERE username=?", new String[]{username});
            if (cursor.moveToFirst()) {
                ContentValues cv = new ContentValues();
                cv.put("image", image);
                updatedSuccessfully = database.update("profile", cv, "username=?", new String[]{username}) > 0;
            } else {
                // If no profile image exists, insert a new record
                ContentValues cv = new ContentValues();
                cv.put("username", username);
                cv.put("image", image);
                updatedSuccessfully = database.insert("profile", null, cv) != -1;
            }
        } catch (Exception e) {
            // Handle exceptions, log or show error message
            e.printStackTrace();
        } finally {
            // Close cursor and database
            if (cursor != null) {
                cursor.close();
            }
            if (database != null) {
                database.close();
            }
        }
        return updatedSuccessfully;
    }

    public boolean isCartEmpty(String username, String otype) {
        boolean isEmpty = true;
        SQLiteDatabase database = null;
        Cursor cursor = null;

        try {
            database = getReadableDatabase();
            String[] selectionArgs = {username, otype}; // Correct array declaration
            cursor = database.rawQuery("SELECT COUNT(*) FROM cart WHERE username = ? AND otype = ?", selectionArgs);
            if (cursor != null && cursor.moveToFirst()) {
                int count = cursor.getInt(0);
                isEmpty = count == 0;
            }
        } catch (Exception e) {
            // Handle exceptions, log or show error message
            e.printStackTrace();
        } finally {
            // Close cursor and database
            if (cursor != null) {
                cursor.close();
            }
            if (database != null) {
                database.close();
            }
        }
        return isEmpty;
    }

}