package com.clicagency.lastfmapp.data.remote;

import com.clicagency.lastfmapp.tools.Constants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    //Not used

    public static LastFmApi getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        // create OkHttpClient and register an interceptor
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit.Builder builder = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(Constants.BASE_API_URL);

        return builder.build().create(LastFmApi.class);
    }

//    public static Retrofit getClient(String protocol) {
//        if (retrofit == null || authenticated) {
//            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//            httpClient.addInterceptor(interceptor);
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(protocol + Constants.base_url + "/")
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(httpClient.build())
//                    .build();
//            authenticated = false;
//        }
//        return retrofit;
//    }


//    public static Retrofit getClientJwt(final User user, String protocol) {
//        if (retrofit_jwt == null || !authenticated_jwt || last_user == null || !BasicTools.areEqualed(user, last_user)) {
//            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//            httpClient.addInterceptor(interceptor);
//            httpClient.readTimeout(1, TimeUnit.MINUTES)
//                    .connectTimeout(1, TimeUnit.MINUTES);
//            httpClient.addInterceptor(new Interceptor() {
//                @Override
//                public Response intercept(Chain chain) throws IOException {
//                    last_user = user;
//                    Request original = chain.request();
//                    Request request;
//                    if (user.isSocial()) {
//                        request = original.newBuilder()
//                                .header("Accept", "application/json")
//                                .header("ASTRO-AUTH-KEY", user.getKey())
//                                .method(original.method(), original.body())
//                                .build();
//                    } else {
//                        //WsseToken wsseToken = new WsseToken();
//                        String auth_token = user.getJwt_token();
//                        request = original.newBuilder()
//                                .header("Accept", "application/ld+json")
//                                .header("Authorization", "Bearer "+ user.getJwt_token())
//                                .method(original.method(), original.body())
//                                .build();
//                    }
//
//                    Response response = chain.proceed(request);
//                    return response;
//                }
//            });
//            retrofit_jwt = new Retrofit.Builder()
//                    .baseUrl(protocol + Constants.Myshamra_api_url + "/")
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(httpClient.build())
//                    .build();
//        }
//        authenticated_jwt = true;
//        return retrofit_jwt;
//    }

}
