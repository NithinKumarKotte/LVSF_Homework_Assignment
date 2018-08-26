package com.livesafemobile.android_places_demo_app.app;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import rx.functions.Action0;

public final class Permissions {

    public static boolean areGranted(int[] grantResults) {
        for (int i : grantResults) {
            if (i != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }

    public static boolean hasPermissionInManifest(Context context, String permissionName) {
        final String packageName = context.getPackageName();
        try {
            final PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
            final String[] declaredPermisisons = packageInfo.requestedPermissions;
            if (declaredPermisisons != null && declaredPermisisons.length > 0) {
                for (String p : declaredPermisisons) {
                    if (p.equals(permissionName)) {
                        return true;
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void request(Activity activity, String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }

    public static void ask(Activity activity,
                           String[] permissions,
                           int requestCode,
                           Action0 onShowRational,
                           Action0 onPermissionsAlreadyGranted) {

        if (!arePermissionsAlreadyGranted(activity, permissions)) {

            if (shouldShowRationale(activity, permissions)) {
                onShowRational.call();
            }
            else {
                request(activity, permissions, requestCode);
            }
        }
        else {
            onPermissionsAlreadyGranted.call();
        }
    }

    private static boolean shouldShowRationale(Activity activity, String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                return true;
            }
        }
        return false;
    }

    private static boolean arePermissionsAlreadyGranted(Activity activity, String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity,
                    permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}
