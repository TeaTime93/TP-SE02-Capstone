import HookClient from "../api/HookClient";
import BindingClass from "../util/bindingClass";

/**
 * The header component for the website.
 */
export default class Header extends BindingClass {
  constructor() {
    super();

    const methodsToBind = [
      "addHeaderToPage",
      "createUserInfoForHeader",
      "createLoginButton",
      "createLogoutButton",
      "handleSearch"
    ];
    this.bindClassMethods(methodsToBind, this);

    this.client = new HookClient();
  }

  async addHeaderToPage() {
    const isLoggedIn = await this.client.isUserLoggedIn();
    const currentUser = await this.client.getIdentity();
    let hookUser;
  
    if (isLoggedIn && currentUser && currentUser.email) {
      hookUser = await this.client.getUserByEmail(currentUser.email);
      console.log("hookUser from header:", hookUser);
    } else {
      hookUser = await this.client.getUser("0000");
    }
  
    const userInfo = this.createUserInfoForHeader(currentUser, hookUser);
  
    const header = document.getElementById("header");
    header.style.display = "flex";
    header.style.justifyContent = "space-between";
    header.style.alignItems = "center"; 
  
    // Create div containers for each section
    const leftContainer = document.createElement("div");
    const centerContainer = document.createElement("div");
    const rightContainer = document.createElement("div");
  
    // Create the link element
    const homeLink = document.createElement("a");
    homeLink.href = "index.html";
    homeLink.style.display = "flex";
    homeLink.style.alignItems = "center";
  
    // Create the site logo element
    const siteLogo = document.createElement("img");
    siteLogo.src = "Boat.png"; 
    siteLogo.alt = "Site Logo"; 
    siteLogo.style.width = "50px"; 
    siteLogo.style.height = "50px"; 
  
    // Create the site title element
    const siteTitle = document.createElement("div");
    siteTitle.classList.add("site-title");
    siteTitle.innerText = "Hook";
    siteTitle.style.color = "#000080";
    siteTitle.style.fontWeight = "bold";
    siteTitle.style.fontSize = "32px";
  
    // Append the logo and title to the link element
    homeLink.appendChild(siteLogo);
    homeLink.appendChild(siteTitle);
  
    // Append homeLink to the left container
    leftContainer.appendChild(homeLink);
  
    // Create and append search bar to the center container
    const searchBar = this.createSearchBar();
    centerContainer.appendChild(searchBar);
  
    // Append userInfo to the right container
    rightContainer.appendChild(userInfo);
  
    // Append all containers to the header
    header.appendChild(leftContainer);
    header.appendChild(centerContainer);
    header.appendChild(rightContainer);
  }
  


  createUserInfoForHeader(currentUser, hookUser) {
    const userInfo = document.createElement("div");
    userInfo.classList.add("user");
    userInfo.style.position = "relative"; 

    if (!currentUser) {
      const loginButton = this.createLoginButton();
      loginButton.classList.add("button", "button-primary");
      userInfo.appendChild(loginButton);
    }

    // If currentUser is defined, add dropdown menu
    if (currentUser && hookUser) {
      const dropDownMenu = this.createProfileDropdown(currentUser, hookUser);
      userInfo.appendChild(dropDownMenu);
    }

    return userInfo;
  }

  createLoginButton() {
    return this.createButton("Login", this.client.login);
  }

  createLogoutButton(currentUser) {
    return this.createButton(`Logout: ${currentUser.name}`, this.client.logout);
  }

  createButton(text, clickHandler) {
    const button = document.createElement("a");
    button.classList.add("button");
    button.href = "#";
    button.innerText = text;

    button.addEventListener("click", async () => {
      await clickHandler();
    });

    return button;
  }

  createProfileDropdown(currentUser, hookUser) {
    const dropdown = document.createElement("div");
    dropdown.classList.add("profile-dropdown");

    const profileButton = this.createProfileButton(currentUser, hookUser);
    dropdown.appendChild(profileButton);

    const dropdownContent = this.createDropdownContent(currentUser, hookUser);
    dropdown.appendChild(dropdownContent);

    return dropdown;
  }

  createProfileButton(currentUser, hookUser) {
    const profileButton = document.createElement("button");
    profileButton.classList.add("profile-button");
    profileButton.innerText = hookUser.userName;
    profileButton.style.fontWeight = "bold";
    profileButton.style.fontSize = "20px";

    profileButton.addEventListener("click", () => {
      dropdownContent.classList.toggle("show");
    });

    return profileButton;
  }

  createDropdownContent(currentUser, hookUser) {
    const dropdownContent = document.createElement("div");
    dropdownContent.classList.add("dropdown-content");

    const viewProfileButton = this.createViewProfileButton(hookUser);
    dropdownContent.appendChild(viewProfileButton);

    const createStoryButton = this.createCreateStoryButton();
    dropdownContent.appendChild(createStoryButton);

    const logoutOption = this.createLogoutOption(currentUser);
    dropdownContent.appendChild(logoutOption);

    return dropdownContent;
  }

  createViewProfileButton(hookUser) {
    const viewProfileButton = document.createElement("a");
    viewProfileButton.classList.add("view-profile-button");
    const userId = hookUser.userId;
    viewProfileButton.href = `userProfile.html?userId=${userId}`;
    viewProfileButton.innerText = "Profile";
    viewProfileButton.style.fontWeight = "bold";
    viewProfileButton.style.fontSize = "20px";

    return viewProfileButton;
  }

  createCreateStoryButton() {
    const createStoryButton = document.createElement("a");
    createStoryButton.classList.add("view-profile-button");
    createStoryButton.href = `createStory.html`;
    createStoryButton.innerText = "Story";
    createStoryButton.style.fontWeight = "bold";
    createStoryButton.style.fontSize = "20px";

    return createStoryButton;
  }

  createLogoutOption(currentUser) {
    const logoutOption = document.createElement("button");
    logoutOption.classList.add("logout-option");
    logoutOption.classList.add("button", "button-primary");
    logoutOption.innerText = "Logout";

    // Add event listener to handle logout
    logoutOption.addEventListener("click", async () => {
      await this.client.logout();
      // Redirect or perform any additional logic after logout
    });

    return logoutOption;
  }

  createSearchBar() {
    // create form for search bar
    const form = document.createElement("form");
    form.classList.add("search-bar");
    form.addEventListener("submit", this.handleSearch);

    // create input field
    const input = document.createElement("input");
    input.type = "text";
    input.id = "storySearchInput";
    input.name = "storySearch";
    input.placeholder = "Search by title and user ID...";

    // create submit button
    const submitButton = document.createElement("button");
    submitButton.type = "submit";
    submitButton.innerText = "Search";

    // add elements to form
    form.appendChild(input);
    form.appendChild(submitButton);

    return form;
  }

  async handleSearch(event) {
    event.preventDefault();
    const inputField = document.getElementById("storySearchInput");
    const searchText = inputField.value;
  
    const [title, userId] = searchText.split(",").map((e) => e.trim());
    console.log('from handleSearch title: ', title);
    console.log('from handleSearch userId: ', userId);
    if (title && userId) {
      const story = await this.client.getStoryByTitleAndAuthor(title, userId);
      console.log('Story data from API:', story);
      if (story && story.storyId) {
        window.location.href = `fullStory.html?storyId=${story.storyId}`;
      } else {
        console.log('Story not found or storyId missing');
      }
    }    
}

}
