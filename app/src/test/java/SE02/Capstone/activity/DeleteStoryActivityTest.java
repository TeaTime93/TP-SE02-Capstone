package SE02.Capstone.activity;

import SE02.Capstone.activity.request.DeleteStoryRequest;
import SE02.Capstone.activity.result.DeleteStoryResult;
import SE02.Capstone.converters.ModelConverter;
import SE02.Capstone.dynamodb.StoryDao;
import SE02.Capstone.dynamodb.models.Story;
import SE02.Capstone.exceptions.UserNotFoundException;
import SE02.Capstone.models.StoryModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteStoryActivityTest {

    @Mock
    private StoryDao storyDao;

    @InjectMocks
    private DeleteStoryActivity deleteStoryActivity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleRequest_withValidRequest_returnsStory() {
        // GIVEN
        String expectedStoryId = "story123";
        String expectedUserId = "user123";
        String expectedContent = "Story Content";
        String expectedSnippet = "Story Snippet";
        String expectedTitle = "Story Title";
        List<String> expectedTags = List.of("Tag1", "Tag2");
        int expectedLikes = 10;
        int expectedDislikes = 2;
        int expectedHooks = 2;

        Story expectedStory = new Story();
        expectedStory.setStoryId(expectedStoryId);
        expectedStory.setUserId(expectedUserId);
        expectedStory.setContent(expectedContent);
        expectedStory.setSnippet(expectedSnippet);
        expectedStory.setTitle(expectedTitle);
        expectedStory.setTags(expectedTags);
        expectedStory.setLikes(expectedLikes);
        expectedStory.setDislikes(expectedDislikes);
        expectedStory.setHooks(expectedHooks);
        StoryModel expectedStoryModel = new ModelConverter().toStoryModel(expectedStory);

        DeleteStoryRequest request = DeleteStoryRequest.builder()
                .withStoryId(expectedStoryId)
                .build();

        when(storyDao.deleteStory(expectedStoryId)).thenReturn(expectedStory);

        // WHEN
        DeleteStoryResult result = deleteStoryActivity.handleRequest(request);

        // THEN
        assertEquals(expectedStoryModel, result.getStory());
    }

    @Test
    void handleRequest_withInvalidRequest_throwsException() {
        // GIVEN
        String expectedStoryId = "story123";

        DeleteStoryRequest request = DeleteStoryRequest.builder()
                .withStoryId(expectedStoryId)
                .build();

        when(storyDao.deleteStory(expectedStoryId)).thenReturn(null);

        // THEN
        assertThrows(UserNotFoundException.class, () -> {
            // WHEN
            deleteStoryActivity.handleRequest(request);
        });
    }
}
