package floaterr.floater;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Typeface;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class FloatingWindow extends Service {

    private int recButtonLastX;
    private int recButtonLastY;
    private int recButtonFirstX;
    private int recButtonFirstY;
    private boolean touchconsumedbyMove = false;

    @Override
    public void onCreate() {
        super.onCreate();


        final WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams();
        final Button  recButton = new Button(this);
        recButton.setText("Hello");
        recButton.setTypeface(null, Typeface.BOLD);
        recButton.setGravity(Gravity.CENTER_VERTICAL);
        recButton.setBackgroundColor(Color.parseColor("#65FF0000"));
        recButton.setPadding(100, 30, 100, 30);
        mLayoutParams.format = PixelFormat.TRANSLUCENT;
        mLayoutParams.flags = WindowManager.LayoutParams.FORMAT_CHANGED; // 8
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        mLayoutParams.gravity = Gravity.TOP | Gravity.CENTER;
        mLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        final WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.addView(recButton, mLayoutParams);
        wm.updateViewLayout(recButton, mLayoutParams);
        recButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {

                int totalDeltaX = recButtonLastX - recButtonFirstX;
                int totalDeltaY = recButtonLastY - recButtonFirstY;
                wm.updateViewLayout(recButton, mLayoutParams);
                switch(event.getActionMasked())
                {
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
                        if (Math.abs(totalDeltaX) >= 5  || Math.abs(totalDeltaY) >= 5) {
                            if (event.getPointerCount() == 1) {
                                mLayoutParams.x += deltaX;
                                mLayoutParams.y += deltaY;
                                touchconsumedbyMove = true;
                                wm.updateViewLayout(recButton, mLayoutParams);
                            }
                            else{
                                touchconsumedbyMove = false;
                            }
                        }else{
                            touchconsumedbyMove = false;
                        }
                        break;
                    default:
                        break;
                }
                return touchconsumedbyMove;
            }
        });

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}