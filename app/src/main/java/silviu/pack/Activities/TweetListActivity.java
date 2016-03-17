package silviu.pack.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import retrofit2.Call;
import retrofit2.Response;
import silviu.pack.Adapters.DataAdapter;
import silviu.pack.DataHolder;
import silviu.pack.EndlessRecyclerOnScrollListener;
import silviu.pack.FindTweetsApp;
import silviu.pack.Keys;
import silviu.pack.Logger;
import silviu.pack.Models.QueryResponseModel;
import silviu.pack.R;

public class TweetListActivity extends FragmentActivity
{
	public static final String TAG = "TweetListActivity";
	private SwipeRefreshLayout  mSwipeRefreshLayout;
	private RecyclerView        mRecyclerView;
	private DataAdapter         mAdapter;
	private LinearLayoutManager mLayoutManager;
	private String              mQuery;
	private String              mResultType;
	EndlessRecyclerOnScrollListener endlessRecyclerOnScrollListener;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tweet_list);

		final Intent intent = getIntent();
		if (intent.hasExtra(Keys.KEY_TEXT_INPUT))
		{
			mQuery = intent.getStringExtra(Keys.KEY_TEXT_INPUT);
			mResultType = intent.getStringExtra(Keys.KEY_RESULT_TYPE);
		}


		if (mAdapter == null)
		{
			mAdapter = new DataAdapter(LayoutInflater.from(this));
		}

		mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
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
				resetAdapter();
				addPageDataToAdapter(0, 0, 10);
			}
		});
	}

	private void resetAdapter()
	{
		mAdapter.clearDataList();
		mAdapter.notifyDataSetChanged();
	}

	@Override
	public void onResume()
	{
		super.onResume();
		endlessRecyclerOnScrollListener.reset(0, true);
	}

	private void addPageDataToAdapter(int currentPage, long maxId, int count)
	{
		final String authBearer = String.format("Bearer %s", DataHolder.getBearerToken());


		Logger.getLogger().i(TAG, "Query Params: authBearer = " + authBearer + "\n query = " + mQuery + "\n count = "
				+ count + "\n result_type = " + mResultType + "\n currentPage = " + currentPage);
		final Call<QueryResponseModel> queryCall = FindTweetsApp.getInstance()
																.getRetrofitService()
																.search(authBearer, mQuery, count, mResultType, maxId);
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
						Logger.getLogger().i(TAG, "Call to search successful");
						mAdapter.setData(searchResponse.body().getStatuses());
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


	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		mAdapter.clearDataList();
		mAdapter = null;
		mRecyclerView = null;
	}


}
