package SE02.Capstone.activity;

import SE02.Capstone.activity.request.GetStoryRequest;
import SE02.Capstone.activity.result.GetStoryResult;
import SE02.Capstone.dynamodb.StoryDao;
import SE02.Capstone.dynamodb.models.Story;
import SE02.Capstone.exceptions.UserNotFoundException;
import SE02.Capstone.models.StoryModel;
import SE02.Capstone.converters.ModelConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class GetStoryActivityTest {

    @Mock
    private StoryDao storyDao;

    private GetStoryActivity getStoryActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        getStoryActivity = new GetStoryActivity(storyDao);
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

        GetStoryRequest request = GetStoryRequest.builder()
                .withStoryId(expectedStoryId)
                .build();

        when(storyDao.getStory(expectedStoryId)).thenReturn(expectedStory);

        // WHEN
        GetStoryResult result = getStoryActivity.handleRequest(request);

        // THEN
        assertEquals(expectedStoryModel, result.getStory());
    }


    @Test
    void handleRequest_withInvalidStoryId_throwsUserNotFoundException() {
        // GIVEN
        String invalidStoryId = "invalidStory123";

        GetStoryRequest request = GetStoryRequest.builder()
                .withStoryId(invalidStoryId)
                .build();

        when(storyDao.getStory(invalidStoryId)).thenReturn(null);

        // WHEN + THEN
        assertThrows(UserNotFoundException.class, () -> getStoryActivity.handleRequest(request));
    }
}
