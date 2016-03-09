package silviu.pack.Models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;
import com.twitter.sdk.android.core.models.User;

/**
 * Created by Silviu on 3/4/2016.
 */
public class UserMentionsModel implements Parcelable
{
	@SerializedName("screen_name")
	String screenName; //"screen_name":"Farahvirta",
	String name; //"name":"Farah Fauziah",
	long   id; //"id":2437771242,
	@SerializedName("id_str")
	String idStr; //"id_str":"2437771242",

	public UserMentionsModel(Parcel in)
	{
		screenName = in.readString();
		name = in.readString();
		id = in.readLong();
		idStr = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeString(screenName);
		dest.writeString(name);
		dest.writeLong(id);
		dest.writeString(idStr);
	}

	@Override
	public int describeContents()
	{
		return 0;
	}

	public static final Creator<UserMentionsModel> CREATOR = new Creator<UserMentionsModel>()
	{
		@Override
		public UserMentionsModel createFromParcel(Parcel source)
		{
			return new UserMentionsModel(source);
		}

		@Override
		public UserMentionsModel[] newArray(int size)
		{
			return new UserMentionsModel[size];
		}
	};
}
