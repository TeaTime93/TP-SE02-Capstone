package SE02.Capstone.dynamodb;

import SE02.Capstone.dynamodb.models.Feed;
import SE02.Capstone.dynamodb.models.ScoredStory;
import SE02.Capstone.dynamodb.models.Story;
import SE02.Capstone.dynamodb.models.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class FeedGenerator {
    private StoryDao storyDao;
    private UserDao userDao;

    private final Logger log = LogManager.getLogger();

    public FeedGenerator(StoryDao storyDao, UserDao userDao) {
        this.storyDao = storyDao;
        this.userDao = userDao;
    }

    public void generate(String userId, Feed feed) {
        // Fetch stories from DynamoDB
        List<Story> fetchedStories = storyDao.getAllStories();
        User user = userDao.getUser(userId);

        // Filter any stories the user has already liked
        List<String> favorites = (user.getFavorites() != null) ? user.getFavorites() : new ArrayList<>();
        List<Story> filteredStories = filterLikedStories(fetchedStories, user);

        // Assign scores to the stories
        List<ScoredStory> scoredStories = assignScoresToStories(filteredStories);

        // Sort the scored stories based on their scores in descending order
        Collections.sort(scoredStories, Collections.reverseOrder());

        // Select the top twenty stories
        List<String> topTwentyStories = selectTopTwentyStories(scoredStories);

        // Populate the feed with the top twenty stories
        feed.setStories(topTwentyStories);
    }

    private List<Story> filterLikedStories(List<Story> stories, User user) {
        List<Story> filteredStories = new ArrayList<>();
        String userId = user.getUserId(); // Get the user's ID
        List<String> userFavorites = (user.getFavorites() != null) ? user.getFavorites() : new ArrayList<>();
        for (Story story : stories) {
            // Check if the user has favorited the story or if the user is the author of the story
            if (!userFavorites.contains(story.getStoryId()) && !userId.equals(story.getUserId())) {
                filteredStories.add(story);
            }
        }
        return filteredStories;
    }


    private List<ScoredStory> assignScoresToStories(List<Story> stories) {
        Map<Story, Double> storyScores = new HashMap<>();

        for (Story story : stories) {
            double score = calculateScoreForStory(story);
            storyScores.put(story, score);
        }

        List<ScoredStory> scoredStories = new ArrayList<>();
        for (Map.Entry<Story, Double> entry : storyScores.entrySet()) {
            Story story = entry.getKey();
            Double score = entry.getValue();
            scoredStories.add(new ScoredStory(story, score));
        }

        return scoredStories;
    }

    private List<String> selectTopTwentyStories(List<ScoredStory> scoredStories) {
        Collections.sort(scoredStories, Collections.reverseOrder());

        // Select the top twenty stories or fewer if there are less than twenty stories available
        int numStories = Math.min(scoredStories.size(), 20);

        // Shuffle the scored stories within the range of the top twenty
        List<ScoredStory> topTwentyStories = scoredStories.subList(0, numStories);
        Collections.shuffle(topTwentyStories);

        // Extract the story objects from the shuffled list
        List<String> result = new ArrayList<>();
        for (ScoredStory scoredStory : topTwentyStories) {
            result.add(scoredStory.getStory().getStoryId());
        }

        return result;
    }

    private double calculateScoreForStory(Story story) {
        try {
            // Get the user who authored the story
            User author = userDao.getUser(story.getUserId());

            int numFollowers = (author.getFollowers() != null) ? author.getFollowers().size() : 0;
            int authorScore = author.getUserScore();
            int storyLikes = story.getLikes();

            double score = storyLikes * 0.5 + numFollowers * 0.3 + authorScore * 0.2;

            return score;
        } catch (Exception e) {
            log.error("Exception while calculating score for story: " + e.getMessage());
            return 0.5;
        }
    }
}

