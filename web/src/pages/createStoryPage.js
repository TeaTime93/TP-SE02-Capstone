import HookClient from "../api/HookClient";
import Header from "../components/header";
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import CreateStoryCard from "../components/createStoryCard";
import createDOMPurify from 'dompurify';
import { Auth } from "@aws-amplify/auth";

const SEARCH_CRITERIA_KEY = "search-criteria";
const SEARCH_RESULTS_KEY = "search-results";
const EMPTY_DATASTORE_STATE = {
  [SEARCH_CRITERIA_KEY]: "",
  [SEARCH_RESULTS_KEY]: [],
};

class CreateStoryPage extends BindingClass {
  constructor() {
    super();

    this.bindClassMethods(["mount"], this);

    this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
    this.header = new Header(this.dataStore);
    this.createStoryCard = new CreateStoryCard(this.dataStore);
    console.log("CreateStoryPage constructor");
    this.dataStore.addChangeListener(this.displaySearchResults);
  }

  async mount() {
    this.header.addHeaderToPage();
    this.client = new HookClient();
    this.createStoryCard.createStoryForm();
    console.log("Create Story page script loaded successfully!");
  }

  async getCurrentUserInfo() {
    const cognitoUser = await Auth.currentAuthenticatedUser();
    const { email, name } = cognitoUser.signInUserSession.idToken.payload;
    return { email, name };
  }
}

const main = async () => {
  const createStoryPage = new CreateStoryPage();
  createStoryPage.mount();
};

window.addEventListener("DOMContentLoaded", main);
