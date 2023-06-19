package SE02.Capstone.activity.request;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

public class GetCommentsRequest {
    private final String storyId;

    private GetCommentsRequest(String storyId) {
        this.storyId = storyId;
    }

    public String getStoryId() {
        return storyId;
    }

    @Override
    public String toString() {
        return "GetCommentsRequest{" +
                "storyId='" + storyId + '\'' +
                '}';
    }

    public static GetCommentsRequest.Builder builder() {
        return new GetCommentsRequest.Builder();
    }
    @JsonPOJOBuilder
    public static class Builder {
        private String storyId;

        public GetCommentsRequest.Builder withStoryId(String storyId) {
            this.storyId = storyId;
            return this;
        }

        public GetCommentsRequest build() {
            return new GetCommentsRequest(storyId);
        }

    }
}
