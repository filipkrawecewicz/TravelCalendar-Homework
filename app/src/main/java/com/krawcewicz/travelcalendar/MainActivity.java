package com.krawcewicz.travelcalendar;

import static java.util.Calendar.DAY_OF_MONTH;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        CalendarConstraints constraints = new CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now())
                .setStart(new Date().getTime())
                .setEnd(getLastDayOfYear())
                .build();

        MaterialDatePicker<Long> firstPicker = MaterialDatePicker.Builder.datePicker()
                .setCalendarConstraints(constraints)
                .setTitleText("Select start date")
                .build();

        MaterialDatePicker<Long> secondPicker = MaterialDatePicker.Builder.datePicker()
                .setCalendarConstraints(constraints)
                .setTitleText("Select middle date")
                .build();

        MaterialDatePicker<Long> thirdPicker = MaterialDatePicker.Builder.datePicker()
                .setCalendarConstraints(constraints)
                .setTitleText("Select end date")
                .build();

        Button firstDateBtn = findViewById(R.id.firstDateBtn);
        Button secondDateBtn = findViewById(R.id.secondDateBtn);
        Button thirdDateBtn = findViewById(R.id.thirdDateBtn);
        TextView firstDateTxt = findViewById(R.id.firstDateTxt);
        TextView secondDateTxt = findViewById(R.id.secondDateTxt);
        TextView thirdDateTxt = findViewById(R.id.thirdDateTxt);
        MaterialCalendarView calendarView = findViewById(R.id.calendarView);


        firstPicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                String stringDate = dateStringFromLong(selection);
                firstDateTxt.setText(stringDate);
                calendarView.setDateSelected(dateFromLong(selection), true);
            }
        });
        secondPicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                String stringDate = dateStringFromLong(selection);
                secondDateTxt.setText(stringDate);
                calendarView.setDateSelected(dateFromLong(selection), true);
            }
        });
        thirdPicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                String stringDate = dateStringFromLong(selection);
                thirdDateTxt.setText(stringDate);
                calendarView.setDateSelected(dateFromLong(selection), true);
            }
        });

        firstDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstPicker.show(getSupportFragmentManager(), "DATE_PICKER1");
            }
        });
        secondDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondPicker.show(getSupportFragmentManager(), "DATE_PICKER2");
            }
        });
        thirdDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thirdPicker.show(getSupportFragmentManager(), "DATE_PICKER3");
            }
        });


    }

    private String dateStringFromLong(Long selection) {
        return Instant.ofEpochMilli(selection).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_LOCAL_DATE);
    }
    private Date dateFromLong(Long selection) {
        return new Date(selection);
    }

    private long getLastDayOfYear() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(DAY_OF_MONTH, 31);
        Date lastDay = cal.getTime();
        System.out.println("### " + lastDay);
        return lastDay.getTime();
    }
}