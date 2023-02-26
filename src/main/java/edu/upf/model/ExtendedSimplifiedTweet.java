package edu.upf.model;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Optional;

public class ExtendedSimplifiedTweet implements Serializable {
    private final long tweetId; // the id of the tweet (’id’)
    private final String text; // the content of the tweet (’text’)
    private final long userId; // the user id (’user->id’)
    private final String userName; // the user name (’user’->’name’)
    private final long followersCount; // the number of followers (’user’->’followers_count’)
    private final String language; // the language of a tweet (’lang’)
    private final boolean isRetweeted; // is it a retweet? (the object ’retweeted_status’ exists?)
    private final Long retweetedUserId; // [if retweeted] (’retweeted_status’->’user’->’id’)
    private final Long retweetedTweetId; // [if retweeted] (’retweeted_status’->’id’)
    private final long timestampMs; // seconds from epoch (’timestamp_ms’)

    // The JsonParser is used to parse JSON strings into JsonObjects
    private static Gson parser = new Gson();
    
    public ExtendedSimplifiedTweet(long tweetId, String text, long userId, String userName,
    long followersCount, String language, boolean isRetweeted,
    Long retweetedUserId, Long retweetedTweetId, long timestampMs) {
    // IMPLEMENT ME
        this.tweetId = tweetId;
        this.text = text;
        this.userId = userId;
        this.userName = userName;
        this.followersCount = followersCount;
        this.language = language;
        this.isRetweeted = isRetweeted;
        this.retweetedUserId = retweetedUserId;
        this.retweetedTweetId = retweetedTweetId;
        this.timestampMs = timestampMs;
    }
    
    /*
    * Returns a {@link ExtendedSimplifiedTweet} from a JSON String.
    * If parsing fails, for any reason, return an {@link Optional#empty()}
    *
    * @param jsonStr
    * @return an {@link Optional} of a {@link ExtendedSimplifiedTweet}
    */
    
    public static Optional<ExtendedSimplifiedTweet> fromJson(String jsonStr) {
    // IMPLEMENT ME
        JsonObject json = parser.fromJson(jsonStr, JsonObject.class);
        long tweetId = json.get("id").getAsLong();
        String text = json.get("text").getAsString();
        JsonObject user = json.get("user").getAsJsonObject();
        long userId = user.get("id").getAsLong();
        String userName = user.get("name").getAsString();
        long followersCount = user.get("followers_count").getAsLong();
        String language = json.get("lang").getAsString();
        boolean isRetweeted = json.get("retweeted_status") != null;
        Long retweetedUserId = null;
        Long retweetedTweetId = null;
        long timestampMs = json.get("timestamp_ms").getAsLong();
        if (isRetweeted) {
            JsonObject retweetedStatus = json.get("retweeted_status").getAsJsonObject();
            JsonObject retweetedUser = retweetedStatus.get("user").getAsJsonObject();
            retweetedUserId = retweetedUser.get("id").getAsLong();
            retweetedTweetId = retweetedStatus.get("id").getAsLong();
        }
        ExtendedSimplifiedTweet tweet = new ExtendedSimplifiedTweet(tweetId, text, userId, userName,
                    followersCount, language, isRetweeted, retweetedUserId, retweetedTweetId, timestampMs);
    }
  }
