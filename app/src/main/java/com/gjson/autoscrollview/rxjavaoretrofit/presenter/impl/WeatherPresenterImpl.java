package com.gjson.autoscrollview.rxjavaoretrofit.presenter.impl;


import com.gjson.autoscrollview.rxjavaoretrofit.bean.WeatherBean;
import com.gjson.autoscrollview.rxjavaoretrofit.model.callback.OnWeatherCallback;
import com.gjson.autoscrollview.rxjavaoretrofit.model.impl.WeatherRxJavaModelImpl;
import com.gjson.autoscrollview.rxjavaoretrofit.model.interfaces.IWeatherModel;
import com.gjson.autoscrollview.rxjavaoretrofit.presenter.interfaces.IWeatherPresenter;
import com.gjson.autoscrollview.rxjavaoretrofit.view.interfaces.IWeatherView;

/**
 * @DateTime: 2016-07-08 10:31
 * @Author: gjson
 * @Deacription: presenter实现类
 */
public class WeatherPresenterImpl implements IWeatherPresenter, OnWeatherCallback {
    private IWeatherView iMainActivity;
    private IWeatherModel iWeatherModel;

    public WeatherPresenterImpl(IWeatherView iMainActivity) {
        this.iMainActivity = iMainActivity;
        //iWeatherModel = new WeatherNetModelImpl(this);
        iWeatherModel = new WeatherRxJavaModelImpl(this);
    }

    @Override
    public void getWeather() {
        iWeatherModel.getWeather();
    }

    @Override
    public void onResponse(WeatherBean weatherBean) {
        iMainActivity.setTextShow(weatherBean.weatherinfo.toString());
    }

    @Override
    public void onFailure(String error) {
        iMainActivity.setTextShow(error);
    }
}
