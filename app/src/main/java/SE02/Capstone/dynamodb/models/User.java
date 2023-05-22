package SE02.Capstone.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.List;
import java.util.Objects;

@DynamoDBTable(tableName = "users")
public class User {

    private String userID;
    private String userName;
    private String email;
    private String bio;
    private int age;
    private List<String> follows;
    private List<String> favorites;

    @DynamoDBHashKey(attributeName = "userId")
    public String getUserID() {
        return userID;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User userModel = (User) o;
        return userID == userModel.userID &&
                userName.equals(userModel.userName) &&
                email.equals(userModel.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, userName, email);
    }

    public void setUserID(String userID) {
        this.userID = userID;
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
}
