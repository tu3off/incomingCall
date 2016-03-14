package floaterr.floater.floatingwindow;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class FloatingWindow extends LinearLayout {
    public interface OnWindowChangeListener {
        void move(View view);

        void moveTo(View view, int deltaX, int deltaY);
    }

    private OnWindowChangeListener windowChangeListener;
    private int recButtonLastX;
    private int recButtonLastY;
    private int recButtonFirstX;
    private int recButtonFirstY;
    private boolean touchconsumedbyMove = false;


    public void setWindowChangeListener(OnWindowChangeListener windowChangeListener) {
        this.windowChangeListener = windowChangeListener;
    }

    public FloatingWindow(Context context) {
        super(context);
    }

    public FloatingWindow(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatingWindow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public FloatingWindow(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int totalDeltaX = recButtonLastX - recButtonFirstX;
        int totalDeltaY = recButtonLastY - recButtonFirstY;
        windowChangeListener.move(this);
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                recButtonLastX = (int) event.getRawX();
                recButtonLastY = (int) event.getRawY();
                recButtonFirstX = recButtonLastX;
                recButtonFirstY = recButtonLastY;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaX = (int) event.getRawX() - recButtonLastX;
                int deltaY = (int) event.getRawY() - recButtonLastY;
                recButtonLastX = (int) event.getRawX();
                recButtonLastY = (int) event.getRawY();
                if (Math.abs(totalDeltaX) >= 5 || Math.abs(totalDeltaY) >= 5) {
                    if (event.getPointerCount() == 1) {
                        touchconsumedbyMove = true;
                        windowChangeListener.moveTo(this, deltaX, deltaY);
                    } else {
                        touchconsumedbyMove = false;
                    }
                } else {
                    touchconsumedbyMove = false;
                }
                break;
            default:
                break;
        }
        return touchconsumedbyMove;
    }
}
