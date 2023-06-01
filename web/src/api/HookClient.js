import axios from "axios";
import BindingClass from "../util/bindingClass";
import Authenticator from "./authenticator";

export default class HookClient extends BindingClass {

    constructor(props = {}) {
        super();
        //Methods found in this class
        const methodsToBind = ['clientLoaded', 'getIdentity', 'login', 'logout', 'getUser', 'getStory', 'getFeed'];
        this.bindClassMethods(methodsToBind, this);

        //this is the login
        this.authenticator = new Authenticator();;
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
            this.handleError(error, errorCallback)
        }
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
          return response.data.story;
      } catch (error) {
          this.handleError(error, errorCallback)
      }
  }  

  async getFeed(userId, errorCallback) {
    try {
        const response = await this.axiosClient.get(`feed/${userId}`);
        return response.data.feed.stories;
    } catch (error) {
        this.handleError(error, errorCallback)
    }
}

  
    handleError(error, errorCallback) {
        console.error(error);

        const errorFromApi = error?.response?.data?.error_message;
        if (errorFromApi) {
            console.error(errorFromApi)
            error.message = errorFromApi;
        }

        if (errorCallback) {
            errorCallback(error);
        }
    }  
}

