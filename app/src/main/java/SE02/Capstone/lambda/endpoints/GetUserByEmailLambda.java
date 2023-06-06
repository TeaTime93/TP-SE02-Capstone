package SE02.Capstone.lambda.endpoints;

import SE02.Capstone.activity.request.GetUserByEmailRequest;
import SE02.Capstone.activity.result.GetUserByEmailResult;
import SE02.Capstone.lambda.LambdaActivityRunner;
import SE02.Capstone.lambda.LambdaRequest;
import SE02.Capstone.lambda.LambdaResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetUserByEmailLambda extends LambdaActivityRunner<GetUserByEmailRequest, GetUserByEmailResult>
        implements RequestHandler<LambdaRequest<GetUserByEmailRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetUserByEmailRequest>input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
                () -> input.fromPath(path ->
                        GetUserByEmailRequest.builder()
                                .withEmail(path.get("email"))
                                .build()),
                (request, serviceComponent) ->
                        serviceComponent.provideGetUserByEmailActivity().handleRequest(request)
        );
    }
}