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
    private int likes;
    private int dislikes;
    private int hooks;

    private CreateStoryRequest(String storyId, String userId, String title, String content, String snippet, List<String> tags,
                               int likes, int dislikes, int hooks) {
        this.storyId = storyId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.snippet = snippet;
        this.tags = tags;
        this.likes = likes;
        this.dislikes = dislikes;
        this.hooks = hooks;
    }

    public String getStoryId() {
        return storyId;
    }

    public String getUserId() {
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

    public int getLikes() {
        return likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public int getHooks() {
        return hooks;
    }

    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String storyId;
        private String userId;
        private String title;
        private String content;
        private String snippet;
        private List<String> tags;
        private int likes;
        private int dislikes;
        private int hooks;

        public Builder withStoryId(String storyId) {
            this.storyId = storyId;
            return this;
        }

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withContent(String content) {
            this.content = content;
            return this;
        }

        public Builder withSnippet(String snippet) {
            this.snippet = snippet;
            return this;
        }

        public Builder withTags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public Builder withLikes(int likes) {
            this.likes = likes;
            return this;
        }

        public Builder withDislikes(int dislikes) {
            this.dislikes = dislikes;
            return this;
        }

        public Builder withHooks(int hooks) {
            this.hooks = hooks;
            return this;
        }

        public CreateStoryRequest build() {
            return new CreateStoryRequest(storyId, userId, title, content, snippet, tags, likes, dislikes, hooks);
        }
    }
}
