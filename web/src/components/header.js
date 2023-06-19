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

    // Create the link element
    const homeLink = document.createElement("a");
    homeLink.href = "index.html";
    homeLink.style.display = "flex";
    homeLink.style.alignItems = "center";

    // Create the site logo element
    const siteLogo = document.createElement("img");
    siteLogo.src = "Boat.png"; // adjust as needed
    siteLogo.alt = "Site Logo"; // alternative text for accessibility
    siteLogo.style.width = "50px"; // adjust as needed
    siteLogo.style.height = "50px"; // adjust as needed

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

    // Append the link and user info to the header
    header.appendChild(homeLink);
    header.appendChild(userInfo);
  }

  createUserInfoForHeader(currentUser, hookUser) {
    const userInfo = document.createElement("div");
    userInfo.classList.add("user");
    userInfo.style.position = "relative"; // Set position to relative
  
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
}
