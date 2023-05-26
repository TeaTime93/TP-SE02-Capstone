package SE02.Capstone.activity.result;

import SE02.Capstone.models.FeedModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class GenerateFeedResult {
    private final FeedModel feed;

    private final Logger log = LogManager.getLogger();

    private GenerateFeedResult(FeedModel feed) {
        this.feed = feed;
    }

    public FeedModel getFeed() {
        return feed;
    }

    @Override
    public String toString() {
        return "GenerateFeedResult{" +
                "feed=" + feed +
                '}';
    }

    public static GenerateFeedResult.Builder builder() {
        return new GenerateFeedResult.Builder();
    }

    public static class Builder {
        private FeedModel feed;

        public GenerateFeedResult.Builder withFeed(FeedModel feed) {
            this.feed = feed;
            return this;
        }

        public GenerateFeedResult build() {
            return new GenerateFeedResult(feed);
        }
    }
}


