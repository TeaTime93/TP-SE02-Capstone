package SE02.Capstone.activity.result;

import SE02.Capstone.models.StoryModel;

import java.util.List;

public class GetStoryByTitleAndAuthorResult {
    private final StoryModel story;

    private GetStoryByTitleAndAuthorResult(StoryModel story) {
        this.story = story;
    }

    public StoryModel getStory() {
        return story;
    }

    @Override
    public String toString() {
        return "GetStoryResult{" +
                "story=" + story +
                '}';
    }

    public static GetStoryByTitleAndAuthorResult.Builder builder() {
        return new GetStoryByTitleAndAuthorResult.Builder();
    }

    public static class Builder {
        private StoryModel story;

        public GetStoryByTitleAndAuthorResult.Builder withStory(StoryModel story) {
            this.story = story;
            return this;
        }

        public GetStoryByTitleAndAuthorResult build() {
            return new GetStoryByTitleAndAuthorResult(story);
        }
    }
}
