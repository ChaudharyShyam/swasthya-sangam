package com.example.swasthyasangam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;

public class OrderDetailsAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<HashMap<String, String>> mList;

    public OrderDetailsAdapter(Context context, ArrayList<HashMap<String, String>> list) {
        this.mContext = context;
        this.mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.expand_layout, parent, false);
            holder = new ViewHolder();
            holder.lineA = convertView.findViewById(R.id.line_a);
            holder.expandableLayout = convertView.findViewById(R.id.expandableLayout);
            holder.lineB = convertView.findViewById(R.id.line_b);
            holder.lineC = convertView.findViewById(R.id.line_c);
            holder.lineD = convertView.findViewById(R.id.line_d);
            holder.lineE = convertView.findViewById(R.id.line_e);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HashMap<String, String> item = mList.get(position);

        holder.lineA.setText(item.get("line1"));
        holder.lineB.setText(item.get("line2"));
        holder.lineC.setText(item.get("line3"));
        holder.lineD.setText(item.get("line4"));
        holder.lineE.setText(item.get("line5"));

        // Initially hide the expandable layout
        holder.expandableLayout.setVisibility(View.GONE);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleExpandableLayout(holder.expandableLayout);
            }
        });

        return convertView;
    }

    private void toggleExpandableLayout(View expandableLayout) {
        if (expandableLayout.getVisibility() == View.VISIBLE) {
            expandableLayout.setVisibility(View.GONE);
        } else {
            expandableLayout.setVisibility(View.VISIBLE);
        }
    }

    static class ViewHolder {
        TextView lineA;
        View expandableLayout;
        TextView lineB;
        TextView lineC;
        TextView lineD;
        TextView lineE;
    }
}
