package SE02.Capstone.activity;

import SE02.Capstone.activity.request.DeleteStoryRequest;
import SE02.Capstone.activity.request.GetStoryRequest;
import SE02.Capstone.activity.result.DeleteStoryResult;
import SE02.Capstone.activity.result.GetStoryResult;
import SE02.Capstone.converters.ModelConverter;
import SE02.Capstone.dynamodb.StoryDao;
import SE02.Capstone.dynamodb.models.Story;
import SE02.Capstone.exceptions.UserNotFoundException;
import SE02.Capstone.models.StoryModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class DeleteStoryActivity {
    private final Logger log = LogManager.getLogger();
    private final StoryDao storyDao;


    @Inject
    public DeleteStoryActivity(StoryDao storyDao) {
        this.storyDao = storyDao;
    }

    public DeleteStoryResult handleRequest(final DeleteStoryRequest deleteStoryRequest) {
        log.info("Received StoryRequest {}", deleteStoryRequest);
        String requestId = deleteStoryRequest.getStoryId();
        Story story = storyDao.deleteStory(requestId);
        if (story == null){
            throw new UserNotFoundException("Coming from the activity");
        }
        StoryModel storyModel = new ModelConverter().toStoryModel(story);
        return DeleteStoryResult.builder()
                .withStory(storyModel)
                .build();
    }
}