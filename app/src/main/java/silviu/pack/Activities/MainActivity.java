package silviu.pack.Activities;

import android.support.v4.app.FragmentActivity;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterSession;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
import silviu.pack.Models.QueryResponseModel;
import silviu.pack.Models.TwitterResultModel;
import silviu.pack.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity extends FragmentActivity
{
	public static final String TAG = "MainActivity";

	private EditText mSearchField;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mSearchField = (EditText) findViewById(R.id.search_field);
		mSearchField.setOnEditorActionListener(mSearchFieldListener);

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
					performSearch();
					return false;
			}

			return false;
		}
	};

	private void performSearch()
	{
		String textInput = mSearchField.getText().toString();
		if (!TextUtils.isEmpty(textInput))
		{
			TwitterSession session = Twitter.getSessionManager().getActiveSession();
			TwitterAuthToken authToken = session.getAuthToken();
			String token = authToken.token;
			String secret = authToken.secret;

			Call<QueryResponseModel> queryCall = FindTweetsApp.getInstance().getRetrofitService().search(textInput);
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

	private void showResults(QueryResponseModel model)
	{
		Intent intent = new Intent(this, TweetListActivity.class);
		intent.putExtra(Keys.QUERY_RESPONSE_MODEL, model);
		startActivity(intent);
	}
}
