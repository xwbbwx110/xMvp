package com.xwb.wb.enity.response;

import com.xwb.wb.enity.BaseWBHttpResult;

public class WeatherBean extends BaseWBHttpResult {

    /**
     * weatherinfo : {"city":"北京","cityid":"101010100","temp1":"-2℃","temp2":"16℃","weather":"晴","img1":"n0.gif","img2":"d0.gif","ptime":"18:00"}
     */

    public WeatherinfoBean weatherinfo;


    public static class WeatherinfoBean {
        /**
         * city : 北京
         * cityid : 101010100
         * temp1 : -2℃
         * temp2 : 16℃
         * weather : 晴
         * img1 : n0.gif
         * img2 : d0.gif
         * ptime : 18:00
         */

        public String city;
        public String cityid;
        public String temp1;
        public String temp2;
        public String weather;
        public String img1;
        public String img2;
        public String ptime;

        @Override
        public String toString() {
            return "WeatherinfoBean{" +
                    "city='" + city + '\'' +
                    ", cityid='" + cityid + '\'' +
                    ", temp1='" + temp1 + '\'' +
                    ", temp2='" + temp2 + '\'' +
                    ", weather='" + weather + '\'' +
                    ", img1='" + img1 + '\'' +
                    ", img2='" + img2 + '\'' +
                    ", ptime='" + ptime + '\'' +
                    '}';
        }
    }
}
