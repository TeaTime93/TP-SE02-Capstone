package SE02.Capstone.activity;

import static org.junit.jupiter.api.Assertions.*;

import SE02.Capstone.activity.request.CreateStoryRequest;
import SE02.Capstone.activity.result.CreateStoryResult;
import SE02.Capstone.dynamodb.StoryDao;
import SE02.Capstone.dynamodb.models.Story;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class CreateStoryActivityTest {

    @Mock
    private StoryDao storyDao;

    private CreateStoryActivity createStoryActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        createStoryActivity = new CreateStoryActivity(storyDao);
    }

    @Test
    public void handleRequest_withStory_createsAndSavesStoryWithProperties() {
        // GIVEN
        String expectedUserId = "user123";
        String expectedContent = "Story Content";
        String expectedSnippet = "Story Snippet";
        String expectedTitle = "Story Title";
        List<String> expectedTags = List.of("Tag1", "Tag2");
        int expectedLikes = 10;
        int expectedDislikes = 2;
        int expectedHooks = 2;

        CreateStoryRequest request = CreateStoryRequest.builder()
                .withUserId(expectedUserId)
                .withContent(expectedContent)
                .withSnippet(expectedSnippet)
                .withTitle(expectedTitle)
                .withTags(expectedTags)
                .withLikes(expectedLikes)
                .withDislikes(expectedDislikes)
                .withHooks(expectedHooks)
                .build();

        // WHEN
        CreateStoryResult result = createStoryActivity.handleRequest(request);

        // THEN
        verify(storyDao).saveStory(any(Story.class));

        assertNotNull(result.getStory().getStoryId()); // Story ID should be generated
        assertEquals(expectedUserId, result.getStory().getUserId());
        assertEquals(expectedContent, result.getStory().getContent());
        assertEquals(expectedSnippet, result.getStory().getSnippet());
        assertEquals(expectedTitle, result.getStory().getTitle());
        assertEquals(expectedTags, result.getStory().getTags());
        assertEquals(expectedLikes, result.getStory().getLikes());
        assertEquals(expectedDislikes, result.getStory().getDislikes());
        assertEquals(expectedHooks, result.getStory().getHooks());
    }
}
