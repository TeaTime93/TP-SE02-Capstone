package SE02.Capstone.dynamodb;

import SE02.Capstone.dynamodb.models.Feed;
import SE02.Capstone.dynamodb.models.Story;
import SE02.Capstone.dynamodb.models.User;
import SE02.Capstone.exceptions.UserNotFoundException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class FeedGeneratorTest {
    @Mock
    private DynamoDBMapper mapper;
    @Mock
    private StoryDao storyDao;
    @Mock
    private UserDao userDao;
    private FeedGenerator feedGenerator;

    private User user;
    @Mock
    private PaginatedScanList<Story> paginatedScanListStories;
    private List<Story> stories;
    private Feed feed;

    @BeforeEach
    void setUp() {
        initMocks(this);
        feedGenerator = new FeedGenerator(storyDao, userDao);


        user = new User();
        user.setUserId("user1");
        user.setUserName("John Doe");
        user.setEmail("johndoe@example.com");
        user.setBio("Just an example bio");
        user.setAge(30);
        user.setFollows(Arrays.asList("user2", "user3"));
        user.setFollowers(Arrays.asList("user4", "user5"));
        user.setFavorites(Arrays.asList("story1", "story2"));
        user.setUserScore(100);
        user.setStoriesWritten(Arrays.asList("story3", "story4"));
        user.setFeatured("story5");
        user.setDislikedStories(Arrays.asList("story6", "story7"));
        user.setPreferredTags(Arrays.asList("tag1", "tag2"));

        Story story1 = new Story();
        story1.setStoryId("story1");
        story1.setUserId("user1");
        story1.setTitle("Title 1");
        story1.setContent("This is the content for story 1");
        story1.setSnippet("This is the snippet for story 1");
        story1.setTags(Arrays.asList("tag1", "tag2"));
        story1.setLikes(100);
        story1.setDislikes(50);
        story1.setHooks(200);

        Story story2 = new Story();
        story2.setStoryId("story2");
        story2.setUserId("user1");
        story2.setTitle("Title 2");
        story2.setContent("This is the content for story 2");
        story2.setSnippet("This is the snippet for story 2");
        story2.setTags(Arrays.asList("tag1", "tag3"));
        story2.setLikes(120);
        story2.setDislikes(30);
        story2.setHooks(210);

        Story story3 = new Story();
        story3.setStoryId("story3");
        story3.setUserId("user2");
        story3.setTitle("Title 3");
        story3.setContent("This is the content for story 3");
        story3.setSnippet("This is the snippet for story 3");
        story3.setTags(Arrays.asList("tag2", "tag3"));
        story3.setLikes(130);
        story3.setDislikes(20);
        story3.setHooks(220);

        Story story4 = new Story();
        story4.setStoryId("story4");
        story4.setUserId("user2");
        story4.setTitle("Title 4");
        story4.setContent("This is the content for story 4");
        story4.setSnippet("This is the snippet for story 4");
        story4.setTags(Arrays.asList("tag2", "tag3"));
        story4.setLikes(140);
        story4.setDislikes(20);
        story4.setHooks(230);

        List<String> storyIds = new ArrayList<>();
        stories = Arrays.asList(story1, story2, story3, story4);
        storyIds.add(story1.getStoryId());

        Story[] storiesArray = {story1, story2, story3, story4};

        when(paginatedScanListStories.toArray()).thenReturn(storiesArray);

        feed = new Feed(storyIds);
    }

    @Test
    void testGenerate_shouldPopulateFeedWithTopTwentyStories() {
        // Given
        String userId = "user1";

        when(mapper.scan(eq(Story.class), any(DynamoDBScanExpression.class))).thenReturn(paginatedScanListStories);
        when(storyDao.getAllStories()).thenReturn(stories);
        when(userDao.getUser("user1")).thenReturn(user);

        // When
        feedGenerator.generate(userId, feed);

        // Then
        assertNotNull(feed.getStories());
        assertTrue(feed.getStories().size() <= 20);
        verify(storyDao).getAllStories();
        verify(userDao, atLeastOnce()).getUser(userId);
    }

    @Test
    void testGenerate_shouldThrowUserNotFoundException_whenUserDoesNotExist() {
        // Given
        String userId = "nonexistentUser";

        // When userDao is asked to retrieve a user who doesn't exist, it throws a UserNotFoundException
        when(userDao.getUser(userId)).thenThrow(new UserNotFoundException("User does not exist."));

        // Then
        assertThrows(UserNotFoundException.class, () -> {
            // When
            feedGenerator.generate(userId, feed);
        });
    }
}
