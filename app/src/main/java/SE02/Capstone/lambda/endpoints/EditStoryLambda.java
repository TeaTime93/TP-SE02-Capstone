package SE02.Capstone.lambda.endpoints;

import SE02.Capstone.activity.request.EditStoryRequest;
import SE02.Capstone.activity.result.EditStoryResult;
import SE02.Capstone.lambda.AuthenticatedLambdaRequest;
import SE02.Capstone.lambda.LambdaActivityRunner;
import SE02.Capstone.lambda.LambdaResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EditStoryLambda
        extends LambdaActivityRunner<EditStoryRequest, EditStoryResult>
        implements RequestHandler<AuthenticatedLambdaRequest<EditStoryRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<EditStoryRequest> input, Context context) {
        log.info("entered lambda for update");
        return super.runActivity(
                () -> {
                    EditStoryRequest authenticatedRequest = input.fromBody(EditStoryRequest.class);
                    return input.fromUserClaims(claims -> {
                        return   EditStoryRequest.builder()
                                .withStoryId(authenticatedRequest.getStoryId())
                                .withUserId(authenticatedRequest.getUserId())
                                .withTitle(authenticatedRequest.getTitle())
                                .withContent(authenticatedRequest.getContent())
                                .withSnippet(authenticatedRequest.getSnippet())
                                .withTags(authenticatedRequest.getTags())
                                .withLikes(authenticatedRequest.getLikes())
                                .withDislikes(authenticatedRequest.getDislikes())
                                .withHooks(authenticatedRequest.getHooks())
                                .build();}

                    );

                },
                (request, serviceComponent) ->
                        serviceComponent.provideEditStoryActivity().handleRequest(request)
        );
    }
}