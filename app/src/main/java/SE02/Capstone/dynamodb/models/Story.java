package SE02.Capstone.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.List;
import java.util.Objects;
@DynamoDBTable(tableName = "stories")
public class Story {
    private String storyId;
    private String userId;
    private String title;
    private String content;
    private String snippet;
    private List<String> tags;
    private int likes;

    @DynamoDBHashKey(attributeName = "storyId")
    public String getStoryId() {
        return storyId;
    }
    @DynamoDBAttribute(attributeName = "userId")
    public String getUserId() {
        return userId;
    }
    @DynamoDBAttribute(attributeName = "title")
    public String getTitle() {
        return title;
    }
    @DynamoDBAttribute(attributeName = "content")
    public String getContent() {
        return content;
    }
    @DynamoDBAttribute(attributeName = "snippet")
    public String getSnippet() {
        return snippet;
    }
    @DynamoDBAttribute(attributeName = "tags")
    public List<String> getTags() {
        return tags;
    }
    @DynamoDBAttribute(attributeName =  "likes")
    public int getLikes() { return likes; }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Story storyModel = (Story) o;
        return storyId == storyModel.storyId &&
                userId.equals(storyModel.userId) &&
                title.equals(storyModel.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storyId, userId, title);
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
