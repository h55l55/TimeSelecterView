package com.wscq.timeselecterview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.wscq.recyclerwheelview.view.TimePickerView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private TimePickerView pickerView;
    private TextView time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
        initView();
        setListener();
    }

    private void findViewById() {
        pickerView = findViewById(R.id.timePickerView);
        time = findViewById(R.id.time);
    }

    private void initView() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        time.setText(format.format(new Date()));
    }

    private void setListener() {
        pickerView.setOnDateChangeListener(new TimePickerView.OnDateChangeListener() {
            @Override
            public void onDateChange(long date) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                time.setText(format.format(new Date(date)));
            }
        });
    }
}
