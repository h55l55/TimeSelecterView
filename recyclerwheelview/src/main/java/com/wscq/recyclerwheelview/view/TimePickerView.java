package com.wscq.recyclerwheelview.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.wscq.recyclerwheel.R;
import com.wscq.recyclerwheelview.adapter.DayWheelAdapter;
import com.wscq.recyclerwheelview.adapter.WheelAdapter;
import com.wscq.recyclerwheelview.module.Day;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2019/1/9
 * @describe 时间选择器
 */
public class TimePickerView extends LinearLayout {
    private String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    SimpleDateFormat format = new SimpleDateFormat("yyyy年M月d");
    Calendar calendar = Calendar.getInstance();
    private WheelRecyclerView yearWheel;
    private WheelRecyclerView mouthWheel;
    private WheelRecyclerView dayWheel;
    private WheelRecyclerView hourWheel;
    private WheelRecyclerView minutesWheel;

    private List<String> yearList;
    private List<String> mouthList;
    private List<String> hourList;
    private List<String> minuteList;
    private DayWheelAdapter dayAdapter;
    private OnDateChangeListener onDateChangeListener;

    public TimePickerView(Context context) {
        this(context, null);
    }

    public TimePickerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimePickerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //设置当前view对应的布局
        LayoutInflater.from(context).inflate(R.layout.view_time_picker, this);
        findView();
        //初始化各个view状态
        initView();
    }

    private void findView() {
        yearWheel = findViewById(R.id.yaer_wheel);
        mouthWheel = findViewById(R.id.mouth_wheel);
        dayWheel = findViewById(R.id.day_wheel);
        hourWheel = findViewById(R.id.hour_wheel);
        minutesWheel = findViewById(R.id.minutes_wheel);
    }

    public void initView() {
        initData();
        setAdapter();
        selectToday();
        setListener();
    }

    /**
     * 给年月时分的各个view赋初值
     */
    private void initData() {
        yearList = new ArrayList<>();
        for (int i = 1970; i < 2099; i++) {
            yearList.add(i + "年");
        }
        mouthList = new ArrayList<>();
        for (int i = 1; i < 13; i++) {
            mouthList.add(i + "月");
        }
        hourList = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            hourList.add("" + i);
        }
        minuteList = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            minuteList.add("" + i);
        }
    }

    /**
     * 设置年/月/日/时/分对应的adapter
     */
    private void setAdapter() {
        yearWheel.setAdapter(new WheelAdapter(getContext(), yearList));
        mouthWheel.setAdapter(new WheelAdapter(getContext(), mouthList));
        List<Day> dayList = getDayList(yearList.get(yearWheel.getSelected()),
                mouthList.get(mouthWheel.getSelected()));
        dayAdapter = new DayWheelAdapter(getContext(), dayList);
        dayWheel.setAdapter(dayAdapter);
        hourWheel.setAdapter(new WheelAdapter(getContext(), hourList));
        minutesWheel.setAdapter(new WheelAdapter(getContext(), minuteList));
    }

    /**
     * 设置默认选中的日期为今天
     */
    private void selectToday() {
        //当前日期转化为对应的年月日时分字符串
        calendar.setTime(new Date(System.currentTimeMillis()));
        String year = calendar.get(Calendar.YEAR) + "年";
        String mouth = (calendar.get(Calendar.MONTH) + 1) + "月";
        String dayStr = (calendar.get(Calendar.DAY_OF_MONTH)) + "";
        Day day = getDay(dayStr);
        String hour = calendar.get(Calendar.HOUR_OF_DAY) + "";
        String minute = calendar.get(Calendar.MINUTE) + "";

        //设置年/月/时/分的选择器选中
        yearWheel.setSelect(getSafeIndex(yearList.indexOf(year)));
        mouthWheel.setSelect(getSafeIndex(mouthList.indexOf(mouth)));
        hourWheel.setSelect(getSafeIndex(hourList.indexOf(hour)));
        minutesWheel.setSelect(getSafeIndex(minuteList.indexOf(minute)));

        List<Day> dayList = getDayList(year, mouth);
        dayAdapter.setDayList(dayList);
        dayWheel.setSelect(getSafeIndex(dayList.indexOf(day)));
    }

    private void setListener() {
        yearWheel.setOnSelectListener(new WheelRecyclerView.OnSelectListener() {
            @Override
            public void onSelect(int position) {
                String year = yearList.get(position);
                String mouth = mouthList.get(mouthWheel.getSelected());
                dayAdapter.setDayList(getDayList(year, mouth));
                if (onDateChangeListener != null) {
                    onDateChangeListener.onDateChange(getSelected());
                }
            }
        });
        mouthWheel.setOnSelectListener(new WheelRecyclerView.OnSelectListener() {
            @Override
            public void onSelect(int position) {
                String year = yearList.get(yearWheel.getSelected());
                String mouth = mouthList.get(position);
                dayAdapter.setDayList(getDayList(year, mouth));
                if (onDateChangeListener != null) {
                    onDateChangeListener.onDateChange(getSelected());
                }
            }
        });

        WheelRecyclerView.OnSelectListener onSelectListener = new WheelRecyclerView.OnSelectListener() {

            @Override
            public void onSelect(int position) {
                if (onDateChangeListener != null) {
                    onDateChangeListener.onDateChange(getSelected());
                }
            }
        };
        dayWheel.setOnSelectListener(onSelectListener);
        hourWheel.setOnSelectListener(onSelectListener);
        minutesWheel.setOnSelectListener(onSelectListener);
    }

    private int getSafeIndex(int index) {
        return index < 0 ? 0 : index;
    }

    private List<Day> getDayList(String year, String mouth) {
        List<Day> dayOfMouth = new ArrayList<>();
        Date date = null;
        format.applyPattern("yyyy年M月d");
        try {
            date = format.parse(year + mouth + "1");
            calendar.setTime(date);
            int dayCount = calendar.getActualMaximum(Calendar.DATE);
            for (int i = 1; i <= dayCount; i++) {
                calendar.setTime(format.parse(year + mouth + i));
                dayOfMouth.add(getDay(i + ""));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dayOfMouth;
    }

    private Day getDay(String dayStr) {
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return new Day(dayStr, weekDays[w]);
    }

    public long getSelected() {
        try {
            format.applyPattern("yyyy年M月dd-H-m");
            String year = yearList.get(yearWheel.getSelected());
            String mouth = mouthList.get(mouthWheel.getSelected());
            List<Day> dayList = dayAdapter.getDayList();
            int selected = dayWheel.getSelected();
            selected = selected < dayList.size() ? selected : dayList.size() - 1;
            String day = dayList.get(selected).getDay();
            String hour = hourList.get(hourWheel.getSelected());
            String minute = minuteList.get(minutesWheel.getSelected());
            return format.parse(year + mouth + day + "-" + hour + "-" + minute).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return System.currentTimeMillis();
    }

    public void setOnDateChangeListener(OnDateChangeListener onDateChangeListener) {
        this.onDateChangeListener = onDateChangeListener;
    }

    public interface OnDateChangeListener {
        void onDateChange(long time);
    }
}
