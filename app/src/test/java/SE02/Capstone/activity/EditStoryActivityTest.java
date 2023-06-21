package SE02.Capstone.activity;

import SE02.Capstone.activity.request.EditStoryRequest;
import SE02.Capstone.activity.result.EditStoryResult;
import SE02.Capstone.dynamodb.StoryDao;
import SE02.Capstone.dynamodb.models.Story;
import SE02.Capstone.metrics.MetricsPublisher;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class EditStoryActivityTest {

    @Mock
    private StoryDao storyDao;

    @Mock
    private MetricsPublisher metricsPublisher;

    private EditStoryActivity editStoryActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        editStoryActivity = new EditStoryActivity(storyDao, metricsPublisher);
    }

    @Test
    void handleRequest_withValidRequest_editsAndSavesStoryWithProperties() {
        // GIVEN
        String expectedStoryId = "story123";
        String expectedUserId = "user123";
        String expectedTitle = "New Title";
        String expectedContent = "New Content";
        String expectedSnippet = "New Snippet";
        List<String> expectedTags = List.of("NewTag1", "NewTag2");
        int expectedLikes = 20;
        int expectedDislikes = 4;
        int expectedHooks = 3;

        EditStoryRequest request = EditStoryRequest.builder()
                .withStoryId(expectedStoryId)
                .withUserId(expectedUserId)
                .withTitle(expectedTitle)
                .withContent(expectedContent)
                .withSnippet(expectedSnippet)
                .withTags(expectedTags)
                .withLikes(expectedLikes)
                .withDislikes(expectedDislikes)
                .withHooks(expectedHooks)
                .build();

        Story existingStory = new Story(); // Assuming Story has a default constructor
        existingStory.setStoryId(expectedStoryId);
        existingStory.setUserId(expectedUserId);
        existingStory.setTitle("Old Title");
        existingStory.setContent("Old Content");
        existingStory.setSnippet("Old Snippet");
        existingStory.setTags(List.of("OldTag1", "OldTag2"));
        existingStory.setLikes(10);
        existingStory.setDislikes(2);
        existingStory.setHooks(1);

        when(storyDao.getStory(expectedStoryId)).thenReturn(existingStory);
        when(storyDao.saveStory(existingStory)).thenReturn(existingStory);

        // WHEN
        EditStoryResult result = editStoryActivity.handleRequest(request);

        // THEN
        assertEquals(expectedTitle, result.getStoryModel().getTitle());
        assertEquals(expectedUserId, result.getStoryModel().getUserId());
        assertEquals(expectedContent, result.getStoryModel().getContent());
        assertEquals(expectedSnippet, result.getStoryModel().getSnippet());
        assertEquals(expectedTags, result.getStoryModel().getTags());
        assertEquals(expectedLikes, result.getStoryModel().getLikes());
        assertEquals(expectedDislikes, result.getStoryModel().getDislikes());
        assertEquals(expectedHooks, result.getStoryModel().getHooks());
    }
}

