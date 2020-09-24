package com.yan.highprivacytest;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author wangjieming
 * @date 2019-07-31.
 */
class MissingSplitsComponentHelper {

    private static final String TAG = "MissingSplitsApp";
    private PackageManager packageManager;
    private String packageName;

    MissingSplitsComponentHelper(Context context) {
        packageManager = context.getPackageManager();
        packageName = context.getPackageName();
    }

    void disablingNonActComponents() {
        Log.d(TAG, "Disabling all non-activity components");
        setComponentEnabledSetting(PackageManager.COMPONENT_ENABLED_STATE_DISABLED);
    }

    void enableNonActComponents() {
        Log.d(TAG, "Resetting enabled state of all non-activity components");
        setComponentEnabledSetting(PackageManager.COMPONENT_ENABLED_STATE_DEFAULT);
    }

    private void setComponentEnabledSetting(@EnabledState int newState) {
        for (ComponentInfo componentInfo : getAllComponent()) {
            this.packageManager.setComponentEnabledSetting(
                    new ComponentName(componentInfo.packageName, componentInfo.name), newState,
                    PackageManager.DONT_KILL_APP);
        }
    }

    /**
     * 是否所有组件被禁用
     */
    boolean isAllNonActComponentDisable(List<ComponentInfo> componentInfos) {
        if (componentInfos == null || componentInfos.size() == 0) {
            return false;
        }
        for (ComponentInfo componentInfo : componentInfos) {
            ComponentName componentName =
                    new ComponentName(componentInfo.packageName, componentInfo.name);
            boolean disable = packageManager.getComponentEnabledSetting(componentName)
                    == PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
            if (disable) {
                Log.d(TAG, "Not all non-activity components are disabled");
                continue;
            }
            return false;
        }
        Log.d(TAG, "All non-activity components are disabled");
        return true;
    }

    List<ComponentInfo> getAllComponent() {
        try {
            ArrayList<ComponentInfo> list = new ArrayList<>();
            PackageInfo packageInfo;
            int value = PackageManager.MATCH_DISABLED_COMPONENTS
//                    | PackageManager.GET_ACTIVITIES
                    | PackageManager.GET_PROVIDERS
//                    | PackageManager.GET_RECEIVERS
//                    | PackageManager.GET_SERVICES
                    ;
            if ((packageInfo = packageManager.getPackageInfo(packageName, value)).providers != null) {
                Collections.addAll(list, packageInfo.providers);
            }

            if (packageInfo.receivers != null) {
                Collections.addAll(list, packageInfo.receivers);
            }

            if (packageInfo.services != null) {
                Collections.addAll(list, packageInfo.services);
            }

            if (packageInfo.activities != null) {
                Collections.addAll(list, packageInfo.activities);
            }

            return list;
        } catch (Exception exception) {
            return Collections.emptyList();
        }
    }

    /**
     * @hide
     */
    @IntDef(value = {
            PackageManager.COMPONENT_ENABLED_STATE_DEFAULT,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER,
            PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED,
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface EnabledState {
    }
}
