package SE02.Capstone.activity;

import SE02.Capstone.activity.request.CreateCommentsRequest;
import SE02.Capstone.activity.result.CreateCommentsResult;
import SE02.Capstone.converters.ModelConverter;
import SE02.Capstone.dynamodb.CommentsDao;
import SE02.Capstone.dynamodb.models.Comments;
import SE02.Capstone.models.CommentsModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

public class CreateCommentsActivity {
    private final Logger log = LogManager.getLogger();
    private final CommentsDao commentsDao;


    @Inject
    public CreateCommentsActivity(CommentsDao commentsDao) {
        this.commentsDao = commentsDao;
    }

    public CreateCommentsResult handleRequest(final CreateCommentsRequest createCommentsRequest) {
        log.info("Received CommentsRequest {}", createCommentsRequest);

        Comments newComments = new Comments();
        newComments.setStoryId(createCommentsRequest.getStoryId());
        newComments.setPosComments(createCommentsRequest.getPosComments());
        newComments.setNegComments(createCommentsRequest.getNegComments());

        commentsDao.saveComments(newComments);

        CommentsModel commentsModel = new ModelConverter().toCommentsModel(newComments);
        return CreateCommentsResult.builder()
                .withComments(commentsModel)
                .build();
    }
}