package SE02.Capstone.models;

import java.util.List;
import java.util.Objects;

public class StoryModel {

    private final String storyId;
    private final String userID;
    private final String title;
    private final String content;
    private final String snippet;
    private List<String> tags;

    private StoryModel(String storyId, String userID, String title, String content, String snippet, List<String> tags) {
        this.storyId = storyId;
        this.userID = userID;
        this.title = title;
        this.content = content;
        this.snippet = snippet;
        this.tags = tags;
    }

    public String getStoryId() {
        return storyId;
    }

    public String getUserID() {
        return userID;
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
                userID.equals(storyModel.userID) &&
                title.equals(storyModel.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storyId, userID, title);
    }

    //CHECKSTYLE:OFF:Builder
    public static UserModel.Builder builder() {
        return new UserModel.Builder();
    }

    public static class Builder {
        private String storyId;
        private String userId;
        private String title;
        private String content;
        private String snippet;
        private List<String> tags;

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

        public StoryModel build() {
            return new StoryModel(storyId, userId, title, content, snippet, tags);
        }
    }
}

