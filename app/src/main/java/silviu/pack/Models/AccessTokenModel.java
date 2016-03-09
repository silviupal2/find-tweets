package silviu.pack.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Silviu on 3/6/2016.
 */
public class AccessTokenModel
{
	@SerializedName("token_type")
	String tokenType;
	@SerializedName("access_token")
	String accessToken;

	public void setTokenType(String tokenType)
	{
		this.tokenType = tokenType;
	}

	public String getTokenType()
	{
		return tokenType;
	}

	public void setAccessToken(String accessToken)
	{
		this.accessToken = accessToken;
	}

	public String getAccessToken()
	{
		return accessToken;
	}
}
