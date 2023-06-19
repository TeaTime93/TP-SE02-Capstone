package SE02.Capstone.dependency;

import SE02.Capstone.activity.*;
import SE02.Capstone.lambda.endpoints.DeleteStoryLambda;
import dagger.Component;

import javax.inject.Singleton;

/**
 * Dagger component for providing dependency injection in the Music Playlist Service.
 */
@Singleton
@Component(modules = {DaoModule.class, MetricsModule.class})
public interface ServiceComponent {

    CreateUserActivity provideCreateUserActivity();
    GetUserActivity provideGetUserActivity();
    CreateStoryActivity provideCreateStoryActivity();
    GetStoryActivity provideGetStoryActivity();
    GenerateFeedActivity provideGenerateFeedActivity();
    GetUserByEmailActivity provideGetUserByEmailActivity();
    EditUserActivity provideEditUserActivity();
    EditStoryActivity provideEditStoryActivity();
    DeleteStoryActivity provideDeleteStoryActivity();
    CreateCommentsActivity provideCreateCommentsActivity();
    EditCommentsActivity provideEditCommentsActivity();
    GetCommentsActivity provideGetCommentsActivity();

}