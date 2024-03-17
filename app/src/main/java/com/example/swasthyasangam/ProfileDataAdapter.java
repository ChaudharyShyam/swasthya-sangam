package com.example.swasthyasangam;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

public class ProfileDataAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private ArrayList<String> mProfileData;

    public ProfileDataAdapter(Context context, ArrayList<String> profileData) {
        super(context, 0, profileData);
        mContext = context;
        mProfileData = profileData;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if (listItem == null) {
            listItem = LayoutInflater.from(mContext).inflate(R.layout.profile_list_item, parent, false);
        }

        String currentProfile = mProfileData.get(position);

        // Splitting profile data
        String[] profileParts = currentProfile.split("\\$");
        String username = profileParts[0];
        String imageBase64 = profileParts[1];

        TextView usernameTextView = listItem.findViewById(R.id.usernameTextView);
        ImageView profileImageView = listItem.findViewById(R.id.profileImageView);

        usernameTextView.setText(username);

        // Decode base64 string to bitmap for image display
        byte[] imageByteArray = Base64.decode(imageBase64, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
        profileImageView.setImageBitmap(bitmap);

        return listItem;
    }
}

