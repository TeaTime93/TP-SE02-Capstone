package SE02.Capstone.lambda.endpoints;

import SE02.Capstone.activity.request.GetStoryByTitleAndAuthorRequest;
import SE02.Capstone.activity.result.GetStoryByTitleAndAuthorResult;
import SE02.Capstone.lambda.LambdaActivityRunner;
import SE02.Capstone.lambda.LambdaRequest;
import SE02.Capstone.lambda.LambdaResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class GetStoryByTitleAndAuthorLambda extends LambdaActivityRunner<GetStoryByTitleAndAuthorRequest, GetStoryByTitleAndAuthorResult>
        implements RequestHandler<LambdaRequest<GetStoryByTitleAndAuthorRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<GetStoryByTitleAndAuthorRequest> input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
                () -> input.fromPath(path -> {
                    String title = URLDecoder.decode(path.get("title"), StandardCharsets.UTF_8);
                    String userId = URLDecoder.decode(path.get("userId"), StandardCharsets.UTF_8);
                    log.info("Extracted title: {}", title);
                    log.info("Extracted userId: {}", userId);

                    return GetStoryByTitleAndAuthorRequest.builder()
                            .withTitle(title)
                            .withUserId(userId)
                            .build();
                }),
                (request, serviceComponent) ->
                        serviceComponent.provideGetStoryByTitleAndAuthorActivity().handleRequest(request)
        );
    }

}