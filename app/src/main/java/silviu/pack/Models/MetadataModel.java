package silviu.pack.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Silviu on 3/4/2016.
 */
public class MetadataModel implements Parcelable
{
	private String iso_language_code;
	private String result_type;

	public MetadataModel(Parcel in){
		iso_language_code = in.readString();
		result_type = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeString(iso_language_code);
		dest.writeString(result_type);
	}

	@Override
	public int describeContents()
	{
		return 0;
	}

	public static final Creator<MetadataModel> CREATOR = new Creator<MetadataModel>()
	{
		@Override
		public MetadataModel createFromParcel(Parcel source)
		{
			return new MetadataModel(source);
		}

		@Override
		public MetadataModel[] newArray(int size)
		{
			return new MetadataModel[size];
		}
	};
}
