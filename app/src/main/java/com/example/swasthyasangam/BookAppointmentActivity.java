package com.example.swasthyasangam;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;
public class BookAppointmentActivity extends AppCompatActivity {
    EditText ed1 , ed2 , ed3 ,ed4;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button dateButton, timeButton,btnbook;
    TextView tv;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);
        tv = findViewById(R.id.textViewApptitle);
        ed1 = findViewById(R.id.editTextAppname);
        ed2 = findViewById(R.id.editTextappaddress);
        ed3 = findViewById(R.id.editTextAppnumber);
        ed4 = findViewById(R.id.editTextAppfees);

        dateButton = findViewById(R.id.buttonDate);
        timeButton = findViewById(R.id.buttonTime);

        btnbook = findViewById(R.id.buttonBookAppointment);

        ed1.setKeyListener(null);
        ed2.setKeyListener(null);
        ed3.setKeyListener(null);
        ed4.setKeyListener(null);

        Intent it = getIntent();
        String title = it.getStringExtra("title");
        String doctorName = it.getStringExtra("doctor_name");
        String hospitalAddress = it.getStringExtra("hospital_address");
        String mobileNumber = it.getStringExtra("mobile_number");
        String consultationFees = it.getStringExtra("consultation_fees");

        tv.setText(title);
        ed1.setText(doctorName);
        ed2.setText(hospitalAddress);
        ed3.setText(mobileNumber);
        ed4.setText(consultationFees);

        initDatePicker();
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        initTimePicker();
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show();
            }
        });

        btnbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database database = new Database(getApplicationContext(),"SwasthayaSangam",null,1);
                SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username"," ").toString();
                if(database.checkAppointmentExists(username,title+": "+doctorName,hospitalAddress,mobileNumber,dateButton.getText().toString(),timeButton.getText().toString())==1){
                    Toast.makeText(getApplicationContext(),"Appointment Already booked",Toast.LENGTH_LONG).show();
                }else{
                    database.addOrder(username,title+": "+doctorName,hospitalAddress,mobileNumber,0,dateButton.getText().toString(),timeButton.getText().toString(),Float.parseFloat(consultationFees),"Appointment");
                    Toast.makeText(getApplicationContext(),"Your Appointment Booked successfully",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(BookAppointmentActivity.this,HomeActivity.class));
                }

            }
        });


    }

    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int i, int i1, int i2) {
                i1=i1+1;
                dateButton.setText(i2+"/"+i1+"/"+i);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int date = cal.get(Calendar.DATE);

        int style = AlertDialog.THEME_DEVICE_DEFAULT_DARK;
        datePickerDialog = new DatePickerDialog(this,style,dateSetListener,year,month,date);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis()+86400000);
    }

    private void initTimePicker(){
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int i, int i1) {
                timeButton.setText(i+":"+i1);
            }
        };
        Calendar cal = Calendar.getInstance();
        int hrs = cal.get(Calendar.HOUR);
        int mins = cal.get(Calendar.MINUTE);

        int style = AlertDialog.THEME_DEVICE_DEFAULT_DARK;
        timePickerDialog = new TimePickerDialog(this, style,timeSetListener,hrs,mins,true);
    }

}