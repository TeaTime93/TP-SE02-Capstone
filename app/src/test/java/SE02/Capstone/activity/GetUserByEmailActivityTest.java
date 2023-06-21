package SE02.Capstone.activity;

import SE02.Capstone.activity.request.GetUserByEmailRequest;
import SE02.Capstone.activity.result.GetUserByEmailResult;
import SE02.Capstone.dynamodb.UserDao;
import SE02.Capstone.dynamodb.models.User;
import SE02.Capstone.exceptions.UserNotFoundException;
import SE02.Capstone.models.UserModel;
import SE02.Capstone.converters.ModelConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class GetUserByEmailActivityTest {

    @Mock
    private UserDao userDao;

    private GetUserByEmailActivity getUserByEmailActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        getUserByEmailActivity = new GetUserByEmailActivity(userDao);
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

        GetUserByEmailRequest request = GetUserByEmailRequest.builder()
                .withEmail(expectedEmail)
                .build();

        when(userDao.getUserByEmail(expectedEmail)).thenReturn(expectedUser);

        // WHEN
        GetUserByEmailResult result = getUserByEmailActivity.handleRequest(request);

        // THEN
        assertEquals(expectedUserModel, result.getUser());
    }

    @Test
    void handleRequest_withInvalidEmail_throwsUserNotFoundException() {
        // GIVEN
        String invalidEmail = "invalidEmail@example.com";

        GetUserByEmailRequest request = GetUserByEmailRequest.builder()
                .withEmail(invalidEmail)
                .build();

        when(userDao.getUserByEmail(invalidEmail)).thenReturn(null);

        // WHEN + THEN
        assertThrows(UserNotFoundException.class, () -> getUserByEmailActivity.handleRequest(request));
    }
}
