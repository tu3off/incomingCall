package floaterr.floater.floatingwindow;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import floaterr.floater.R;

public class FloatingWindowService extends Service {


    @Override
    public void onCreate() {
        super.onCreate();
        final WindowManager.LayoutParams mLayoutParams = new WindowManager.LayoutParams();
        final Button recButton = new Button(this);
        recButton.setText("Hello");
        recButton.setGravity(Gravity.CENTER_VERTICAL);
        recButton.setBackgroundColor(Color.parseColor("#65FF0000"));
        recButton.setPadding(100, 30, 100, 30);
        final View view = View.inflate(this, R.layout.test_layout, null);
        final FloatingWindow main = (FloatingWindow) view.findViewById(R.id.main);
        final View yes = view.findViewById(R.id.yesButton);
        mLayoutParams.format = PixelFormat.TRANSLUCENT;
        mLayoutParams.flags = WindowManager.LayoutParams.FORMAT_CHANGED; // 8
        mLayoutParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        mLayoutParams.gravity = Gravity.TOP | Gravity.CENTER;
        mLayoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mLayoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        final WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.addView(view, mLayoutParams);
        wm.updateViewLayout(view, mLayoutParams);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("FloatingWindowService", "Click");
                wm.removeView(view);
            }
        });
        main.setWindowChangeListener(new FloatingWindow.OnWindowChangeListener() {
            @Override
            public void move(View v) {
                wm.updateViewLayout(view, mLayoutParams);
            }

            @Override
            public void moveTo(View v, int deltaX, int deltaY) {
                mLayoutParams.x += deltaX;
                mLayoutParams.y += deltaY;
                wm.updateViewLayout(view, mLayoutParams);
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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }
}