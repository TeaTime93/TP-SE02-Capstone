package SE02.Capstone.dynamodb;

import SE02.Capstone.dynamodb.models.User;
import SE02.Capstone.exceptions.UserNotFoundException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserDao {

    private final DynamoDBMapper dynamoDbMapper;

    /**
     * Instantiates an InventoryDao object.
     *
     * @param dynamoDbMapper the {@link DynamoDBMapper} used to interact with the album_track table
     */

    @Inject
    public UserDao(DynamoDBMapper dynamoDbMapper) {
        this.dynamoDbMapper = dynamoDbMapper;
    }

    public User getUser(String userId) {
        User user = this.dynamoDbMapper.load(User.class, userId);

        if (user == null) {
            throw new UserNotFoundException("Could not find playlist with id " + userId);
        }
        return user;
    }

    public User saveUser(User newUser) {
        this.dynamoDbMapper.save(newUser);
        return newUser;
    }

}