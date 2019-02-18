package com.wscq.recyclerwheelview.module;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2019/1/10
 * @describe
 */
public class Day {
    private String day;
    private String week;

    public Day(String day, String week) {
        this.day = day;
        this.week = week;
    }

    public String getDay() {
        return day;
    }

    public String getWeek() {
        return week;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Day day1 = (Day) o;
        return day.equals(day1.day);
    }

    @Override
    public int hashCode() {
        return day.hashCode();
    }
}
