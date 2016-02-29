package findtweets.Models;

import com.twitter.sdk.android.core.TwitterAuthToken;

/**
 * Created by Silviu on 2/29/2016.
 */
public class TwitterResultModel
{
	private TwitterAuthToken token;

	public void setToken(TwitterAuthToken token)
	{
		this.token = token;
	}

	public TwitterAuthToken getToken()
	{
		return token;
	}
}
