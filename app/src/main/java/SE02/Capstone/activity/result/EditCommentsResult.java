package SE02.Capstone.activity.result;

import SE02.Capstone.models.CommentsModel;

public class EditCommentsResult {

    private final CommentsModel commentsModel;

    private EditCommentsResult(CommentsModel commentsModel) {
        this.commentsModel = commentsModel;
    }

    public CommentsModel getStoryModel() {
        return commentsModel;
    }

    @Override
    public String toString() {
        return "EditCommentsResult{" +
                "commentsModel=" + commentsModel +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static EditCommentsResult.Builder builder() {
        return new EditCommentsResult.Builder();
    }

    public static class Builder {
        private CommentsModel commentsModel;

        public EditCommentsResult.Builder withCommentsModel(CommentsModel commentsModel) {
            this.commentsModel = commentsModel;
            return this;
        }

        public EditCommentsResult build() {
            return new EditCommentsResult(commentsModel);
        }
    }
}