package SE02.Capstone.activity;

import SE02.Capstone.activity.request.EditCommentsRequest;
import SE02.Capstone.activity.result.EditCommentsResult;
import SE02.Capstone.converters.ModelConverter;
import SE02.Capstone.dynamodb.CommentsDao;
import SE02.Capstone.dynamodb.models.Comments;
import SE02.Capstone.exceptions.UserNotFoundException;
import SE02.Capstone.metrics.MetricsPublisher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class EditCommentsActivity {

    private final Logger log = LogManager.getLogger();

    private final CommentsDao commentsDao;

    private final MetricsPublisher metricsPublisher;


    /**
     * Instantiates a new UpdatePlaylistActivity object.
     *
     * @param commentsDao storyDao to access the inventory table.
     * @param metricsPublisher MetricsPublisher to publish metrics.
     */
    @Inject
    public EditCommentsActivity(CommentsDao commentsDao, MetricsPublisher metricsPublisher) {
        this.commentsDao = commentsDao;
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
     * @param EditCommentsRequest request object containing the story ID, packageing type,
     *                               available units and reserved units associated with it
     * @return updatePlaylistResult result object containing the API defined
     */
    public EditCommentsResult handleRequest(final EditCommentsRequest EditCommentsRequest) {
        log.info("Received EditStoryRequest {}", EditCommentsRequest);

        // Check to ensure requested changes will not result in a negative inventory amount, add count to CloudWatch
        if (EditCommentsRequest.getStoryId() == null) {
            throw new UserNotFoundException("requested availableUnit or reservedUnits invalid");
        }
        Comments comments = commentsDao.getComments(EditCommentsRequest.getStoryId());
        if (comments == null) {
            throw new UserNotFoundException("comments " + comments + " not found to update");
        }

        comments.setStoryId(EditCommentsRequest.getStoryId());

        comments.setPosComments(EditCommentsRequest.getPosComments());
        comments.setNegComments(EditCommentsRequest.getNegComments());

        comments = commentsDao.saveComments(comments);

        return EditCommentsResult.builder()
                .withCommentsModel(new ModelConverter().toCommentsModel(comments))
                .build();
    }
}