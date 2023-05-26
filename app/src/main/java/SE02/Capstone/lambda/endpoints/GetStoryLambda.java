package SE02.Capstone.lambda.endpoints;

import SE02.Capstone.activity.request.GetStoryRequest;
import SE02.Capstone.activity.result.GetStoryResult;
import SE02.Capstone.lambda.LambdaActivityRunner;
import SE02.Capstone.lambda.LambdaRequest;
import SE02.Capstone.lambda.LambdaResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetStoryLambda extends LambdaActivityRunner<GetStoryRequest, GetStoryResult>
        implements RequestHandler<LambdaRequest<GetStoryRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetStoryRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
                () -> input.fromPath(path ->
                        GetStoryRequest.builder()
                                .withStoryId(path.get("storyId"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideGetStoryActivity().handleRequest(request)
        );
    }
}
