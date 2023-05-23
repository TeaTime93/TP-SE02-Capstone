package SE02.Capstone.lambda.endpoints;

import SE02.Capstone.activity.request.GetUserRequest;
import SE02.Capstone.activity.result.GetUserResult;
import SE02.Capstone.lambda.LambdaActivityRunner;
import SE02.Capstone.lambda.LambdaRequest;
import SE02.Capstone.lambda.LambdaResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetUserLambda extends LambdaActivityRunner<GetUserRequest, GetUserResult>
        implements RequestHandler<LambdaRequest<GetUserRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetUserRequest>input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
                () -> input.fromPath(path ->
                        GetUserRequest.builder()
                                .withUserId(path.get("userId"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideGetUserActivity().handleRequest(request)
        );
    }
}