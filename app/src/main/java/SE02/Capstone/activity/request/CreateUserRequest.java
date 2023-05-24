package SE02.Capstone.activity.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.List;

@JsonDeserialize(builder = CreateUserRequest.Builder.class)
public class CreateUserRequest {

    private String userId;
    private String userName;
    private String email;
    private String bio;
    private int age;

    public CreateUserRequest(String userId, String userName, String email, String bio, int age) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.bio = bio;
        this.age = age;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public String getBio() {
        return bio;
    }

    public int getAge() {
        return age;
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() { return new Builder(); }

    @JsonPOJOBuilder
    public static class Builder {
        private String userId;
        private String userName;
        private String email;
        private String bio;
        private int age;

        public CreateUserRequest.Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public CreateUserRequest.Builder withUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public CreateUserRequest.Builder withEmail(String email) {
            this.email = email;
            return this;
        }

        public CreateUserRequest.Builder withBio(String bio) {
            this.bio = bio;
            return this;
        }

        public CreateUserRequest.Builder withAge(int age) {
            this.age = age;
            return this;
        }

        public CreateUserRequest build() {
            return new CreateUserRequest(userId, userName, email, bio, age);
        }
    }
}
