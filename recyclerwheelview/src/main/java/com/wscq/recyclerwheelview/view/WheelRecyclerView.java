package com.wscq.recyclerwheelview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.wscq.recyclerwheel.R;
import com.wscq.recyclerwheelview.adapter.AWheelAdapter;
import com.wscq.recyclerwheelview.utils.Util;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2019/1/4
 * @describe
 */
public class WheelRecyclerView extends RecyclerView {

    /**
     * 选择器对应的adapter
     */
    private AWheelAdapter adapter;
    private LinearLayoutManager layoutManager;
    /**
     * 滚动监听
     */
    private OnSelectListener onSelectListener;
    /**
     * 绘制背景色的paint
     */
    private Paint paint;
    /**
     * 当前选中的色块编号
     */
    private int selected;

    public WheelRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    @Override
    public void onDraw(Canvas c) {
        //绘制分割线
        if (adapter != null) {
            float startX = 0;
            int itemHeight = adapter.getItemHeight();
            float topY = itemHeight * adapter.getOffset();
            float endX = getMeasuredWidth();
            float bottomY = itemHeight * (adapter.getOffset() + 1);
            //绘制选择块背景色
            c.drawRect(startX, topY, endX, bottomY, paint);
        }
        super.onDraw(c);
    }

    private void initPaint() {
        paint = new Paint();
        paint.setColor(0xffFFF8E2);
    }

    private void init(AWheelAdapter adapter) {
        layoutManager = new LinearLayoutManager(getContext());
        setLayoutManager(layoutManager);
        this.adapter = adapter;
        setSelect(0);
        scrollTo(0, 0);
        addOnScrollListener(new OnWheelScrollListener());
    }

    public void setAdapter(AWheelAdapter adapter) {
        init(adapter);
        super.setAdapter(adapter);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        if (adapter != null) {
            int itemHeight = adapter.getItemHeight();
            setMeasuredDimension(widthSpec, itemHeight * adapter.getShowItemNum());
        } else {
            super.onMeasure(widthSpec, heightSpec);
        }
    }

    public void setOnSelectListener(OnSelectListener listener) {
        onSelectListener = listener;
    }

    public void setSelect(int position) {
        selected = position;
        layoutManager.scrollToPosition(selected);
    }

    public int getSelected() {
        return selected;
    }

    private class OnWheelScrollListener extends OnScrollListener {
        private boolean isCallback;

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                //控制停下时候滚动的位置
                //当控件停止滚动时，获取可视范围第一个item的位置，滚动调整控件以使选中的item刚好处于正中间
                int firstVisiblePos = layoutManager.findFirstVisibleItemPosition();
                if (firstVisiblePos == RecyclerView.NO_POSITION) {
                    return;
                }

                Rect rect = new Rect();
                layoutManager.findViewByPosition(firstVisiblePos).getHitRect(rect);
                isCallback = true;
                if (Math.abs(rect.top) > adapter.getItemHeight() / 2) {
                    smoothScrollBy(0, rect.bottom);
                } else if (rect.top == 0) {
                    if (onSelectListener != null) {
                        selected = firstVisiblePos;
                        onSelectListener.onSelect(selected);
                    }
                } else {
                    smoothScrollBy(0, rect.top);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            //控制滚动到对应位置的颜色
            setSelectedItem();
        }
    }

    private void setSelectedItem() {
        //获取可视范围的第一个控件的位置
        int firstVisiblePos = layoutManager.findFirstVisibleItemPosition();
        if (firstVisiblePos == RecyclerView.NO_POSITION) {
            return;
        }
        Rect rect = new Rect();
        View viewByPosition = layoutManager.findViewByPosition(firstVisiblePos);
        viewByPosition.getHitRect(rect);
        //被选中item是否已经滑动超出中间区域
        boolean overScroll = Math.abs(rect.top) > adapter.getItemHeight() / 2 ? true : false;
        //更新可视范围内所有item的样式
        for (int i = 0; i < 1 + adapter.getOffset() * 2; i++) {
            View item;
            if (overScroll) {
                item = layoutManager.findViewByPosition(firstVisiblePos + i + 1);
            } else {
                item = layoutManager.findViewByPosition(firstVisiblePos + i);
            }
            if (item != null) {
                if (i == adapter.getOffset()) {
                    //设置选中色
                    adapter.setSelected(findContainingViewHolder(item));
                } else {
                    //设置正常色
                    adapter.setNormal(findContainingViewHolder(item));
                }
            }
        }
    }

    public interface OnSelectListener {
        void onSelect(int position);
    }
}
