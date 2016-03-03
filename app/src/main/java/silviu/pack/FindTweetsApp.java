package silviu.pack;

import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.ndk.CrashlyticsNdk;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import io.fabric.sdk.android.Fabric;
import silviu.pack.Networking.RetrofitCalls;
import silviu.pack.Networking.RetrofitConfiguration;
import android.app.Application;

/**
 * Created by Silviu on 2/26/2016.
 */
public class FindTweetsApp extends Application
{
	private static RetrofitCalls mRetrofitService;
	private static FindTweetsApp mInstance;
	// Note: Your consumer key and secret should be obfuscated in your source code before shipping.
	private static final String TWITTER_KEY    = "4eLYCOklkKjFVkl6j9k06GlMb";
	private static final String TWITTER_SECRET = "ODXD0wqMbIdVUGgM5eRBykq2p7wMzNBjOtUPI7rom68SUYO5hx";



	public static FindTweetsApp getInstance()
	{
		return mInstance;
	}

	@Override
	public void onCreate()
	{
		super.onCreate();
		mInstance = this;
		mRetrofitService = getRetrofitService();
		TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
		Fabric.with(FindTweetsApp.getInstance(), new Twitter(authConfig), new Crashlytics());

	}

	public RetrofitCalls getRetrofitService()
	{
		if (mRetrofitService == null)
		{
			mRetrofitService = newRetrofitService();
		}
		return mRetrofitService;
	}

	private RetrofitCalls newRetrofitService()
	{
		return RetrofitConfiguration.createService(RetrofitCalls.class);
	}
}
