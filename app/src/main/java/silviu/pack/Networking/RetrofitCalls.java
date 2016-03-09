package silviu.pack.Networking;

import com.twitter.sdk.android.core.TwitterAuthToken;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import silviu.pack.Keys;
import silviu.pack.Models.AccessTokenModel;
import silviu.pack.Models.QueryResponseModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.Map;

/**
 * Created by Silviu on 2/26/2016.
 */
public interface RetrofitCalls
{

	// Add more parameters - https://dev.twitter.com/rest/reference/get/search/tweets

	@POST("oauth2/token")
	@FormUrlEncoded
	@Headers("Content-Type:application/x-www-form-urlencoded; charset=UTF-8")
	Call<AccessTokenModel> getAuthentication(@Header("Authorization") String auth, @Field(Keys.KEY_GRANT_TYPE) String
			cred);

	//TODO add search call from twitter
	@GET("1.1/search/tweets.json")
	Call<QueryResponseModel> search(@Header("Authorization") String authorization, @Query("q") String query);

}
