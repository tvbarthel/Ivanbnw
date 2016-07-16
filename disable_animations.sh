#!/bin/bash
#
#

if [ "$#" = 0 ]; then
    echo "No parameters found, run with set_animation_permissions.sh <adbPath>"
    exit 0
fi

adb=$1

# get all the devices
devices=$("$adb" devices | grep -v 'List of devices' | cut -f1 | grep '.')

for device in $devices; do
    echo "Disabling animation for device" $device
    rebootNeeded=0

    window_animation_scale=$($adb -s $device shell settings get global window_animation_scale | tr -d '\r')
    if [ $window_animation_scale != "0" ] && [ $window_animation_scale != "0.0" ]; then
        $adb -s $device shell settings put global window_animation_scale 0
        rebootNeeded=1
        echo "Window animation scale need to be set to 0 : current is $window_animation_scale"
    else
        echo "Window animation scale already set to $window_animation_scale"
    fi

    transition_animation_scale=$($adb -s $device shell settings get global transition_animation_scale | tr -d '\r')
    if [ $transition_animation_scale != "0" ] && [ $transition_animation_scale != "0.0" ]; then
        $adb -s $device shell settings put global transition_animation_scale 0
        rebootNeeded=1
        echo "Transition animation scale need to be set to 0 : current is $transition_animation_scale"
    else
        echo "Transition animation scale already set to $transition_animation_scale"
    fi

    animator_duration_scale=$($adb -s $device shell settings get global animator_duration_scale | tr -d '\r')
    if [ $animator_duration_scale != "0" ] && [ $animator_duration_scale != "0.0" ]; then
        $adb -s $device shell settings put global animator_duration_scale 0
        rebootNeeded=1
        echo "Animation duration scale need to be set to 0 current is $animator_duration_scale"
    else
        echo "Animation duration scale already set to $animator_duration_scale"
    fi

    if [ "$rebootNeeded" -eq 1 ]; then
        echo "Rebooting device" $device
        $adb -s $device shell reboot
    fi
done

for device in $devices; do
    echo "Waiting for device" $device
    $adb -s $device shell wait-for-device
    A=$($adb -s $device shell getprop sys.boot_completed | tr -d '\r')

    while [ "$A" != "1" ]; do
            sleep 2
            A=$($adb -s $device shell getprop sys.boot_completed | tr -d '\r')
    done
    echo "Unlocking lock screen for device" $device
    $adb -s $device shell input keyevent 82
done