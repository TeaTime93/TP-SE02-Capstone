import HookClient from "../api/HookClient";
import BindingClass from "../util/bindingClass";
import { Auth } from "@aws-amplify/auth";

export default class OtherStoriesCard extends BindingClass {
  constructor() {
    super();

    const methodsToBind = ["userStoriesInformation", "createUserCard"];
    this.bindClassMethods(methodsToBind, this);

    this.client = new HookClient();
  }

  async userStoriesInformation() {
    const userStoriesContainer = document.getElementById(
      "userStories-container"
    );
    const userId = window.userId;
    const thisUser = await this.getCurrentUserInfo();
    const thisUserData = await this.client.getUserByEmail(thisUser.email);

    const form = document.createElement("form");
    const userData = await this.client.getUser(userId);
    const userCard = this.createUserCard(userData);

    form.append(userCard);
    console.log("data from otherStoriesCard: ", userData);
    userStoriesContainer.append(form);
    userStoriesContainer.classList.add("card-content");
  }

  async getCurrentUserInfo() {
    const cognitoUser = await Auth.currentAuthenticatedUser();
    const { email, name } = cognitoUser.signInUserSession.idToken.payload;
    return { email, name };
  }

  createUserCard(userData) {
    const card = document.createElement("div");
    card.classList.add("card");
    const storiesWritten = userData.storiesWritten;

    const writtenStoriesElement = this.createLabelAndContent(
      "Written Stories",
      ""
    );
    this.appendStoryLinks(storiesWritten, writtenStoriesElement);
    card.appendChild(writtenStoriesElement);

    return card;
  }

  async appendStoryLinks(storyIds, element) {
    const list = document.createElement("ul");

    for (let i = 0; i < storyIds.length; i++) {
      let storyId = storyIds[i];

      const storyData = await this.client.getStory(storyId);
      console.log(`Received storyData: ${JSON.stringify(storyData)}`);

      if (storyData && storyData.title) {
        const title = storyData.title;
        const titleLink = document.createElement("a");
        titleLink.href = `fullStory.html?storyId=${storyId}`;
        titleLink.textContent = title;
        titleLink.style.color = "#000080";

        const listItem = document.createElement("li"); // Create list item element
        listItem.appendChild(titleLink); // Append link to list item
        list.appendChild(listItem); // Append list item to list
      } else {
        console.error(`Unable to get storyData for storyId: ${storyId}`);
      }
    }

    element.appendChild(list); // Append the list to the element
  }

  createLabelAndContent(labelText, contentText, className) {
    const label = document.createElement("h3");
    label.textContent = `${labelText}:`;
    label.classList.add(`${className}-label`);

    const content = document.createElement("p");
    content.textContent = contentText;
    content.classList.add(className);

    const container = document.createElement("div");
    container.appendChild(label);
    container.appendChild(content);

    return container;
  }
}
