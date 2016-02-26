package findtweets;

import findtweets.Networking.RetrofitCalls;
import findtweets.Networking.RetrofitConfiguration;
import android.app.Application;

/**
 * Created by Silviu on 2/26/2016.
 */
public class FindTweetsApp extends Application
{
	private static RetrofitCalls mRetrofitService;
	private static FindTweetsApp mInstance;

	public static FindTweetsApp getInstance()
	{
		return mInstance;
	}

	@Override
	public void onCreate()
	{
		super.onCreate();
		if (mRetrofitService == null)
		{
			mRetrofitService = newRetrofitService();
		}
		mInstance = this;
	}

	private RetrofitCalls newRetrofitService()
	{
		return RetrofitConfiguration.getBuilder().build().create(RetrofitCalls.class);
	}

	public RetrofitCalls getRetrofitService()
	{
		if (mRetrofitService == null)
		{
			mRetrofitService = newRetrofitService();
		}
		return mRetrofitService;
	}
}
