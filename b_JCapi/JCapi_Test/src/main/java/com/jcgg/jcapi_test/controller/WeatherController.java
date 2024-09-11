package com.jcgg.jcapi_test.controller;

import com.alibaba.fastjson.JSON;
import com.jcgg.jcapi_test.utils.RequestUtils;
import com.jcgg.jcapiclientsdk.model.response.WeatherResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @GetMapping("/localWeather")
    public WeatherResponse getWeather(){
        System.out.println("妈的我进来了");
        String weatherJson = RequestUtils.get("https://api.vvhan.com/api/weather");
        return JSON.parseObject(weatherJson, WeatherResponse.class);
    }
}
