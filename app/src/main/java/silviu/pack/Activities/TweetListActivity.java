package silviu.pack.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import retrofit2.Call;
import retrofit2.Response;
import silviu.pack.Adapters.DataAdapter;
import silviu.pack.DataHolder;
import silviu.pack.EndlessRecyclerOnScrollListener;
import silviu.pack.FindTweetsApp;
import silviu.pack.Keys;
import silviu.pack.Logger;
import silviu.pack.Models.QueryResponseModel;
import silviu.pack.Models.StatusModel;
import silviu.pack.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static silviu.pack.AppUtils.hasInternetConnection;

public class TweetListActivity extends FragmentActivity
{
	public static final String TAG = "TweetListActivity";
	private SwipeRefreshLayout  mSwipeRefreshLayout;
	private RecyclerView        mRecyclerView;
	private DataAdapter         mAdapter;
	private LinearLayoutManager mLayoutManager;
	private String              mQuery;
	private String              mResultType;
	private String              mLang;
	private TextView            mSearchInfo;
	EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;
	private boolean ZERO_RESULTS = false;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tweet_list);

		final Intent intent = getIntent();
		mQuery = intent.getStringExtra(Keys.KEY_TEXT_INPUT);
		mResultType = intent.getStringExtra(Keys.KEY_RESULT_TYPE);
		int arrayLangPosition = intent.getIntExtra(Keys.KEY_LANG_POSITION, 0);
		ArrayList<String> array = new ArrayList<String>();
		array.addAll(Arrays.asList(getResources().getStringArray(R.array.locales_strings_prefix)));

		mLang = array.get(arrayLangPosition);

		if (mAdapter == null)
		{
			mAdapter = new DataAdapter(LayoutInflater.from(this), this);
		}

		mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
		mSearchInfo = (TextView) findViewById(R.id.tv_info_search);
		mRecyclerView = (RecyclerView) findViewById(R.id.twitter_recycler_view);
		mLayoutManager = new LinearLayoutManager(FindTweetsApp.getInstance());
		mRecyclerView.setLayoutManager(mLayoutManager);
		mRecyclerView.setAdapter(mAdapter);
		addPageDataToAdapter(0, 0, 10);
		mRecyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);
		mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
		{
			@Override
			public void onRefresh()
			{
				if (hasInternetConnection(TweetListActivity.this))
				{
					resetAdapter();
					addPageDataToAdapter(0, 0, 10);
					mSwipeRefreshLayout.setRefreshing(false);
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
		});
		mSearchInfo.setText(String.format("Search for '%s' Type: '%s' Language: '%s'", mQuery, mResultType, mLang));
	}

	private void resetAdapter()
	{
		mRecyclerView.removeOnScrollListener(endlessRecyclerOnScrollListener);
		if (mAdapter != null)
		{
			mAdapter.clearDataList();
			mAdapter.setDataList(null);
			endlessRecyclerOnScrollListener.reset(0, true);
			mRecyclerView.setAdapter(mAdapter);
			mRecyclerView.addOnScrollListener(endlessRecyclerOnScrollListener);
		}
	}

	@Override
	public void onResume()
	{
		super.onResume();
		endlessRecyclerOnScrollListener.reset(0, true);
	}

	private void addPageDataToAdapter(int currentPage, final long maxId, int count)
	{
		final String authBearer = String.format("Bearer %s", DataHolder.getBearerToken());

		Logger.getLogger().i(TAG, "Query Params: authBearer = " + authBearer + "\n query = " + mQuery + "\n count = "
				+ count + "\n result_type = " + mResultType + "\n currentPage = " + currentPage);
		final Call<QueryResponseModel> queryCall = FindTweetsApp.getInstance()
																.getRetrofitService()
																.search(authBearer, mQuery, count, mResultType, maxId, mLang);
		Logger.getLogger().i(TAG, "Query Call = " + queryCall.request().url());
		queryCall.enqueue(new retrofit2.Callback<QueryResponseModel>()
		{
			@Override
			public void onResponse(Call<QueryResponseModel> call, Response<QueryResponseModel> searchResponse)
			{
				if (searchResponse != null)
				{
					if (searchResponse.body() != null)
					{
						if (searchResponse.body().getStatuses() != null)
						{
							Logger.getLogger().i(TAG, "Call to search successful");
							mAdapter.setData(getOnlyGoodData(searchResponse.body().getStatuses(), maxId));
						}
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
		endlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(mLayoutManager)
		{
			@Override
			public void onLoadMore(int current_page)
			{
				addPageDataToAdapter(current_page, mAdapter.getLowerIdFromList(), 10);
			}
		};
	}

	private List<StatusModel> getOnlyGoodData(List<StatusModel> statusModelsList, long maxId)
	{
		List<StatusModel> auxList = new ArrayList<>();
		for (StatusModel model : statusModelsList)
		{
			if (model.getId() != maxId)
			{
				auxList.add(model);
			}
		}
		/*if (auxList.size() == 0 && ZERO_RESULTS == false)
		{
			zeroResultsVisibility(true);
			ZERO_RESULTS = true;
		}
		else if (ZERO_RESULTS == true)
		{
			zeroResultsVisibility(false);
			ZERO_RESULTS = false;
		}*/
		return auxList;

	}


	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		mAdapter.clearDataList();
		mAdapter = null;
		mRecyclerView = null;
		ZERO_RESULTS = false;
	}

}
