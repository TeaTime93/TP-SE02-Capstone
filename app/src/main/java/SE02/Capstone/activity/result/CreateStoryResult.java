package SE02.Capstone.activity.result;

import SE02.Capstone.models.StoryModel;
import SE02.Capstone.models.UserModel;

public class CreateStoryResult {
    private final StoryModel story;

    public CreateStoryResult(StoryModel story) {
        this.story = story;
    }
    //this method below seems like it will be used for just the test
    public StoryModel getOrder() {
        return story;
    }

    @Override
    public String toString() {
        return "CreateStoryResult{" +
                "story=" + story +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static CreateStoryResult.Builder builder() {
        return new CreateStoryResult.Builder();
    }

    public static class Builder {
        private StoryModel story;

        public CreateStoryResult.Builder withStory(StoryModel story) {
            this.story = story;
            return this;
        }

        public CreateStoryResult build() {
            return new CreateStoryResult(story);
        }
    }
}
