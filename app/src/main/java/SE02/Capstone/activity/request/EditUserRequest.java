package SE02.Capstone.activity.request;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
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
    private final List<String> storiesWritten;
    private final String featured;
    private final List<String> dislikedStories;
    private final List<String> preferredTags;

    private EditUserRequest(String userId, String userName, String email, String bio, int age,
                            List<String> follows, List<String> followers, List<String> favorites, int userScore,
                            List<String> storiesWritten, String featured, List<String> dislikedStories, List<String> preferredTags) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.bio = bio;
        this.age = age;
        this.follows = new ArrayList<>(follows);
        this.followers = new ArrayList<>(followers);
        this.favorites = new ArrayList<>(favorites);
        this.userScore = userScore;
        this.storiesWritten = new ArrayList<>(storiesWritten);
        this.featured = featured;
        this.dislikedStories = new ArrayList<>(dislikedStories);
        this.preferredTags = new ArrayList<>(preferredTags);
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

    @JsonProperty("storiesWritten")
    public List<String> getStoriesWritten() {
        return storiesWritten;
    }

    @JsonProperty("featured")
    public String getFeatured() {
        return featured;
    }

    @JsonProperty("dislikedStories")
    public List<String> getDislikedStories() {
        return dislikedStories;
    }

    @JsonProperty("preferredTags")
    public List<String> getPreferredTags() {
        return preferredTags;
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
                ", storiesWritten='" + storiesWritten + '\'' +
                ", featured='" + featured + '\'' +
                ", dislikedStories='" + dislikedStories + '\'' +
                ", preferredTags='" + preferredTags + '\'' +
                '}';
    }

    //CHECKSTYLE:OFF:Builder
    public static Builder builder() {
        return new Builder();
    }

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
        private List<String> storiesWritten;
        private String featured;
        private List<String> dislikedStories;
        private List<String> preferredTags;

        public Builder withUserId(String userId) {
            this.userId = userId;
            return this;
        }

        public Builder withUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder withEmail(String email) {
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
            this.follows = follows;
            return this;
        }

        public Builder withFollowers(List<String> followers) {
            this.followers = followers;
            return this;
        }

        public Builder withFavorites(List<String> favorites) {
            this.favorites = favorites;
            return this;
        }

        public Builder withUserScore(int userScore) {
            this.userScore = userScore;
            return this;
        }

        public Builder withStoriesWritten(List<String> storiesWritten) {
            this.storiesWritten = storiesWritten;
            return this;
        }

        public Builder withFeatured(String featured) {
            this.featured = featured;
            return this;
        }

        public Builder withDislikedStories(List<String> dislikedStories) {
            this.dislikedStories = dislikedStories;
            return this;
        }

        public Builder withPreferredTags(List<String> preferredTags) {
            this.preferredTags = preferredTags;
            return this;
        }

        public EditUserRequest build() {
            return new EditUserRequest(userId, userName, email, bio, age, follows, followers, favorites, userScore,
                    storiesWritten, featured, dislikedStories, preferredTags);
        }
    }

}