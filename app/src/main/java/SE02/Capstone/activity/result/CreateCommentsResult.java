package SE02.Capstone.activity.result;

import SE02.Capstone.models.CommentsModel;

public class CreateCommentsResult {
    private final CommentsModel comments;

    public CreateCommentsResult(CommentsModel comments) {
        this.comments = comments;
    }
    public CommentsModel getComments() {
        return comments;
    }

    @Override
    public String toString() {
        return "CreateCommentsResult{" +
                "comments=" + comments +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static CreateCommentsResult.Builder builder() {
        return new CreateCommentsResult.Builder();
    }

    public static class Builder {
        private CommentsModel comments;

        public CreateCommentsResult.Builder withComments(CommentsModel comments) {
            this.comments = comments;
            return this;
        }

        public CreateCommentsResult build() {
            return new CreateCommentsResult(comments);
        }
    }
}
