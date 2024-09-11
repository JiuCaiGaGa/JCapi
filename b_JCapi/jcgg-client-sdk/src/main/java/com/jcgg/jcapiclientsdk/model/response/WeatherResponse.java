package com.jcgg.jcapiclientsdk.model.response;

import lombok.Data;

/**
 * @author jcgg
 */
@Data
public class WeatherResponse {
    private String city;
    private WeatherDetail data;
    private WeatherAir air;
    private String tip;
}

@Data
class WeatherDetail {
    private String date;
    private String week;
    private String type;
    private String low;
    private String high;
    private String fengxiang;
    private String fengli;
    private WeatherSubDetail night;
    private WeatherAir air;
    private String tip;
}

@Data
class WeatherSubDetail {
    private String type;
    private String fengxiang;
    private String fengli;
}

@Data
class WeatherAir {
    private int aqi;
    private int aqi_level;
    private String aqi_name;
    private String co;
    private String no2;
    private String o3;
    private String pm10;
    private String pm2_5;
    private String so2;
}
