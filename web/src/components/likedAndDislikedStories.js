import HookClient from "../api/HookClient";
import BindingClass from "../util/bindingClass";
import { Auth } from "@aws-amplify/auth";
import Authenticator from "../api/authenticator";
import Animate from "./animate";

export default class LikedAndDisLikedStories extends BindingClass {
  constructor() {
    super();

    const methodsToBind = [
      "userStoriesInformation",
      "createUserCard",
      "appendStoryLinks",
    ];
    this.bindClassMethods(methodsToBind, this);

    this.client = new HookClient();
    this.authenticator = new Authenticator();
    this.animate = new Animate();
  }

  async userStoriesInformation() {
    const likesAndDislikesContainer = document.getElementById(
      "likes-and-dislikes-container"
    );
    const thisUser = await this.getCurrentUserInfo();

    const form = document.createElement("form");
    const userId = window.userId;
    const userData = await this.client.getUser(userId);
    const userCard = this.createUserCard(userData);

    form.append(userCard);
    console.log("data from otherStoriesCard: ", userData);
    likesAndDislikesContainer.append(form);
    likesAndDislikesContainer.classList.add("card-content");

    // Pass thisUser.email to appendStoryLinks
    this.appendStoryLinks(
      userId.storiesWritten,
      userCard,
      userData,
      thisUser.email
    );
  }

  async getCurrentUserInfo() {
    const cognitoUser = await Auth.currentAuthenticatedUser();
    const { email, name } = cognitoUser.signInUserSession.idToken.payload;
    return { email, name };
  }

  async appendStoryLinks(storyIds, element) {
    const storyContainer = document.createElement("div");
    element.appendChild(storyContainer);

    const maxPerPage = 5;
    const totalPages = Math.ceil(storyIds.length / maxPerPage);

    let currentPage = 1;
    let startIndex = (currentPage - 1) * maxPerPage;
    let endIndex = startIndex + maxPerPage;

    const renderStories = async () => {
      storyContainer.innerHTML = "";

      for (let i = startIndex; i < endIndex && i < storyIds.length; i++) {
        let storyId = storyIds[i];
        console.log(`Processing storyId: ${storyId}`);

        const storyData = await this.client.getStory(storyId);
        console.log(`Received storyData: ${JSON.stringify(storyData)}`);

        if (storyData && storyData.title) {
          const title = storyData.title;
          const titleContainer = document.createElement("div"); // Container for each title

          const titleLink = document.createElement("a");
          titleLink.href = `fullStory.html?storyId=${storyId}`;
          titleLink.textContent = title;
          titleLink.style.color = "#000080";

          titleContainer.appendChild(titleLink);
          storyContainer.appendChild(titleContainer);
        } else {
          console.error(`Unable to get storyData for storyId: ${storyId}`);
        }
      }
    };

    const navigateToPage = (page) => {
      currentPage = page;
      startIndex = (currentPage - 1) * maxPerPage;
      endIndex = startIndex + maxPerPage;
      renderStories();
    };

    renderStories();

    // Pagination buttons
    const paginationContainer = document.createElement("div");
    for (let i = 1; i <= totalPages; i++) {
      const pageButton = document.createElement("button");
      pageButton.textContent = i;
      pageButton.style.margin = "2px", "2px", "0px", "2px";
      pageButton.classList.add("button", "button-primary");
      pageButton.addEventListener("click", (event) => {
        event.preventDefault(); // prevent the default behaviour
        navigateToPage(i);
      });
      paginationContainer.appendChild(pageButton);
    }

    element.appendChild(paginationContainer);
  }

  createUserCard(userData) {
    const card = document.createElement("div");
    card.classList.add("card");
    card.style.opacity = 0;

    // Favorites
    const favoritesElement = this.createLabelAndContent("Liked Stories", "");
    this.appendStoryLinks(userData.favorites, favoritesElement);
    card.appendChild(favoritesElement);

    return card;
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
