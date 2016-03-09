package silviu.pack.Networking;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Silviu on 2/26/2016.
 */
public class RetrofitConfiguration
{
	public static final String API_BASE_URL = "https://api.twitter.com/";

	private static OkHttpClient httpClient = new OkHttpClient();

	public static Retrofit.Builder getBuilder()
	{
		return new Retrofit.Builder()
				.baseUrl(API_BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.client(httpClient);
	}

	public static <S> S createService(Class<S> serviceClass)
	{
		Retrofit adapter = getBuilder().build();

		return adapter.create(serviceClass);
	}
}
