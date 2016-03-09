package silviu.pack.Models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Silviu on 3/4/2016.
 */
public class EntityModel implements Parcelable
{
	List<HashtagModel> hashtags;
	@SerializedName("user_mentions")
	List<UserMentionsModel> userMentions;

	public EntityModel(Parcel in){
		if (hashtags == null)
		{
			hashtags = in.createTypedArrayList(HashtagModel.CREATOR);
		}
		else
		{
			in.readTypedList(hashtags, HashtagModel.CREATOR);
		}
		if (userMentions == null)
		{
			userMentions = in.createTypedArrayList(UserMentionsModel.CREATOR);
		}
		else
		{
			in.readTypedList(userMentions, UserMentionsModel.CREATOR);
		}
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeTypedList(hashtags);
		dest.writeTypedList(userMentions);
	}

	@Override
	public int describeContents()
	{
		return 0;
	}

	public static final Creator<EntityModel> CREATOR = new Creator<EntityModel>()
	{
		@Override
		public EntityModel createFromParcel(Parcel source)
		{
			return new EntityModel(source);
		}

		@Override
		public EntityModel[] newArray(int size)
		{
			return new EntityModel[0];
		}
	};
}
