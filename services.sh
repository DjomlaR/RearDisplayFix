#!/system/bin/sh

MODDIR=${0%/*}

# Fix permissions for the APK
chmod 755 $MODDIR/system/priv-app/RearDisplayFix
chmod 644 $MODDIR/system/priv-app/RearDisplayFix/RearDisplayFix.apk