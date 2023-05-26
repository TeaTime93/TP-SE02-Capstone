package SE02.Capstone.activity.request;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

public class GetStoryRequest {
    private final String storyId;

    private GetStoryRequest(String storyId) {
        this.storyId = storyId;
    }

    public String getStoryId() {
        return storyId;
    }

    @Override
    public String toString() {
        return "GetStoryRequest{" +
                "storyId='" + storyId + '\'' +
                '}';
    }

    public static GetStoryRequest.Builder builder() {
        return new GetStoryRequest.Builder();
    }
    @JsonPOJOBuilder
    public static class Builder {
        private String storyId;

        public GetStoryRequest.Builder withStoryId(String storyId) {
            this.storyId = storyId;
            return this;
        }

        public GetStoryRequest build() {
            return new GetStoryRequest(storyId);
        }

    }
}
