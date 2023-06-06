package SE02.Capstone.activity;

import SE02.Capstone.activity.request.GetUserByEmailRequest;
import SE02.Capstone.activity.request.GetUserRequest;
import SE02.Capstone.activity.result.GetUserByEmailResult;
import SE02.Capstone.activity.result.GetUserResult;
import SE02.Capstone.converters.ModelConverter;
import SE02.Capstone.dynamodb.UserDao;
import SE02.Capstone.dynamodb.models.User;
import SE02.Capstone.exceptions.UserNotFoundException;
import SE02.Capstone.models.UserModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class GetUserByEmailActivity {

    private final Logger log = LogManager.getLogger();
    private final UserDao userDao;


    @Inject
    public GetUserByEmailActivity(UserDao userDao) {
        this.userDao = userDao;
    }

    public GetUserByEmailResult handleRequest(final GetUserByEmailRequest getUserByEmailRequest) {
        log.info("Received UserByEmailRequest {}", getUserByEmailRequest);
        String requestId = getUserByEmailRequest.getEmail();
        User user = userDao.getUserByEmail(requestId);
        if (user == null){
            throw new UserNotFoundException("Coming from the activity");
        }
        UserModel userModel = new ModelConverter().toUserModel(user);
        return GetUserByEmailResult.builder()
                .withUser(userModel)
                .build();
    }
}