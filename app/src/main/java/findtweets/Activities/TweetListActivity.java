package findtweets.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import findtweets.Logger;
import retrofit.Response;
import retrofit.Retrofit;
import silviu.findtweets.R;

public class TweetListActivity extends AppCompatActivity
{
	private final String TAG = TweetListActivity.this.getClass().getSimpleName();
	TwitterLoginButton twitterLoginButton;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tweet_list);
		twitterLoginButton = (TwitterLoginButton) findViewById(R.id.login_button);
		twitterLoginButton.setCallback(new Callback<TwitterSession>()
		{
			@Override
			public void success(Result<TwitterSession> result)
			{
				Logger.getLogger().i(TAG, result.data.getUserName());
				result.data.getAuthToken();
			}

			@Override
			public void failure(TwitterException e)
			{
				e.printStackTrace();
				Logger.getLogger().i(TAG, "failure(e)");
			}

			@Override
			public void onResponse(Response<TwitterSession> response, Retrofit retrofit)
			{

			}

			@Override
			public void onFailure(Throwable t)
			{

			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		// Pass the activity result to the login button.
		twitterLoginButton.onActivityResult(requestCode, resultCode, data);
	}
}
