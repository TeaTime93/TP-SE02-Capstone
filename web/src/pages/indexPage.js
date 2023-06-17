import HookClient from "../api/HookClient";
import Header from "../components/header";
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import Feed from "../components/feed";
import { Auth } from "@aws-amplify/auth";

const SEARCH_CRITERIA_KEY = "search-criteria";
const SEARCH_RESULTS_KEY = "search-results";
const EMPTY_DATASTORE_STATE = {
  [SEARCH_CRITERIA_KEY]: "",
  [SEARCH_RESULTS_KEY]: [],
};

class IndexPage extends BindingClass {
  constructor() {
    super();

    this.bindClassMethods(["mount"], this);

    this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
    this.header = new Header(this.dataStore);
    console.log("indexPage constructor");
    this.dataStore.addChangeListener(this.displaySearchResults);

    this.feed = new Feed();
  }

  async mount() {
    this.header.addHeaderToPage();
    this.client = new HookClient();
    console.log("Index page script loaded successfully!");

    if (await this.isUserLoggedIn()) {
      const userInfo = await this.getCurrentUserInfo();
      // check if there's a user with this email
      const user = await this.client.getUserByEmail(userInfo.email);
      // if no user found, redirect to create user page
      console.log(userInfo.email);
      console.log(user);
      if (!user) {
        console.log("No user");
        window.location.href = "/createuser.html";
      }
    }

    this.feed.init();
  }

  async isUserLoggedIn() {
    try {
      await Auth.currentAuthenticatedUser();
      return true;
    } catch {
      return false;
    }
  }

  async getCurrentUserInfo() {
    const cognitoUser = await Auth.currentAuthenticatedUser();
    const { email, name } = cognitoUser.signInUserSession.idToken.payload;
    return { email, name };
  }
}

const main = async () => {
  const index = new IndexPage();
  index.mount();
};

window.addEventListener("DOMContentLoaded", main);
