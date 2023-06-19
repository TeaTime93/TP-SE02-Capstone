package SE02.Capstone.activity.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.List;

@JsonDeserialize(builder = CreateCommentsRequest.Builder.class)
public class CreateCommentsRequest {
    private String storyId;
    private List<String> posComments;
    private List<String> negComments;
    public CreateCommentsRequest(String storyId, List<String> posComments, List<String> negComments) {
        this.storyId = storyId;
        this.posComments = posComments;
        this.negComments = negComments;
    }

    public String getStoryId() { return storyId; }
    public List<String> getPosComments() { return posComments; }
    public List<String> getNegComments() { return negComments; }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() { return new Builder(); }

    @JsonPOJOBuilder
    public static class Builder {
        private String storyId;
        private List<String> posComments;
        private List<String> negComments;

        public Builder withStoryId(String storyId) {
            this.storyId = storyId;
            return this;
        }

        public Builder withPosComments(List<String> posComments) {
            this.posComments = posComments;
            return this;
        }
        public Builder withNegComments(List<String> negComments) {
            this.negComments = negComments;
            return this;
        }

        public CreateCommentsRequest build() {
            return new CreateCommentsRequest(storyId, posComments, negComments);
        }
    }
}
