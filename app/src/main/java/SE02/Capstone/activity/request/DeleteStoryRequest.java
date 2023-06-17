package SE02.Capstone.activity.request;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

public class DeleteStoryRequest {
    private final String storyId;

    private DeleteStoryRequest(String storyId) {
        this.storyId = storyId;
    }

    public String getStoryId() {
        return storyId;
    }

    @Override
    public String toString() {
        return "DeleteStoryRequest{" +
                "storyId='" + storyId + '\'' +
                '}';
    }

    public static DeleteStoryRequest.Builder builder() {
        return new DeleteStoryRequest.Builder();
    }
    @JsonPOJOBuilder
    public static class Builder {
        private String storyId;

        public DeleteStoryRequest.Builder withStoryId(String storyId) {
            this.storyId = storyId;
            return this;
        }

        public DeleteStoryRequest build() {
            return new DeleteStoryRequest(storyId);
        }

    }
}