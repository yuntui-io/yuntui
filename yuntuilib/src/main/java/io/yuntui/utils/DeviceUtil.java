package io.yuntui.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * @author arkoosa
 */
public class DeviceUtil {

    /**
     * 获取osVersion
     */
    public static String getOsVersion() {
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取系统包名
     */
    public static String getPackageName(Context context) {
        if(context != null) {
            return context.getPackageName();
        }

        return "";
    }

    /**
     * 获取应用版本名称，非versioncode
     */
    public static String getVersionName(Context context) {
        if(context != null) {
            try {
                PackageManager packageManager = context.getPackageManager();
                PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                return packInfo.versionName;
            } catch (PackageManager.NameNotFoundException ignored) {
                ignored.printStackTrace();
            }
        }

        return "";
    }

    /**
     * 获取设备的唯一标识，deviceId
     *
     * @param context
     * @return
     */
    public static String getDeviceId(Context context) {
        if(context != null) {
            // TODO
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String deviceId = "";
            try {
                deviceId = tm.getDeviceId();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
            if(!TextUtils.isEmpty(deviceId)) {
                return deviceId;
            }
        }

        return UUID.randomUUID().toString();
    }

}
