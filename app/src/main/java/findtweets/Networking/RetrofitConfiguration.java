package findtweets.Networking;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

import java.io.IOException;

/**
 * Created by Silviu on 2/26/2016.
 */
public class RetrofitConfiguration
{
	public static final String API_BASE_URL = "https://api.twitter.com/1.1/";

	private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

	public static Retrofit.Builder getBuilder()
	{
		return new Retrofit.Builder()
				.baseUrl(API_BASE_URL)
				.addConverterFactory(GsonConverterFactory.create());
	}

	public static <S> S createService(Class<S> serviceClass)
	{
		Retrofit adapter = getBuilder().build();

		return adapter.create(serviceClass);
	}
}
