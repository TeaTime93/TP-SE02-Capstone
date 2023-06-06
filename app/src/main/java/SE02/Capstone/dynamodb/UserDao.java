package SE02.Capstone.dynamodb;

import SE02.Capstone.dynamodb.models.User;
import SE02.Capstone.exceptions.UserNotFoundException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public User getUserByEmail(String email) {
        Map<String, AttributeValue> eav = new HashMap<String, AttributeValue>();
        eav.put(":val1", new AttributeValue().withS(email));

        DynamoDBQueryExpression<User> queryExpression = new DynamoDBQueryExpression<User>()
                .withIndexName("EmailIndex")
                .withConsistentRead(false)
                .withKeyConditionExpression("email = :val1")
                .withExpressionAttributeValues(eav);

        List<User> users = dynamoDbMapper.query(User.class, queryExpression);

        if (users.isEmpty()) {
            throw new UserNotFoundException("UserDao: Could not find user with email " + email);
        }

        return users.get(0);
    }


    public User saveUser(User newUser) {
        this.dynamoDbMapper.save(newUser);
        return newUser;
    }

}