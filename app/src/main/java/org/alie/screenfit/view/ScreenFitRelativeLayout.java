package org.alie.screenfit.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import org.alie.screenfit.UiUtil;

/**
 * Created by Alie on 2019/4/29.
 * 类描述
 * 版本
 */
public class ScreenFitRelativeLayout extends RelativeLayout {

    private static final String TAG = "ScreenFitRelativeLayout";
    private boolean isFlag;

    public ScreenFitRelativeLayout(Context context) {
        super(context);
    }

    public ScreenFitRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScreenFitRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!isFlag) {
            int count = this.getChildCount();
            float horizontalScale = UiUtil.getInstance(this.getContext()).getHorizontalScale();
            float verticalScale = UiUtil.getInstance(this.getContext()).getVerticalScale();

            Log.i(TAG,"横向比例："+horizontalScale+" 纵向比例："+verticalScale);
            for (int i = 0; i < count; i++) {
                View childAt = this.getChildAt(i);
                LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                layoutParams.width = (int) (layoutParams.width * horizontalScale);
                layoutParams.height = (int) (layoutParams.height * verticalScale);
                layoutParams.leftMargin = (int) (layoutParams.leftMargin * horizontalScale);
                layoutParams.rightMargin = (int) (layoutParams.rightMargin * verticalScale);
                layoutParams.topMargin = (int) (layoutParams.topMargin * verticalScale);
                layoutParams.bottomMargin = (int) (layoutParams.bottomMargin * verticalScale);
            }
            isFlag = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
