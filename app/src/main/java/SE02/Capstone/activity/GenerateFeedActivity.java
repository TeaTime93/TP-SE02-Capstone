package SE02.Capstone.activity;

import SE02.Capstone.activity.request.GenerateFeedRequest;
import SE02.Capstone.activity.result.GenerateFeedResult;
import SE02.Capstone.converters.ModelConverter;
import SE02.Capstone.dynamodb.FeedGenerator;
import SE02.Capstone.dynamodb.StoryDao;
import SE02.Capstone.dynamodb.UserDao;
import SE02.Capstone.dynamodb.models.Feed;
import SE02.Capstone.models.FeedModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class GenerateFeedActivity {
    private final Logger log = LogManager.getLogger();
    private final StoryDao storyDao;
    private final UserDao userDao;

    @Inject
    public GenerateFeedActivity(StoryDao storyDao, UserDao userDao) {
        this.storyDao = storyDao;
        this.userDao = userDao;
    }

    public GenerateFeedResult handleRequest(GenerateFeedRequest generateFeedRequest) {
        log.info("Received GenerateFeedRequest {}", generateFeedRequest);
        FeedGenerator feedGenerator = new FeedGenerator(storyDao, userDao);
        String userId = generateFeedRequest.getUserId();
        List<String> list = new ArrayList<>();

        Feed feed = new Feed(list);
        feedGenerator.generate(userId, feed);
        log.info(feed);

        // Create a result object and return
        FeedModel feedModel = new ModelConverter().toFeedModel(feed);
        return GenerateFeedResult.builder()
                .withFeed(feedModel)
                .build();
    }
}

