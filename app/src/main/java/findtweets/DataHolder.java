package findtweets;

import findtweets.Models.TwitterResultModel;

/**
 * Created by CronianAdmin on 2/29/2016.
 */
public class DataHolder
{
	private static final String TAG = "DataHolder";

	public static TwitterResultModel getTwitterResultModel()
	{
		return twitterResultModel;
	}

	public static void setTwitterResultModel(TwitterResultModel twitterResultModel)
	{
		DataHolder.twitterResultModel = twitterResultModel;
	}

	private static TwitterResultModel twitterResultModel;


}
