package com.gjson.autoscrollview.rxjavaoretrofit.retrofitApi;


import com.gjson.autoscrollview.rxjavaoretrofit.bean.WeatherBean;
import com.gjson.autoscrollview.rxjavaoretrofit.config.HttpServerConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @DateTime: 2016-07-08 15:56
 * @Author: gjson
 * @Deacription:  retrofit管理类
 */
public class RetrofitManager {
    private volatile static RetrofitManager mInstance;
    private OkHttpClient okHttpClient;
    private Retrofit retrofit;

    private RetrofitManager() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(10000, TimeUnit.MILLISECONDS)
                    .writeTimeout(10000, TimeUnit.MILLISECONDS)
                    .connectTimeout(10000, TimeUnit.MILLISECONDS)
                    .build();
        }

        if (retrofit == null)
            retrofit = new Retrofit.Builder()
                    .baseUrl(HttpServerConfig.server)
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//用于返回Rxjava调用,非必须
                    .addConverterFactory(JacksonConverterFactory.create())
                    .client(okHttpClient)
                    .build();
        IWeatherService service =  retrofit.create(IWeatherService.class);
        service.getWeatherRxJava().subscribeOn(Schedulers.io())//这连续几个方法都是RxJava里面的
                .observeOn(AndroidSchedulers.mainThread())//AndroidSchedulers是RxAndroid里面的类
                .subscribe(new Subscriber<WeatherBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(WeatherBean weather) {

                    }
                });
    }

    public static RetrofitManager getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitManager.class) {
                if (mInstance == null)
                    mInstance = new RetrofitManager();
            }
        }
        return mInstance;
    }

    public <T> T createService(Class<T> clz) {
        return retrofit.create(clz);
    }
}