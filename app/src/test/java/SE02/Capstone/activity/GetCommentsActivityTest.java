package SE02.Capstone.activity;

import SE02.Capstone.activity.request.GetCommentsRequest;
import SE02.Capstone.activity.result.GetCommentsResult;
import SE02.Capstone.dynamodb.CommentsDao;
import SE02.Capstone.dynamodb.models.Comments;
import SE02.Capstone.exceptions.UserNotFoundException;
import SE02.Capstone.models.CommentsModel;
import SE02.Capstone.converters.ModelConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class GetCommentsActivityTest {

    @Mock
    private CommentsDao commentsDao;

    private GetCommentsActivity getCommentsActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        getCommentsActivity = new GetCommentsActivity(commentsDao);
    }

    @Test
    void handleRequest_withValidRequest_returnsComments() {
        // GIVEN
        String expectedStoryId = "story123";
        List<String> expectedPosComments = List.of("PosComment1", "PosComment2");
        List<String> expectedNegComments = List.of("NegComment1", "NegComment2");

        Comments expectedComments = new Comments();
        expectedComments.setStoryId(expectedStoryId);
        expectedComments.setPosComments(expectedPosComments);
        expectedComments.setNegComments(expectedNegComments);

        CommentsModel expectedCommentsModel = new ModelConverter().toCommentsModel(expectedComments);

        GetCommentsRequest request = GetCommentsRequest.builder()
                .withStoryId(expectedStoryId)
                .build();

        when(commentsDao.getComments(expectedStoryId)).thenReturn(expectedComments);

        // WHEN
        GetCommentsResult result = getCommentsActivity.handleRequest(request);

        // THEN
        assertEquals(expectedCommentsModel, result.getComments());
    }


    @Test
    void handleRequest_withInvalidStoryId_throwsUserNotFoundException() {
        // GIVEN
        String invalidStoryId = "invalidStory123";

        GetCommentsRequest request = GetCommentsRequest.builder()
                .withStoryId(invalidStoryId)
                .build();

        when(commentsDao.getComments(invalidStoryId)).thenReturn(null);

        // WHEN + THEN
        assertThrows(UserNotFoundException.class, () -> getCommentsActivity.handleRequest(request));
    }
}
