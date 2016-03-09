package silviu.pack.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Silviu on 3/4/2016.
 */
public class HashtagModel implements Parcelable
{
	private String text;

	public HashtagModel(Parcel in)
	{
		text = in.readString();

	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeString(text);
	}

	@Override
	public int describeContents()
	{
		return 0;
	}

	public static final Creator<HashtagModel> CREATOR = new Creator<HashtagModel>()
	{
		@Override
		public HashtagModel createFromParcel(Parcel source)
		{
			return new HashtagModel(source);
		}

		@Override
		public HashtagModel[] newArray(int size)
		{
			return new HashtagModel[size];
		}
	};
}
