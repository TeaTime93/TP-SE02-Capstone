import HookClient from "../api/HookClient";
import BindingClass from "../util/bindingClass";
import { Auth } from "@aws-amplify/auth";
import Quill from "quill";
import Animate from "./animate";
import createDOMPurify from "dompurify";
const DOMPurify = createDOMPurify(window);

export default class CreateStoryCard extends BindingClass {
  constructor() {
    super();

    const methodsToBind = ["createStoryForm", "createInputField", "submitForm"];
    this.bindClassMethods(methodsToBind, this);

    this.client = new HookClient();
    this.quill = null;
    this.animate = new Animate();
  }

  createStoryForm() {
    const formContainer = document.getElementById("form-container");

    // Create form element
    const form = document.createElement("form");
    form.addEventListener("submit", this.submitForm.bind(this));

    // Create input elements
    const storyTitleInput = this.createCard("title", "Title");
    const content = this.createCard("content", "Content");
    const tags = this.createCard("tags", "Tags");

    // Create submit button
    const submitButton = document.createElement("button");
    submitButton.type = "submit";
    submitButton.textContent = "Submit";
    submitButton.classList.add("button", "button-secondary");
    submitButton.addEventListener("click", () => {
      submitButton.textContent = "Loading...";
    });
    submitButton.addEventListener("click", this.submitForm.bind(this));

    // Append elements to form
    form.append(storyTitleInput, content, tags, submitButton);

    // Append form to formContainer
    formContainer.append(form);
    formContainer.classList.add("card-content");
    this.animate.addCardAnimations();

    this.quill = new Quill(".quill-editor", {
      theme: "snow",
    });
  }

  createCard(id, labelText, type = "text") {
    const card = document.createElement("div");
    card.classList.add("card");
    card.style.opacity = 0;

    if (id === "title" || id === "content") {
      card.classList.add("create-story-card");
    }

    if (id === "tags") {
      card.classList.add("multi-column");
    }

    const inputField = this.createInputField(id, labelText, type);
    card.append(inputField);

    return card;
  }

  createInputField(id, labelText, type = "text") {
    const inputGroup = document.createElement("div");
    inputGroup.classList.add("form-field");

    // Create label
    const label = document.createElement("label");
    label.for = id;
    label.textContent = labelText;
    label.classList.add("input-label");

    let input;

    if (id === "content") {
      // Create div
      input = document.createElement("div");
      input.id = id;
      input.classList.add("quill-editor");
    } else if (id === "tags") {
      input = document.createElement("div");
      input.id = id;

      const tags = [
        "Fiction",
        "Non-Fiction",
        "Mystery",
        "Fantasy",
        "Science Fiction",
        "Horror",
        "Romance",
        "Thriller",
        "Comedy",
        "Adventure",
        "Drama",
        "Mature",
      ];

      for (let tag of tags) {
        let checkboxWrapper = document.createElement("div");
        checkboxWrapper.classList.add("checkbox-wrapper");

        let checkbox = document.createElement("input");
        checkbox.type = "checkbox";
        checkbox.id = tag;
        checkbox.value = tag;

        let checkboxLabel = document.createElement("label");
        checkboxLabel.htmlFor = tag;
        checkboxLabel.textContent = tag;

        checkboxWrapper.appendChild(checkbox);
        checkboxWrapper.appendChild(checkboxLabel);
        input.appendChild(checkboxWrapper);
      }
    } else {
      input = document.createElement("input");
      input.type = type;
    }

    input.id = id;
    input.classList.add("validated-field");

    inputGroup.append(label, input);

    return inputGroup;
  }

  async submitForm(event) {
    event.preventDefault();
    const cognitoUser = await Auth.currentAuthenticatedUser();
    const { email, name } = cognitoUser.signInUserSession.idToken.payload;
    const user = await this.client.getUserByEmail(email);
    const title = document.getElementById("title").value;

    // Get story content
    let content = this.quill.root.innerHTML;
    let rawContent = this.quill.root.innerHTML;
    let sanitizedContent = DOMPurify.sanitize(rawContent);
    const snippet = content.substring(0, 300);

    // Get tags from checkboxes
    let tags = [];
    const tagsDiv = document.getElementById("tags");
    for (let checkbox of tagsDiv.getElementsByTagName("input")) {
      if (checkbox.checked) {
        tags.push(checkbox.value);
      }
    }

    try {
      const storyData = await this.client.createStory(
        user.userId,
        title,
        sanitizedContent,
        snippet,
        tags
      );
      console.log("createStory returned storydata: ", storyData);
      const updatedStoriesWritten = [...user.storiesWritten, storyData.storyId];
      await this.client.editUser(
        user.userId,
        user.userName,
        user.email,
        user.bio,
        user.age,
        user.follows,
        user.followers,
        user.favorites,
        user.userScore,
        updatedStoriesWritten,
        user.featured,
        user.dislikedStories,
        user.preferredTags
      );
      const redirectId = storyData.storyId;
      window.location.href = `fullStory.html?storyId=${redirectId}`;
    } catch (error) {
      console.error("An error occurred while creating story: ", error);
    }
  }
}
