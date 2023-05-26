package SE02.Capstone.dynamodb.models;

import java.util.*;

public class Feed {
    private List<String> stories;

    public Feed(List<String> stories) {
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
        Feed feedModel = (Feed) o;
        return stories.equals(feedModel.stories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stories);
    }
}

