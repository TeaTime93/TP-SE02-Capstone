package SE02.Capstone.activity;

import static org.junit.jupiter.api.Assertions.*;

import SE02.Capstone.activity.request.CreateUserRequest;
import SE02.Capstone.activity.result.CreateUserResult;
import SE02.Capstone.dynamodb.UserDao;
import SE02.Capstone.dynamodb.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class CreateUserActivityTest {

    @Mock
    private UserDao userDao;

    private CreateUserActivity createUserActivity;

    @BeforeEach
    void setUp() {
        openMocks(this);
        createUserActivity = new CreateUserActivity(userDao);
    }

    @Test
    public void handleRequest_withUser_createsAndSavesUserWithProperties() {
        // GIVEN
        String expectedUserName = "Test User";
        String expectedEmail = "test.user@example.com";
        String expectedBio = "Test Bio";
        int expectedAge = 30;
        List<String> expectedFollows = List.of("User1", "User2");
        List<String> expectedFollowers = List.of("User3", "User4");
        List<String> expectedFavorites = List.of("Story1", "Story2");
        int expectedUserScore = 100;
        List<String> expectedStoriesWritten = List.of("Story3", "Story4");
        String expectedFeatured = "Yes";
        List<String> expectedDislikedStories = List.of("Story5", "Story6");
        List<String> expectedPreferredTags = List.of("Tag1", "Tag2");

        CreateUserRequest request = CreateUserRequest.builder()
                .withUserName(expectedUserName)
                .withEmail(expectedEmail)
                .withBio(expectedBio)
                .withAge(expectedAge)
                .withFollows(expectedFollows)
                .withFollowers(expectedFollowers)
                .withFavorites(expectedFavorites)
                .withUserScore(expectedUserScore)
                .withStoriesWritten(expectedStoriesWritten)
                .withFeatured(expectedFeatured)
                .withDislikedStories(expectedDislikedStories)
                .withPreferredTags(expectedPreferredTags)
                .build();

        // WHEN
        CreateUserResult result = createUserActivity.handleRequest(request);

        // THEN
        verify(userDao).saveUser(any(User.class));

        assertNotNull(result.getUser().getUserId()); // User ID should be generated
        assertEquals(expectedUserName, result.getUser().getUserName());
        assertEquals(expectedEmail, result.getUser().getEmail());
        assertEquals(expectedBio, result.getUser().getBio());
        assertEquals(expectedAge, result.getUser().getAge());
        assertEquals(expectedFollows, result.getUser().getFollows());
        assertEquals(expectedFollowers, result.getUser().getFollowers());
        assertEquals(expectedFavorites, result.getUser().getFavorites());
        assertEquals(expectedUserScore, result.getUser().getUserScore());
        assertEquals(expectedStoriesWritten, result.getUser().getStoriesWritten());
        assertEquals(expectedFeatured, result.getUser().getFeatured());
        assertEquals(expectedDislikedStories, result.getUser().getDislikedStories());
        assertEquals(expectedPreferredTags, result.getUser().getPreferredTags());
    }
}
