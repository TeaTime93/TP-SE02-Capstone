import HookClient from "../api/HookClient";
import BindingClass from "../util/bindingClass";
import { Auth } from "@aws-amplify/auth";

export default class FollowAndFollowersCard extends BindingClass {
  constructor() {
    super();

    const methodsToBind = [
      "userSocialInformation",
      "createUserCard",
      "createInputField",
    ];
    this.bindClassMethods(methodsToBind, this);

    this.client = new HookClient();
  }

  async userSocialInformation() {
    const userProfileContainer = document.getElementById(
      "follows-and-followers-container"
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
  }

  async getCurrentUserInfo() {
    const cognitoUser = await Auth.currentAuthenticatedUser();
    const { email, name } = cognitoUser.signInUserSession.idToken.payload;
    return { email, name };
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

  createUserCard(userData) {
    const card = document.createElement("div");
    card.classList.add("card");

    // Follows
    const followsElement = this.createLabelAndContent("Follows", "");
    this.appendUserLinks(userData.follows, followsElement);
    card.appendChild(followsElement);

    // Followers
    const followersElement = this.createLabelAndContent("Followers", "");
    this.appendUserLinks(userData.followers, followersElement);
    card.appendChild(followersElement);

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

  async appendUserLinks(userIds, element) {
    for (let i = 0; i < userIds.length; i++) {
      let userId = userIds[i];
      console.log(`Processing userId: ${userId}`); // Debug line

      if (i > 0) {
        element.appendChild(document.createTextNode(", "));
      }

      const userData = await this.client.getUser(userId);
      console.log(`Received userData: ${JSON.stringify(userData)}`); // Debug line

      if (userData && userData.userName) {
        const userName = userData.userName;
        const usernameLink = document.createElement("a");
        usernameLink.href = `userProfile.html?userId=${userId}`;
        usernameLink.textContent = userName;
        usernameLink.style.color = "#000080";
        element.appendChild(usernameLink);
      } else {
        console.error(`Unable to get userData for userId: ${userId}`);
      }
    }
  }
}
