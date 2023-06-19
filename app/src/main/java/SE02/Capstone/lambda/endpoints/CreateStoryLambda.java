package SE02.Capstone.lambda.endpoints;

import SE02.Capstone.activity.request.CreateStoryRequest;
import SE02.Capstone.activity.result.CreateStoryResult;
import SE02.Capstone.lambda.LambdaActivityRunner;
import SE02.Capstone.lambda.LambdaRequest;
import SE02.Capstone.lambda.LambdaResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateStoryLambda extends LambdaActivityRunner<CreateStoryRequest, CreateStoryResult>
        implements RequestHandler<LambdaRequest<CreateStoryRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<CreateStoryRequest>input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
                () -> {
                    CreateStoryRequest arg = input.fromBody(CreateStoryRequest.class);
                    return input.fromPath(claims ->
                            CreateStoryRequest.builder()
                                    .withStoryId(arg.getStoryId())
                                    .withUserId(arg.getUserId())
                                    .withTitle(arg.getTitle())
                                    .withContent(arg.getContent())
                                    .withSnippet(arg.getSnippet())
                                    .withTags(arg.getTags())
                                    .withLikes(arg.getLikes())
                                    .withDislikes(arg.getDislikes())
                                    .withHooks(arg.getHooks())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideCreateStoryActivity().handleRequest(request)
        );
    }
}
