package SE02.Capstone.dynamodb;

import SE02.Capstone.dynamodb.models.Story;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class StoryDao {

    private final DynamoDBMapper dynamoDbMapper;

    /**
     * Instantiates an InventoryDao object.
     *
     * @param dynamoDbMapper the {@link DynamoDBMapper} used to interact with the album_track table
     */

    @Inject
    public StoryDao(DynamoDBMapper dynamoDbMapper) {
        this.dynamoDbMapper = dynamoDbMapper;
    }

    public Story getStory(String storyId) {
        Story user = dynamoDbMapper.load(Story.class, storyId);
        if (null == user) {
            throw new NullPointerException();
        }
        return user;
    }
}

