package org.alie.screenfit;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

/**
 * Created by Alie on 2019/4/28.
 * 类描述  自定义控件工具类
 * 版本
 */
public class UiUtil {

    private static final String TAG = "UiUtil";
    /**
     * 基础参照宽高，此宽高来自于，UI设计的原型参照尺寸
     * UI在进行布局设计，与切图时候，都会依照屏幕分辨率来进行比如 720 * 1230，这就
     * 说明，这套UI设计图的的屏幕分辨率是720*1230，因此我们在进行自定义控件法进行缩放
     * 处理时，就可以以这个尺寸为基准，进行比例换算
     */
    private static final int STANDER_WIDTH = 720;
    private static final int STANDER_HEIGHT = 1230;


    public float displayMetricsWidth;
    public float displayMetricsHeight;
    private Context mContext;
    private WeakReference<Context> weakReference;
    // 这个是获取系统dimen中的字段，由于我们需要获取顶部状态栏的高度，所以这里要获取下
    private static final String DIMEN_CLASS = "com.android.internal.R$dimen";
    private static final String SYSTEM_BAR_HEIGHT = "system_bar_height";
    private static final int DEFUALT_SYSTEM_BAR_HEIGHT = 48;


    private static UiUtil mInstance;

    public static UiUtil getInstance(Context mContext) {
        if (null == mInstance) {
            synchronized (UiUtil.class) {
                if (null == mInstance) {
                    mInstance = new UiUtil(mContext);
                }
            }
        }
        return mInstance;
    }

    private UiUtil(Context context) {
        weakReference = new WeakReference<>(context);
        WindowManager windowManager = (WindowManager) weakReference.get().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        int systemStateBarHeight = getValue(mContext, SYSTEM_BAR_HEIGHT, DEFUALT_SYSTEM_BAR_HEIGHT);
        displayMetricsWidth = displayMetrics.widthPixels;
        displayMetricsHeight = displayMetrics.heightPixels - systemStateBarHeight;

        Log.i(TAG, "宽：" + displayMetricsWidth + " 高：" + displayMetricsHeight +
                " 屏幕密度：" + displayMetrics.densityDpi + " 状态栏高度：" + systemStateBarHeight);
    }


    /**
     * 通过反射 获取系统dimen中的数据
     *
     * @param context
     * @param systemid
     * @param defValue
     * @return
     */
    public int getValue(Context context, String systemid, int defValue) {
        try {
            Class<?> clazz = Class.forName(DIMEN_CLASS);
            Object r = clazz.newInstance();
            Field field = clazz.getField(systemid);
            int x = (int) field.get(r);
            return context.getResources().getDimensionPixelOffset(x);

        } catch (Exception e) {
            return defValue;
        }
    }


    public float getHorizontalScale() {
        return displayMetricsWidth / STANDER_WIDTH;
    }

    public float getVerticalScale() {
        return displayMetricsHeight / STANDER_HEIGHT;
    }
}
