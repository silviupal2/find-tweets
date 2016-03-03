package silviu.pack;

import silviu.pack.Models.TwitterResultModel;

/**
 * Created by Silviu on 2/29/2016.
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
