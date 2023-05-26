package SE02.Capstone.lambda.endpoints;

import SE02.Capstone.activity.request.GenerateFeedRequest;
import SE02.Capstone.activity.result.GenerateFeedResult;
import SE02.Capstone.lambda.LambdaActivityRunner;
import SE02.Capstone.lambda.LambdaRequest;
import SE02.Capstone.lambda.LambdaResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GenerateFeedLambda extends LambdaActivityRunner<GenerateFeedRequest, GenerateFeedResult>
        implements RequestHandler<LambdaRequest<GenerateFeedRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GenerateFeedRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
                () -> input.fromPath(path ->
                        GenerateFeedRequest.builder()
                                .withUserId(path.get("userId"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideGenerateFeedActivity().handleRequest(request)
        );
    }
}

