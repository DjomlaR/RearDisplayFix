package com.djomlar.reardisplayfix2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ScreenOffReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("com.djomlar.reardisplayfix2.ACTION_HANDLE_SCREEN_OFF".equals(intent.getAction())) {
            new Thread(() -> {
                try {
                    // Read current subscreen state
                    Process check = Runtime.getRuntime().exec(
                        new String[]{"su", "-c", "settings get system subscreen_switch"}
                    );
                    BufferedReader reader = new BufferedReader(
                        new InputStreamReader(check.getInputStream())
                    );
                    String value = reader.readLine();
                    reader.close();

                    if (value == null || value.trim().equals("0")) {
                        Runtime.getRuntime().exec(new String[]{
                            "su", "-c",
                            "settings put system subscreen_switch 1; " +
                            "settings put system subscreen_gesture_wakeup_mode 1; " +
                            "settings put system key_set_gesture_wakeup_by_user 1; " +
                            "am force-stop com.xiaomi.misubscreenui"
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}