package findtweets.Activities;

import android.content.Intent;
import android.text.TextUtils;
import findtweets.FindTweetsApp;
import findtweets.Keys;
import findtweets.Models.QueryResponseModel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import retrofit.Call;
import retrofit.Response;
import silviu.findtweets.R;

import java.io.IOException;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity extends AppCompatActivity
{
	private EditText mSearchField;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (getSupportActionBar() != null)
		{
			getSupportActionBar().hide();
		}
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
			Call<QueryResponseModel> queryCall = FindTweetsApp.getInstance().getRetrofitService().search(textInput);
			try
			{
				Response<QueryResponseModel> searchResponse = queryCall.execute();
				if (searchResponse != null)
				{
					if (searchResponse.body() != null)
					{
						showResults(searchResponse.body());
					}
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}

		}

	}

	private void showResults(QueryResponseModel model)
	{
		Intent intent = new Intent(this, TweetListActivity.class);
		intent.putExtra(Keys.QUERY_RESPONSE_MODEL, model);
		startActivity(intent);
	}
}
