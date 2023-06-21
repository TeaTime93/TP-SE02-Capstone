package SE02.Capstone.activity.request;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

public class GetStoryByTitleAndAuthorRequest {
    private final String title;
    private final String userId;

    private GetStoryByTitleAndAuthorRequest(String title, String userId) {
        this.title = title;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "GetStoryByTitleAndAuthorRequest{" +
                "title='" + title + '\'' +
                "userId='" + userId + '\'' +
                '}';
    }

    public static GetStoryByTitleAndAuthorRequest.Builder builder() {
        return new GetStoryByTitleAndAuthorRequest.Builder();
    }
    @JsonPOJOBuilder
    public static class Builder {
        private String title;
        private String userId;

        public GetStoryByTitleAndAuthorRequest.Builder withTitle(String title) {
            this.title = title;
            return this;
        }
        public GetStoryByTitleAndAuthorRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public GetStoryByTitleAndAuthorRequest build() {
            return new GetStoryByTitleAndAuthorRequest(title, userId);
        }

    }
}