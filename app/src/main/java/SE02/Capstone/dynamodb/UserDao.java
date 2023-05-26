package SE02.Capstone.dynamodb;

import SE02.Capstone.dynamodb.models.User;
import SE02.Capstone.exceptions.UserNotFoundException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserDao {

    private final DynamoDBMapper dynamoDbMapper;

    @Inject
    public UserDao(DynamoDBMapper dynamoDbMapper) {
        this.dynamoDbMapper = dynamoDbMapper;
    }

    public User getUser(String userId) {
        User user = this.dynamoDbMapper.load(User.class, userId);
        System.out.println(user);

        if (user == null) {
            throw new UserNotFoundException("UserDao: Could not find user with id " + userId);
        }
        return user;
    }

    public User saveUser(User newUser) {
        this.dynamoDbMapper.save(newUser);
        return newUser;
    }

}