package SE02.Capstone.activity.result;

import SE02.Capstone.models.CommentsModel;

public class GetCommentsResult {
    private final CommentsModel comments;

    private GetCommentsResult(CommentsModel comments) {
        this.comments = comments;
    }

    public CommentsModel getComments() {
        return comments;
    }

    @Override
    public String toString() {
        return "GetCommentsResult{" +
                "comments=" + comments +
                '}';
    }

    public static GetCommentsResult.Builder builder() {
        return new GetCommentsResult.Builder();
    }

    public static class Builder {
        private CommentsModel comments;

        public GetCommentsResult.Builder withComments(CommentsModel comments) {
            this.comments = comments;
            return this;
        }

        public GetCommentsResult build() {
            return new GetCommentsResult(comments);
        }
    }
}
