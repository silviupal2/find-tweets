package findtweets;

import android.util.Log;
import silviu.findtweets.BuildConfig;

/**
 * Created by Silviu on 2/26/2016.
 */
public class Logger
{
	private static Logger instance;

	public static Logger getLogger()
	{
		if (instance == null)
		{
			instance = new Logger();
		}
		return instance;
	}

	public void i(String TAG, String msg)
	{
		if (BuildConfig.DEBUG)
		{
			Log.i(TAG, "<<< " + msg);
		}
	}
}
