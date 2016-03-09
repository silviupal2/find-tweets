package silviu.pack.Models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Silviu on 3/4/2016.
 */
public class UserModel implements Parcelable
{
	long id;//"id":2326072440,
	@SerializedName("id_str")
	String idStr; // "id_str":"2326072440",
	String name;//		"name":"silviu dancu",
	@SerializedName("screen_name")
	String screenName; // "screen_name":"dancusilviu",
	String location; // "location":"București",
	String description;  // "description":""
	String url; // "url":"http://t.co/yDdwsYcTDO",

	@SerializedName("profile_image_url")
	String profileImageUrl;
	@SerializedName("profile_image_url_https")
	String profileImageUrlHttps;

	public UserModel()
	{
	}

	public UserModel(Parcel in)
	{
		id = in.readLong();
		idStr = in.readString();
		name = in.readString();
		screenName = in.readString();
		location = in.readString();
		description = in.readString();
		url = in.readString();
		profileImageUrl = in.readString();
		profileImageUrlHttps = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeLong(id);
		dest.writeString(idStr);
		dest.writeString(name);
		dest.writeString(screenName);
		dest.writeString(location);
		dest.writeString(url);
		dest.writeString(profileImageUrl);
		dest.writeString(profileImageUrlHttps);
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getIdStr()
	{
		return idStr;
	}

	public void setIdStr(String idStr)
	{
		this.idStr = idStr;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getScreenName()
	{
		return screenName;
	}

	public void setScreenName(String screenName)
	{
		this.screenName = screenName;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getProfileImageUrl()
	{
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl)
	{
		this.profileImageUrl = profileImageUrl;
	}

	public String getProfileImageUrlHttps()
	{
		return profileImageUrlHttps;
	}

	public void setProfileImageUrlHttps(String profileImageUrlHttps)
	{
		this.profileImageUrlHttps = profileImageUrlHttps;
	}

	@Override
	public int describeContents()
	{
		return 0;
	}

	public static final Creator<UserModel> CREATOR = new Creator<UserModel>()
	{
		@Override
		public UserModel createFromParcel(Parcel source)
		{
			return new UserModel(source);
		}

		@Override
		public UserModel[] newArray(int size)
		{
			return new UserModel[size];
		}
	};
}
