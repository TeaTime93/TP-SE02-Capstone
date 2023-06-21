package SE02.Capstone.dynamodb;

import SE02.Capstone.dynamodb.models.Story;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

class StoryDaoTest {
    @Mock
    private DynamoDBMapper mapper;

    @Mock
    private PaginatedScanList<Story> paginatedScanListStories;

    private StoryDao storyDao;

    @BeforeEach
    void setUp() {
        initMocks(this);
        storyDao = new StoryDao(mapper);

        Story story1 = new Story();
        story1.setStoryId("story1");
        story1.setUserId("user1");

        Story story2 = new Story();
        story2.setStoryId("story2");
        story2.setUserId("user2");

        Story[] storiesArray = {story1, story2};

        when(paginatedScanListStories.toArray()).thenReturn(storiesArray);
        when(mapper.scan(eq(Story.class), any(DynamoDBScanExpression.class))).thenReturn(paginatedScanListStories);
        when(mapper.load(Story.class, "story1")).thenReturn(story1);
        when(mapper.load(Story.class, "story2")).thenReturn(story2);
    }

    @Test
    void getStory_shouldReturnCorrectStory() {
        Story retrievedStory = storyDao.getStory("story1");
        assertEquals("story1", retrievedStory.getStoryId());
    }

    @Test
    void getStory_shouldThrowException_whenStoryDoesNotExist() {
        assertThrows(NullPointerException.class, () -> storyDao.getStory("nonexistent"));
    }

    @Test
    void getAllStories_shouldReturnAllStories() {
        List<Story> stories = storyDao.getAllStories();
        assertEquals(2, stories.size());
    }

    @Test
    void saveStory_shouldSaveStoryCorrectly() {
        Story newStory = new Story();
        newStory.setStoryId("story3");
        newStory.setUserId("user3");

        Story savedStory = storyDao.saveStory(newStory);

        assertEquals(newStory, savedStory);
        verify(mapper).save(newStory);
    }

    @Test
    void deleteStory_shouldDeleteStoryCorrectly() {
        Story deletedStory = storyDao.deleteStory("story1");

        assertNotNull(deletedStory);
        assertEquals("story1", deletedStory.getStoryId());
        verify(mapper).delete(deletedStory);
    }

    @Test
    void deleteStory_shouldThrowException_whenStoryDoesNotExist() {
        assertThrows(NullPointerException.class, () -> storyDao.deleteStory("nonexistent"));
    }
}

