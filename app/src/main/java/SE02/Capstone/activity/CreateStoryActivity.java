package SE02.Capstone.activity;

import SE02.Capstone.activity.request.CreateStoryRequest;
import SE02.Capstone.activity.result.CreateStoryResult;
import SE02.Capstone.converters.ModelConverter;
import SE02.Capstone.dynamodb.StoryDao;
import SE02.Capstone.dynamodb.models.Story;
import SE02.Capstone.models.StoryModel;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class CreateStoryActivity {
    private final Logger log = LogManager.getLogger();
    private final StoryDao storyDao;


    @Inject
    public CreateStoryActivity(StoryDao storyDao) {
        this.storyDao = storyDao;
    }

    public CreateStoryResult handleRequest(final CreateStoryRequest createStoryRequest) {
        log.info("Received OrderRequest {}", createStoryRequest);

        Story newStory = new Story();
        newStory.setStoryId(generateUserId());
        newStory.setUserId(createStoryRequest.getUserID());
        newStory.setContent(createStoryRequest.getContent());
        newStory.setSnippet(createStoryRequest.getSnippet());
        newStory.setTitle(createStoryRequest.getTitle());
        newStory.setTags(createStoryRequest.getTags());

        storyDao.saveStory(newStory);

        StoryModel storyModel = new ModelConverter().toStoryModel(newStory);
        return CreateStoryResult.builder()
                .withStory(storyModel)
                .build();
    }

    public static String generateUserId() {
        return RandomStringUtils.randomAlphanumeric(5);
    }
}
