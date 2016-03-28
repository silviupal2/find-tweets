package silviu.pack;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Silviu on 3/25/2016.
 */
public class AppUtils
{
	private static final String TAG = "AppUtils";

	public static boolean hasInternetConnection(Activity activity)
	{
		ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context
				.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		boolean hasInternetConnection = activeNetworkInfo != null && activeNetworkInfo.isConnected();
		Logger.getLogger().i(TAG, "Internet Connection = " + hasInternetConnection);
		return hasInternetConnection;
	}
}
