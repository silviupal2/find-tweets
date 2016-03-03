package silviu.pack.Models;

import android.os.Parcel;
import android.os.Parcelable;
import com.twitter.sdk.android.core.TwitterAuthToken;

/**
 * Created by Silviu on 2/29/2016.
 */
public class TwitterResultModel implements Parcelable
{
	private TwitterAuthToken token;

	public TwitterResultModel()
	{
	}

	public TwitterResultModel(Parcel in)
	{
		token = in.readParcelable(TwitterResultModel.class.getClassLoader());
	}

	public void setToken(TwitterAuthToken token)
	{
		this.token = token;
	}

	public TwitterAuthToken getToken()
	{
		return token;
	}

	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeParcelable(token, flags);
	}

	public static final Parcelable.Creator<TwitterResultModel> CREATOR = new Parcelable.Creator<TwitterResultModel>()
	{
		@Override
		public TwitterResultModel createFromParcel(Parcel source)
		{
			return new TwitterResultModel(source);
		}

		@Override
		public TwitterResultModel[] newArray(int size)
		{
			return new TwitterResultModel[size];
		}
	};
}
