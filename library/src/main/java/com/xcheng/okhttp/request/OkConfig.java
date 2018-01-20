package com.xcheng.okhttp.request;

import com.xcheng.okhttp.callback.HttpParser;
import com.xcheng.okhttp.callback.JsonParser;
import com.xcheng.okhttp.util.EasyPreconditions;

import okhttp3.OkHttpClient;

/**
 * 全局的EasyOkHttp配置类
 * Created by chengxin on 2017/6/27.
 */
public class OkConfig {
    private final OkHttpClient client;
    private final String host;
    private final Class<? extends HttpParser> parserClass;
    private final boolean postUiIfCanceled;

    public static Builder newBuilder() {
        return new Builder();
    }

    private OkConfig(Builder builder) {
        client = builder.client;
        host = builder.host;
        postUiIfCanceled = builder.postUiIfCanceled;
        parserClass = builder.parserClass;
    }

    public OkHttpClient client() {
        return client;
    }

    public String host() {
        EasyPreconditions.checkState(host != null, "host==null,have you init it in OkConfig");
        return host;
    }

    public boolean postUiIfCanceled() {
        return postUiIfCanceled;
    }

    public Class<? extends HttpParser> parserClass() {
        return parserClass;
    }

    public static class Builder {
        private OkHttpClient client;
        private String host;
        private boolean postUiIfCanceled;
        private Class<? extends HttpParser> parserClass;

        public Builder() {
            postUiIfCanceled = false;
        }

        public Builder host(String host) {
            this.host = EasyPreconditions.checkNotNull(host, "host==null");
            return this;
        }

        public Builder client(OkHttpClient client) {
            this.client = EasyPreconditions.checkNotNull(client, "client==null");
            return this;
        }

        /**
         * @param postUiIfCanceled true 当请求被取消的时候仍然回调UI,false 不回调
         */
        public Builder postUiIfCanceled(boolean postUiIfCanceled) {
            this.postUiIfCanceled = postUiIfCanceled;
            return this;
        }

        public Builder parserClass(Class<? extends HttpParser> parserClass) {
            this.parserClass = EasyPreconditions.checkNotNull(parserClass, "parserClass==null");
            return this;
        }

        /**
         * Note: If  {@link #client(OkHttpClient)}  is not called a default {@link
         * OkHttpClient} will be created and used.
         * If  {@link #parserClass(Class)}  is not called a default {@link JsonParser} will be used.
         */
        public OkConfig build() {
            if (client == null) {
                //Lazy Initialization,because it is weight
                client = new OkHttpClient();
            }
            if (parserClass == null) {
                parserClass = JsonParser.class;
            }
            return new OkConfig(this);
        }
    }
}
