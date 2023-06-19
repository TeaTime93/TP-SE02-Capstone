package SE02.Capstone.lambda.endpoints;

import SE02.Capstone.activity.request.EditCommentsRequest;
import SE02.Capstone.activity.result.EditCommentsResult;
import SE02.Capstone.lambda.AuthenticatedLambdaRequest;
import SE02.Capstone.lambda.LambdaActivityRunner;
import SE02.Capstone.lambda.LambdaResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EditCommentsLambda
        extends LambdaActivityRunner<EditCommentsRequest, EditCommentsResult>
        implements RequestHandler<AuthenticatedLambdaRequest<EditCommentsRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<EditCommentsRequest> input, Context context) {
        log.info("entered lambda for update");
        return super.runActivity(
                () -> {
                    EditCommentsRequest authenticatedRequest = input.fromBody(EditCommentsRequest.class);
                    return input.fromUserClaims(claims -> {
                        return   EditCommentsRequest.builder()
                                .withStoryId(authenticatedRequest.getStoryId())
                                .withPosComments(authenticatedRequest.getPosComments())
                                .withNegComments(authenticatedRequest.getNegComments())
                                .build();}

                    );

                },
                (request, serviceComponent) ->
                        serviceComponent.provideEditCommentsActivity().handleRequest(request)
        );
    }
}