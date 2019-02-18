package com.wscq.recyclerwheelview.utils;

import android.content.res.Resources;

/**
 * @author 胡文勇
 * @email wenyong.hu@139.com
 * @createTime 2019/1/4
 * @describe
 */
public class Util {
    /**
     * dp转px
     * @param dp
     */
    public static int dp2px(float dp){
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density + 0.5f);
    }

    /**
     * sp转px
     * @param sp
     * @return
     */
    public static int sp2px(float sp){
        return (int) (sp * Resources.getSystem().getDisplayMetrics().scaledDensity + 0.5f);
    }

}
