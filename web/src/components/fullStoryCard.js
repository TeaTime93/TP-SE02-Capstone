import HookClient from "../api/HookClient";
import BindingClass from "../util/bindingClass";
import Authenticator from "../api/authenticator";
import Animate from "./animate";
import createDOMPurify from "dompurify";
import unsubmitLikeDislike from "./unsubmitLikeDislike";
const DOMPurify = createDOMPurify(window);

export default class FullStoryCard extends BindingClass {
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
    this.authenticator = new Authenticator();
    this.animate = new Animate();
    this.submit = new unsubmitLikeDislike();
  }

  async fullStory() {
  const fullStoryContainer = document.getElementById("full-story-container");

  const storyId = window.storyId;
  console.log("storyId from fullStory:", storyId);

  const form = document.createElement("form");

  try {
    const storyData = await this.client.getStory(storyId);
    console.log("storyData from fullStoryCard:", storyData);
    const author = await this.client.getUser(storyData.userId);
    console.log("author from fullStoryCard:", author);
    const currentUserEmail = await this.authenticator.getCurrentUserInfo();
    const currentUser = await this.client.getUserByEmail(
      currentUserEmail.email
    );

    const storyCard = this.createFullStoryCard(storyData, author, currentUser);

    form.append(storyCard);
    fullStoryContainer.append(form);
    fullStoryContainer.classList.add("card-content");
    this.animate.addCardAnimations();
  } catch (error) {
    console.error("Error retrieving story data:", error);
  }
}


createFullStoryCard(story, author, currentUser) {
  const card = document.createElement("div");
  card.classList.add("card");
  card.style.opacity = 0;

  const titleElement = document.createElement("h1");
  titleElement.textContent = story.title;
  titleElement.style.textAlign = "center";
  card.appendChild(titleElement);

  const authorElement = document.createElement("h2");
  authorElement.style.textAlign = "center";

  const textContent = `by ${author.userName}`;

  const authorLink = document.createElement("a");
  authorLink.href = `userProfile.html?userId=${author.userId}`;
  authorLink.textContent = textContent;
  authorLink.classList.add("author-link");

  authorElement.appendChild(authorLink);
  card.appendChild(authorElement);

  const contentElement = document.createElement("div");
  contentElement.innerHTML = DOMPurify.sanitize(story.content);
  card.appendChild(contentElement);

  const tagsElement = document.createElement("p");
  tagsElement.textContent = `Tags: ${story.tags.join(", ")}`;
  card.appendChild(tagsElement);

  const likesElement = document.createElement("p");
  likesElement.textContent = `Likes: ${story.likes}`;
  card.appendChild(likesElement);

  // Check if the story is in favorites or disliked stories of the current user
  const inFavorites = currentUser && currentUser.favorites.includes(story.storyId);
  const inDislikedStories = currentUser && currentUser.dislikedStories.includes(story.storyId);

  if (currentUser) {
    // Check if the currentUser is the author of the story
    if (currentUser.userId === author.userId) {
      // Show delete button
      const deleteButton = document.createElement("button");
      deleteButton.textContent = "Delete";
      deleteButton.classList.add("button", "button-primary");
      deleteButton.addEventListener("click", (event) => {
        event.preventDefault();
        this.handleDelete(story.storyId);
      });
      card.appendChild(deleteButton);
    } else {
      if (inFavorites) {
        // Show unlike button
        const unlikeButton = document.createElement("button");
        unlikeButton.textContent = "Unlike";
        unlikeButton.classList.add("button", "button-primary");
        unlikeButton.addEventListener("click", (event) => {
          event.preventDefault();
          unlikeButton.textContent = "Loading...";
          this.submit.unsubmitLike(story.storyId); // Call the unsubmitLike method from the unsubmitLikeDislike class
        });
        card.appendChild(unlikeButton);
      } else if (inDislikedStories) {
        // Show undislike button
        const undislikeButton = document.createElement("button");
        undislikeButton.textContent = "Undislike";
        undislikeButton.classList.add("button", "button-primary");
        undislikeButton.addEventListener("click", (event) => {
          event.preventDefault();
          undislikeButton.textContent = "Loading...";
          this.submit.unsubmitDislike(story.storyId); // Call the unsubmitDislike method from the unsubmitLikeDislike class
        });
        card.appendChild(undislikeButton);
      } else {
        // Show like and dislike buttons
        const likeButton = document.createElement("button");
        likeButton.textContent = "Like";
        likeButton.classList.add("button", "button-primary");
        likeButton.addEventListener("click", (event) => {
          event.preventDefault();
          likeButton.textContent = "Loading...";
          this.submit.submitDislike(story.storyId);
        });
        card.appendChild(likeButton);

        const dislikeButton = document.createElement("button");
        dislikeButton.textContent = "Dislike";
        dislikeButton.classList.add("button", "button-primary");
        dislikeButton.addEventListener("click", (event) => {
          event.preventDefault();
          dislikeButton.textContent = "Loading...";
          this.submit.submitLike(story.storyId);
        });
        card.appendChild(dislikeButton);
      }
    }
  }

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
  async handleDelete(storyId) {
    try {
      console.log('handleDeleting storyId: ', storyId);
      const success = await this.client.deleteStory(storyId);
      if (success) {
        console.log("Story deleted successfully");
  
        // Fetch the current user
        const currentUserEmail = await this.authenticator.getCurrentUserInfo();
        const currentUser = await this.client.getUserByEmail(currentUserEmail.email);
  
        // Remove the storyId from the user's storiesWritten
        currentUser.storiesWritten = currentUser.storiesWritten.filter(id => id !== storyId);
        
        // Check if the deleted story is the featured story, if so, remove it
        if (currentUser.featured === storyId) {
          currentUser.featured = '';
        }
  
        // Update the user
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
          currentUser.featured,
          currentUser.dislikedStories,
          currentUser.preferredTags
        );
        window.location.href = `userProfile.html?userId=${currentUser.userId}`;
      }
    } catch (error) {
      console.error("Error deleting story:", error);
    }
  }
}
