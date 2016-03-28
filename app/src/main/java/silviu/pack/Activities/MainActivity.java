package silviu.pack.Activities;

import android.content.Context;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import silviu.pack.BuildConfig;
import silviu.pack.DataHolder;
import silviu.pack.FindTweetsApp;
import silviu.pack.Keys;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import silviu.pack.Logger;
import silviu.pack.Models.AccessTokenModel;
import silviu.pack.R;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import static silviu.pack.AppUtils.hasInternetConnection;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class MainActivity extends FragmentActivity
{
	public static final String TAG                        = "MainActivity";
	private             String CONSUMER_KEY_PRODUCTION    = "4eLYCOklkKjFVkl6j9k06GlMb";
	private             String CONSUMER_SECRET_PRODUCTION = "ODXD0wqMbIdVUGgM5eRBykq2p7wMzNBjOtUPI7rom68SUYO5hx";
	private             String consumerKeyAndSecret       = String.format("%s:%s", CONSUMER_KEY_PRODUCTION, CONSUMER_SECRET_PRODUCTION);

	private String CONSUMER_KEY_DEBUG        = "WA9dCfE5EhxZTWutGNW8Oygie";
	private String CONSUMER_SECRET_DEBUG     = "ZDYEeDKOuFpWlzi76aiCIumyqImBNl7xGL2IEdKtxliMNcdFLl";
	private String consumerKeyAndSecretDebug = String.format("%s:%s", CONSUMER_KEY_DEBUG,
			CONSUMER_SECRET_DEBUG);


	private TextInputEditText mSearchField;
	private AppCompatButton   mSearchButton;
	private RadioButton       mRadioButtonPopular;
	private RadioButton       mRadioButtonRecent;
	private Spinner           mSpinner;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mSearchField = (TextInputEditText) findViewById(R.id.search_field);
		mSearchButton = (AppCompatButton) findViewById(R.id.search_button);
		mRadioButtonPopular = (RadioButton) findViewById(R.id.radio_popular);
		mRadioButtonRecent = (RadioButton) findViewById(R.id.radio_recent);
		mSpinner = (Spinner) findViewById(R.id.locales_spinner);
		mSearchButton.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				try
				{
					if (hasInternetConnection(MainActivity.this))
					{
						performSearch();
					}
					else
					{
						Handler handler = new Handler();
						handler.post(new Runnable()
						{
							@Override
							public void run()
							{
								Toast.makeText(getApplicationContext(), getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
							}
						});
					}
				}
				catch (UnsupportedEncodingException e)
				{
					e.printStackTrace();
				}
			}
		});

		mSearchField.setOnEditorActionListener(mSearchFieldListener);
		setAuthenticationToken();

		ArrayAdapter<String> localesArray = new ArrayAdapter<String>(this, android.R.layout
				.simple_list_item_1, getResources().getStringArray(R.array
				.locales_strings));
		mSpinner.setAdapter(localesArray);
		mSpinner.setSelection(0);
	}

	private void setAuthenticationToken()
	{
		String encodedString;
		if (BuildConfig.DEBUG)
		{
			encodedString = Base64.encodeToString(consumerKeyAndSecretDebug.getBytes(), Base64.NO_WRAP);
		}
		else
		{
			encodedString = Base64.encodeToString(consumerKeyAndSecret.getBytes(), Base64.NO_WRAP);
		}
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
	}

	private TextView.OnEditorActionListener mSearchFieldListener = new TextView.OnEditorActionListener()
	{
		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event)
		{
			switch (actionId)
			{
				case EditorInfo.IME_ACTION_DONE:
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
					Logger.getLogger().i(TAG, "hideKeyboard");
					return false;
			}
			return false;
		}
	};

	public void performSearch() throws UnsupportedEncodingException
	{
		if (isValid() && noMoreThanTenWords())
		{
			showResults();
		}
		else
		{
			if (!mSearchField.hasFocus())
			{
				Logger.getLogger().i(TAG, "request focus needed");
				mSearchField.requestFocus();
			}
			Handler handler = new Handler();
			handler.post(new Runnable()
			{
				@Override
				public void run()
				{
					if (TextUtils.isEmpty(mSearchField.getText()))
					{
						Toast.makeText(getApplicationContext(), getResources().getString(R.string.empty_error_string), Toast
								.LENGTH_SHORT).show();
					}
					else
					{
						Toast.makeText(getApplicationContext(), getResources().getString(R.string.test_error_string), Toast.LENGTH_SHORT).show();
					}
				}
			});
		}
	}

	private boolean isValid()
	{
		final String inputText = mSearchField.getText().toString();
		if (!TextUtils.isEmpty(inputText) && !isWhitespace(inputText))
		{
			return true;
		}
		return false;
	}

	public static boolean isWhitespace(String str)
	{
		if (str == null)
		{
			return false;
		}
		int stringSize = str.length();
		int whiteSpacesCount = 0;
		for (int i = 0; i < stringSize; i++)
		{
			if (!Character.isWhitespace(str.charAt(i)) == false)
			{
				whiteSpacesCount++;
			}
		}
		Logger.getLogger().i(TAG, "whiteSpaceSize = " + whiteSpacesCount);
		if (whiteSpacesCount == stringSize)
		{
			return true;
		}
		return false;
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

	private void showResults()
	{
		Intent intent = new Intent(this, TweetListActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra(Keys.KEY_TEXT_INPUT, mSearchField.getText().toString());
		intent.putExtra(Keys.KEY_RESULT_TYPE, getSelectedResultType());
		intent.putExtra(Keys.KEY_LANG_POSITION, mSpinner.getSelectedItemPosition());
		startActivity(intent);
	}

	private String getSelectedResultType()
	{
		if (mRadioButtonPopular.isChecked())
		{
			return mRadioButtonPopular.getText().toString().toLowerCase();
		}
		else if (mRadioButtonRecent.isChecked())
		{
			return mRadioButtonRecent.getText().toString().toLowerCase();
		}
		else
		{
			return "mixed";
		}
	}
}
