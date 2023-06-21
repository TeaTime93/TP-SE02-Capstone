package SE02.Capstone.activity;

import SE02.Capstone.activity.request.GetUserRequest;
import SE02.Capstone.activity.result.GetUserResult;
import SE02.Capstone.dynamodb.UserDao;
import SE02.Capstone.dynamodb.models.User;
import SE02.Capstone.exceptions.UserNotFoundException;
import SE02.Capstone.models.UserModel;
import SE02.Capstone.converters.ModelConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class GetUserActivityTest {

    @Mock
    private UserDao userDao;

    private GetUserActivity getUserActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        getUserActivity = new GetUserActivity(userDao);
    }

    @Test
    void handleRequest_withValidRequest_returnsUser() {
        // GIVEN
        String expectedUserId = "user123";
        String expectedUserName = "User Name";
        String expectedEmail = "email@example.com";


        User expectedUser = new User();
        expectedUser.setUserId(expectedUserId);
        expectedUser.setUserName(expectedUserName);
        expectedUser.setEmail(expectedEmail);

        UserModel expectedUserModel = new ModelConverter().toUserModel(expectedUser);

        GetUserRequest request = GetUserRequest.builder()
                .withUserId(expectedUserId)
                .build();

        when(userDao.getUser(expectedUserId)).thenReturn(expectedUser);

        // WHEN
        GetUserResult result = getUserActivity.handleRequest(request);

        // THEN
        assertEquals(expectedUserModel, result.getUser());
    }

    @Test
    void handleRequest_withInvalidUserId_throwsUserNotFoundException() {
        // GIVEN
        String invalidUserId = "invalidUser123";

        GetUserRequest request = GetUserRequest.builder()
                .withUserId(invalidUserId)
                .build();

        when(userDao.getUser(invalidUserId)).thenReturn(null);

        // WHEN + THEN
        assertThrows(UserNotFoundException.class, () -> getUserActivity.handleRequest(request));
    }
}
