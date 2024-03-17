package com.example.swasthyasangam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class UserListAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private ArrayList<String> mUsers;

    public UserListAdapter(Context context, ArrayList<String> users) {
        super(context, 0, users);
        mContext = context;
        mUsers = users;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View listItem;
        if (convertView == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.databaselayout, parent, false);
        } else {
            listItem = convertView;
        }

        final String currentUser = mUsers.get(position);

        // Splitting user data
        final String[] userData = currentUser.split("\\$");
        final String username = userData[0];
        final String email = userData[1];
        final String password = userData[2];

        final TextView usernameTextView = listItem.findViewById(R.id.titleTextView);
        final TextView emailTextView = listItem.findViewById(R.id.textView3);
        final TextView passwordTextView = listItem.findViewById(R.id.answerTextView);

        usernameTextView.setText(username);
        emailTextView.setText("Email: " + email);
        passwordTextView.setText("Password: " + password);

        // Initially hide the expandable layout
        final View expandableLayout = listItem.findViewById(R.id.expandableLayout);
        expandableLayout.setVisibility(View.GONE);

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

        return listItem;
    }


}


