package SE02.Capstone.lambda.endpoints;

import SE02.Capstone.activity.request.GetCommentsRequest;
import SE02.Capstone.activity.result.GetCommentsResult;
import SE02.Capstone.lambda.LambdaActivityRunner;
import SE02.Capstone.lambda.LambdaRequest;
import SE02.Capstone.lambda.LambdaResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetCommentsLambda extends LambdaActivityRunner<GetCommentsRequest, GetCommentsResult>
        implements RequestHandler<LambdaRequest<GetCommentsRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetCommentsRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
                () -> input.fromPath(path ->
                        GetCommentsRequest.builder()
                                .withStoryId(path.get("storyId"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideGetCommentsActivity().handleRequest(request)
        );
    }
}