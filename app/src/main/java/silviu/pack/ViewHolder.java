package silviu.pack;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Silviu on 3/4/2016.
 */
public class ViewHolder extends RecyclerView.ViewHolder
{
	public ImageView profilePicture;
	public TextView  userName;
	public TextView  userNickname;
	public TextView  tweetDate;
	public TextView  tweetText;

	public ViewHolder(View itemView)
	{
		super(itemView);
	}
}
