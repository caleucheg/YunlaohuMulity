package com.yang.yunwang.base.ret;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.yang.yunwang.base.util.ConstantUtils;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 */

public class HttpsStringServiceGenerator {
    public static final String API_BASE_URL = ConstantUtils.BASE;
    public static int READ_TIMEOUT = 30;
    public static int WRIT_TIMEOUT = 30;
    public static int CONNECT_TIMEOUT = 30;
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(ToStringConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

    public static <S> S createService(Class<S> serviceClass) {
        return createService(serviceClass, null, READ_TIMEOUT, WRIT_TIMEOUT, CONNECT_TIMEOUT);
    }

    public static <S> S createService(Class<S> serviceClass, int READ_TIMEOUT, int WRIT_TIMEOUT, int CONNECT_TIMEOUT) {
        return createService(serviceClass, null, READ_TIMEOUT, WRIT_TIMEOUT, CONNECT_TIMEOUT);
    }

    public static <S> S createService(Class<S> serviceClass, final String authToken, int READ_TIMEOUT, int WRIT_TIMEOUT, int CONNECT_TIMEOUT) {
        if (authToken != null) {
            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();

                    // Request customization: add request headers
                    Request.Builder requestBuilder = original.newBuilder()
                            .method(original.method(), original.body());

                    Request request = requestBuilder.build();
                    return chain.proceed(request);
                }
            });
        }
        Retrofit retrofit = builder.client(getUnsafeOkHttpClient(READ_TIMEOUT, WRIT_TIMEOUT, CONNECT_TIMEOUT)).build();
        return retrofit.create(serviceClass);
    }

    private static OkHttpClient getUnsafeOkHttpClient(int READ_TIMEOUT, int WRIT_TIMEOUT, int CONNECT_TIMEOUT) {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public X509Certificate[] getAcceptedIssuers() {
                            X509Certificate[] x509Certificates = new X509Certificate[0];
                            return x509Certificates;
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            OkHttpClient okHttpClient =
                    new OkHttpClient.Builder()
                            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                            .writeTimeout(WRIT_TIMEOUT, TimeUnit.SECONDS)//设置写的超时时间
                            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                            .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                            .sslSocketFactory(sslSocketFactory)
                            .hostnameVerifier(new HostnameVerifier() {
                                @Override
                                public boolean verify(String hostname, SSLSession session) {
                                    return true;
                                }
                            }).build();
//            KLog.i(okHttpClient);
//            KLog.i(READ_TIMEOUT);
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
