package SE02.Capstone.activity.result;

import SE02.Capstone.models.StoryModel;

public class EditStoryResult {

    private final StoryModel storyModel;

    private EditStoryResult(StoryModel storyModel) {
        this.storyModel = storyModel;
    }

    public StoryModel getStoryModel() {
        return storyModel;
    }

    @Override
    public String toString() {
        return "EditStoryResult{" +
                "storyModel=" + storyModel +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static EditStoryResult.Builder builder() {
        return new EditStoryResult.Builder();
    }

    public static class Builder {
        private StoryModel storyModel;

        public EditStoryResult.Builder withStoryModel(StoryModel storyModel) {
            this.storyModel = storyModel;
            return this;
        }

        public EditStoryResult build() {
            return new EditStoryResult(storyModel);
        }
    }
}