package SE02.Capstone.activity.result;

import SE02.Capstone.models.StoryModel;

public class DeleteStoryResult {
    private final StoryModel story;

    private DeleteStoryResult(StoryModel story) {
        this.story = story;
    }

    public StoryModel getStory() {
        return story;
    }

    @Override
    public String toString() {
        return "DeleteStoryResult{" +
                "story=" + story +
                '}';
    }

    public static DeleteStoryResult.Builder builder() {
        return new DeleteStoryResult.Builder();
    }

    public static class Builder {
        private StoryModel story;

        public DeleteStoryResult.Builder withStory(StoryModel story) {
            this.story = story;
            return this;
        }

        public DeleteStoryResult build() {
            return new DeleteStoryResult(story);
        }
    }
}