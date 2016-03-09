package silviu.pack;

import retrofit.http.Query;
import silviu.pack.Models.QueryResponseModel;
import silviu.pack.Models.TwitterResultModel;

/**
 * Created by Silviu on 2/29/2016.
 */
public class DataHolder
{
	private static final String TAG = "DataHolder";
	private static TwitterResultModel twitterResultModel;
	private static QueryResponseModel queryResponseModel;
	private static String bearerToken;

	public static TwitterResultModel getTwitterResultModel()
	{
		return twitterResultModel;
	}

	public static void setTwitterResultModel(TwitterResultModel twitterResultModel)
	{
		DataHolder.twitterResultModel = twitterResultModel;
	}

	public static void setQueryResponseModel(QueryResponseModel queryResponseModel)
	{
		DataHolder.queryResponseModel = queryResponseModel;
	}

	public static QueryResponseModel getQueryResponseModel()
	{
		return queryResponseModel;
	}

	public static void setBearerToken(String bearerToken)
	{
		DataHolder.bearerToken = bearerToken;
	}

	public static String getBearerToken()
	{
		return bearerToken;
	}
}
