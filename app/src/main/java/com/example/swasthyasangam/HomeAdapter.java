package com.example.swasthyasangam;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.HashMap;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private Context context;
    private ArrayList<HashMap<String, String>> appointments;

    public HomeAdapter(Context context, ArrayList<HashMap<String, String>> appointments) {
        this.context = context;
        this.appointments = appointments;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HashMap<String, String> appointment = appointments.get(position);

        holder.fullnameTextView.setText(appointment.get("fullname"));
        holder.addressTextView.setText(appointment.get("address"));
        holder.datetimeTextView.setText(appointment.get("date") + " " + appointment.get("time"));
        holder.amountTextView.setText(appointment.get("amount"));
        holder.contactnoTextView.setText(appointment.get("contactno"));
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView fullnameTextView;
        TextView addressTextView;
        TextView datetimeTextView;
        TextView amountTextView;
        TextView contactnoTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fullnameTextView = itemView.findViewById(R.id.line_a);
            addressTextView = itemView.findViewById(R.id.line_b);
            datetimeTextView = itemView.findViewById(R.id.line_c);
            amountTextView = itemView.findViewById(R.id.line_d);
            contactnoTextView = itemView.findViewById(R.id.line_e);
        }
    }
}
