package SE02.Capstone.converters;

import SE02.Capstone.dynamodb.models.Feed;
import SE02.Capstone.dynamodb.models.Story;
import SE02.Capstone.dynamodb.models.User;
import SE02.Capstone.models.FeedModel;
import SE02.Capstone.models.StoryModel;
import SE02.Capstone.models.UserModel;

import java.util.ArrayList;
import java.util.List;


public class ModelConverter {

    public UserModel toUserModel(User user) {

        return UserModel.builder()
                .withUserId(user.getUserId())
                .withUserName(user.getUserName())
                .withEmail(user.getEmail())
                .withBio(user.getBio())
                .withAge(user.getAge())
                .build();
    }

    public StoryModel toStoryModel(Story story) {

        return StoryModel.builder()
                .withStoryId(story.getStoryId())
                .withUserId(story.getUserId())
                .withTitle(story.getTitle())
                .withContent(story.getContent())
                .withSnippet(story.getSnippet())
                .withTags(story.getTags())
                .build();
    }


    public List<StoryModel> toStoryModelList(List<Story> stories) {
        List<StoryModel> storyModels = new ArrayList<>();
        for (Story story : stories) {
            storyModels.add(toStoryModel(story));
        }
        return storyModels;
    }

    public FeedModel toFeedModel(Feed feed) {
        return FeedModel.builder()
                .withStories(feed.getStories())
                .build();
    }
}