import HookClient from "../api/HookClient";
import BindingClass from "../util/bindingClass";
import { Auth } from "@aws-amplify/auth";
import Authenticator from "../api/authenticator";
import Animate from "./animate";

export default class OtherStoriesCard extends BindingClass {
  constructor() {
    super();

    const methodsToBind = ["userStoriesInformation", "createUserCard"];
    this.bindClassMethods(methodsToBind, this);

    this.client = new HookClient();
    this.authenticator = new Authenticator();
    this.animate = new Animate();
  }

  async userStoriesInformation() {
    const userStoriesContainer = document.getElementById(
      "userStories-container"
    );
    const thisUser = await this.getCurrentUserInfo();

    const form = document.createElement("form");
    const userId = window.userId;
    const userData = await this.client.getUser(userId);
    const userCard = this.createUserCard(userData);

    form.append(userCard);
    console.log("data from otherStoriesCard: ", userData);
    userStoriesContainer.append(form);
    userStoriesContainer.classList.add("card-content");

    this.animate.addCardAnimations();

    // Pass thisUser.email to appendStoryLinks
    this.appendStoryLinks(
      userData.storiesWritten,
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

  createUserCard(userData) {
    const card = document.createElement("div");
    card.classList.add("card");
    card.style.opacity = 0;
    const storiesWritten = userData.storiesWritten;
  
    const writtenStoriesElement = this.createLabelAndContent(
      "Written Stories",
      ""
    );
    card.appendChild(writtenStoriesElement);
  
    return card;
  }
  

  async appendStoryLinks(storyIds, element, userData, thisUserEmail) {
    const list = document.createElement("ul");
    const select = document.createElement("select"); 
    select.id = "story-dropdown"; 

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

        const listItem = document.createElement("li"); 
        listItem.appendChild(titleLink); 

        list.appendChild(listItem); 

        // Add each story as an option in the select dropdown
        const option = document.createElement("option");
        option.value = storyId;
        option.text = title;
        select.appendChild(option);
      } else {
        console.error(`Unable to get storyData for storyId: ${storyId}`);
      }
    }
    const pageUserId = window.userId;
    const pageUser = await this.client.getUser(pageUserId);

    // Only show the "Feature this story" button for the current user's stories
    if (thisUserEmail === pageUser.email) {
      const featureButton = document.createElement("button");
      featureButton.textContent = "Feature this story";
      featureButton.classList.add("button", "button-primary");
      featureButton.addEventListener('click', () => {
        featureButton.textContent = 'Loading...';
      });
      featureButton.addEventListener("click", async (event) => {
        event.preventDefault(); 

        const selectedStoryId = document.getElementById("story-dropdown").value;
        const thisUser = await this.getCurrentUserInfo();
        const currentUser = await this.client.getUserByEmail(thisUser.email);

        // Update the user's featured story
        await this.client.editUser(
          currentUser.userId,
          currentUser.userName,
          currentUser.email,
          currentUser.bio,
          currentUser.age,
          currentUser.follows,
          currentUser.followers,
          currentUser.favorites,
          currentUser.userScore,
          currentUser.storiesWritten,
          selectedStoryId, 
          currentUser.dislikedStories,
          currentUser.preferredTags
        );
          window.location.href = `userProfile.html?userId=${currentUser.userId}`;
      });

      element.appendChild(select); 
      element.appendChild(featureButton); 
    }

    element.appendChild(list); 
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
