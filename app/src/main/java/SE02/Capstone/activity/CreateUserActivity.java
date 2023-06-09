package SE02.Capstone.activity;

import SE02.Capstone.activity.request.CreateUserRequest;
import SE02.Capstone.activity.result.CreateUserResult;
import SE02.Capstone.converters.ModelConverter;
import SE02.Capstone.dynamodb.UserDao;
import SE02.Capstone.dynamodb.models.User;
import SE02.Capstone.models.UserModel;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class CreateUserActivity {

    private final Logger log = LogManager.getLogger();
    private final UserDao userDao;


    @Inject
    public CreateUserActivity(UserDao userDao) {
        this.userDao = userDao;
    }

    public CreateUserResult handleRequest(final CreateUserRequest createUserRequest) {
        log.info("Received UserRequest {}", createUserRequest);

        User newUser = new User();
        newUser.setUserId(generateUserId());
        newUser.setUserName(createUserRequest.getUserName());
        newUser.setEmail(createUserRequest.getEmail());
        newUser.setBio(createUserRequest.getBio());
        newUser.setAge(createUserRequest.getAge());
        newUser.setFollows(createUserRequest.getFollows());
        newUser.setFollowers(createUserRequest.getFollowers());
        newUser.setFavorites(createUserRequest.getFavorites());
        newUser.setUserScore(createUserRequest.getUserScore());
        newUser.setStoriesWritten(createUserRequest.getStoriesWritten());
        newUser.setFeatured(createUserRequest.getFeatured());
        newUser.setDislikedStories(createUserRequest.getDislikedStories());
        newUser.setPreferredTags(createUserRequest.getPreferredTags());

        userDao.saveUser(newUser);

        UserModel userModel = new ModelConverter().toUserModel(newUser);
        return CreateUserResult.builder()
                .withUser(userModel)
                .build();
    }

    public static String generateUserId() {
        return RandomStringUtils.randomAlphanumeric(5);
    }
}