package SE02.Capstone.activity.request;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

public class GenerateFeedRequest {
    private final String userId;

    private GenerateFeedRequest(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "GenerateFeed{" +
                "userId='" + userId + '\'' +
                '}';
    }

    public static GenerateFeedRequest.Builder builder() {
        return new GenerateFeedRequest.Builder();
    }
    @JsonPOJOBuilder
    public static class Builder {
        private String userId;

        public GenerateFeedRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public GenerateFeedRequest build() {
            return new GenerateFeedRequest(userId);
        }

    }
}
