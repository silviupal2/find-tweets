package silviu.pack.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import com.crashlytics.android.Crashlytics;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import silviu.pack.Keys;
import silviu.pack.Logger;
import silviu.pack.Models.TwitterResultModel;
import silviu.pack.R;

/**
 * // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
 * private static final String TWITTER_KEY = "4eLYCOklkKjFVkl6j9k06GlMb";
 * private static final String TWITTER_SECRET = "ODXD0wqMbIdVUGgM5eRBykq2p7wMzNBjOtUPI7rom68SUYO5hx";
 * Created by Silviu on 2/29/2016.
 */
public class LoginWithTwitterActivity extends FragmentActivity
{
	public static final String TAG = "LoginWithTwitterActivity";
	private TwitterLoginButton twitterLoginButton;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.twitter_activity);

		twitterLoginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
		twitterLoginButton.setCallback(new Callback<TwitterSession>()
		{
			@Override
			public void success(Result<TwitterSession> result)
			{
				goToMainActivity();
				logUser();
			}


			@Override
			public void failure(TwitterException e)
			{

			}
		});
	}

	private void goToMainActivity()
	{
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		// Pass the activity result to the login button.
		twitterLoginButton.onActivityResult(requestCode, resultCode, data);
	}

	public void forceCrash(View view)
	{
		throw new RuntimeException("This is a crash");
	}

	private void logUser()
	{
		// TODO: Use the current user's information
		// You can call any combination of these three methods
		Crashlytics.setUserIdentifier("12345");
		Crashlytics.setUserEmail("user@fabric.io");
		Crashlytics.setUserName("Test User");
	}


}
