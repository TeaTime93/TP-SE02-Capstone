package SE02.Capstone.activity;

import SE02.Capstone.activity.request.GetStoryRequest;
import SE02.Capstone.activity.result.GetStoryResult;
import SE02.Capstone.converters.ModelConverter;
import SE02.Capstone.dynamodb.StoryDao;
import SE02.Capstone.dynamodb.models.Story;
import SE02.Capstone.exceptions.UserNotFoundException;
import SE02.Capstone.models.StoryModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class GetStoryActivity {
    private final Logger log = LogManager.getLogger();
    private final StoryDao storyDao;


    @Inject
    public GetStoryActivity(StoryDao storyDao) {
        this.storyDao = storyDao;
    }

    public GetStoryResult handleRequest(final GetStoryRequest getStoryRequest) {
        log.info("Received StoryRequest {}", getStoryRequest);
        String requestId = getStoryRequest.getStoryId();
        Story story = storyDao.getStory(requestId);
        if (story == null){
            throw new UserNotFoundException("Coming from the activity");
        }
        StoryModel storyModel = new ModelConverter().toStoryModel(story);
        return GetStoryResult.builder()
                .withStory(storyModel)
                .build();
    }
}
