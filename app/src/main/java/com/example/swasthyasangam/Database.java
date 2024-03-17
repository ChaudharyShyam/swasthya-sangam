package com.example.swasthyasangam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Base64;

import androidx.annotation.Nullable;
import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    private Context context;
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
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

        String qry5 = "create table Problems(username, name text, email text, problem text)";
        db.execSQL(qry5);
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

    public ArrayList<String> getUserData() {
        ArrayList<String> arr = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery("SELECT * FROM users", null);
        if (c != null) {
            int usernameIndex = c.getColumnIndex("username");
            int emailIndex = c.getColumnIndex("email");
            int passwordIndex = c.getColumnIndex("password");

            while (c.moveToNext()) {
                String username = "";
                String email = "";
                String password = "";

                // Check if column indexes are valid
                if (usernameIndex != -1) {
                    username = c.getString(usernameIndex);
                }
                if (emailIndex != -1) {
                    email = c.getString(emailIndex);
                }
                if (passwordIndex != -1) {
                    password = c.getString(passwordIndex);
                }

                // Add valid data to the ArrayList
                if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    arr.add(username + "$" + email + "$" + password);
                }
            }
            c.close(); // Close the cursor after use
        }
        database.close();
        return arr;
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

    public void updatePassword(String username, String newPassword) {
        ContentValues cv = new ContentValues();
        cv.put("password", newPassword);
        SQLiteDatabase database = getWritableDatabase();
        database.update("users", cv, "username=?", new String[]{username});
        database.close();
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

    public ArrayList<String> getOrderPlaceData() {
        ArrayList<String> arr = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery("SELECT * FROM orderplace", null);
        if (c != null) {
            int usernameIndex = c.getColumnIndex("username");
            int fullnameIndex = c.getColumnIndex("fullname");
            int addressIndex = c.getColumnIndex("address");
            int contactNoIndex = c.getColumnIndex("contactno");
            int pincodeIndex = c.getColumnIndex("pincode");
            int dateIndex = c.getColumnIndex("date");
            int timeIndex = c.getColumnIndex("time");
            int amountIndex = c.getColumnIndex("amount");
            int otypeIndex = c.getColumnIndex("otype");

            while (c.moveToNext()) {
                String username = "";
                String fullname = "";
                String address = "";
                String contactNo = "";
                int pincode = 0;
                String date = "";
                String time = "";
                float amount = 0f;
                String otype = "";

                // Check if column indexes are valid
                if (usernameIndex != -1) {
                    username = c.getString(usernameIndex);
                }
                if (fullnameIndex != -1) {
                    fullname = c.getString(fullnameIndex);
                }
                if (addressIndex != -1) {
                    address = c.getString(addressIndex);
                }
                if (contactNoIndex != -1) {
                    contactNo = c.getString(contactNoIndex);
                }
                if (pincodeIndex != -1) {
                    pincode = c.getInt(pincodeIndex);
                }
                if (dateIndex != -1) {
                    date = c.getString(dateIndex);
                }
                if (timeIndex != -1) {
                    time = c.getString(timeIndex);
                }
                if (amountIndex != -1) {
                    amount = c.getFloat(amountIndex);
                }
                if (otypeIndex != -1) {
                    otype = c.getString(otypeIndex);
                }

                // Add valid data to the ArrayList
                arr.add(username + "$" + fullname + "$" + address + "$" + contactNo + "$" + pincode + "$" +
                        date + "$" + time + "$" + amount + "$" + otype);
            }
            c.close(); // Close the cursor after use
        }
        database.close();
        return arr;
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

    public ArrayList<String> getProfileData() {
        ArrayList<String> arr = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery("SELECT * FROM profile", null);
        if (c != null) {
            int usernameIndex = c.getColumnIndex("username");
            int imageIndex = c.getColumnIndex("image");

            while (c.moveToNext()) {
                String username = c.getString(usernameIndex);
                byte[] imageByteArray = c.getBlob(imageIndex);

                // Convert byte array to base64 string for easier display or manipulation
                String imageBase64 = Base64.encodeToString(imageByteArray, Base64.DEFAULT);

                // Add username and imageBase64 string to the ArrayList
                arr.add(username + "$" + imageBase64);
            }
            c.close(); // Close the cursor after use
        }
        database.close();
        return arr;
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

    public boolean insertData(String username, String name, String email, String problem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username); // Add username
        contentValues.put("name", name);
        contentValues.put("email", email);
        contentValues.put("problem", problem);
        long result = db.insert("Problems", null, contentValues); // Change table name to "Problems"
        db.close(); // Close the database connection

        return result != -1; // Return true if insertion was successful, false otherwise
    }

    public ArrayList<String> getProblemsData() {
        ArrayList<String> arr = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor c = database.rawQuery("SELECT * FROM Problems", null);
        if (c != null) {
            int usernameIndex = c.getColumnIndex("username");
            int nameIndex = c.getColumnIndex("name");
            int emailIndex = c.getColumnIndex("email");
            int problemIndex = c.getColumnIndex("problem");

            while (c.moveToNext()) {
                String username = c.getString(usernameIndex);
                String name = c.getString(nameIndex);
                String email = c.getString(emailIndex);
                String problem = c.getString(problemIndex);

                arr.add(username + "$" + name + "$" + email + "$" + problem);
            }
            c.close(); // Close the cursor after use
        }
        database.close();
        return arr;
    }



}
