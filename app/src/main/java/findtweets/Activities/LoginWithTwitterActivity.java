package findtweets.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import com.crashlytics.android.Crashlytics;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import findtweets.Models.TwitterResultModel;
import io.fabric.sdk.android.Fabric;
import retrofit.Response;
import retrofit.Retrofit;
import silviu.findtweets.R;

/**
 * Created by Silviu on 2/29/2016.
 */
public class LoginWithTwitterActivity extends AppCompatActivity
{
	private TwitterLoginButton twitterLoginButton;


	@Override
	public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState)
	{
		super.onCreate(savedInstanceState, persistentState);
		Fabric.with(this, new Crashlytics());
		setContentView(R.layout.twitter_activity);

		twitterLoginButton = (TwitterLoginButton)findViewById(R.id.login_button);
		twitterLoginButton.setCallback(new Callback<TwitterSession>()
		{
			@Override
			public void success(Result<TwitterSession> result)
			{
				if (result.data != null){
					final TwitterResultModel model = new TwitterResultModel();
					if (result.data.getAuthToken() != null){
						model.setToken(result.data.getAuthToken());
					}
				}
			}

			@Override
			public void failure(TwitterException e)
			{

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
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// Pass the activity result to the login button.
		twitterLoginButton.onActivityResult(requestCode, resultCode, data);
	}
}
