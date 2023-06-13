package SE02.Capstone.activity.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.ArrayList;
import java.util.List;


@JsonDeserialize(builder = EditStoryRequest.Builder.class)
public class EditStoryRequest {
    private String storyId;
    private String userId;
    private String title;
    private String content;
    private String snippet;
    private List<String> tags;
    private int likes;


    private EditStoryRequest(String storyId, String userId, String title, String content, String snippet,
                             List<String> tags, int likes) {
        this.storyId = storyId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.snippet = snippet;
        this.tags = new ArrayList<>(tags);
        this.likes = likes;
    }
    @JsonProperty("storyId")
    public String getStoryId() {
        return storyId;
    }
    @JsonProperty("userId")
    public String getUserId() {
        return userId;
    }
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }
    @JsonProperty("content")
    public String getContent() {
        return content;
    }
    @JsonProperty("snippet")
    public String getSnippet() {
        return snippet;
    }
    @JsonProperty("tags")
    public List<String> getTags() {
        return tags;
    }
    @JsonProperty("likes")
    public int getLikes() {
        return likes;
    }

    @Override
    public String toString() {
        return "EditStoryRequest{" +
                "storyId='" + storyId + '\'' +
                ", userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", snippet='" + snippet + '\'' +
                ", tags='" + tags + '\'' +
                ", likes='" + likes + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
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

        @JsonProperty("storyId")
        public Builder withStoryId(String storyId) {
            this.storyId = storyId;
            return this;
        }
        @JsonProperty("userId")
        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }
        @JsonProperty("title")
        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }
        @JsonProperty("content")
        public Builder withContent(String content) {
            this.content = content;
            return this;
        }
        @JsonProperty("snippet")
        public Builder withSnippet(String snippet) {
            this.snippet = snippet;
            return this;
        }
        @JsonProperty("tags")
        public Builder withTags(List<String> tags) {
            this.tags = new ArrayList<>(tags);
            return this;
        }
        @JsonProperty("likes")
        public Builder withLikes(int likes) {
            this.likes = likes;
            return this;
        }

        public EditStoryRequest build() {
            return new EditStoryRequest(storyId, userId, title, content, snippet, tags, likes);
        }
    }
}
