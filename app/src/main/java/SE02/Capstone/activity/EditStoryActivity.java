package SE02.Capstone.activity;

import SE02.Capstone.activity.request.EditStoryRequest;
import SE02.Capstone.activity.result.EditStoryResult;
import SE02.Capstone.converters.ModelConverter;
import SE02.Capstone.dynamodb.StoryDao;
import SE02.Capstone.dynamodb.models.Story;
import SE02.Capstone.exceptions.UserNotFoundException;
import SE02.Capstone.metrics.MetricsPublisher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * Implementation of the UpdatePlaylistActivity for the MusicPlaylistService's UpdatePlaylist API.
 *
 * This API allows the customer to update their saved playlist's information.
 */
public class EditStoryActivity {

    private final Logger log = LogManager.getLogger();

    private final StoryDao storyDao;

    private final MetricsPublisher metricsPublisher;


    /**
     * Instantiates a new UpdatePlaylistActivity object.
     *
     * @param storyDao storyDao to access the inventory table.
     * @param metricsPublisher MetricsPublisher to publish metrics.
     */
    @Inject
    public EditStoryActivity(StoryDao storyDao, MetricsPublisher metricsPublisher) {
        this.storyDao = storyDao;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * This method handles the incoming request by retrieving the story object, updating it,
     * and persisting the inventory object.
     * <p>
     * It then returns the updated inventory item.
     * <p>
     * If the story does not exist, this should throw a storyNotFoundException.
     * <p>
     * If the provided story or customer ID has invalid characters, throws an
     * InvalidAttributeValueException
     * <p>
     * If the request tries to update the customer ID,
     * this should throw an InvalidAttributeChangeException
     *
     * @param EditStoryRequest request object containing the story ID, packageing type,
     *                               available units and reserved units associated with it
     * @return updatePlaylistResult result object containing the API defined
     */
    public EditStoryResult handleRequest(final EditStoryRequest EditStoryRequest) {
        log.info("Received EditStoryRequest {}", EditStoryRequest);

        // Check to ensure requested changes will not result in a negative inventory amount, add count to CloudWatch
        if (EditStoryRequest.getStoryId() == null) {
            throw new UserNotFoundException("requested availableUnit or reservedUnits invalid");
        }
        Story story = storyDao.getStory(EditStoryRequest.getStoryId());
        // If story not found in the table throws storyNotFound and adds count to CloudWatch
        if (story == null) {
            throw new UserNotFoundException("story " + story + " not found to update");
        }

        story.setStoryId(EditStoryRequest.getStoryId());
        story.setUserId(EditStoryRequest.getUserId());
        story.setTitle(EditStoryRequest.getTitle());
        story.setContent(EditStoryRequest.getContent());
        story.setSnippet(EditStoryRequest.getSnippet());
        story.setTags(EditStoryRequest.getTags());
        story.setLikes(EditStoryRequest.getLikes());

        story = storyDao.saveStory(story);
        log.info("updated retrieved object and saved story to table story = {}", story);
        return EditStoryResult.builder()
                .withStoryModel(new ModelConverter().toStoryModel(story))
                .build();
    }
}