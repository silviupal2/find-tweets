package silviu.pack.Adapters;

import android.content.Context;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import org.w3c.dom.Text;
import silviu.pack.Models.StatusModel;
import silviu.pack.R;
import silviu.pack.ViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//	adapters
public class DataAdapter extends RecyclerView.Adapter<ViewHolder>
{
	private final int DEFAULT_ITEM = 0;

	private final LayoutInflater    mInflater;
	private final Context           mContext;
	private       List<StatusModel> mData;

	public DataAdapter(LayoutInflater inflater)
	{

		mContext = inflater.getContext();
		mInflater = inflater;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		ViewHolder viewHolder = null;
		View view;
		switch (viewType)
		{
			case DEFAULT_ITEM:
				view = mInflater.inflate(R.layout.list_view_item, parent, false);
				viewHolder = new ViewHolder(view);
				viewHolder.profilePicture = (ImageView) view.findViewById(R.id.user_profile_picture);
				viewHolder.userNickname = (TextView) view.findViewById(R.id.user_nickname);
				viewHolder.userName = (TextView) view.findViewById(R.id.user_name);
				viewHolder.tweetDate = (TextView) view.findViewById(R.id.date);
				viewHolder.tweetText = (TextView) view.findViewById(R.id.tweet_content);
				return viewHolder;
		}
		return null;
	}

	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int position)
	{
		if (viewHolder != null)
		{
			// get item position
			final int viewType = getItemViewType(position);
			switch (viewType)
			{
				case DEFAULT_ITEM:
					final StatusModel statusModel = mData.get(position);
					if (statusModel != null)
					{
						if (!TextUtils.isEmpty(statusModel.getUser().getName()))
						{
							viewHolder.userName.setText(statusModel.getUser().getName());
						}
						if (!TextUtils.isEmpty(statusModel.getUser().getScreenName()))
						{
							viewHolder.userNickname.setText("@" + statusModel.getUser().getScreenName());
						}
						if (!TextUtils.isEmpty(statusModel.getCreatedAt()))
						{
							final String date = dateProcessing(statusModel.getCreatedAt());

							viewHolder.tweetDate.setText(date);
						}
						if (!TextUtils.isEmpty(statusModel.getText()))
						{
							viewHolder.tweetText.setText(statusModel.getText());
						}

						Picasso.with(mContext).load(statusModel.getUser().getProfileImageUrlHttps()).into(viewHolder.profilePicture);
					}
					break;
			}
		}

	}

	@Override
	public int getItemCount()
	{
		return mData.size();
	}

	@Override
	public int getItemViewType(int position)
	{
		if (position >= 0 && position < mData.size())
		{
			return DEFAULT_ITEM;
		}
		else
		{
			return -1;
		}
	}

	public void clearDataList()
	{
		if (mData != null)
		{
			mData.clear();
		}
		notifyDataSetChanged();
	}

	public void setData(List<StatusModel> data)
	{
		this.mData = data;
	}


	private String dateProcessing(String dateString)
	{
		String finalString = " ";
		SimpleDateFormat dateFormat = new SimpleDateFormat("ccc MMM dd hh:mm:ss Z yyyy");
		Date createdAtDate;
		try
		{
			createdAtDate = dateFormat.parse(dateString);
			long currentTimeInMillis = System.currentTimeMillis();
			long createdAtTimeInMillis = createdAtDate.getTime();
			long timePassedInMillis = currentTimeInMillis - createdAtTimeInMillis;
			long days = (int) ((timePassedInMillis / 1000) / 86400);
			long hours = (int) (((timePassedInMillis / 1000) - (days * 86400)) / 3600);
			long minutes = (int) (((timePassedInMillis / 1000) - ((days * 86400) + (hours * 3600))) / 60);

			if (days == 0 && hours == 0)
			{
				finalString = String.format("%dm", minutes);
			}
			else if (days == 0)
			{
				finalString = String.format("%dh %dm", hours, minutes);
			}
			else
			{
				finalString = String.format("%dd", days);
			}

		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		return finalString;
	}

}