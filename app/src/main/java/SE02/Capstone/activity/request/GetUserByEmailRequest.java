package SE02.Capstone.activity.request;

import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

public class GetUserByEmailRequest {

    private final String email;

    private GetUserByEmailRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "GetUserByEmailRequest{" +
                "email='" + email + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }
    @JsonPOJOBuilder
    public static class Builder {
        private String email;

        public Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public GetUserByEmailRequest build() {
            return new GetUserByEmailRequest(email);
        }

    }
}
