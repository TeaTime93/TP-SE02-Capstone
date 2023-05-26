package SE02.Capstone.activity.result;

import SE02.Capstone.models.StoryModel;

public class GetStoryResult {
    private final StoryModel story;

    private GetStoryResult(StoryModel story) {
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

    public static GetStoryResult.Builder builder() {
        return new GetStoryResult.Builder();
    }

    public static class Builder {
        private StoryModel story;

        public GetStoryResult.Builder withStory(StoryModel story) {
            this.story = story;
            return this;
        }

        public GetStoryResult build() {
            return new GetStoryResult(story);
        }
    }
}
