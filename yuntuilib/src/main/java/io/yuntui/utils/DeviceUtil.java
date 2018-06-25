package io.yuntui.utils;

import android.Manifest;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Process;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

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
        if (context != null) {
            return context.getPackageName();
        }

        return "";
    }

    /**
     * 获取应用版本名称，非versioncode
     */
    public static String getVersionName(Context context) {
        if (context != null) {
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
        String deviceId = SPUtil.getInstance().getDeviceId();
        if (TextUtils.isEmpty(deviceId)) {
            deviceId = generateDeviceId(context);
            SPUtil.getInstance().setDeviceId(deviceId);
        }

        return deviceId;
    }

    private static String generateDeviceId(Context context) {
        if (context == null) {
            return UUID.randomUUID().toString();
        }
        try {
            TelephonyManager tel = (TelephonyManager) context.getSystemService(
                    Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                return tel.getDeviceId();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return UUID.randomUUID().toString();
    }
}
