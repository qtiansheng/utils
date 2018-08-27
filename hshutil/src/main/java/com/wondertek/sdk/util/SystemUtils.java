package com.wondertek.sdk.util;

/**
 * 判断小米，华为机型
 * Created by wondertek on 2016/9/20.
 */
public final class SystemUtils {
    private static SystemUtils instance = null;
//    private static final String SYS_EMUI = "sys_emui";
//    private static final String SYS_MIUI = "sys_miui";
//    private static final String SYS_FLYME = "sys_flyme";
//    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
//    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
//    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
//    private static final String KEY_EMUI_API_LEVEL = "ro.build.hw_emui_api_level";
//    private static final String KEY_EMUI_VERSION = "ro.build.version.emui";
//    private static final String KEY_EMUI_CONFIG_HW_SYS_VERSION = "ro.confg.hw_systemversion";

    private SystemUtils() {
        //TODO
    }

    public static SystemUtils getInstance() {
        if (instance == null) {
            instance = new SystemUtils();
        }
        return instance;
    }

    public static String getSystem(){
        String SYS="";
        try {
            String manufacturer = android.os.Build.MANUFACTURER;
            if("xiaomi".equalsIgnoreCase(manufacturer))
            {
                SYS = "sys_miui";//小米
            }else if("huawei".equalsIgnoreCase(manufacturer))
            {
                SYS = "sys_emui";//华为
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return SYS;
    }

}
