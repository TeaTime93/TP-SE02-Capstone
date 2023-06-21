package SE02.Capstone.activity;

import static org.junit.jupiter.api.Assertions.*;

import SE02.Capstone.activity.request.CreateCommentsRequest;
import SE02.Capstone.activity.result.CreateCommentsResult;
import SE02.Capstone.dynamodb.CommentsDao;
import SE02.Capstone.dynamodb.models.Comments;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class CreateCommentsActivityTest {

    @Mock
    private CommentsDao commentsDao;

    private CreateCommentsActivity createCommentsActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        createCommentsActivity = new CreateCommentsActivity(commentsDao);
    }

    @Test
    public void handleRequest_withComments_createsAndSavesPlaylistWithComments() {
        // GIVEN
        String expectedStoryId = "123";
        List<String> expectedPosComments = List.of("expectedPosComment");
        List<String> expectedNegComments = List.of("expectedNegComment");

        CreateCommentsRequest request = CreateCommentsRequest.builder()
                .withStoryId(expectedStoryId)
                .withPosComments(expectedPosComments)
                .withNegComments(expectedNegComments)
                .build();

        // WHEN
        CreateCommentsResult result = createCommentsActivity.handleRequest(request);

        // THEN
        verify(commentsDao).saveComments(any(Comments.class));

        assertNotNull(result.getComments().getStoryId());
        assertEquals(expectedStoryId, result.getComments().getStoryId());
        assertEquals(expectedPosComments, result.getComments().getPosComments());
        assertEquals(expectedNegComments, result.getComments().getNegComments());
    }
}
