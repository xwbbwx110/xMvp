package com.xwb.wb.net;

import com.uoko.uk.BuildConfig;

/**
 * Created by dubai on 2017/12/13.
 */

public class ServerUrl  {
    //
    static String ENV_DEBUG = "debug";
    static String ENV_REL = "release";

    static String CRM_PRE = "http://www.weather.com.cn/";
    static String CRM_RELEASE = "http://crm.uoko.com/ukcrm/";

    /**
     * 当前是否 Release 环境
     *
     * @return
     */
    public static boolean isRelease() {
        return ENV_REL.equals(BuildConfig.API_ENV);
    }

    /**
     * 基础 api地址
     *
     * @return
     */
    public static String baseApi() {
        return isRelease() ? CRM_RELEASE : CRM_PRE;
    }
}
