package com.wscq.recyclerwheelview.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2019/1/7
 * @describe
 */
public abstract class AWheelAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter {
    @Override
    public int getItemCount() {
        return getItemSize() == 0 ? 0 : getItemSize() + getOffset() * 2;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //头尾填充offset个空白view以使数据能处于中间选中状态
        int visibleType;
        int realPosition = position - getOffset();
        if (realPosition >= 0 && realPosition < getItemSize()) {
            onBindWheelViewHolder((VH) holder, realPosition);
            visibleType = View.VISIBLE;
        } else {
            visibleType = View.INVISIBLE;
        }
        holder.itemView.setVisibility(visibleType);
        if (holder.itemView instanceof ViewGroup) {
            int childCount = ((ViewGroup) holder.itemView).getChildCount();
            for (int i = 0; i < childCount; i++) {
                ((ViewGroup) holder.itemView).getChildAt(i).setVisibility(visibleType);
            }
        }
    }


    public int getOffset() {
        return getShowItemNum() / 2;
    }

    /**
     * 获取item的总数,相当于平时的getItemCount
     *
     * @return item的总数量
     */
    public abstract int getItemSize();

    /**
     * 根据position绑定对应的数据
     *
     * @param holder   viewHolder
     * @param position item下标
     */
    public abstract void onBindWheelViewHolder(VH holder, int position);

    /**
     * 获取wheelView中要显示的数量
     *
     * @return 每个wheelView要显示的数量
     */
    public abstract int getShowItemNum();

    /**
     * 获取wheelView中每个item高度
     * 需要设置一个定值,否则wheelview的高度会有问题
     *
     * @return wheelView中每个item的高度
     */
    public abstract int getItemHeight();

    /**
     * 普通状态时候设置item对应的样式
     *
     * @param viewHolder 普通状态的viewHolder
     */
    public void setNormal(VH viewHolder) {
    }

    /**
     * 设置选中状态的item样式
     *
     * @param viewHolder 选中状态的viewHolder
     */
    public void setSelected(VH viewHolder) {
    }
}
