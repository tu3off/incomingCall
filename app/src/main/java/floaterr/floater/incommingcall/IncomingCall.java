package floaterr.floater.incommingcall;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import floaterr.floater.floatingwindow.FloatingWindowService;

public class IncomingCall extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("IncomingCall", "yeah");
        try {
            TelephonyManager tmgr = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            MyPhoneStateListener PhoneListener = new MyPhoneStateListener(context);
            tmgr.listen(PhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
        } catch (Exception e) {
            Log.d("IncomingCall", e.getMessage());
        }

    }

    private class MyPhoneStateListener extends PhoneStateListener {
        private final Context context;
        public MyPhoneStateListener(Context context) {
            this.context = context;
        }

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            super.onCallStateChanged(state, incomingNumber);
            Log.d("IncomingCall", "state: " + state);
            switch (state) {
                case PhoneStateListener.LISTEN_SERVICE_STATE:
                    context.startService(new Intent(context, FloatingWindowService.class));
                    break;

            }
        }
    }
}
