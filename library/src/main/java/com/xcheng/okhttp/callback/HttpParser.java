package com.xcheng.okhttp.callback;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xcheng.okhttp.error.EasyError;
import com.xcheng.okhttp.request.OkCall;
import com.xcheng.okhttp.request.OkRequest;
import com.xcheng.okhttp.request.OkResponse;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * http 请求返回解析接口类，解析{@link Response}和{@link IOException}
 */
public interface HttpParser<T> {

    /**
     * 拦截http请求，模拟服务端返回的数据
     *
     * @param okCall call
     * @return 模拟的响应, 如果为null,则会正常请求服务端数据
     */
    @Nullable
    OkResponse<T> mockResponse(OkCall<T> okCall);

    /**
     * called by {@link okhttp3.Callback#onResponse(Call, Response)}}
     *
     * @throws NullPointerException 如果返回值为空
     */
    @NonNull
    OkResponse<T> parseNetworkResponse(OkCall<T> okCall, Response response) throws IOException;

    /**
     * called by {@link okhttp3.Callback#onFailure(Call, IOException)}
     *
     * @param e IO错误
     * @return EasyError  对应IOException的error信息
     * @throws NullPointerException 如果返回值为空
     */
    @NonNull
    EasyError parseException(OkCall<T> okCall, IOException e);

    /**
     * 根据类型获取解析的HttpParser
     * Created by chengxin on 2018/1/22.
     */
    interface Factory {

        /**
         * @param request request请求
         * @return 创建对应的 HttpParser
         */
        @NonNull
        HttpParser<?> parser(OkRequest request);
    }
}