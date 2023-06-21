package SE02.Capstone.dynamodb;

import SE02.Capstone.dynamodb.models.Comments;
import SE02.Capstone.exceptions.UserNotFoundException;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.junit.jupiter.api.Assertions.*;

class CommentsDaoTest {
    @Mock
    private DynamoDBMapper dynamoDbMapper;

    private CommentsDao commentsDao;

    @BeforeEach
    void setUp() {
        initMocks(this);
        commentsDao = new CommentsDao(dynamoDbMapper);

        Comments comments1 = new Comments();
        comments1.setStoryId("story1");
        comments1.setPosComments(Arrays.asList("Nice story!", "Love it!"));
        comments1.setNegComments(Arrays.asList("Didn't like it", "Not my taste"));

        when(dynamoDbMapper.load(Comments.class, "story1")).thenReturn(comments1);
    }

    @Test
    void getComments_shouldReturnCorrectComments() {
        Comments retrievedComments = commentsDao.getComments("story1");
        assertEquals("story1", retrievedComments.getStoryId());
        assertEquals(2, retrievedComments.getPosComments().size());
        assertEquals(2, retrievedComments.getNegComments().size());
    }

    @Test
    void getComments_shouldThrowException_whenCommentsDoNotExist() {
        assertThrows(UserNotFoundException.class, () -> commentsDao.getComments("nonexistent"));
    }

    @Test
    void saveComments_shouldSaveCommentsCorrectly() {
        Comments newComments = new Comments();
        newComments.setStoryId("story2");
        newComments.setPosComments(Arrays.asList("Great story!", "Keep it up!"));
        newComments.setNegComments(Arrays.asList("Could be better", "It's okay"));

        Comments savedComments = commentsDao.saveComments(newComments);

        assertEquals(newComments, savedComments);
        verify(dynamoDbMapper).save(newComments);
    }
}
