package com.krawcewicz.travelcalendar;

import static java.util.Calendar.DAY_OF_MONTH;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Pair;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointBackward;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button showBtn = findViewById(R.id.showDatePickerBtn);

        CalendarConstraints constraints = new CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now())
                .setStart(new Date().getTime())
                .setEnd(getLastDayOfYear())
                .build();
        MaterialDatePicker<Pair<Long, Long>> datePicker = MaterialDatePicker.Builder.dateRangePicker().setCalendarConstraints(constraints)
                .setTitleText("title").build();

        showBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                datePicker.show(getSupportFragmentManager(), "DATE_RANGE_PICKER");
            }
        });
    }

    private long getLastDayOfYear(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(DAY_OF_MONTH, 31);
        Date lastDay = cal.getTime();
        System.out.println("### "+lastDay);
        return lastDay.getTime();
    }
}