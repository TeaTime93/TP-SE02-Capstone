package SE02.Capstone.dynamodb.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.List;
import java.util.Objects;
@DynamoDBTable(tableName = "comments")
public class Comments {
    private String storyId;
    private List<String> posComments;
    private List<String> negComments;

    @DynamoDBHashKey(attributeName = "storyId")
    public String getStoryId() {
        return storyId;
    }
    @DynamoDBAttribute(attributeName = "posComments")
    public List<String> getPosComments() {
        return posComments;
    }
    @DynamoDBAttribute(attributeName = "negComments")
    public List<String> getNegComments() {
        return negComments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comments commentsModel = (Comments) o;
        return storyId == commentsModel.storyId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(storyId);
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }

    public void setPosComments(List<String> posComments) {
        this.posComments = posComments;
    }

    public void setNegComments(List<String> negComments) {
        this.negComments = negComments;
    }
}
