import HookClient from "../api/HookClient";
import BindingClass from "../util/bindingClass";
import { Auth } from "@aws-amplify/auth";

export default class UnsubmitLikeDislike extends BindingClass {
  constructor() {
    super();

    const methodsToBind = ["unsubmitLike", "unsubmitDislike"];
    this.bindClassMethods(methodsToBind, this);
    this.client = new HookClient();
  }

  async unsubmitLike(storyId) {
    const cognitoUser = await Auth.currentAuthenticatedUser();
    const { email, name } = cognitoUser.signInUserSession.idToken.payload;
    const userData = await this.client.getUserByEmail(email);
    const storyData = await this.client.getStory(storyId);

    try {
      const updatedFavorites = userData.favorites.filter(
        (storyId) => storyId !== storyData.storyId
      );

      const updatedLikes = Math.max(storyData.likes - 1, 0);
      await this.client.editUser(
        userData.userId,
        userData.userName,
        email,
        userData.bio,
        userData.age,
        userData.follows,
        userData.followers,
        updatedFavorites,
        userData.userScore,
        userData.storiesWritten,
        userData.featured,
        userData.dislikedStories,
        userData.preferredTags
      );

      await this.client.editStory(
        storyData.storyId,
        storyData.userId,
        storyData.title,
        storyData.content,
        storyData.snippet,
        storyData.tags,
        updatedLikes,
        storyData.dislikes,
        storyData.hooks
      );
      window.location.href = `fullStory.html?storyId=${storyId}`;
    } catch (error) {
      console.error("An error occurred while editing user: ", error);
    }
  }

  async unsubmitDislike(storyId) {
    const cognitoUser = await Auth.currentAuthenticatedUser();
    const { email, name } = cognitoUser.signInUserSession.idToken.payload;
    const userData = await this.client.getUserByEmail(email);
    const storyData = await this.client.getStory(storyId);

    try {
      const updatedDislikes = userData.dislikedStories.filter(
        (storyId) => storyId !== storyData.storyId
      );

      const updatedDislikesStory = Math.max(storyData.dislikes - 1, 0);
      await this.client.editUser(
        userData.userId,
        userData.userName,
        email,
        userData.bio,
        userData.age,
        userData.follows,
        userData.followers,
        userData.favorites,
        userData.userScore,
        userData.storiesWritten,
        userData.featured,
        updatedDislikes,
        userData.preferredTags
      );

      await this.client.editStory(
        storyData.storyId,
        storyData.userId,
        storyData.title,
        storyData.content,
        storyData.snippet,
        storyData.tags,
        storyData.likes,
        updatedDislikesStory,
        storyData.hooks
      );
      window.location.href = `fullStory.html?storyId=${storyId}`;
    } catch (error) {
      console.error("An error occurred while editing user: ", error);
    }
  }

  async submitLike(storyId) {
    console.log("submitLike this.feed: ", this.feed);
    console.log("submitLike this.storyIndex: ", this.storyIndex);
    const cognitoUser = await Auth.currentAuthenticatedUser();
    const { email, name } = cognitoUser.signInUserSession.idToken.payload;
    const userData = await this.client.getUserByEmail(email);
    const storyData = await this.client.getStory(storyId);
  
    try {
      const updatedFavorites = userData.favorites.includes(storyId)
        ? userData.favorites
        : [...userData.favorites, storyId];
  
      const updatedLikes = ++storyData.likes;
      await this.client.editUser(
        userData.userId,
        userData.userName,
        email,
        userData.bio,
        userData.age,
        userData.follows,
        userData.followers,
        updatedFavorites,
        userData.userScore,
        userData.storiesWritten,
        userData.featured,
        userData.dislikedStories,
        userData.preferredTags
      );
  
      await this.client.editStory(
        storyId,
        storyData.userId,
        storyData.title,
        storyData.content,
        storyData.snippet,
        storyData.tags,
        updatedLikes,
        storyData.dislikes,
        storyData.hooks
      );
      window.location.href = `fullStory.html?storyId=${storyId}`;
    } catch (error) {
      console.error("An error occurred while editing user: ", error);
    }
  }
  
  async submitDislike(storyId) {
    const cognitoUser = await Auth.currentAuthenticatedUser();
    const { email, name } = cognitoUser.signInUserSession.idToken.payload;
    const userData = await this.client.getUserByEmail(email);
    const storyData = await this.client.getStory(storyId);
  
    try {
      const updatedDislikes = userData.dislikedStories.includes(storyId)
        ? userData.dislikedStories
        : [...userData.dislikedStories, storyId];
  
      const updatedDislikesStory = ++storyData.dislikes;
      await this.client.editUser(
        userData.userId,
        userData.userName,
        email,
        userData.bio,
        userData.age,
        userData.follows,
        userData.followers,
        userData.favorites,
        userData.userScore,
        userData.storiesWritten,
        userData.featured,
        updatedDislikes,
        userData.preferredTags
      );
  
      await this.client.editStory(
        storyId,
        storyData.userId,
        storyData.title,
        storyData.content,
        storyData.snippet,
        storyData.tags,
        storyData.likes,
        updatedDislikesStory,
        storyData.hooks
      );
      window.location.href = `fullStory.html?storyId=${storyId}`;
    } catch (error) {
      console.error("An error occurred while editing user: ", error);
    }
  }
}
