package SE02.Capstone.lambda.endpoints;

import SE02.Capstone.activity.request.CreateUserRequest;
import SE02.Capstone.activity.result.CreateUserResult;
import SE02.Capstone.lambda.LambdaActivityRunner;
import SE02.Capstone.lambda.LambdaRequest;
import SE02.Capstone.lambda.LambdaResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateUserLambda extends LambdaActivityRunner<CreateUserRequest, CreateUserResult>
        implements RequestHandler<LambdaRequest<CreateUserRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();

    @Override
    public LambdaResponse handleRequest(LambdaRequest<CreateUserRequest>input, Context context) {
        log.info("handleRequest");
        return super.runActivity(
                () -> {
                    CreateUserRequest arg = input.fromBody(CreateUserRequest.class);
                    return input.fromPath(claims ->
                            CreateUserRequest.builder()
                                    .withUserId(arg.getUserId())
                                    .withUserName(arg.getUserName())
                                    .withEmail(arg.getEmail())
                                    .withBio(arg.getBio())
                                    .withAge(arg.getAge())
                                    .withFollows(arg.getFollows())
                                    .withFollowers(arg.getFollowers())
                                    .withFavorites(arg.getFavorites())
                                    .withUserScore(arg.getUserScore())
                                    .withStoriesWritten(arg.getStoriesWritten())
                                    .withFeatured(arg.getFeatured())
                                    .withDislikedStories(arg.getDislikedStories())
                                    .withPreferredTags(arg.getPreferredTags())
                                    .build());
                },
                (request, serviceComponent) ->
                        serviceComponent.provideCreateUserActivity().handleRequest(request)
        );
    }
}