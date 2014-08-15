package nerd_is.in.number_converter;

import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by Nerd on 2014/6/11 0011.
 */
public class FloatingWindowManager {

    public static String TAG = "FloatingWindowManager";

    private static int mViewWidth;
    private static int mViewHeight;
    private static int mViewX;
    private static int mViewY;

    private static WindowManager mWindowManager;
    private static View mFloatingView;
    private static WindowManager.LayoutParams mFloatingParam;

    public static void createFloatingWindow(final Context context) {
        Log.d(TAG, "createFloatingWindow");

        mWindowManager = getWindowManager(context);
        int screenWidth = mWindowManager.getDefaultDisplay().getWidth();

        if (mFloatingView == null) {
            mFloatingView = LayoutInflater.from(context).inflate(R.layout.floating_window, null);
            View view = mFloatingView.findViewById(R.id.floating_icon);

            Point p = PreferenceUtils.loadFloatingViewPosition(context);
            mViewWidth = view.getLayoutParams().width;
            mViewHeight = view.getLayoutParams().height;
            mViewX = p.x == -1 ? screenWidth - mViewWidth : p.x;
            mViewY = p.y == -1 ? 0 : p.y;
            Log.d(TAG, "floating view width: " + mViewWidth + ", height: " + mViewHeight);

            if (mFloatingParam == null) {
                mFloatingParam = new WindowManager.LayoutParams();
                mFloatingParam.type = WindowManager.LayoutParams.TYPE_PHONE;
                mFloatingParam.format = PixelFormat.TRANSPARENT;
                mFloatingParam.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
                mFloatingParam.gravity = Gravity.LEFT | Gravity.TOP;
                mFloatingParam.width = mViewWidth;
                mFloatingParam.height = mViewHeight;
                mFloatingParam.x = mViewX;
                mFloatingParam.y = mViewY;
            }
            mFloatingView.setLayoutParams(mFloatingParam);

            mFloatingView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

            mFloatingView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mWindowManager.removeView(mFloatingView);
                    mFloatingView = null;
                    PreferenceUtils.saveFloatingViewPosition(context, mViewX, mViewY);
                    context.stopService(new Intent(context, FloatingWindowService.class));
                    return true;
                }
            });

            mFloatingView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_MOVE:
                            mViewX = (int) event.getRawX();
                            mViewY = (int) event.getRawY() - getStatusBarHeight(context);
                            updateFloatingView();
                            break;
                        default:
                            break;
                    }
                    return false;
                }
            });

            mWindowManager.addView(mFloatingView, mFloatingParam);
        }
    }

    private static void updateFloatingView() {
        mFloatingParam.x = mViewX;
        mFloatingParam.y = mViewY;
        mWindowManager.updateViewLayout(mFloatingView, mFloatingParam);
    }

    private static WindowManager getWindowManager(Context context) {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}
