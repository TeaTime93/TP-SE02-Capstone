import HookClient from "../api/HookClient";
import BindingClass from "../util/bindingClass";
import { Auth } from "@aws-amplify/auth";

export default class FollowComponent extends BindingClass {
  constructor() {
    super();

    const methodsToBind = ["followUser", "unfollowUser"];
    this.bindClassMethods(methodsToBind, this);
    this.client = new HookClient();
  }

  async followUser(event) {
    this.client = new HookClient();
    event.preventDefault();
    const cognitoUser = await Auth.currentAuthenticatedUser();
    const { email, name } = cognitoUser.signInUserSession.idToken.payload;
    const userData = await this.client.getUserByEmail(email);

    const viewedUserId = window.userId;

    try {
      // Add the viewed user to the follows list
      const updatedFollows = [...userData.follows, viewedUserId];

      await this.client.editUser(
        userData.userId,
        userData.userName,
        userData.email,
        userData.bio,
        userData.age,
        updatedFollows,
        userData.followers,
        userData.favorites,
        userData.userScore,
        userData.storiesWritten,
        userData.featured,
        userData.dislikedStories,
        userData.preferredTags
      );

      const viewedUserData = await this.client.getUser(viewedUserId);
      const updatedFollowers = [...viewedUserData.followers, userData.userId];
      await this.client.editUser(
        viewedUserData.userId,
        viewedUserData.userName,
        viewedUserData.email,
        viewedUserData.bio,
        viewedUserData.age,
        viewedUserData.follows,
        updatedFollowers,
        viewedUserData.favorites,
        viewedUserData.userScore,
        viewedUserData.storiesWritten,
        viewedUserData.featured,
        viewedUserData.dislikedStories,
        viewedUserData.preferredTags
      );

      const userId = viewedUserData.userId;
      window.location.href = `userProfile.html?userId=${userId}`;
    } catch (error) {
      console.error("An error occurred while editing user: ", error);
    }
  }

  async unfollowUser(event) {
    this.client = new HookClient();
    event.preventDefault();
    const cognitoUser = await Auth.currentAuthenticatedUser();
    const { email, name } = cognitoUser.signInUserSession.idToken.payload;
    const userData = await this.client.getUserByEmail(email);

    const viewedUserId = window.userId;

    try {
      const updatedFollows = userData.follows.filter(
        (userId) => userId !== viewedUserId
      );
      const viewedUserData = await this.client.getUser(viewedUserId);
      const updatedFollowers = viewedUserData.followers.filter(
        (userId) => userId !== userData.userId
      );

      // Update the current user's information
      await this.client.editUser(
        userData.userId,
        userData.userName,
        userData.email,
        userData.bio,
        userData.age,
        updatedFollows,
        userData.followers,
        userData.favorites,
        userData.userScore,
        userData.storiesWritten,
        userData.featured,
        userData.dislikedStories,
        userData.preferredTags
      );

      // Update the viewed user's information
      await this.client.editUser(
        viewedUserData.userId,
        viewedUserData.userName,
        viewedUserData.email,
        viewedUserData.bio,
        viewedUserData.age,
        viewedUserData.follows,
        updatedFollowers,
        viewedUserData.favorites,
        viewedUserData.userScore,
        viewedUserData.storiesWritten,
        viewedUserData.featured,
        viewedUserData.dislikedStories,
        viewedUserData.preferredTags
      );

      const userId = viewedUserData.userId;
      window.location.href = `userProfile.html?userId=${userId}`;
    } catch (error) {
      console.error("An error occurred while editing user: ", error);
    }
  }
}
