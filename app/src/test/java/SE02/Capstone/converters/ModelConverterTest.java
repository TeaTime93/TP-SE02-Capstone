package SE02.Capstone.converters;

import SE02.Capstone.dynamodb.models.*;
import SE02.Capstone.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ModelConverterTest {

    private ModelConverter modelConverter;

    @BeforeEach
    void setUp() {
        modelConverter = new ModelConverter();
    }

    @Test
    void toUserModel_ShouldConvertUserToUserModel() {
        User user = new User();
        user.setUserId("user1");
        user.setUserName("userName1");
        user.setEmail("user1@example.com");
        user.setBio("This is a bio.");
        user.setAge(25);
        user.setFollows(Arrays.asList("user2", "user3"));
        user.setFollowers(Arrays.asList("user4", "user5"));
        user.setFavorites(Arrays.asList("story1", "story2"));
        user.setUserScore(100);
        user.setStoriesWritten(Arrays.asList("story3", "story4"));
        user.setFeatured("story5");
        user.setDislikedStories(Arrays.asList("story6", "story7"));
        user.setPreferredTags(Arrays.asList("tag1", "tag2"));

        UserModel userModel = modelConverter.toUserModel(user);

        assertEquals(user.getUserId(), userModel.getUserId());
        assertEquals(user.getUserName(), userModel.getUserName());
        assertEquals(user.getEmail(), userModel.getEmail());
        assertEquals(user.getBio(), userModel.getBio());
        assertEquals(user.getAge(), userModel.getAge());
        assertEquals(user.getFollows(), userModel.getFollows());
        assertEquals(user.getFollowers(), userModel.getFollowers());
        assertEquals(user.getFavorites(), userModel.getFavorites());
        assertEquals(user.getUserScore(), userModel.getUserScore());
        assertEquals(user.getStoriesWritten(), userModel.getStoriesWritten());
        assertEquals(user.getFeatured(), userModel.getFeatured());
        assertEquals(user.getDislikedStories(), userModel.getDislikedStories());
        assertEquals(user.getPreferredTags(), userModel.getPreferredTags());
    }


    @Test
    void toStoryModel_ShouldConvertStoryToStoryModel() {
        // Set up a test Story
        Story story = new Story();
        story.setStoryId("testStoryId");
        story.setUserId("testUserId");
        story.setTitle("testTitle");
        story.setContent("testContent");
        story.setSnippet("testSnippet");
        story.setTags(Arrays.asList("tag1", "tag2"));
        story.setLikes(100);
        story.setDislikes(50);
        story.setHooks(25);

        // Use the method we're testing to convert the Story to a StoryModel
        StoryModel storyModel = modelConverter.toStoryModel(story);

        // Assertions to check that the fields in storyModel match the corresponding fields in story
        assertEquals(story.getStoryId(), storyModel.getStoryId());
        assertEquals(story.getUserId(), storyModel.getUserId());
        assertEquals(story.getTitle(), storyModel.getTitle());
        assertEquals(story.getContent(), storyModel.getContent());
        assertEquals(story.getSnippet(), storyModel.getSnippet());
        assertEquals(story.getTags(), storyModel.getTags());
        assertEquals(story.getLikes(), storyModel.getLikes());
        assertEquals(story.getDislikes(), storyModel.getDislikes());
        assertEquals(story.getHooks(), storyModel.getHooks());
    }


    @Test
    void toCommentsModel_ShouldConvertCommentsToCommentsModel() {
        // Set up a test Comments
        Comments comments = new Comments();
        comments.setStoryId("testStoryId");
        comments.setPosComments(Arrays.asList("positiveComment1", "positiveComment2"));
        comments.setNegComments(Arrays.asList("negativeComment1", "negativeComment2"));

        // Use the method we're testing to convert the Comments to a CommentsModel
        CommentsModel commentsModel = modelConverter.toCommentsModel(comments);

        // Assertions to check that the fields in commentsModel match the corresponding fields in comments
        assertEquals(comments.getStoryId(), commentsModel.getStoryId());
        assertEquals(comments.getPosComments(), commentsModel.getPosComments());
        assertEquals(comments.getNegComments(), commentsModel.getNegComments());
    }


    @Test
    void toStoryModelList_ShouldConvertListOfStoriesToListOfStoryModels() {
        // Set up a test Stories
        Story story1 = new Story();
        story1.setStoryId("story1");
        story1.setUserId("user1");
        story1.setTitle("title1");
        story1.setContent("content1");
        story1.setSnippet("snippet1");
        story1.setTags(Arrays.asList("tag1", "tag2"));
        story1.setLikes(10);
        story1.setDislikes(1);
        story1.setHooks(5);

        Story story2 = new Story();
        story2.setStoryId("story2");
        story2.setUserId("user2");
        story2.setTitle("title2");
        story2.setContent("content2");
        story2.setSnippet("snippet2");
        story2.setTags(Arrays.asList("tag3", "tag4"));
        story2.setLikes(20);
        story2.setDislikes(2);
        story2.setHooks(10);

        List<Story> stories = Arrays.asList(story1, story2);

        // Use the method we're testing to convert the List<Story> to List<StoryModel>
        List<StoryModel> storyModels = modelConverter.toStoryModelList(stories);

        // Assertions to check that the fields in each StoryModel match the corresponding fields in each Story
        for(int i = 0; i < stories.size(); i++) {
            Story story = stories.get(i);
            StoryModel storyModel = storyModels.get(i);

            assertEquals(story.getStoryId(), storyModel.getStoryId());
            assertEquals(story.getUserId(), storyModel.getUserId());
            assertEquals(story.getTitle(), storyModel.getTitle());
            assertEquals(story.getContent(), storyModel.getContent());
            assertEquals(story.getSnippet(), storyModel.getSnippet());
            assertEquals(story.getTags(), storyModel.getTags());
            assertEquals(story.getLikes(), storyModel.getLikes());
            assertEquals(story.getDislikes(), storyModel.getDislikes());
            assertEquals(story.getHooks(), storyModel.getHooks());
        }
    }


    @Test
    void toFeedModel_ShouldConvertFeedToFeedModel() {
        // Set fields for feed
        List<String> stories = Arrays.asList("story1", "story2", "story3");
        Feed feed = new Feed(stories);

        // Conversion
        FeedModel feedModel = modelConverter.toFeedModel(feed);

        // Assertions to check that the fields in feedModel match the corresponding fields in feed
        assertEquals(feed.getStories(), feedModel.getStories());
    }

}
