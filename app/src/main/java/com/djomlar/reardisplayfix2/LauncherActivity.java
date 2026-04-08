package com.djomlar.reardisplayfix2;

import android.app.Activity;
import android.hardware.display.DisplayManager;
import android.view.Display;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class LauncherActivity extends Activity {

    private volatile boolean finished = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Delay slightly to ensure main display is actually off
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            DisplayManager displayManager = (DisplayManager) getSystemService(DISPLAY_SERVICE);
            Display mainDisplay = displayManager.getDisplay(Display.DEFAULT_DISPLAY);

            if (mainDisplay != null && mainDisplay.getState() == Display.STATE_OFF) {
                runRearDisplayFix();
            }
        }, 500);

        // Finish immediately to satisfy lifecycle
        safeFinish();
    }

    private void runRearDisplayFix() {
        new Thread(() -> {
            try {
                // Read subscreen state (root)
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

            } catch (Throwable ignored) {
            }

            runOnUiThread(this::safeFinish);
        }).start();        new Thread(() -> {
            try {
                // Read subscreen state (root)
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

            } catch (Throwable ignored) {
            }

            // Finish AFTER work
            runOnUiThread(this::safeFinish);
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        safeFinish();
    }

    private void safeFinish() {
        if (!finished) {
            finished = true;
            finish();
        }
    }
}