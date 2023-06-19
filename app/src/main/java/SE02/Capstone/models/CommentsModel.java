package SE02.Capstone.models;

import java.util.ArrayList;
import java.util.List;

public class CommentsModel {
        private String storyId;
        private List<String> posComments;
        private List<String> negComments;

    public CommentsModel(String storyId, List<String> posComments, List<String> negComments) {
        this.storyId = storyId;
        this.posComments = posComments != null ? new ArrayList<>(posComments) : null;
        this.negComments = negComments != null ? new ArrayList<>(negComments) : null;
    }

        public String getStoryId() {
            return storyId;
        }
        public List<String> getPosComments() {
            return posComments;
        }
        public List<String> getNegComments() {
            return negComments;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            CommentsModel commentsModel = (CommentsModel) o;
            return storyId == commentsModel.storyId;
        }
    public static CommentsModel.Builder builder() {
        return new CommentsModel.Builder();
    }

    public static class Builder {
        private String storyId;
        private List<String> posComments;
        private List<String> negComments;

        public CommentsModel.Builder withStoryId(String storyId) {
            this.storyId = storyId;
            return this;
        }

        public CommentsModel.Builder withPosComments(List<String> posComments) {
            this.posComments = posComments;
            return this;
        }
        public CommentsModel.Builder withNegComments(List<String> negComments) {
            this.negComments = negComments;
            return this;
        }

        public CommentsModel build() {
            return new CommentsModel(storyId, posComments, negComments);
        }
    }
}