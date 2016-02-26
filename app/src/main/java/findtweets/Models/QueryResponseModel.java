package findtweets.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Silviu on 2/26/2016.
 */
public class QueryResponseModel implements Parcelable
{

	//TODO get params from search

	public QueryResponseModel(Parcel in)
	{

	}

	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{

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
