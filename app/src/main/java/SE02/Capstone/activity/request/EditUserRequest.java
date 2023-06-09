package SE02.Capstone.activity.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.util.ArrayList;
import java.util.List;


@JsonDeserialize(builder = EditUserRequest.Builder.class)
public class EditUserRequest {
    private final String userId;
    private final String userName;
    private final String email;
    private final String bio;
    private final int age;
    private final List<String> follows;
    private final List<String> followers;
    private final List<String> favorites;
    private final int userScore;

    private EditUserRequest(String userId, String userName, String email, String bio, int age,
                            List<String> follows, List<String> followers, List<String> favorites, int userScore) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.bio = bio;
        this.age = age;
        this.follows = new ArrayList<>(follows);
        this.followers = new ArrayList<>(followers);
        this.favorites = new ArrayList<>(favorites);
        this.userScore = userScore;
    }
    @JsonProperty("userId")
    public String getUserId() {
        return userId;
    }
    @JsonProperty("userName")
    public String getUserName() {
        return userName;
    }
    @JsonProperty("email")
    public String getEmail() {
        return email;
    }
    @JsonProperty("bio")
    public String getBio() {
        return bio;
    }
    @JsonProperty("age")
    public int getAge() {
        return age;
    }
    @JsonProperty("follows")
    public List<String> getFollows() {
        return follows;
    }
    @JsonProperty("followers")
    public List<String> getFollowers() {
        return followers;
    }
    @JsonProperty("favorites")
    public List<String> getFavorites() {
        return favorites;
    }
    @JsonProperty("userScore")
    public int getUserScore() {
        return userScore;
    }

    @Override
    public String toString() {
        return "EditUserRequest{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", bio='" + bio + '\'' +
                ", age='" + age + '\'' +
                ", follows='" + follows + '\'' +
                ", followers='" + followers + '\'' +
                ", favorites='" + favorites + '\'' +
                ", userScore='" + userScore + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

    @JsonPOJOBuilder
    public static class Builder {
        private String userId;
        private String userName;
        private String email;
        private String bio;
        private int age;
        private List<String> follows;
        private List<String> followers;
        private List<String> favorites;
        private int userScore;

        @JsonProperty("userId")
        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        @JsonProperty("userName")
        public Builder withUserName(String userName) {
            this.userName = userName;
            return this;
        }

        @JsonProperty("email")
        public Builder email(String email) {
            this.email = email;
            return this;
        }
        @JsonProperty("bio")
        public Builder withBio(String bio) {
            this.bio = bio;
            return this;
        }

        @JsonProperty("age")
        public Builder withAge(int age) {
            this.age = age;
            return this;
        }

        @JsonProperty("follows")
        public Builder withFollows(List<String> follows) {
            this.follows = new ArrayList<>(follows);
            return this;
        }

        @JsonProperty("followers")
        public Builder withFollowers(List<String> followers) {
            this.followers = new ArrayList<>(followers);
            return this;
        }

        @JsonProperty("favorites")
        public Builder withFavorites(List<String> favorites) {
            this.favorites = new ArrayList<>(favorites);
            return this;
        }

        @JsonProperty("userScore")
        public Builder withUserScore(int userScore) {
            this.userScore = userScore;
            return this;
        }
        public EditUserRequest build() {
            return new EditUserRequest(userId, userName, email, bio, age, follows, followers, favorites, userScore);
        }
    }
}