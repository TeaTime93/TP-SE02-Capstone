package SE02.Capstone.models;

import java.util.List;
import java.util.Objects;

public class UserModel {

    private final String userId;
    private final String userName;
    private final String email;
    private final String bio;
    private final int age;
    private List<String> follows;
    private List<String> favorites;

    private UserModel(String userId, String userName, String email, String bio, int age) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.bio = bio;
        this.age = age;
    }

    public String getUserID() {
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
        private List<String> favorites;

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

        public Builder withFavorites(List<String> favorites) {
            this.favorites = favorites;
            return this;
        }

        public UserModel build() {
            return new UserModel(userId, userName, email, bio, age);
        }
    }
}

