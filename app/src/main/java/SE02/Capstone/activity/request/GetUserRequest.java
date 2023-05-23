package SE02.Capstone.activity.request;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

public class GetUserRequest {

    private final String userId;

    private GetUserRequest(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "GetUserRequest{" +
                "userId='" + userId + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }
    @JsonPOJOBuilder
    public static class Builder {
        private String userId;

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public GetUserRequest build() {
            return new GetUserRequest(userId);
        }

    }
}
