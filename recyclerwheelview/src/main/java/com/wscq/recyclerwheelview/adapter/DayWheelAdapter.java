package com.wscq.recyclerwheelview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wscq.recyclerwheel.R;
import com.wscq.recyclerwheelview.module.Day;
import com.wscq.recyclerwheelview.utils.Util;

import java.util.List;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2019/1/7
 * @describe
 */
public class DayWheelAdapter extends AWheelAdapter<DayWheelAdapter.WheelHolder> {
    private volatile List<Day> dayList;
    private Context context;

    public DayWheelAdapter(Context context, List<Day> mDatas) {
        this.context = context;
        this.dayList = mDatas;
    }

    public void setDayList(List<Day> dayList) {
        this.dayList = dayList;
        notifyDataSetChanged();
    }

    @Override
    public WheelHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_day_wheel, parent, false);
        WheelHolder holder = new WheelHolder(view);
        return holder;
    }

    @Override
    public int getItemSize() {
        return dayList.size();
    }

    @Override
    public void onBindWheelViewHolder(WheelHolder holder, int position) {
        holder.day.setText(dayList.get(position).getDay());
        holder.week.setText(dayList.get(position).getWeek());
    }

    @Override
    public int getShowItemNum() {
        return 5;
    }

    public int getItemHeight() {
        return Util.dp2px(46);
    }

    public List<Day> getDayList() {
        return dayList;
    }

    class WheelHolder extends RecyclerView.ViewHolder {

        TextView day;
        TextView week;
        View view;

        public WheelHolder(View itemView) {
            super(itemView);
            view = itemView;
            day = itemView.findViewById(R.id.day);
            week = itemView.findViewById(R.id.week);
        }
    }
}
