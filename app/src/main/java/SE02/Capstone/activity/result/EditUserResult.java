package SE02.Capstone.activity.result;

import SE02.Capstone.models.UserModel;

public class EditUserResult {

    private final UserModel userModel;

    private EditUserResult(UserModel userModel) {
        this.userModel = userModel;
    }

    public UserModel getUserModel() {
        return userModel;
    }

    @Override
    public String toString() {
        return "EditUserResult{" +
                "userModel=" + userModel +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static EditUserResult.Builder builder() {
        return new EditUserResult.Builder();
    }

    public static class Builder {
        private UserModel userModel;

        public EditUserResult.Builder withUserModel(UserModel userModel) {
            this.userModel = userModel;
            return this;
        }

        public EditUserResult build() {
            return new EditUserResult(userModel);
        }
    }
}
