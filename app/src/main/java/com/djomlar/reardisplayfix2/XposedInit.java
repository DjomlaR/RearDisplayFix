package com.djomlar.reardisplayfix2;

import android.content.Context;
import android.content.Intent;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class XposedInit implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam lpparam) {

        if (!"android".equals(lpparam.packageName)) return;

        try {
            Class<?> pwmClass = lpparam.classLoader.loadClass(
                    "com.android.server.policy.PhoneWindowManager"
            );

            XposedBridge.hookAllMethods(
                    pwmClass,
                    "startedGoingToSleep",
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) {
                            try {
                                Context context = (Context) Class
                                        .forName("android.app.ActivityThread")
                                        .getMethod("currentApplication")
                                        .invoke(null);

                                if (context == null) return;

                                Intent i = new Intent();
                                i.setClassName(
                                        "com.djomlar.reardisplayfix2",
                                        "com.djomlar.reardisplayfix2.LauncherActivity"
                                );
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                                context.startActivity(i);

                            } catch (Throwable ignored) {
                                // Ignore errors silently, nothing to log
                            }
                        }
                    }
            );

        } catch (Throwable ignored) {
            // Ignore errors silently
        }
    }
}