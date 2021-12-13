package com.example.imthere;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText date_inserted,time_inserted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        date_inserted = findViewById(R.id.EventDate);
        time_inserted = findViewById(R.id.EventTime);

        // Hiding Keyboard
        date_inserted.setInputType(InputType.TYPE_NULL);
        time_inserted.setInputType(InputType.TYPE_NULL);

        // Date Picker
        date_inserted.setOnClickListener(v -> {
            showDate(date_inserted);
        });

        // Time Picker
        time_inserted.setOnClickListener(v -> {
            showTime(time_inserted);
        });
    }

    private void showDate(EditText date_inserted){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH,month);
            calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-mm-dd");
            date_inserted.setText(simpleDateFormat.format(calendar));
        };
        new DatePickerDialog(MainActivity.this,dateSetListener,calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
    }

    private void showTime(EditText time_inserted) {
        Calendar calendar = Calendar.getInstance();
        TimePickerDialog.OnTimeSetListener timeSetListener = (view, hourOfDay, minute) -> {
            calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
            calendar.set(Calendar.MINUTE,minute);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            time_inserted.setText(simpleDateFormat.format(calendar));

        };
        new TimePickerDialog(MainActivity.this,timeSetListener,calendar.get(calendar.HOUR_OF_DAY),calendar.get(calendar.MINUTE),false);
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
}