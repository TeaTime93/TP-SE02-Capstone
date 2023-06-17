import HookClient from "../api/HookClient";
import BindingClass from "../util/bindingClass";
import { Auth } from "@aws-amplify/auth";
import FollowComponent from "./followComponent";
import FullStoryCardForProfile from "./fullStoryCardForProfile";
import Animate from "./animate";

export default class UserProfileCard extends BindingClass {
  constructor() {
    super();

    const methodsToBind = [
      "userProfileInformation",
      "createUserCard",
      "createInputField",
      "createCard",
    ];
    this.bindClassMethods(methodsToBind, this);

    this.client = new HookClient();
    this.followComponent = new FollowComponent();
    this.fullStoryCardForProfile = new FullStoryCardForProfile();
    this.animate = new Animate();
  }

  async userProfileInformation() {
    const userProfileContainer = document.getElementById(
      "user-profile-container"
    );
    const userId = window.userId;
    const thisUser = await this.getCurrentUserInfo();
    const thisUserData = await this.client.getUserByEmail(thisUser.email);

    const form = document.createElement("form");
    const userData = await this.client.getUser(userId);
    const userCard = this.createUserCard(userData);

    form.append(userCard);
    userProfileContainer.append(form);
    userProfileContainer.classList.add("card-content");
    this.animate.addCardAnimations();

    if (userData.email === thisUser.email) {
      // Create an edit button
      const editButton = document.createElement("button");
      editButton.classList.add("button", "button-secondary");
      editButton.textContent = "Edit";
      editButton.classList.add("button");
      form.append(editButton);

      // When edit button is clicked
      editButton.addEventListener("click", (event) => {
        event.preventDefault(); // prevent form submission

        // Remove the userCard
        form.removeChild(userCard);

        // Remove the edit button
        form.removeChild(editButton);

        // Create and append the editCard
        const editCard = this.createEditCard(userData);
        form.append(editCard);
      });

      form.addEventListener("submit", this.submitForm);
      this.fullStoryCardForProfile.autoFeatureStory(
        thisUserData,
        thisUserData.featured
      );
    } else {
      const isFollowing = thisUserData.follows.includes(userId);
      if (isFollowing) {
        const unfollowButton = document.createElement("button");
        unfollowButton.type = "button";
        unfollowButton.textContent = "Unfollow User";
        unfollowButton.classList.add("button", "button-secondary");
        unfollowButton.addEventListener("click", (event) =>
          this.followComponent.unfollowUser(event)
        );
        form.append(unfollowButton);
      } else {
        const followButton = document.createElement("button");
        followButton.type = "button";
        followButton.textContent = "Follow User";
        followButton.classList.add("button", "button-secondary");
        followButton.addEventListener("click", (event) =>
          this.followComponent.followUser(event)
        );
        form.append(followButton);
      }
    }
  }

  async getCurrentUserInfo() {
    const cognitoUser = await Auth.currentAuthenticatedUser();
    const { email, name } = cognitoUser.signInUserSession.idToken.payload;
    return { email, name };
  }

  createEditCard(userData) {
    const card = document.createElement("div");
    card.classList.add("card");

    const userNameField = this.createInputField(
      "userName",
      "Username",
      userData.userName
    );
    const bioField = this.createInputField("bio", "Bio", userData.bio);
    const ageField = this.createInputField(
      "age",
      "Age",
      userData.age,
      "number"
    );

    card.append(userNameField, bioField, ageField);

    const saveButton = document.createElement("button");
    saveButton.classList.add("button", "button-primary");
    saveButton.type = "submit";
    saveButton.textContent = "Save Changes";
    saveButton.addEventListener('click', () => {
      saveButton.textContent = 'Loading...';
    });
    card.append(saveButton);

    return card;
  }

  createInputField(id, labelText, initialValue = "", type = "text") {
    const inputGroup = document.createElement("div");
    inputGroup.classList.add("form-field");

    // Create label
    const label = document.createElement("label");
    label.for = id;
    label.textContent = labelText;
    label.classList.add("input-label");

    // Create input
    const input = document.createElement("input");
    input.id = id;
    input.type = type;
    input.value = initialValue;
    input.classList.add("validated-field");

    inputGroup.append(label, input);

    return inputGroup;
  }

  async submitForm(event) {
    this.client = new HookClient();
    event.preventDefault();
    const cognitoUser = await Auth.currentAuthenticatedUser();
    const { email, name } = cognitoUser.signInUserSession.idToken.payload;
    const userData = await this.client.getUserByEmail(email);
    const userName = document.getElementById("userName").value;
    const bio = document.getElementById("bio").value;
    const age = document.getElementById("age").value;

    try {
      await this.client.editUser(
        userData.userId,
        userName,
        email,
        bio,
        age,
        userData.follows,
        userData.followers,
        userData.favorites,
        userData.userScore,
        userData.storiesWritten,
        userData.featured
      );
      const userId = userData.userId;
      window.location.href = `userProfile.html?userId=${userId}`;
    } catch (error) {
      console.error("An error occurred while editing user: ", error);
    }
  }

  createUserCard(userData) {
    const card = document.createElement("div");
    card.classList.add("card");
    card.style.opacity = 0;

    // Name
    this.addLabelAndContent(card, "User Name", userData.userName, "user-name");

    // Email
    this.addLabelAndContent(card, "Email", userData.email, "user-email");

    // Bio
    this.addLabelAndContent(card, "Bio", userData.bio, "user-bio");

    // Age
    this.addLabelAndContent(card, "Age", userData.age.toString(), "user-age");

    // Favorites
    const favoritesElement = this.createLabelAndContent("Favorites", "");
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

  addLabelAndContent(parent, labelText, contentText, className) {
    const element = this.createLabelAndContent(
      labelText,
      contentText,
      className
    );
    parent.appendChild(element);
  }

  async appendStoryLinks(storyIds, element) {
    for (let i = 0; i < storyIds.length; i++) {
      let storyId = storyIds[i];
      console.log(`Processing storyId: ${storyId}`); // Debug line

      if (i > 0) {
        element.appendChild(document.createTextNode(", "));
      }

      const storyData = await this.client.getStory(storyId);
      console.log(`Received storyData: ${JSON.stringify(storyData)}`); // Debug line

      if (storyData && storyData.title) {
        const title = storyData.title;
        const titleLink = document.createElement("a");
        titleLink.href = `fullStory.html?storyId=${storyId}`;
        titleLink.textContent = title;
        titleLink.style.color = "#000080";
        element.appendChild(titleLink);
      } else {
        console.error(`Unable to get storyData for storyId: ${storyId}`);
      }
    }
  }

  createCard(id, labelText, type = "text") {
    const card = document.createElement("div");
    card.classList.add("card");

    const inputField = this.createInputField(id, labelText, type);
    card.append(inputField);

    return card;
  }

  displayLoadingElement() {
    const parentElement = document.getElementById("story-card-container");
    parentElement.innerHTML = "";

    const loadingContainer = document.createElement("div");
    loadingContainer.classList.add("loading-card");

    const loadingElement = document.createElement("p");
    loadingElement.textContent = "Loading...";
    loadingElement.style.textAlign = "center";
    loadingContainer.appendChild(loadingElement);

    parentElement.appendChild(loadingContainer);
  }
}
