package SE02.Capstone.activity.result;

import SE02.Capstone.models.UserModel;

import java.util.List;

public class GetUserByEmailResult {

    private final UserModel user;

    private GetUserByEmailResult(UserModel user) {
        this.user = user;
    }

    public UserModel getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "GetUserByEmailResult{" +
                "user=" + user +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private UserModel user;

        public Builder withUser(UserModel user) {
            this.user = user;
            return this;
        }

        public GetUserByEmailResult build() {
            return new GetUserByEmailResult(user);
        }
    }
}
