import HookClient from "../api/HookClient";
import BindingClass from "../util/bindingClass";
import Authenticator from "../api/authenticator";
import Animate from "./animate";
import createDOMPurify from "dompurify";
import CreateCommentsAction from "./createCommentsAction";
const DOMPurify = createDOMPurify(window);

export default class CommentsCard extends BindingClass {
  constructor() {
    super();

    const methodsToBind = [
      "comments",
      "createCommentsCard",
      "createCommentsSection",
      "refreshComments",
    ];
    this.bindClassMethods(methodsToBind, this);

    this.client = new HookClient();
    this.authenticator = new Authenticator();
    this.animate = new Animate();
    this.createComments = new CreateCommentsAction();
  }

  async comments() {
    const commentsContainer = document.getElementById("comments-container");

    const storyId = window.storyId;
    console.log("storyId from fullStory:", storyId);

    const form = document.createElement("form");

    try {
      const storyData = await this.client.getStory(storyId);
      const author = await this.client.getUser(storyData.userId);
      console.log("author from commentsCard:", author);

      const currentUserEmail = await this.authenticator.getCurrentUserInfo();
      const currentUser = await this.client.getUserByEmail(
        currentUserEmail.email
      );

      // Fetch existing comments
      let commentsData = await this.client.getComments(storyId);
      console.log("commentsData from commentsCard:", commentsData);

      // Auto-generate comments if current user is not the author and no comments exist
      if (currentUser && currentUser.userId !== author.userId) {
        if (!commentsData || commentsData.length === 0) {
          commentsData = await this.client.createComments(storyId);
        }
      }

      // If commentsData still doesn't exist or is in an unexpected format, create an empty object
      if (
        !commentsData ||
        !commentsData.posComments ||
        !commentsData.negComments
      ) {
        commentsData = { posComments: [], negComments: [] };
        console.warn("Invalid or no comments data:", commentsData);
      }

      const commentsCard = this.createCommentsCard(
        commentsData,
        author,
        currentUser
      );

      const selectedPosOption = this.positiveCommentsSection.dropdown.value;
      const selectedNegOption = this.negativeCommentsSection.dropdown.value;

      form.append(commentsCard);
      commentsContainer.append(form);
      if (currentUser && currentUser.userId !== author.userId) {
        const submitButton = document.createElement("button");
        submitButton.textContent = "Submit Comments";
        submitButton.classList.add("button", "button-secondary");
        submitButton.style.width = "200px"; // Adjust size of the button here
        submitButton.addEventListener("click", async (event) => {
          event.preventDefault();
          submitButton.textContent = "Loading...";
        
          const selectedPosOption = this.positiveCommentsSection.dropdown.value;
          const selectedNegOption = this.negativeCommentsSection.dropdown.value;
        
          if (!selectedPosOption || !selectedNegOption) {
            alert("You must select both a positive and negative comment!");
            return;
          }
        
          await this.createComments.createComments(
            selectedPosOption,
            selectedNegOption
          );
          await this.refreshComments();
        });

        commentsContainer.appendChild(submitButton);
      }
      commentsContainer.classList.add("card-content");
      this.animate.addCardAnimations();
    } catch (error) {
      console.error("Error retrieving story data:", error);
    }
  }

  createCommentsCard(comments, author, currentUser) {
    const card = document.createElement("div");
    card.classList.add("card");
    card.style.opacity = 0;
    card.style.display = "flex";
    card.style.width = "100%";
    card.style.flexDirection = "column";

    const positiveComments = Array.isArray(comments.posComments)
      ? comments.posComments
      : [];
    const negativeComments = Array.isArray(comments.negComments)
      ? comments.negComments
      : [];

      this.positiveCommentsSection = this.createCommentsSection(
        positiveComments,
        "Positive Comments",
        currentUser,
        author,
      [
        "Engaging narrative style. Kept me hooked.",
        "Vivid descriptions. Felt real and alive.",
        "Fluid dialogue. Revealed character personalities.",
        "Intriguing plot with unexpected twists.",
        "Unique and interesting perspective. Stands out.",
        "Well-paced. Good tension build-up.",
        "Handled themes with nuance and respect.",
        "Satisfying ending. Left room for imagination.",
      ]
    );

    this.negativeCommentsSection = this.createCommentsSection(
      negativeComments,
      "Negative Comments",
      currentUser,
      author,
      [
        "Some parts felt rushed. Explore more.",
        "Characters seemed one-dimensional. Add depth.",
        "Dialogue feels formal at times. More conversational?",
        "Abrupt ending. Needs gradual resolution.",
        "Descriptions overtake narrative. Balance needed.",
        "Conflict resolved too easily. More obstacles?",
        "Inconsistent point of view. Confuses readers.",
        "Instances of telling over showing. Show more.",
        "Theme not woven into narrative. Subtler?",
      ]
    );

    // Create a container for the submit button
    const buttonContainer = document.createElement("div");
    buttonContainer.style.display = "flex";
    buttonContainer.style.justifyContent = "center";
    buttonContainer.style.alignItems = "center";
    buttonContainer.style.marginTop = "20px";
    buttonContainer.style.marginBottom = "20px";
    buttonContainer.style.padding = "10px";

    card.appendChild(this.positiveCommentsSection.section);
    card.appendChild(this.negativeCommentsSection.section);
    card.appendChild(buttonContainer); // Append the button container to the card

    return card;
  }


  createCommentsSection(comments, sectionTitle, currentUser, author, options) {
    const commentsSection = document.createElement("div");
    commentsSection.classList.add("comments-section");
    commentsSection.style.flexGrow = "1";

    const titleElement = document.createElement("h2");
    titleElement.textContent = sectionTitle;
    commentsSection.appendChild(titleElement);

    const commentsList = document.createElement("ul"); // Create an unordered list element

    // Check if comments exist and display them or display no comments message
    if (comments && comments.length > 0) {
      for (const comment of comments) {
        const commentElement = document.createElement("li"); // Create list item for each comment
        commentElement.textContent = comment;
        commentsList.appendChild(commentElement);
      }
      commentsSection.appendChild(commentsList); // Append the list to the section
    } else {
      const noCommentsMsg = document.createElement("p");
      noCommentsMsg.textContent = "No comments!";
      commentsSection.appendChild(noCommentsMsg);
    }

    let commentDropdown;

    // Only create a dropdown if the currentUser exists and is not the author
    if (currentUser && currentUser.userId !== author.userId) {
      commentDropdown = document.createElement("select");
      for (const option of options) {
        const optionElement = document.createElement("option");
        optionElement.value = option;
        optionElement.textContent = option;
        commentDropdown.appendChild(optionElement);
      }
      commentsSection.appendChild(commentDropdown);
    }

    return { section: commentsSection, dropdown: commentDropdown };
  }

  async refreshComments() {
    const commentsContainer = document.getElementById("comments-container");
    commentsContainer.innerHTML = ""; // Clear existing comments

    // Run the existing logic to fetch and display comments
    await this.comments();
  }
}
