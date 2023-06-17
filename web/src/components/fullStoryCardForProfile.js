import HookClient from "../api/HookClient";
import BindingClass from "../util/bindingClass";
import { Auth } from "@aws-amplify/auth";
import createDOMPurify from "dompurify";
const DOMPurify = createDOMPurify(window);


export default class FullStoryCardForProfile extends BindingClass {
  constructor() {
    super();

    const methodsToBind = [
      "fullStory",
      "createFullStoryCard",
      "createInputField",
      "createCard",
    ];
    this.bindClassMethods(methodsToBind, this);

    this.client = new HookClient();
  }

  async fullStory() {
    const fullStoryContainer = document.getElementById("full-story-container");
    const userId = window.userId;
    const userData = await this.client.getUser(userId);
    let storyData = userData.featured
      ? await this.client.getStory(userData.featured)
      : null;
  
    if(!storyData) {
      const noStoryCard = document.createElement("div");
      noStoryCard.classList.add("card");
      
      const noStoryText = document.createElement("p");
      noStoryText.textContent = "No featured story.";
      noStoryCard.append(noStoryText);
      
      fullStoryContainer.append(noStoryCard);
      return; // return early since there's no story data to process
    }
  
    const form = document.createElement("form");
    const author = await this.client.getUser(storyData.userId);
    const storyCard = this.createFullStoryCard(storyData, author);
    form.append(storyCard);
    fullStoryContainer.append(form);
    fullStoryContainer.classList.add("card-content");
  }
  

  createFullStoryCard(story, author) {
    const card = document.createElement("div");
    card.classList.add("card");

    const titleElement = document.createElement("h1");
    titleElement.textContent = story.title;
    titleElement.style.textAlign = "center";
    card.appendChild(titleElement);

    const aboutElement = document.createElement("h2");
    aboutElement.textContent = `by ${author.userName}`;
    aboutElement.style.textAlign = "center";
    card.appendChild(aboutElement);

    const contentElement = document.createElement("div");
    contentElement.innerHTML = DOMPurify.sanitize(story.content);
    card.appendChild(contentElement);

    const tagsElement = document.createElement("p");
    tagsElement.textContent = `Tags: ${story.tags.join(", ")}`;
    card.appendChild(tagsElement);

    const likesElement = document.createElement("p");
    likesElement.textContent = `Likes: ${story.likes}`;
    card.appendChild(likesElement);

    console.log("story from createFullStoryCard method:", story);

    return card;
  }

  createInputField(id, labelText, type = "text") {
    const card = document.createElement("div");
    card.classList.add("card");

    const label = document.createElement("label");
    label.innerText = labelText;
    const input = document.createElement("input");
    input.id = id;
    input.type = type;

    card.append(label, input);

    return card;
  }

  createCard(id, labelText, type = "text") {
    const card = document.createElement("div");
    card.classList.add("card");

    const inputField = this.createInputField(id, labelText, type);
    card.append(inputField);

    return card;
  }

  async autoFeatureStory(userData, storyId) {
    const storyData = await this.client.getStory(storyId);
    if (!storyData) {
      // If there's no featured story
      if (userData.storiesWritten && userData.storiesWritten.length > 0) {
        try {
          const firstStory = userData.storiesWritten[0];
  
          await this.client.editUser(
            userData.userId,
            userData.userName,
            userData.email,
            userData.bio,
            userData.age,
            userData.follows,
            userData.followers,
            userData.favorites,
            userData.userScore,
            userData.storiesWritten,
            firstStory
          );
  
          const userId = userData.userId;
          window.location.href = `userProfile.html?userId=${userId}`;
        } catch (error) {
          console.error("An error occurred while editing user: ", error);
        }
      }
    }
  }
  
}
