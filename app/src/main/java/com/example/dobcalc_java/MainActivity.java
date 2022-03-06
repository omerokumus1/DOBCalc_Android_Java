package com.example.dobcalc_java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView selectedDateTextView;
    private TextView inMinutesTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectedDateTextView = findViewById(R.id.selectedDateTextView);
        inMinutesTextView = findViewById(R.id.inMinutesTextView);

        Button datePicker = findViewById(R.id.datePicketBtn);
        datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickDatePickerBtn();
            }
        });

    }

    private void onClickDatePickerBtn() {


        DatePickerDialog dpd = createDatePickerDialog();
        dpd.getDatePicker().setMaxDate(System.currentTimeMillis() - 86400000);
        dpd.show();


    }

    private DatePickerDialog createDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(this,
                getDarePickerDialogListener(), year, month, day);

    }

    @NonNull
    private DatePickerDialog.OnDateSetListener getDarePickerDialogListener() {
        return new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                String selectedDate = String.format("%d/%d/%d", selectedDay, selectedMonth+1, selectedYear);
                selectedDateTextView.setText(selectedDate);
                String elapsedDayInMinutes = String.valueOf(getElapsedDayInMinutes(selectedDate));
                inMinutesTextView.setText(elapsedDayInMinutes);

            }
        };
    }

    private long getElapsedDayInMinutes(String selectedDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        try {
            Date theDate = sdf.parse(selectedDate);
//                    assert theDate != null;
            long selectedDateInMinutes = (theDate != null ? theDate.getTime() : 0) / 60000;

            Date currentDate = sdf.parse(sdf.format(System.currentTimeMillis()));
            long currentDateInMinutes = (currentDate != null ? currentDate.getTime() : 0) / 60000;

            return currentDateInMinutes - selectedDateInMinutes;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
}