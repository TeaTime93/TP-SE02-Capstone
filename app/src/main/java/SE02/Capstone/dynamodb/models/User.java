package SE02.Capstone.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.List;
import java.util.Objects;

@DynamoDBTable(tableName = "users")
public class User {

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

    @DynamoDBHashKey(attributeName = "userId")
    public String getUserId() {
        return userId;
    }
    @DynamoDBAttribute(attributeName = "userName")
    public String getUserName() {
        return userName;
    }
    @DynamoDBAttribute(attributeName = "email")
    public String getEmail() {
        return email;
    }
    @DynamoDBAttribute(attributeName = "bio")
    public String getBio() {
        return bio;
    }
    @DynamoDBAttribute(attributeName = "age")
    public int getAge() {
        return age;
    }
    @DynamoDBAttribute(attributeName = "follows")
    public List<String> getFollows() {
        return follows;
    }
    @DynamoDBAttribute(attributeName = "favorites")
    public List<String> getFavorites() {
        return favorites;
    }
    @DynamoDBAttribute(attributeName = "followers")
    public List<String> getFollowers() {
        return followers;
    }
    @DynamoDBAttribute(attributeName = "userScore")
    public int getUserScore() {
        return userScore;
    }
    @DynamoDBAttribute(attributeName = "storiesWritten")
    public List<String> getStoriesWritten() {
        return storiesWritten;
    }
    @DynamoDBAttribute(attributeName = "featured")
    public String getFeatured() {
        return featured;
    }
    @DynamoDBAttribute(attributeName = "dislikedStories")
    public List<String> getDislikedStories() { return dislikedStories; }
    @DynamoDBAttribute(attributeName = "preferredTags")
    public List<String> getPreferredTags() { return preferredTags; }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User userModel = (User) o;
        return userId == userModel.userId &&
                userName.equals(userModel.userName) &&
                email.equals(userModel.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, email);
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setFollows(List<String> follows) {
        this.follows = follows;
    }

    public void setFavorites(List<String> favorites) {
        this.favorites = favorites;
    }
    public void setStoriesWritten(List<String> storiesWritten) {
        this.storiesWritten = storiesWritten;
    }
    public void setFeatured(String featured) {
        this.featured = featured;
    }

    public void setFollowers(List<String> followers) {
        this.followers = followers;
    }

    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }

    public void setDislikedStories(List<String> dislikedStories) { this.dislikedStories = dislikedStories; }
    public void setPreferredTags(List<String> preferredTags) { this.preferredTags = preferredTags; }
}
