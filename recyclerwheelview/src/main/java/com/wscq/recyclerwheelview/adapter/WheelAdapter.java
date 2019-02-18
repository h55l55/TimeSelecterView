package com.wscq.recyclerwheelview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wscq.recyclerwheel.R;
import com.wscq.recyclerwheelview.utils.Util;

import java.util.List;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2019/1/7
 * @describe
 */
public class WheelAdapter extends AWheelAdapter<WheelAdapter.WheelHolder> {
    private List<String> dataList;
    private Context context;

    public WheelAdapter(Context context, List<String> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public WheelHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_wheel, parent, false);
        WheelHolder holder = new WheelHolder(view);
        return holder;
    }

    @Override
    public int getItemSize() {
        return dataList.size();
    }

    @Override
    public void onBindWheelViewHolder(WheelHolder holder, int position) {
        holder.name.setText(dataList.get(position));
    }

    @Override
    public int getShowItemNum() {
        return 5;
    }

    public int getItemHeight() {
        return Util.dp2px(46);
    }

    class WheelHolder extends RecyclerView.ViewHolder {

        TextView name;
        View view;

        public WheelHolder(View itemView) {
            super(itemView);
            view = itemView;
            name = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }
}
