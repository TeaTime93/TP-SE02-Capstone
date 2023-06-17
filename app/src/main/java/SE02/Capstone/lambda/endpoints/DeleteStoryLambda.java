package SE02.Capstone.lambda.endpoints;

import SE02.Capstone.activity.request.DeleteStoryRequest;
import SE02.Capstone.activity.result.DeleteStoryResult;
import SE02.Capstone.lambda.LambdaActivityRunner;
import SE02.Capstone.lambda.LambdaRequest;
import SE02.Capstone.lambda.LambdaResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteStoryLambda extends LambdaActivityRunner<DeleteStoryRequest, DeleteStoryResult>
        implements RequestHandler<LambdaRequest<DeleteStoryRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<DeleteStoryRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
                () -> input.fromPath(path ->
                        DeleteStoryRequest.builder()
                                .withStoryId(path.get("storyId"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideDeleteStoryActivity().handleRequest(request)
        );
    }
}