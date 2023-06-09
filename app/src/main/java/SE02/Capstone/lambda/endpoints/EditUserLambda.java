package SE02.Capstone.lambda.endpoints;

import SE02.Capstone.activity.request.EditUserRequest;
import SE02.Capstone.activity.result.EditUserResult;
import SE02.Capstone.lambda.AuthenticatedLambdaRequest;
import SE02.Capstone.lambda.LambdaActivityRunner;
import SE02.Capstone.lambda.LambdaResponse;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EditUserLambda
        extends LambdaActivityRunner<EditUserRequest, EditUserResult>
        implements RequestHandler<AuthenticatedLambdaRequest<EditUserRequest>, LambdaResponse> {

    private final Logger log = LogManager.getLogger();
    @Override
    public LambdaResponse handleRequest(AuthenticatedLambdaRequest<EditUserRequest> input, Context context) {
        log.info("entered lambda for update");
        return super.runActivity(
                () -> {
                    EditUserRequest authenticatedRequest = input.fromBody(EditUserRequest.class);
                    return input.fromUserClaims(claims -> {
                        return   EditUserRequest.builder()
                                .withUserId(authenticatedRequest.getUserId())
                                .withUserName(authenticatedRequest.getUserName())
                                .withBio(authenticatedRequest.getBio())
                                .withAge(authenticatedRequest.getAge())
                                .withFollows(authenticatedRequest.getFollows())
                                .withFollowers(authenticatedRequest.getFollowers())
                                .withFavorites(authenticatedRequest.getFavorites())
                                .withUserScore(authenticatedRequest.getUserScore())
                                .build();}

                    );

                },
                (request, serviceComponent) ->
                        serviceComponent.provideEditUserActivity().handleRequest(request)
        );
    }
}
