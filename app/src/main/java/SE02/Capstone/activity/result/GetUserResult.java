package SE02.Capstone.activity.result;

import SE02.Capstone.models.UserModel;

import java.util.List;

public class GetUserResult {

    private final UserModel user;

    private GetUserResult(UserModel user) {
        this.user = user;
    }

    public UserModel getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "GetUserResult{" +
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

        public GetUserResult build() {
            return new GetUserResult(user);
        }
    }
}

