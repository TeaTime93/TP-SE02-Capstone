import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";

export default class HookClient extends BindingClass {
  constructor(props = {}) {
    super();
    //Methods found in this class
    const methodsToBind = [
      "clientLoaded",
      "getIdentity",
      "login",
      "logout",
      "getStory",
      "getFeed",
    ];
    this.bindClassMethods(methodsToBind, this);

    //this is the login
    this.authenticator = new Authenticator();
    this.props = props;

    //axios handles the request and response data
    axios.defaults.baseURL = process.env.API_BASE_URL;
    this.axiosClient = axios;
    this.clientLoaded();
  }

  /**
   * Run any functions that are supposed to be called once the client has loaded successfully.
   */
  clientLoaded() {
    if (this.props.hasOwnProperty("onReady")) {
      this.props.onReady(this);
    }
  }

  /**
   * Get the identity of the current user
   * @param errorCallback (Optional) A function to execute if the call fails.
   * @returns The user information for the current user.
   */
  async getIdentity(errorCallback) {
    try {
      const isLoggedIn = await this.authenticator.isUserLoggedIn();

      if (!isLoggedIn) {
        return undefined;
      }

      return await this.authenticator.getCurrentUserInfo();
    } catch (error) {
      this.handleError(error, errorCallback);
    }
  }

  async isUserLoggedIn(errorCallback) {
    const isLoggedIn = await this.authenticator.isUserLoggedIn();
    return isLoggedIn;
  }
  catch(error) {
    this.handleError(error, errorCallback);
  }

  async login() {
    this.authenticator.login();
  }

  async logout() {
    this.authenticator.logout();
  }

  async getTokenOrThrow(unauthenticatedErrorMessage) {
    const isLoggedIn = await this.authenticator.isUserLoggedIn();
    if (!isLoggedIn) {
      throw new Error(unauthenticatedErrorMessage);
    }

    return await this.authenticator.getUserToken();
  }

  async getStory(storyId, errorCallback) {
    try {
      const response = await this.axiosClient.get(`stories/${storyId}`);

      // Check if the story has a content property
      if (response.data.story && response.data.story.content) {
        // Replace <br/> with \n in the story content
        response.data.story.content = response.data.story.content.replace(
          /<br\/>/g,
          "\n"
        );
      }

      return response.data.story;
    } catch (error) {
      this.handleError(error, errorCallback);
    }
  }

  async getFeed(userId, errorCallback) {
    try {
      const response = await this.axiosClient.get(`feed/${userId}`);
      return response.data.feed.stories;
    } catch (error) {
      this.handleError(error, errorCallback);
    }
  }

  async getUser(userId, errorCallback) {
    try {
      const response = await this.axiosClient.get(`users/${userId}`);
      console.log("getUser response: ", response);
      return response.data.user;
    } catch (error) {
      console.log("error in getUser: ", error);
      this.handleError(error, errorCallback);
    }
  }

  async getUserByEmail(email, errorCallback) {
    try {
      const response = await this.axiosClient.get(`usersbyemail/${email}`);
      return response.data.user;
    } catch (error) {
      this.handleError(error, errorCallback);
    }
  }

  async createUser(userName, email, bio, age) {
    try {
      const response = await this.axiosClient.post(`users`, {
        userName: userName,
        email: email,
        bio: bio,
        age: age,
        follows: [],
        followers: [],
        favorites: [],
        userScore: 0,
        storiesWritten: [],
        featured: "",
        dislikedStories: [],
        preferredTags: []
      });
      return response.data;
    } catch (error) {
      this.handleError(error, errorCallback);
    }
  }
  

  async createStory(userId, title, content, snippet, tags) {
    try {
      const response = await this.axiosClient.post(`stories`, {
        userId: userId,
        title: title,
        content: content,
        snippet: snippet,
        tags: tags,
        likes: 0,
        dislikes: 0,
        hooks: 0,
      });
      console.log("createStory response: ", response.data);
      return response.data.story;
    } catch (error) {
      const errorCallback = console.error;
      this.handleError(error, errorCallback);
    }
  }

  async editUser(
    userId,
    userName,
    email,
    bio,
    age,
    follows,
    followers,
    favorites,
    userScore,
    storiesWritten,
    featured,
    dislikedStories,
    preferredTags,
    errorCallback
  ) {
    try {
      const token = await this.getTokenOrThrow(
        "Only authenticated users can update the user."
      );
      const payload = {
        userId: userId,
        userName: userName,
        email: email,
        bio: bio,
        age: age,
        follows: follows,
        followers: followers,
        favorites: favorites,
        userScore: userScore,
        storiesWritten: storiesWritten,
        featured: featured,
        dislikedStories: dislikedStories,
        preferredTags: preferredTags,
      };
      const headers = {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      };

      const response = await this.axiosClient.put(`users/${userId}`, payload, {
        headers,
      });
      return response.data;
    } catch (error) {
      this.handleError(error, errorCallback);
    }
  }

  async editStory(
    storyId,
    userId,
    title,
    content,
    snippet,
    tags,
    likes,
    dislikes,
    hooks,
    errorCallback
  ) {
    try {
      const token = await this.getTokenOrThrow(
        "Only authenticated users can update the story."
      );
      const payload = {
        storyId: storyId,
        userId: userId,
        title: title,
        content: content,
        snippet: snippet,
        tags: tags,
        likes: likes,
      };
      const headers = {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      };

      const response = await this.axiosClient.put(
        `stories/${storyId}`,
        payload,
        { headers }
      );
      return response.data;
    } catch (error) {
      this.handleError(error, errorCallback);
    }
  }

  async deleteStory(storyId, errorCallback) {
    try {
      await this.axiosClient.delete(`stories/${storyId}`);
      return true; // Indicate successful deletion
    } catch (error) {
      this.handleError(error, errorCallback);
      return false; // Indicate deletion failure
    }
  }

  handleError(error, errorCallback) {
    console.error(error);

    const errorFromApi = error?.response?.data?.error_message;
    if (errorFromApi) {
      console.error(errorFromApi);
      error.message = errorFromApi;
    }

    if (errorCallback) {
      errorCallback(error);
    }
  }
}
