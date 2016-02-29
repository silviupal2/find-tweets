package findtweets.Networking;

import findtweets.Models.QueryResponseModel;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Silviu on 2/26/2016.
 */
public interface RetrofitCalls
{

	// Add more parameters - https://dev.twitter.com/rest/reference/get/search/tweets

	//TODO add search call from twitter
	@GET("search/tweets.json")
	Call<QueryResponseModel> search(@Query("q") String query);

}
