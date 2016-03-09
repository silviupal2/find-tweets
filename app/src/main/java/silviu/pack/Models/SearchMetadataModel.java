package silviu.pack.Models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.twitter.sdk.android.core.models.SearchMetadata;

/**
 * Created by Silviu on 3/4/2016.
 */
public class SearchMetadataModel implements Parcelable
{
	@SerializedName("max_id")
	public final long   maxId;
	@SerializedName("since_id")
	public final long   sinceId;
	@SerializedName("refresh_url")
	public final String refreshUrl;
	@SerializedName("next_results")
	public final String nextResults;
	@SerializedName("count")
	public final long   count;
	@SerializedName("completed_in")
	public final double completedIn;
	@SerializedName("since_id_str")
	public final String sinceIdStr;
	@SerializedName("query")
	public final String query;
	@SerializedName("max_id_str")
	public final String maxIdStr;

	public SearchMetadataModel(Parcel in)
	{
		maxId = in.readLong();
		sinceId = in.readLong();
		refreshUrl = in.readString();
		nextResults = in.readString();
		count = in.readLong();
		completedIn = in.readDouble();
		sinceIdStr = in.readString();
		query = in.readString();
		maxIdStr = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeLong(maxId);
		dest.writeLong(sinceId);
		dest.writeString(refreshUrl);
		dest.writeString(nextResults);
		dest.writeLong(count);
		dest.writeDouble(completedIn);
		dest.writeString(sinceIdStr);
		dest.writeString(query);
		dest.writeString(maxIdStr);
	}

	@Override
	public int describeContents()
	{
		return 0;
	}

	public long getMaxId()
	{
		return maxId;
	}

	public long getSinceId()
	{
		return sinceId;
	}

	public String getRefreshUrl()
	{
		return refreshUrl;
	}

	public String getNextResults()
	{
		return nextResults;
	}

	public long getCount()
	{
		return count;
	}

	public double getCompletedIn()
	{
		return completedIn;
	}

	public String getSinceIdStr()
	{
		return sinceIdStr;
	}

	public String getQuery()
	{
		return query;
	}

	public String getMaxIdStr()
	{
		return maxIdStr;
	}

	public static Creator<SearchMetadataModel> getCREATOR()
	{
		return CREATOR;
	}

	public static final Creator<SearchMetadataModel> CREATOR = new Creator<SearchMetadataModel>()
	{
		@Override
		public SearchMetadataModel createFromParcel(Parcel source)
		{
			return new SearchMetadataModel(source);
		}

		@Override
		public SearchMetadataModel[] newArray(int size)
		{
			return new SearchMetadataModel[size];
		}
	};

}

