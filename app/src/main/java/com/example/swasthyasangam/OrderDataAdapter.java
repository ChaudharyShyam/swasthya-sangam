package com.example.swasthyasangam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class OrderDataAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private ArrayList<String> mOrderData;

    public OrderDataAdapter(Context context, ArrayList<String> orderData) {
        super(context, 0, orderData);
        mContext = context;
        mOrderData = orderData;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.ordersdatalayout, parent, false);
        }

        final String currentOrder = mOrderData.get(position);

        // Splitting order data
        final String[] orderParts = currentOrder.split("\\$");
        final String username = orderParts[0];
        final String fullname = orderParts[1];
        final String address = orderParts[2];
        final String contactNo = orderParts[3];
        final String pincode = orderParts[4];
        final String date = orderParts[5];
        final String time = orderParts[6];
        final String amount = orderParts[7];
        final String otype = orderParts[8];

        final TextView usernameTextView = listItem.findViewById(R.id.titleTextView);
        final TextView fullnameTextView = listItem.findViewById(R.id.textView3);
        final TextView addressTextView = listItem.findViewById(R.id.answerTextView);
        final TextView contactNoTextView = listItem.findViewById(R.id.contactNoTextView);
        final TextView pincodeTextView = listItem.findViewById(R.id.pincodeTextView);
        final TextView dateTextView = listItem.findViewById(R.id.dateTextView);
        final TextView timeTextView = listItem.findViewById(R.id.timeTextView);
        final TextView amountTextView = listItem.findViewById(R.id.amountTextView);
        final TextView otypeTextView = listItem.findViewById(R.id.otypeTextView);

        // Initially hide expandable layout
        final View expandableLayout = listItem.findViewById(R.id.expandableLayout);
        expandableLayout.setVisibility(View.GONE);

        usernameTextView.setText(username);

        // Set OnClickListener on usernameTextView to toggle expand/collapse
        usernameTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle visibility of expandable layout
                if (expandableLayout.getVisibility() == View.VISIBLE) {
                    expandableLayout.setVisibility(View.GONE);
                } else {
                    expandableLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        // Set other fields
        fullnameTextView.setText(fullname);
        addressTextView.setText("Address: " + address);
        contactNoTextView.setText("Contact No: " + contactNo);
        pincodeTextView.setText("Pincode: " + pincode);
        dateTextView.setText("Date: " + date);
        timeTextView.setText("Time: " + time);
        amountTextView.setText("Amount:" + amount+ "/-");
        otypeTextView.setText("Order Type: " + otype);

        return listItem;
    }

}

