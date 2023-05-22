package SE02.Capstone.activity.result;

import SE02.Capstone.models.UserModel;

public class CreateUserResult {
    private final UserModel user;

    public CreateUserResult(UserModel user) {
        this.user = user;
    }
    //this method below seems like it will be used for just the test
    public UserModel getOrder() {
        return user;
    }

    @Override
    public String toString() {
        return "CreateUserResult{" +
                "user=" + user +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UserModel user;

        public Builder withUser(UserModel user) {
            this.user = user;
            return this;
        }

        public CreateUserResult build() {
            return new CreateUserResult(user);
        }
    }

}
