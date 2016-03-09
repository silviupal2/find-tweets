package silviu.pack.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import retrofit.http.Query;
import silviu.pack.Adapters.DataAdapter;
import silviu.pack.DataHolder;
import silviu.pack.FindTweetsApp;
import silviu.pack.Keys;
import silviu.pack.Logger;
import silviu.pack.Models.QueryResponseModel;
import silviu.pack.Models.StatusModel;
import silviu.pack.R;
import silviu.pack.ViewHolder;

import java.util.Collection;

public class TweetListActivity extends FragmentActivity
{
	public static final String TAG = "TweetListActivity";
	private RecyclerView        mRecyclerView;
	private DataAdapter         mAdapter;
	private QueryResponseModel  mModel;
	private LinearLayoutManager mLayoutManager;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tweet_list);
		mModel = DataHolder.getQueryResponseModel();
		if (mAdapter == null)
		{
			mAdapter = new DataAdapter(LayoutInflater.from(this));
		}
		mAdapter.setData(mModel.getStatuses());
		mRecyclerView = (RecyclerView) findViewById(R.id.twitter_recycler_view);
		mLayoutManager = new LinearLayoutManager(FindTweetsApp.getInstance());
		mRecyclerView.setLayoutManager(mLayoutManager);
		mRecyclerView.setAdapter(mAdapter);

		//TODO Use RecyclerView.Adapter instead of ArrayAdapter

	}

}
