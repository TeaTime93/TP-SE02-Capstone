package SE02.Capstone.models;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserModel {

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

    private UserModel() {
    }

    public UserModel(String userId, String userName, String email, String bio, int age, List<String> follows,
                     List<String> followers, List<String> favorites, int userScore, List<String> storiesWritten,
                     String featured, List<String> dislikedStories, List<String> preferredTags) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.bio = bio;
        this.age = age;
        this.follows = follows != null ? new ArrayList<>(follows) : null;
        this.followers = followers != null ? new ArrayList<>(followers) : null;
        this.favorites = favorites != null ? new ArrayList<>(favorites) : null;
        this.userScore = userScore;
        this.storiesWritten = storiesWritten != null ? new ArrayList<>(storiesWritten) : null;
        this.featured = featured;
        this.dislikedStories = dislikedStories != null ? new ArrayList<>(dislikedStories) : null;
        this.preferredTags = preferredTags != null ? new ArrayList<>(preferredTags) : null;
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

    public List<String> getStoriesWritten() {
        return storiesWritten;
    }

    public String getFeatured() {
        return featured;
    }

    public List<String> getDislikedStories() {
        return dislikedStories;
    }

    public List<String> getPreferredTags() {
        return preferredTags;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserModel userModel = (UserModel) o;
        return userId == userModel.userId &&
                userName.equals(userModel.userName) &&
                email.equals(userModel.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, email);
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

        public UserModel build() {
            return new UserModel(userId, userName, email, bio, age, follows, followers, favorites, userScore,
                    storiesWritten, featured, dislikedStories, preferredTags);
        }
    }
}


