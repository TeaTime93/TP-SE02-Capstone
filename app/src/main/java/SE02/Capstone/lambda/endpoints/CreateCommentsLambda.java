package SE02.Capstone.lambda.endpoints;

import SE02.Capstone.activity.request.CreateCommentsRequest;
import SE02.Capstone.activity.result.CreateCommentsResult;
import SE02.Capstone.lambda.LambdaActivityRunner;
import SE02.Capstone.lambda.LambdaRequest;
import SE02.Capstone.lambda.LambdaResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateCommentsLambda extends LambdaActivityRunner<CreateCommentsRequest, CreateCommentsResult>
        implements RequestHandler<LambdaRequest<CreateCommentsRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<CreateCommentsRequest>input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
                () -> {
                    CreateCommentsRequest arg = input.fromBody(CreateCommentsRequest.class);
                    return input.fromPath(claims ->
                            CreateCommentsRequest.builder()
                                    .withStoryId(arg.getStoryId())
                                    .withPosComments(arg.getPosComments())
                                    .withNegComments(arg.getNegComments())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideCreateCommentsActivity().handleRequest(request)
        );
    }
}