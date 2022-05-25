package com.krawcewicz.travelcalendar;

import static com.prolificinteractive.materialcalendarview.MaterialCalendarView.SELECTION_MODE_MULTIPLE;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private CalendarDay firstDate;
    private CalendarDay secondDate;
    private CalendarDay thirdDate;
    //This decides how much time between drawing increments on progressBar. (1 day == progressBarTick)
    private int progressBarTick = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /* MY CODE STARTS HERE */
        SeekBar seekbar = findViewById(R.id.seekBar);
        TextView seekBarTxt = findViewById(R.id.seekBarTxt);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress < 10) {
                    progressBarTick = 1000;
                    seekBarTxt.setText("1 day = " + progressBarTick / 1000 + " sec");

                } else {
                    progressBarTick = progress * 100;
                    seekBarTxt.setText("1 day = " + progressBarTick / 1000 + " sec");
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Button startBtn = findViewById(R.id.startBtn);
        startBtn.setEnabled(false);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Instant instant = firstDate.getDate().toInstant();//.minusMillis(firstDate.getDate().getTime());
                long daysBetween = instant.until(thirdDate.getDate().toInstant(), ChronoUnit.DAYS);
                daysBetween += 1; // Because instant.until is endExclusive and doesn't count last day

                int progressIncrement = (int) Math.ceil(100d / daysBetween);
                CountDownTimer countDownTimer = new CountDownTimer(daysBetween * progressBarTick, progressBarTick) {

                    public void onTick(long millisUntilFinished) {
                        progressBar.incrementProgressBy(progressIncrement);
                    }

                    public void onFinish() {
                        // DO something
                    }
                }.start();
            }
        });

        MaterialCalendarView mCalendarView = findViewById(R.id.calendarView);
        mCalendarView.setSelectionMode(SELECTION_MODE_MULTIPLE);
        Calendar endDate = Calendar.getInstance();
        endDate.set(MONTH, 11);
        endDate.set(DAY_OF_MONTH, 31);
        CalendarDay.from(endDate);
        mCalendarView.state().edit()
                .setMinimumDate(CalendarDay.today())
                .setMaximumDate(endDate)
                .commit();

        mCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView calView, @NonNull CalendarDay date, boolean selected) {
                if (firstDate == null) {//on first tap
                    firstDate = date;
                    calView.setDateSelected(date, true);
                    progressBar.setProgress(0, true);
                    startBtn.setEnabled(false);
                } else if (secondDate == null && date.isAfter(firstDate)) {//on second tap
                    secondDate = date;
                    calView.setDateSelected(date, true);
                    startBtn.setEnabled(false);
                } else if (thirdDate == null && secondDate != null && date.isAfter(secondDate)) {//on third tap
                    thirdDate = date;
                    calView.setDateSelected(date, true);
                    startBtn.setEnabled(true);
                } else {
                    firstDate = date;
                    secondDate = null;
                    thirdDate = null;
                    calView.clearSelection();
                    calView.setDateSelected(firstDate, true);
                    progressBar.setProgress(0, true);
                    startBtn.setEnabled(false);
                }

            }
        });

    }

}