package silviu.pack.Models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Silviu on 3/4/2016.
 */
public class StatusModel implements Parcelable
{
	private MetadataModel metadata;
	@SerializedName("created_at")
	private String        createdAt; //		"created_at": "Fri Mar 04 09:47:39 +0000 2016",
	private long          id; //		"id": 705691165018951680,
	@SerializedName("id_str")
	private String        idStr; //		"id_str": "705691165018951680",
	private String        text; //	"text": "puternică faza! https://t.co/WncgSqEjUS",
	private String        source; // "source": "<a href=\"http://www.facebook.com/twitter\" rel=\"nofollow\">Facebook</a>",
	private boolean       truncated; // "truncated": false,
	private UserModel     user;
	@SerializedName("retweet_count")
	private int           retweetCount; //"retweet_count": 1,
	@SerializedName("favourite_count")
	private int           favoriteCount; //			"favorite_count": 0,
	private EntityModel   entities;

	public StatusModel()
	{
	}

	public StatusModel(Parcel in)
	{
		metadata = in.readParcelable(MetadataModel.class.getClassLoader());
		createdAt = in.readString();
		id = in.readLong();
		idStr = in.readString();
		text = in.readString();
		source = in.readString();
		truncated = (in.readByte() == 1);
		user = in.readParcelable(UserModel.class.getClassLoader());
		retweetCount = in.readInt();
		favoriteCount = in.readInt();
		entities = in.readParcelable(EntityModel.class.getClassLoader());

	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeParcelable(metadata,flags);
		dest.writeString(createdAt);
		dest.writeLong(id);
		dest.writeString(idStr);
		dest.writeString(text);
		dest.writeString(source);
		dest.writeByte(truncated ? (byte) 1 : (byte) 0);
		dest.writeParcelable(user, flags);
		dest.writeInt(retweetCount);

	}

	public int getFavoriteCount()
	{
		return favoriteCount;
	}

	public void setFavoriteCount(int favoriteCount)
	{
		this.favoriteCount = favoriteCount;
	}

	public int getRetweetCount()
	{
		return retweetCount;
	}

	public void setRetweetCount(int retweetCount)
	{
		this.retweetCount = retweetCount;
	}

	public UserModel getUser()
	{
		return user;
	}

	public void setUser(UserModel user)
	{
		this.user = user;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public String getCreatedAt()
	{
		return createdAt;
	}

	public void setCreatedAt(String createdAt)
	{
		this.createdAt = createdAt;
	}

	@Override
	public int describeContents()
	{
		return 0;
	}

	public static final Creator<StatusModel> CREATOR = new Creator<StatusModel>()
	{
		@Override
		public StatusModel createFromParcel(Parcel source)
		{
			return new StatusModel(source);
		}

		@Override
		public StatusModel[] newArray(int size)
		{
			return new StatusModel[size];
		}
	};
}
