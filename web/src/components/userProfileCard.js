import HookClient from '../api/HookClient';
import BindingClass from "../util/bindingClass";
import { Auth } from '@aws-amplify/auth';

export default class UserProfileCard extends BindingClass {
    constructor() {
      super();
  
      const methodsToBind = ['userProfileInformation', 'createUserCard', 'createInputField', 'createCard'];
      this.bindClassMethods(methodsToBind, this);
  
      this.client = new HookClient();
    }
  
    async userProfileInformation() {
      const userProfileContainer = document.getElementById('user-profile-container');
      const userId = window.userId;
      const thisUser = await this.getCurrentUserInfo();
      const thisUserData = await this.client.getUserByEmail(thisUser.email);
    
      const form = document.createElement('form');
      const userData = await this.client.getUser(userId);
      const userCard = this.createUserCard(userData);
    
      form.append(userCard);
      userProfileContainer.append(form);
      userProfileContainer.classList.add('card-content');
    
      if (userData.email === thisUser.email) {
        // Create an edit button
        const editButton = document.createElement('button');
        editButton.textContent = 'Edit';
        editButton.classList.add('button');
        form.append(editButton);
    
        // When edit button is clicked
        editButton.addEventListener('click', (event) => {
          event.preventDefault();  // prevent form submission
          
          // Remove the userCard
          form.removeChild(userCard);
          
          // Remove the edit button
          form.removeChild(editButton);
    
          // Create and append the editCard
          const editCard = this.createEditCard(userData);
          form.append(editCard);
        });
    
        form.addEventListener('submit', this.submitForm);
      } else {
        const isFollowing = thisUserData.follows.includes(userId);
        if (isFollowing) {
          const unfollowButton = document.createElement('button');
          unfollowButton.type = 'button';
          unfollowButton.textContent = 'Unfollow User';
          unfollowButton.classList.add('button');
          unfollowButton.addEventListener('click', this.unfollowUser);
          form.append(unfollowButton);
        } else {
          const followButton = document.createElement('button');
          followButton.type = 'button';
          followButton.textContent = 'Follow User';
          followButton.classList.add('button');
          followButton.addEventListener('click', this.followUser);
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
    const card = document.createElement('div');
    card.classList.add("card"); 

    const userNameField = this.createInputField('userName', 'Username', userData.userName);
    const bioField = this.createInputField('bio', 'Bio', userData.bio);
    const ageField = this.createInputField('age', 'Age', userData.age, 'number');

    card.append(userNameField, bioField, ageField);

    const saveButton = document.createElement('button');
    saveButton.type = 'submit';
    saveButton.textContent = 'Save Changes';
    card.append(saveButton);

    return card;
  }
  
  createInputField(id, labelText, initialValue = '', type = 'text') {
    const inputGroup = document.createElement('div');
    inputGroup.classList.add("form-field"); 
  
    // Create label
    const label = document.createElement('label');
    label.for = id;
    label.textContent = labelText;
    label.classList.add("input-label");
  
    // Create input
    const input = document.createElement('input');
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
    const userName = document.getElementById('userName').value;
    const bio = document.getElementById('bio').value;
    const age = document.getElementById('age').value;
    
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
        userData.userScore
      );
      const userId = userData.userId;
      window.location.href = `userProfile.html?userId=${userId}`;
    } catch (error) {
      console.error("An error occurred while editing user: ", error);
    }
  }
  
  createUserCard(userData) {
    const card = document.createElement('div');
    card.classList.add('card');

    // Name
    this.addLabelAndContent(card, 'Name', userData.userName, 'user-name');

    // Email
    this.addLabelAndContent(card, 'Email', userData.email, 'user-email');

    // Bio
    this.addLabelAndContent(card, 'Bio', userData.bio, 'user-bio');

    // Age
    this.addLabelAndContent(card, 'Age', userData.age.toString(), 'user-age');

    // Follows
    const followsElement = this.createLabelAndContent('Follows', '');
    this.appendUserLinks(userData.follows, followsElement);
    card.appendChild(followsElement);

    // Followers
    const followersElement = this.createLabelAndContent('Followers', '');
    this.appendUserLinks(userData.followers, followersElement);
    card.appendChild(followersElement);

    // Favorites
    const favoritesElement = this.createLabelAndContent('Favorites', '');
    this.appendStoryLinks(userData.favorites, favoritesElement);
    card.appendChild(favoritesElement);

    return card;
}

createLabelAndContent(labelText, contentText, className) {
    const label = document.createElement('h3');
    label.textContent = `${labelText}:`;
    label.classList.add(`${className}-label`);

    const content = document.createElement('p');
    content.textContent = contentText;
    content.classList.add(className);

    const container = document.createElement('div');
    container.appendChild(label);
    container.appendChild(content);

    return container;
}

addLabelAndContent(parent, labelText, contentText, className) {
    const element = this.createLabelAndContent(labelText, contentText, className);
    parent.appendChild(element);
}

appendUserLinks(userIds, element) {
    userIds.forEach((userId, index) => {
        if (index > 0) {
            element.appendChild(document.createTextNode(', '));
        }
        const usernameLink = document.createElement('a');
        usernameLink.href = `userProfile.html?userId=${userId}`;
        usernameLink.textContent = userId; 
        usernameLink.style.color = '#000080';
        element.appendChild(usernameLink);
    });
}

appendStoryLinks(storyIds, element) {
    storyIds.forEach((storyId, index) => {
        if (index > 0) {
            element.appendChild(document.createTextNode(', '));
        }
        const storyLink = document.createElement('a');
        storyLink.href = `fullStory.html?storyId=${storyId}`;
        storyLink.textContent = storyId; 
        storyLink.style.color = '#000080';
        element.appendChild(storyLink);
    });
}


  
  
    createCard(id, labelText, type = 'text') {
      const card = document.createElement('div');
      card.classList.add('card');
  
      const inputField = this.createInputField(id, labelText, type);
      card.append(inputField);
  
      return card;
    }

    async followUser(event) {
      this.client = new HookClient();
      event.preventDefault();
      const cognitoUser = await Auth.currentAuthenticatedUser();
      const { email, name } = cognitoUser.signInUserSession.idToken.payload;
      const userData = await this.client.getUserByEmail(email);
      
      const viewedUserId = window.userId;
      
      try {
        // Add the viewed user to the follows list
        const updatedFollows = [...userData.follows, viewedUserId];
      
        await this.client.editUser(
          userData.userId,
          userData.userName,
          userData.email,
          userData.bio,
          userData.age,
          updatedFollows,
          userData.followers,
          userData.favorites,
          userData.userScore
        );

        const viewedUserData = await this.client.getUser(viewedUserId);
        const updatedFollowers = [...viewedUserData.followers, userData.userId];
        await this.client.editUser(
          viewedUserData.userId,
          viewedUserData.userName,
          viewedUserData.email,
          viewedUserData.bio,
          viewedUserData.age,
          viewedUserData.follows,
          updatedFollowers,
          viewedUserData.favorites,
          viewedUserData.userScore
        );
      
        const userId = viewedUserData.userId;
        window.location.href = `userProfile.html?userId=${userId}`;
      } catch (error) {
        console.error("An error occurred while editing user: ", error);
      }
    }
  
    async unfollowUser(event) {
      this.client = new HookClient();
      event.preventDefault();
      const cognitoUser = await Auth.currentAuthenticatedUser();
      const { email, name } = cognitoUser.signInUserSession.idToken.payload;
      const userData = await this.client.getUserByEmail(email);
    
      const viewedUserId = window.userId;
    
      try {
        const updatedFollows = userData.follows.filter(userId => userId !== viewedUserId);
        const viewedUserData = await this.client.getUser(viewedUserId);
        const updatedFollowers = viewedUserData.followers.filter(userId => userId !== userData.userId);
    
        // Update the current user's information
        await this.client.editUser(
          userData.userId,
          userData.userName,
          userData.email,
          userData.bio,
          userData.age,
          updatedFollows,
          userData.followers,
          userData.favorites,
          userData.userScore
        );
    
        // Update the viewed user's information
        await this.client.editUser(
          viewedUserData.userId,
          viewedUserData.userName,
          viewedUserData.email,
          viewedUserData.bio,
          viewedUserData.age,
          viewedUserData.follows,
          updatedFollowers,
          viewedUserData.favorites,
          viewedUserData.userScore
        );
    
        const userId = viewedUserData.userId;
        window.location.href = `userProfile.html?userId=${userId}`;
      } catch (error) {
        console.error("An error occurred while editing user: ", error);
      }
    }

    displayLoadingElement() {
      const parentElement = document.getElementById('story-card-container');
      parentElement.innerHTML = '';

      const loadingContainer = document.createElement('div');
      loadingContainer.classList.add('loading-card');
  
      const loadingElement = document.createElement('p');
      loadingElement.textContent = 'Loading...';
      loadingElement.style.textAlign = 'center';
      loadingContainer.appendChild(loadingElement);
  
      parentElement.appendChild(loadingContainer);
  }
  }
  
