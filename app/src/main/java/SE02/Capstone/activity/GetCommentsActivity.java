package SE02.Capstone.activity;

import SE02.Capstone.activity.request.GetCommentsRequest;
import SE02.Capstone.activity.result.GetCommentsResult;
import SE02.Capstone.converters.ModelConverter;
import SE02.Capstone.dynamodb.CommentsDao;
import SE02.Capstone.dynamodb.models.Comments;
import SE02.Capstone.exceptions.UserNotFoundException;
import SE02.Capstone.models.CommentsModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class GetCommentsActivity {
    private final Logger log = LogManager.getLogger();
    private final CommentsDao commentsDao;


    @Inject
    public GetCommentsActivity(CommentsDao commentsDao) {
        this.commentsDao = commentsDao;
    }

    public GetCommentsResult handleRequest(final GetCommentsRequest getCommentsRequest) {
        log.info("Received CommentsRequest {}", getCommentsRequest);
        String requestId = getCommentsRequest.getStoryId();
        Comments comments = commentsDao.getComments(requestId);
        if (comments == null){
            throw new UserNotFoundException("Coming from the activity");
        }
        CommentsModel commentsModel = new ModelConverter().toCommentsModel(comments);
        return GetCommentsResult.builder()
                .withComments(commentsModel)
                .build();
    }
}
