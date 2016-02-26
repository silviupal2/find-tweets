package findtweets.Networking;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Silviu on 2/26/2016.
 */
public class RetrofitConfiguration
{
	public static Retrofit.Builder getBuilder()
	{
		return new Retrofit.Builder()
				.baseUrl("https://api.twitter.com/1.1/search/tweets.json")
				.addConverterFactory(GsonConverterFactory.create());
	}
}
