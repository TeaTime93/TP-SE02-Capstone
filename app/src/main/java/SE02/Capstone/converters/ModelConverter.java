package SE02.Capstone.converters;

import SE02.Capstone.dynamodb.models.User;
import SE02.Capstone.models.UserModel;


public class ModelConverter {

    public UserModel toUserModel(User user) {

        return UserModel.builder()
                .withUserId(user.getUserId())
                .withUserName(user.getUserName())
                .withEmail(user.getEmail())
                .withBio(user.getBio())
                .withAge(user.getAge())
                .build();
    }
}