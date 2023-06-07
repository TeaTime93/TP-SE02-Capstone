package SE02.Capstone.activity;

import SE02.Capstone.activity.request.EditUserRequest;
import SE02.Capstone.activity.result.EditUserResult;
import SE02.Capstone.converters.ModelConverter;
import SE02.Capstone.dynamodb.UserDao;
import SE02.Capstone.dynamodb.models.User;
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
public class EditUserActivity {

    private final Logger log = LogManager.getLogger();

    private final UserDao userDao;

    private final MetricsPublisher metricsPublisher;


    /**
     * Instantiates a new UpdatePlaylistActivity object.
     *
     * @param userDao UserDao to access the inventory table.
     * @param metricsPublisher MetricsPublisher to publish metrics.
     */
    @Inject
    public EditUserActivity(UserDao userDao, MetricsPublisher metricsPublisher) {
        this.userDao = userDao;
        this.metricsPublisher = metricsPublisher;
    }

    /**
     * This method handles the incoming request by retrieving the User object, updating it,
     * and persisting the inventory object.
     * <p>
     * It then returns the updated inventory item.
     * <p>
     * If the User does not exist, this should throw a UserNotFoundException.
     * <p>
     * If the provided user or customer ID has invalid characters, throws an
     * InvalidAttributeValueException
     * <p>
     * If the request tries to update the customer ID,
     * this should throw an InvalidAttributeChangeException
     *
     * @param EditUserRequest request object containing the User ID, packageing type,
     *                               available units and reserved units associated with it
     * @return updatePlaylistResult result object containing the API defined
     */
    public EditUserResult handleRequest(final EditUserRequest EditUserRequest) {
        log.info("Received EditUserRequest {}", EditUserRequest);

        // Check to ensure requested changes will not result in a negative inventory amount, add count to CloudWatch
        if (EditUserRequest.getUserId() == null) {
            throw new UserNotFoundException("requested availableUnit or reservedUnits invalid");
        }
        User user = userDao.getUser(EditUserRequest.getUserId());
        // If User not found in the table throws UserNotFound and adds count to CloudWatch
        if (user == null) {
            throw new UserNotFoundException("User " + user + " not found to update");
        }

        user.setUserName(EditUserRequest.getUserName());
        user.setBio(EditUserRequest.getBio());
        user.setAge(EditUserRequest.getAge());
        user.setFollows(EditUserRequest.getFollows());
        user.setFollowers(EditUserRequest.getFollowers());
        user.setFavorites(EditUserRequest.getFavorites());
        user.setUserScore(EditUserRequest.getUserScore());

        user = userDao.saveUser(user);
        log.info("updated retrieved object and saved User to table User = {}", user);
        return EditUserResult.builder()
                .withUserModel(new ModelConverter().toUserModel(user))
                .build();
    }
}
