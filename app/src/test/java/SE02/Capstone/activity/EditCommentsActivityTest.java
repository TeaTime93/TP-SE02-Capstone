package SE02.Capstone.activity;

import SE02.Capstone.dynamodb.CommentsDao;
import SE02.Capstone.dynamodb.models.Comments;
import SE02.Capstone.models.CommentsModel;
import SE02.Capstone.exceptions.UserNotFoundException;
import SE02.Capstone.activity.request.EditCommentsRequest;
import SE02.Capstone.activity.result.EditCommentsResult;
import SE02.Capstone.converters.ModelConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EditCommentsActivityTest {

    @Mock
    private CommentsDao commentsDao;

    @InjectMocks
    private EditCommentsActivity editCommentsActivity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleRequest_withValidRequest_updatesComments() {
        // GIVEN
        String expectedStoryId = "story123";
        Comments existingComments = new Comments();
        existingComments.setStoryId(expectedStoryId);
        existingComments.setPosComments(Arrays.asList("positive comment 1"));
        existingComments.setNegComments(Arrays.asList("negative comment 1"));

        EditCommentsRequest request = EditCommentsRequest.builder()
                .withStoryId(expectedStoryId)
                .withPosComments(Arrays.asList("positive comment 2"))
                .withNegComments(Arrays.asList("negative comment 2"))
                .build();

        when(commentsDao.getComments(expectedStoryId)).thenReturn(existingComments);
        when(commentsDao.saveComments(any(Comments.class))).thenReturn(existingComments);

        // WHEN
        EditCommentsResult result = editCommentsActivity.handleRequest(request);

        // THEN
        CommentsModel expectedCommentsModel = new ModelConverter().toCommentsModel(existingComments);
        assertEquals(expectedCommentsModel, result.getCommentsModel());
    }

    @Test
    void handleRequest_withInvalidRequest_throwsException() {
        // GIVEN
        String expectedStoryId = "story123";

        EditCommentsRequest request = EditCommentsRequest.builder()
                .withStoryId(expectedStoryId)
                .withPosComments(Arrays.asList("positive comment 1"))
                .withNegComments(Arrays.asList("negative comment 1"))
                .build();

        when(commentsDao.getComments(expectedStoryId)).thenReturn(null);

        // THEN
        assertThrows(UserNotFoundException.class, () -> {
            // WHEN
            editCommentsActivity.handleRequest(request);
        });
    }
}
