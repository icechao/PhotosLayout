package com.chao.photoslayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import java.util.List;

/**
 * 项目名称:Project
 * 类描述：PhotosLayout照片墙
 * 创建人：超
 * 创建时间: 2015/10/29 11:55
 * 修改人：
 * 修改时间：
 */
public class PhotosLayout extends FrameLayout {


    private int topOffset = 20;
    private int horizentalOffset = 15;
    private int showCount = 4;
    private List<View> views;
    private int downX;
    private int downY;
    private boolean isDrag;
    private int temp;
    private int l;
    private int t;
    private int r;
    private int b;
    private int horizentalSpace;
    private int verticalSpace;
    private OnViewChangerListener listener;

    public PhotosLayout(Context context) {
        super(context);
    }

    public PhotosLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        topOffset = attrs.getAttributeIntValue("http://schemas.android.com/apk/res-auto", "top_offset", 15);
        horizentalOffset = attrs.getAttributeIntValue("http://schemas.android.com/apk/res-auto", "horizental_offset", 20);
        showCount = attrs.getAttributeIntValue("http://schemas.android.com/apk/res-auto", "show_count", 4);
        horizentalSpace = attrs.getAttributeIntValue("http://schemas.android.com/apk/res-auto", "horizental_space", 0);
        verticalSpace = attrs.getAttributeIntValue("http://schemas.android.com/apk/res-auto", "vertical_space", 0);
    }

    public PhotosLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int count = getChildCount();
        if (count < 1) {
            throw new NullPointerException("PhotoLayout 没有子View");
        }
        temp = showCount > count ? count : showCount;
        if (isDrag) {
            super.layout(left, top, right, bottom);
            return;
        }
        layoutChild(left, top, right, bottom, count);
        lastViewDrag(count);
    }


    private void lastViewDrag(final int count) {
        getChildAt(count - 1).setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isDrag = true;
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        downX = (int) event.getRawX();
                        downY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int moveX = (int) event.getRawX();
                        int moveY = (int) event.getRawY();
                        int offsetX = moveX - downX;
                        int offsetY = moveY - downY;
                        v.layout(v.getLeft() + offsetX, v.getTop() + offsetY, v.getRight() + offsetX, v.getBottom() + offsetY);
                        downX = moveX;
                        downY = moveY;
                        break;
                    case MotionEvent.ACTION_UP:
                        int minH = (getLeft() + getRight()) / 2;
                        int minV = (getTop() + getBottom()) / 2;
                        if (v.getLeft() > minH || v.getTop() > minV
                                || v.getLeft() < -minH || v.getTop() < -minV) {
                            v.setOnTouchListener(null);
                            v.setVisibility(View.GONE);
                            removeViewAt(getChildCount() - 1);
                            addView(v, 0);
                            if (listener != null) {
                                listener.viewChanger(v, getChildAt(count - 1));
                            }
                        } else {
                            v.layout(l, t, r, b);
                        }
                        isDrag = false;
                        break;
                }
                return true;
            }
        });
    }

    /**
     * 布局子控件位置
     *
     * @param left   当前控件的左
     * @param top    当前控件的上
     * @param right  当前控件的右
     * @param bottom 当前控件的下
     * @param count  当前控件要显示子View的个数
     */
    private void layoutChild(int left, int top, int right, int bottom, int count) {
        for (int i = temp - 1; i >= 0; i--) {
            View childAt = getChildAt(count - i - 1);
            childAt.setVisibility(VISIBLE);
            l = left + horizentalOffset * i + getPaddingLeft() + horizentalSpace;
            t = top + (showCount - i) * topOffset + getPaddingTop() + verticalSpace;
            r = right - horizentalOffset * i - getPaddingRight() - horizentalSpace;
            b = bottom + (showCount - i) * topOffset - getPaddingBottom() - showCount * topOffset - verticalSpace;
            childAt.layout(l, t, r, b);
        }
    }

    /**
     * 设置显示数量
     *
     * @param showCount 页面显示图片数据 默认4
     */
    public void setShowCount(int showCount) {
        this.showCount = showCount;
        invalidate();
    }

    /**
     * 设置纵向偏移量
     *
     * @param topOffset 偏移量值默认20
     */
    public void setTopOffset(int topOffset) {
        this.topOffset = topOffset;
        invalidate();
    }

    /**
     * 设置横向偏移量
     *
     * @param horizentalOffset 横向偏移量默认15
     */
    public void setHorizentalOffset(int horizentalOffset) {
        this.horizentalOffset = horizentalOffset;
        invalidate();
    }

    /**
     * 横向内容与边框的间距
     *
     * @param horizentalSpace 内容与左右边框的距离不默认为0,使用这个属性代码paddingLeft , paddingRight
     */
    public void setHorizentalSpace(int horizentalSpace) {
        this.horizentalSpace = horizentalSpace;
    }

    /**
     * 纵向内容与边框的间距
     *
     * @param verticalSpace 内容与左右边框的距离不默认为0,使用这个属性代替paddingTop , paddingBottom
     */
    public void setVerticalSpace(int verticalSpace) {
        this.verticalSpace = verticalSpace;
    }

    /**
     * 设置View变换时的接口回调监听
     *
     * @param listener
     */
    public void setOnViewChangerListener(OnViewChangerListener listener) {
        this.listener = listener;
    }

    /**
     * 当最前面的View变换时的接口回调
     */
    public interface OnViewChangerListener {
        /**
         * 当拖动的View发生变化时的回调
         *
         * @param removeView 被拖动出去的View
         * @param newView    新的要显示的view
         */
        void viewChanger(View removeView, View newView);
    }
}


