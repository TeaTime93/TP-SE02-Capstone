package SE02.Capstone.dynamodb;

import SE02.Capstone.dynamodb.models.Story;
import SE02.Capstone.exceptions.UserNotFoundException;
import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Story story = dynamoDbMapper.load(Story.class, storyId);
        if (null == story) {
            throw new NullPointerException();
        }
        return story;
    }

    public List<Story> getAllStories() {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
        PaginatedScanList<Story> scanResult = dynamoDbMapper.scan(Story.class, scanExpression);
        return new ArrayList<>(scanResult);
    }

    public Story saveStory(Story newStory) {
        this.dynamoDbMapper.save(newStory);
        return newStory;
    }

    public Story deleteStory(String storyId) {
        Story story = dynamoDbMapper.load(Story.class, storyId);
        if (null == story) {
            throw new NullPointerException();
        }
        dynamoDbMapper.delete(story);
        return story;
    }

//    public List<Story> getStoriesByTitleAndAuthor(String title, String userId) {
//        DynamoDBQueryExpression<Story> queryExpression = new DynamoDBQueryExpression<Story>()
//                .withIndexName("TitleAndAuthorIndex")
//                .withKeyConditionExpression("title = :title and userId = :userId")
//                .withExpressionAttributeValues(Map.of(":title", new AttributeValue().withS(title),
//                        ":userId", new AttributeValue().withS(userId)));
//
//        PaginatedQueryList<Story> storyList = dynamoDbMapper.query(Story.class, queryExpression);
//        return new ArrayList<>(storyList);
//    }



//    public List<Story> getStoriesByTitleAndAuthor(String title, String userId) {
//        Map<String, AttributeValue> valueMap = new HashMap<>();
//        valueMap.put(":title", new AttributeValue().withS(title));
//        valueMap.put(":userId", new AttributeValue().withS(userId));
//
//        DynamoDBQueryExpression<Story> queryExpression = new DynamoDBQueryExpression<Story>()
//                .withKeyConditionExpression("title = :title and userId = :userId")
//                .withExpressionAttributeValues(valueMap);
//
//        PaginatedQueryList<Story> storyList = dynamoDbMapper.query(Story.class, queryExpression);
//        return new ArrayList<>(storyList);
//    }

    public Story getStoryByTitleAndAuthor(String title, String userId) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":val1", new AttributeValue().withS(title));
        eav.put(":val2", new AttributeValue().withS(userId));

        DynamoDBQueryExpression<Story> queryExpression = new DynamoDBQueryExpression<Story>()
                .withIndexName("TitleAndAuthorIndex")
                .withKeyConditionExpression("title = :val1 and userId = :val2")
                .withExpressionAttributeValues(eav)
                .withConsistentRead(false);


        PaginatedQueryList<Story> queryResult = dynamoDbMapper.query(Story.class, queryExpression);

        if (queryResult.isEmpty()) {
            throw new UserNotFoundException(
                    String.format("Could not find story with title '%s' and userId '%s'", title, userId));
        }

        return queryResult.get(0);
    }

//    public Story getStoryByTitleAndAuthor(String title, String userId){
//        Story story = dynamoDbMapper.load(Story.class, title, userId);
//        if(character == null){
//            throw new UserNotFoundException();
//        }
//        return story;
//    }


}

