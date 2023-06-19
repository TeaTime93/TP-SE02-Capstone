import HookClient from "../api/HookClient";
import Header from "../components/header";
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import FullStoryCard from "../components/fullStoryCard";
import CommentsCard from "../components/commentsCard";
import { Auth } from "@aws-amplify/auth";

const SEARCH_CRITERIA_KEY = "search-criteria";
const SEARCH_RESULTS_KEY = "search-results";
const EMPTY_DATASTORE_STATE = {
  [SEARCH_CRITERIA_KEY]: "",
  [SEARCH_RESULTS_KEY]: [],
};

class FullStoryPage extends BindingClass {
  constructor() {
    super();

    this.bindClassMethods(["mount"], this);

    this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
    this.header = new Header(this.dataStore);
    this.fullStoryCard = new FullStoryCard(this.dataStore);
    this.commentsCard = new CommentsCard(this.dataStore);
    console.log("fullStoryPage constructor");
    this.dataStore.addChangeListener(this.displaySearchResults);
  }

  async mount() {
    this.header.addHeaderToPage();
    this.client = new HookClient();
    this.fullStoryCard.fullStory();
    this.commentsCard.comments();
  }

  async getCurrentUserInfo() {
    const cognitoUser = await Auth.currentAuthenticatedUser();
    const { email, name } = cognitoUser.signInUserSession.idToken.payload;
    return { email, name };
  }
}

const main = async () => {
  const fullStoryPage = new FullStoryPage();
  fullStoryPage.mount();
};

window.addEventListener("DOMContentLoaded", main);
