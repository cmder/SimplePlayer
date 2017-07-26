package yao.player;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Bruce Yao on 2017/5/23.
 */

public class GestureView extends View {

    private GestureDetector gestureDetector;

    private PlayerOnGestureListener mPlayerOnGestureListener;

    private WindowManager windowManager;
    private DisplayMetrics displayMetrics;

    private float mTouchX = -1f; // 触碰点 x 坐标
    private float mTouchY = -1f; // 触碰点 y 坐标
    private float mXRange, mYRange;

    public GestureView(@NonNull Context context) {
        super(context);

        init();
    }

    public GestureView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    public GestureView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        gestureDetector = new GestureDetector(getContext(), new GestureListener());

        windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        mXRange = displayMetrics.widthPixels;
        mYRange = displayMetrics.heightPixels;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float xChange, yChange;
        float coefficient = 0;

        if (mTouchX != -1f && mTouchY != -1f) {
            xChange = event.getRawX() - mTouchX;
            yChange = event.getRawY() - mTouchY;
        } else {
            xChange = 0f;
            yChange = 0f;
        }

        if (xChange != 0) {
            coefficient = Math.abs(yChange / xChange);
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mPlayerOnGestureListener != null) {
                    mPlayerOnGestureListener.onDown();
                }
                mTouchX = event.getRawX();
                mTouchY = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (Math.abs(yChange) > 10 && coefficient > 2) { // 上下滑动
                    if (event.getRawX() < mXRange / 3) { // 左边上下滑动
                        if (yChange < 0) { // 左边向上滑动
                            if (mPlayerOnGestureListener != null) {
                                mPlayerOnGestureListener.onSlideUpLeft(Math.min(1, Math.abs(yChange) * 2 / mYRange));
                            }
                        } else { // 左边向下滑动
                            if (mPlayerOnGestureListener != null) {
                                mPlayerOnGestureListener.onSlideDownLeft(Math.min(1, Math.abs(yChange) * 2 / mYRange));
                            }
                        }
                        mTouchX = event.getRawX();
                        mTouchY = event.getRawY();
                    }
                    if (event.getRawX() > mXRange * 2 / 3) { // 右边上下滑动
                        if (yChange < 0) { // 右边向上滑动
                            if (mPlayerOnGestureListener != null) {
                                mPlayerOnGestureListener.onSlideUpRight(Math.min(1, Math.abs(yChange) * 2 / mYRange));
                            }
                        } else { // 右边向下滑动
                            if (mPlayerOnGestureListener != null) {
                                mPlayerOnGestureListener.onSlideDownRight(Math.min(1, Math.abs(yChange) * 2 / mYRange));
                            }
                        }
                        mTouchX = event.getRawX();
                        mTouchY = event.getRawY();
                    }
                }
                if (Math.abs(xChange) > 20 && coefficient < 0.5f) { // 左右滑动
                    if (xChange > 0) { // 右滑
                        if (mPlayerOnGestureListener != null) {
                            mPlayerOnGestureListener.onSlideRight(Math.min(1, Math.abs(xChange) * 2 / mXRange));
                        }
                    } else { // 左滑
                        if (mPlayerOnGestureListener != null) {
                            mPlayerOnGestureListener.onSlideLeft(Math.min(1, Math.abs(xChange) * 2 / mXRange));
                        }
                    }
                    mTouchX = event.getRawX();
                    mTouchY = event.getRawY();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mPlayerOnGestureListener != null) {
                    mPlayerOnGestureListener.onGestureEnded();
                }
                mTouchX = mTouchY = -1f;
                break;
            default:
                break;
        }
        return gestureDetector.onTouchEvent(event);
    }

    public PlayerOnGestureListener getPlayerOnGestureListener() {
        return mPlayerOnGestureListener;
    }

    public void setPlayerOnGestureListener(PlayerOnGestureListener playerOnGestureListener) {
        this.mPlayerOnGestureListener = playerOnGestureListener;
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (mPlayerOnGestureListener != null) {
                mPlayerOnGestureListener.onDoubleTap();
            }
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            if (mPlayerOnGestureListener != null) {
                mPlayerOnGestureListener.onSingleTap();
            }
            return true;
        }
    }

    interface PlayerOnGestureListener {

        void onDown();

        void onSingleTap();

        void onDoubleTap();

        void onSlideUpLeft(float percentage);

        void onSlideDownLeft(float percentage);

        void onSlideUpRight(float percentage);

        void onSlideDownRight(float percentage);

        void onSlideLeft(float percentage);

        void onSlideRight(float percentage);

        void onGestureEnded();

    }
}
