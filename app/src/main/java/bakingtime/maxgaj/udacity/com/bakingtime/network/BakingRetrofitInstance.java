package bakingtime.maxgaj.udacity.com.bakingtime.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BakingRetrofitInstance {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    /* SOURCES:
        https://square.github.io/retrofit/
        http://www.vogella.com/tutorials/Retrofit/article.html
     */
    public static Retrofit getRetrofit() {
        if (retrofit == null){
            // Uncomment line in retrofit builder to enable logging
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            httpClient.addInterceptor(loggingInterceptor);

            Gson gson = new GsonBuilder().setLenient().create();

            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .client(httpClient.build())
                    .build();
        }
        return retrofit;
    }
}
