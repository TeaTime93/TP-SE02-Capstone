package SE02.Capstone.activity.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.List;

@JsonDeserialize(builder = CreateStoryRequest.Builder.class)
public class CreateStoryRequest {

    private String storyId;
    private String userId;
    private String title;
    private String content;
    private String snippet;
    private List<String> tags;

    private CreateStoryRequest(String storyId, String userId, String title, String content, String snippet, List<String> tags) {
        this.storyId = storyId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.snippet = snippet;
        this.tags = tags;
    }

    public String getStoryId() {
        return storyId;
    }

    public String getUserID() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getSnippet() {
        return snippet;
    }

    public List<String> getTags() {
        return tags;
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() { return new Builder(); }

    @JsonPOJOBuilder
    public static class Builder {
        private String storyId;
        private String userId;
        private String title;
        private String content;
        private String snippet;
        private List<String> tags;

        public CreateStoryRequest.Builder withStoryId(String storyId) {
            this.storyId = storyId;
            return this;
        }

        public CreateStoryRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public CreateStoryRequest.Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public CreateStoryRequest.Builder withContent(String content) {
            this.content = content;
            return this;
        }

        public CreateStoryRequest.Builder withSnippet(String snippet) {
            this.snippet = snippet;
            return this;
        }

        public CreateStoryRequest.Builder withTags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public CreateStoryRequest build() {
            return new CreateStoryRequest(storyId, userId, title, content, snippet, tags);
        }
    }
}