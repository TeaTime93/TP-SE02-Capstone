package SE02.Capstone.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StoryModel {

    private final String storyId;
    private final String userId;
    private final String title;
    private final String content;
    private final String snippet;
    private List<String> tags;
    private final int likes;
    private final int dislikes;
    private final int hooks;

    private StoryModel(String storyId, String userId, String title, String content, String snippet, List<String> tags,
                       int likes, int dislikes, int hooks) {
        this.storyId = storyId;
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.snippet = snippet;
        this.tags = tags != null ? new ArrayList<>(tags) : null;
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

    public int getLikes() { return likes;}

    public int getDislikes() { return dislikes;}

    public int getHooks() { return hooks;}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StoryModel storyModel = (StoryModel) o;
        return storyId == storyModel.storyId &&
                userId.equals(storyModel.userId) &&
                title.equals(storyModel.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storyId, userId, title);
    }

    //CHECKSTYLE:OFF:Builder
    public static StoryModel.Builder builder() {
        return new StoryModel.Builder();
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

        public StoryModel.Builder withStoryId(String storyId) {
            this.storyId = storyId;
            return this;
        }

        public StoryModel.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public StoryModel.Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public StoryModel.Builder withContent(String content) {
            this.content = content;
            return this;
        }

        public StoryModel.Builder withSnippet(String snippet) {
            this.snippet = snippet;
            return this;
        }

        public StoryModel.Builder withTags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public StoryModel.Builder withLikes(int likes){
            this.likes = likes;
            return this;
        }

        public StoryModel.Builder withDislikes(int dislikes){
            this.dislikes = dislikes;
            return this;
        }

        public StoryModel.Builder withHooks(int hooks){
            this.hooks = hooks;
            return this;
        }

        public StoryModel build() {
            return new StoryModel(storyId, userId, title, content, snippet, tags, likes, dislikes, hooks);
        }
    }
}

