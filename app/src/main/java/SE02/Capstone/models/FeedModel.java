package SE02.Capstone.models;

import SE02.Capstone.dynamodb.StoryDao;
import SE02.Capstone.dynamodb.UserDao;

import java.util.List;
import java.util.Objects;

public class FeedModel {

    private List<String> stories;

    private StoryDao storyDao;

    private UserDao userDao;

    public FeedModel(List<String> stories) {
        this.stories = stories;
    }

    public List<String> getStories() {
        return stories;
    }

    public void setStories(List<String> stories) {
        this.stories = stories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FeedModel feedModel = (FeedModel) o;
        return stories.equals(feedModel.stories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stories);
    }

    //CHECKSTYLE:OFF:Builder
    public static FeedModel.Builder builder() {
        return new FeedModel.Builder();
    }

    public static class Builder {
        private List<String> stories;

        public FeedModel.Builder withStories(List<String> stories) {
            this.stories = stories;
            return this;
        }

        public FeedModel build() {
            return new FeedModel(stories);
        }
    }
}
