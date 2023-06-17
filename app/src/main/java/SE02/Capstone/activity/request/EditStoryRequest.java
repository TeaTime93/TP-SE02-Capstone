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
    private int dislikes;
    private int hooks;

    private EditStoryRequest(String storyId, String userId, String title, String content, String snippet,
                             List<String> tags, int likes, int dislikes, int hooks) {
        this.storyId = storyId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.snippet = snippet;
        this.tags = new ArrayList<>(tags);
        this.likes = likes;
        this.dislikes = dislikes;
        this.hooks = hooks;
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

    @JsonProperty("dislikes")
    public int getDislikes() {
        return dislikes;
    }

    @JsonProperty("hooks")
    public int getHooks() {
        return hooks;
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
                ", dislikes='" + dislikes + '\'' +
                ", hooks='" + hooks + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static EditStoryRequest.Builder builder() {
        return new EditStoryRequest.Builder();
    }

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

        public EditStoryRequest.Builder withStoryId(String storyId) {
            this.storyId = storyId;
            return this;
        }

        public EditStoryRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public EditStoryRequest.Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public EditStoryRequest.Builder withContent(String content) {
            this.content = content;
            return this;
        }

        public EditStoryRequest.Builder withSnippet(String snippet) {
            this.snippet = snippet;
            return this;
        }

        public EditStoryRequest.Builder withTags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public EditStoryRequest.Builder withLikes(int likes){
            this.likes = likes;
            return this;
        }

        public EditStoryRequest.Builder withDislikes(int dislikes){
            this.dislikes = dislikes;
            return this;
        }

        public EditStoryRequest.Builder withHooks(int hooks){
            this.hooks = hooks;
            return this;
        }

        public EditStoryRequest build() {
            return new EditStoryRequest(storyId, userId, title, content, snippet, tags, likes, dislikes, hooks);
        }
    }

}
