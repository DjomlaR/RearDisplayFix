#!/system/bin/sh

MODDIR=${0%/*}

# Ensure correct SELinux labels so Android accepts the app as system-privileged
chcon -R u:object_r:system_file:s0 $MODDIR/system

# Optional but recommended: force package scan
touch /data/system/package_cache