package SE02.Capstone.activity.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;
import java.util.List;


@JsonDeserialize(builder = EditCommentsRequest.Builder.class)

public class EditCommentsRequest {
    private String storyId;
    private List<String> posComments;
    private List<String> negComments;

    private EditCommentsRequest(String storyId, List<String> posComments, List<String> negComments) {
        this.storyId = storyId;
        this.posComments = new ArrayList<>(posComments);
        this.negComments = new ArrayList<>(negComments);
    }

    @JsonProperty("storyId")
    public String getStoryId() {
        return storyId;
    }

    @JsonProperty("posComments")
    public List<String> getPosComments() { return posComments; }

    @JsonProperty("negComments")
    public List<String> getNegComments() { return negComments; }

    @Override
    public String toString() {
        return "EditCommentsRequest{" +
                "storyId='" + storyId + '\'' +
                ", posComments='" + posComments + '\'' +
                ", negComments='" + negComments + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static EditCommentsRequest.Builder builder() {
        return new EditCommentsRequest.Builder();
    }

    public static class Builder {
        private String storyId;
        private List<String> posComments;
        private List<String> negComments;

        public EditCommentsRequest.Builder withStoryId(String storyId) {
            this.storyId = storyId;
            return this;
        }

        public EditCommentsRequest.Builder withPosComments(List<String> posComments) {
            this.posComments = posComments;
            return this;
        }
        public EditCommentsRequest.Builder withNegComments(List<String> negComments) {
            this.negComments = negComments;
            return this;
        }


        public EditCommentsRequest build() {
            return new EditCommentsRequest(storyId, posComments, negComments);
        }
    }

}