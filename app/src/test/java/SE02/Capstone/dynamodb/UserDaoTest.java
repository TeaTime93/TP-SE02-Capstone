package SE02.Capstone.dynamodb;

import SE02.Capstone.dynamodb.models.User;
import SE02.Capstone.exceptions.UserNotFoundException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class UserDaoTest {

    @Mock
    private DynamoDBMapper dynamoDbMapper;

    private UserDao userDao;

    @BeforeEach
    void setUp() {
        initMocks(this);
        userDao = new UserDao(dynamoDbMapper);

        User user1 = new User();
        user1.setUserId("user1");
        user1.setEmail("user1@example.com");

        User user2 = new User();
        user2.setUserId("user2");
        user2.setEmail("user2@example.com");

        when(dynamoDbMapper.load(User.class, "user1")).thenReturn(user1);
        when(dynamoDbMapper.load(User.class, "user2")).thenReturn(user2);
    }

    @Test
    void getUser_shouldReturnCorrectUser() {
        User retrievedUser = userDao.getUser("user1");
        assertEquals("user1", retrievedUser.getUserId());
    }

    @Test
    void getUser_shouldThrowException_whenUserDoesNotExist() {
        assertThrows(UserNotFoundException.class, () -> userDao.getUser("nonexistent"));
    }

    @Test
    void saveUser_shouldSaveUserCorrectly() {
        User newUser = new User();
        newUser.setUserId("user3");
        newUser.setEmail("user3@example.com");

        User savedUser = userDao.saveUser(newUser);

        assertEquals(newUser, savedUser);
        verify(dynamoDbMapper).save(newUser);
    }
}
