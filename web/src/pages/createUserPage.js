import HookClient from "../api/HookClient";
import Header from "../components/header";
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import CreateUserCard from "../components/createUserCard";
import { Auth } from "@aws-amplify/auth";

const SEARCH_CRITERIA_KEY = "search-criteria";
const SEARCH_RESULTS_KEY = "search-results";
const EMPTY_DATASTORE_STATE = {
  [SEARCH_CRITERIA_KEY]: "",
  [SEARCH_RESULTS_KEY]: [],
};

class CreateUserPage extends BindingClass {
  constructor() {
    super();

    this.bindClassMethods(["mount"], this);

    this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
    this.header = new Header(this.dataStore);
    this.createUserCard = new CreateUserCard(this.dataStore);
    console.log("CreateUserPage constructor");
    this.dataStore.addChangeListener(this.displaySearchResults);
  }

  async mount() {
    this.header.addHeaderToPage();
    this.client = new HookClient();
    this.createUserCard.createUserForm();
    console.log("Create User page script loaded successfully!");
  }

  async getCurrentUserInfo() {
    const cognitoUser = await Auth.currentAuthenticatedUser();
    const { email, name } = cognitoUser.signInUserSession.idToken.payload;
    return { email, name };
  }
}

const main = async () => {
  const createUserPage = new CreateUserPage();
  createUserPage.mount();
};

window.addEventListener("DOMContentLoaded", main);
