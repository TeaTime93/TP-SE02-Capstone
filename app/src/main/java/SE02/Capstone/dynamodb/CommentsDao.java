package SE02.Capstone.dynamodb;

import SE02.Capstone.dynamodb.models.Comments;
import SE02.Capstone.exceptions.UserNotFoundException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import javax.inject.Inject;

public class CommentsDao {
    private final DynamoDBMapper dynamoDbMapper;

    @Inject
    public CommentsDao(DynamoDBMapper dynamoDbMapper) {
        this.dynamoDbMapper = dynamoDbMapper;
    }

    public Comments getComments(String storyId) {
        Comments comments = this.dynamoDbMapper.load(Comments.class, storyId);
        System.out.println(comments);

        if (comments == null) {
            throw new UserNotFoundException("CommentsDao: Could not find user with id " + storyId);
        }
        return comments;
    }


    public Comments saveComments(Comments newComments) {
        this.dynamoDbMapper.save(newComments);
        return newComments;
    }
}
