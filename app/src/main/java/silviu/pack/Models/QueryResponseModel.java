package silviu.pack.Models;

import android.os.Parcel;
import android.os.Parcelable;
import com.twitter.sdk.android.core.models.SearchMetadata;

import java.util.List;

/**
 * Created by Silviu on 2/26/2016.
 */
public class QueryResponseModel implements Parcelable
{
	List<StatusModel> statuses;
	SearchMetadataModel search_metadata;

	public QueryResponseModel(Parcel in)
	{
		if (statuses == null)
		{
			statuses = in.createTypedArrayList(StatusModel.CREATOR);
		}
		else
		{
			in.readTypedList(statuses, StatusModel.CREATOR);
		}
		search_metadata = in.readParcelable(SearchMetadataModel.class.getClassLoader());
	}

	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeTypedList(statuses);
		dest.writeParcelable(search_metadata,flags);
	}

	public List<StatusModel> getStatuses()
	{
		return statuses;
	}

	public void setStatuses(List<StatusModel> statuses)
	{
		this.statuses = statuses;
	}

	public static final Creator<QueryResponseModel> CREATOR = new Creator<QueryResponseModel>()
	{
		@Override
		public QueryResponseModel createFromParcel(Parcel source)
		{
			return new QueryResponseModel(source);
		}

		@Override
		public QueryResponseModel[] newArray(int size)
		{
			return new QueryResponseModel[size];
		}
	};
}
