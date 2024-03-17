package com.example.swasthyasangam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class ProblemsDataAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private ArrayList<String> mProblemsData;

    public ProblemsDataAdapter(Context context, ArrayList<String> problemsData) {
        super(context, 0, problemsData);
        mContext = context;
        mProblemsData = problemsData;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.problems_list_item, parent, false);
        }

        final String currentProblem = mProblemsData.get(position);

        // Splitting problem data
        final String[] problemParts = currentProblem.split("\\$");
        final String username = problemParts[0];
        final String name = problemParts[1];
        final String email = problemParts[2];
        final String problem = problemParts[3];

        final TextView usernameTextView = listItem.findViewById(R.id.titleTextView);
        final TextView nameTextView = listItem.findViewById(R.id.textView3);
        final TextView emailTextView = listItem.findViewById(R.id.answerTextView);
        final TextView problemTextView = listItem.findViewById(R.id.contactNoTextView);

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
        nameTextView.setText("Name: " + name);
        emailTextView.setText("Email: " + email);
        problemTextView.setText("Problem: " + problem);

        return listItem;
    }

}


