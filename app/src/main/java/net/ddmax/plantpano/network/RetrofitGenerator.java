package net.ddmax.plantpano.network;

import net.ddmax.plantpano.Constants;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author ddMax
 * @since 2017-03-06 11:30 PM.
 */

public class RetrofitGenerator {

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(Constants.URL.BASE)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder();

    public static <S> S createService(
            Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }
}
