package com.example.swasthyasangam;

import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class OrdersDataActivity extends AppCompatActivity {

    private ListView listView;
    private Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_data);

        listView = findViewById(R.id.ODListView);
        database = new Database(this, "SwasthayaSangam", null, 1); // Change with your database name

        // Fetch order data from the database
        ArrayList<String> orderData = database.getOrderPlaceData();

        // Create custom adapter and set it to the ListView
        OrderDataAdapter adapter = new OrderDataAdapter(this, orderData);
        listView.setAdapter(adapter);
    }
}
