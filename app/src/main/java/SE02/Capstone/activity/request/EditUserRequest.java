package SE02.Capstone.activity.request;

import com.fasterxml.jackson.annotation.JsonValue;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import java.math.BigDecimal;
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

    public List<String> getFollows() {
        return follows;
    }

    public List<String> getFollowers() {
        return followers;
    }

    public List<String> getFavorites() {
        return favorites;
    }

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

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }
        public Builder withBio(String bio) {
            this.bio = bio;
            return this;
        }

        public Builder withAge(int age) {
            this.age = age;
            return this;
        }

        public Builder withFollows(List<String> follows) {
            this.follows = new ArrayList<>(follows);
            return this;
        }

        public Builder withFollowers(List<String> followers) {
            this.followers = new ArrayList<>(followers);
            return this;
        }

        public Builder withFavorites(List<String> favorites) {
            this.favorites = new ArrayList<>(favorites);
            return this;
        }

        public Builder withUserScore(int userScore) {
            this.userScore = userScore;
            return this;
        }
        public EditUserRequest build() {
            return new EditUserRequest(userId, userName, email, bio, age, follows, followers, favorites, userScore);
        }
    }
}