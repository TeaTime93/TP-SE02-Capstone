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
        List<Story> filteredStories = filterLikedStories(fetchedStories, favorites);

        // Assign scores to the stories
        List<ScoredStory> scoredStories = assignScoresToStories(filteredStories);

        // Sort the scored stories based on their scores in descending order
        Collections.sort(scoredStories, Collections.reverseOrder());

        // Select the top twenty stories
        List<String> topTwentyStories = selectTopTwentyStories(scoredStories);

        // Populate the feed with the top twenty stories
        feed.setStories(topTwentyStories);
    }

    private List<Story> filterLikedStories(List<Story> stories, List<String> userFavorites) {
        List<Story> filteredStories = new ArrayList<>();
        for (Story story : stories) {
            if (!userFavorites.contains(story.getStoryId())) {
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

            // Get the number of followers for the author
            int numFollowers = (author.getFollowers() != null) ? author.getFollowers().size() : 0;

            // Author score should default to 0 if it's not set
            int authorScore = author.getUserScore();

            // Story likes should also default to 0 if not set
            int storyLikes = story.getLikes();

            // Calculate the score based on the likes and followers
            double score = storyLikes * 0.5 + numFollowers * 0.3 + authorScore * 0.2; // You can adjust the weights as needed

            return score;
        } catch (Exception e) {
            // log the exception here
            log.error("Exception while calculating score for story: " + e.getMessage());

            // return a default score
            return 0.5;
        }
    }
}

