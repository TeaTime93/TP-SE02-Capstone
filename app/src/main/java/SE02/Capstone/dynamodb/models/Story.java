package SE02.Capstone.dynamodb.models;

import SE02.Capstone.models.StoryModel;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.List;
import java.util.Objects;
@DynamoDBTable(tableName = "stories")
public class Story {
    private String storyId;
    private String userID;
    private String title;
    private String content;
    private String snippet;
    private List<String> tags;

    private Story(String storyId, String userID, String title, String content, String snippet, List<String> tags) {
        this.storyId = storyId;
        this.userID = userID;
        this.title = title;
        this.content = content;
        this.snippet = snippet;
        this.tags = tags;
    }
    @DynamoDBHashKey(attributeName = "storyId")
    public String getStoryId() {
        return storyId;
    }
    @DynamoDBRangeKey(attributeName = "userId")
    public String getUserID() {
        return userID;
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
                userID.equals(storyModel.userID) &&
                title.equals(storyModel.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storyId, userID, title);
    }
}
