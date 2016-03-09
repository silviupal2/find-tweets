package silviu.pack.Activities;

import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Base64;
import android.view.View;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.AuthToken;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterSession;
import okhttp3.ResponseBody;
import org.w3c.dom.Text;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import silviu.pack.DataHolder;
import silviu.pack.FindTweetsApp;
import silviu.pack.Keys;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import silviu.pack.Logger;
import silviu.pack.Models.AccessTokenModel;
import silviu.pack.Models.QueryResponseModel;
import silviu.pack.Models.TwitterResultModel;
import silviu.pack.R;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity extends FragmentActivity
{
	public static final String TAG                  = "MainActivity";
	private             String CONSUMER_KEY         = "4eLYCOklkKjFVkl6j9k06GlMb";
	private             String CONSUMER_SECRET      = "ODXD0wqMbIdVUGgM5eRBykq2p7wMzNBjOtUPI7rom68SUYO5hx";
	private             String consumerKeyAndSecret = String.format("%s:%s", CONSUMER_KEY, CONSUMER_SECRET);


	private String auth = "OAuth oauth_consumer_key=\"4eLYCOklkKjFVkl6j9k06GlMb\", " +
			"oauth_nonce=\"efea8a6633304d8772506b960a362d79\", oauth_signature=\"5k%2BP9YnvrFmYIxhEv3pE8oT7hIM%3D\", oauth_signature_method=\"HMAC-SHA1\", oauth_timestamp=\"1457251387\", oauth_token=\"131812266-9vuJ60LWf0JUHVPUNL8kDqYT2yqv6nGXxz1LIKkJ\", oauth_version=\"1.0\"";

	private TextInputEditText mSearchField;
	private AppCompatButton   mSearchButton;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mSearchField = (TextInputEditText) findViewById(R.id.search_field);
		mSearchButton = (AppCompatButton) findViewById(R.id.search_button);
		mSearchButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				try
				{
					performSearch();
				}
				catch (UnsupportedEncodingException e)
				{
					e.printStackTrace();
				}
			}
		});
		mSearchField.setOnEditorActionListener(mSearchFieldListener);

		String encodedString = Base64.encodeToString(consumerKeyAndSecret.getBytes(), Base64.NO_WRAP);
		String bearerString = String.format("Basic %s", encodedString);
		if (TextUtils.isEmpty(DataHolder.getBearerToken()))
		{
			Map<String, String> map = new HashMap<>();
			map.put(Keys.KEY_GRANT_TYPE, Keys.CLIENT_CREDENTIALS_TYPE);
			Call<AccessTokenModel> authCall = FindTweetsApp.getInstance().getRetrofitService().getAuthentication
					(bearerString, Keys.CLIENT_CREDENTIALS_TYPE);
			authCall.enqueue(new Callback<AccessTokenModel>()
			{
				@Override
				public void onResponse(Call<AccessTokenModel> call, Response<AccessTokenModel> response)
				{
					Logger.getLogger().i(TAG, "authCall");
					if (response != null)
					{
						Logger.getLogger().i(TAG, "authCall successful");
						if (response.isSuccess() && response.body() != null)
						{
							final AccessTokenModel accessTokenModel = response.body();
							Logger.getLogger().i(TAG, "AccessTokenModel accessToken = " + accessTokenModel
									.getAccessToken() + " typeToken = " + accessTokenModel.getTokenType());
							DataHolder.setBearerToken(response.body().getAccessToken());
						}
					}
					else
					{
						Logger.getLogger().i(TAG, "authCall not successful");
					}
				}

				@Override
				public void onFailure(Call<AccessTokenModel> call, Throwable t)
				{
					Logger.getLogger().i(TAG, "authCall failure");
				}
			});
		}


		//TODO show validations
	}

	private TextView.OnEditorActionListener mSearchFieldListener = new TextView.OnEditorActionListener()
	{
		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
		{
			switch (actionId)
			{
				case EditorInfo.IME_ACTION_DONE:
					try
					{
						performSearch();
					}
					catch (UnsupportedEncodingException e)
					{
						e.printStackTrace();
					}
					return false;
			}

			return false;
		}
	};

	public void performSearch() throws UnsupportedEncodingException
	{
		String textInput = mSearchField.getText().toString();
		if (!TextUtils.isEmpty(textInput) && noMoreThanTenWords())
		{
/*			TwitterSession session = Twitter.getSessionManager().getActiveSession();
			TwitterAuthToken authToken = session.getAuthToken();
			String token = authToken.token;
			String secret = authToken.secret;
			String username = session.getUserName();
			long userId = session.getUserId();*/

			final String authBearer = String.format("Bearer %s", DataHolder.getBearerToken());

			Call<QueryResponseModel> queryCall = FindTweetsApp.getInstance().getRetrofitService().search
					(authBearer, textInput);
			queryCall.enqueue(new Callback<QueryResponseModel>()
			{
				@Override
				public void onResponse(Call<QueryResponseModel> call, Response<QueryResponseModel> searchResponse)
				{
					if (searchResponse != null)
					{
						if (searchResponse.body() != null)
						{
							Logger.getLogger().i(TAG, "Call to search successful");
							showResults(searchResponse.body());
						}
						else
						{
							Logger.getLogger().i(TAG, "Call not working");
						}
					}
				}

				@Override
				public void onFailure(Call<QueryResponseModel> call, Throwable t)
				{
					t.printStackTrace();
				}
			});
		}
	}

	private boolean noMoreThanTenWords()
	{
		if (!TextUtils.isEmpty(mSearchField.getText().toString()))
		{
			final String query = mSearchField.getText().toString();

			// TODO regex for the query to have no more than 10 words
		}
		return true;
	}

	private void showResults(QueryResponseModel model)
	{
		DataHolder.setQueryResponseModel(model);
		Intent intent = new Intent(this, TweetListActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
}
