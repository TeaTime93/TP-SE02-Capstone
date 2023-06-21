package SE02.Capstone.activity;

import SE02.Capstone.activity.request.GetStoryByTitleAndAuthorRequest;
import SE02.Capstone.activity.result.GetStoryByTitleAndAuthorResult;
import SE02.Capstone.activity.result.GetStoryResult;
import SE02.Capstone.converters.ModelConverter;
import SE02.Capstone.dynamodb.StoryDao;
import SE02.Capstone.dynamodb.models.Story;
import SE02.Capstone.exceptions.UserNotFoundException;
import SE02.Capstone.models.StoryModel;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class GetStoryByTitleAndAuthorActivity {

    private final Logger log = LogManager.getLogger();
    private final StoryDao storyDao;

    @Inject
    public GetStoryByTitleAndAuthorActivity(StoryDao storyDao) {
        this.storyDao = storyDao;
    }

    public GetStoryByTitleAndAuthorResult handleRequest(final GetStoryByTitleAndAuthorRequest getStoryByTitleAndAuthorRequest) {
        log.info("Received StoryRequest {}", getStoryByTitleAndAuthorRequest);
        String requestTitle = getStoryByTitleAndAuthorRequest.getTitle();
        String requestAuthor = getStoryByTitleAndAuthorRequest.getUserId();

        Story story = storyDao.getStoryByTitleAndAuthor(requestTitle, requestAuthor);

        if (story == null){
            throw new UserNotFoundException("Coming from the activity");
        }
        StoryModel storyModel = new ModelConverter().toStoryModel(story);
        return GetStoryByTitleAndAuthorResult.builder()
                .withStory(storyModel)
                .build();
    }
    }

