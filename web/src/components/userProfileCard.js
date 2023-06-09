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
        const editCard = this.createEditCard(userData);
        form.append(editCard);
        form.addEventListener('submit', this.submitForm);
      } else {
        const isFollowing = thisUserData.follows.includes(userId);
        if (isFollowing) {
          const unfollowButton = document.createElement('button');
          unfollowButton.type = 'button';
          unfollowButton.textContent = 'Unfollow User';
          unfollowButton.addEventListener('click', this.unfollowUser);
          form.append(unfollowButton);
        } else {
          const followButton = document.createElement('button');
          followButton.type = 'button';
          followButton.textContent = 'Follow User';
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

        const aboutElement = document.createElement('h2');
        aboutElement.textContent = `About...`;
        card.appendChild(aboutElement);
    
        const nameElement = document.createElement('h1');
        nameElement.textContent = userData.userName;
        card.appendChild(nameElement);
      
        const emailElement = document.createElement('p');
        emailElement.textContent = userData.email;
        card.appendChild(emailElement);
      
        const bioElement = document.createElement('p');
        bioElement.textContent = userData.bio;
        bioElement.classList.add('user-bio');
        card.appendChild(bioElement);
      
        const ageElement = document.createElement('p');
        ageElement.textContent = `Age: ${userData.age}`;
        card.appendChild(ageElement);

        const followsElement = document.createElement('p');
        followsElement.textContent = 'Follows: ';
        userData.follows.forEach((userId, index) => {
          if (index > 0) {
            followsElement.appendChild(document.createTextNode(', '));
          }
          const usernameLink = document.createElement('a');
          usernameLink.href = `userProfile.html?userId=${userId}`;
          usernameLink.textContent = userId; 
          usernameLink.style.color = '#000080';
          followsElement.appendChild(usernameLink);
        });
        card.appendChild(followsElement);

        const followersElement = document.createElement('p');
        followersElement.textContent = 'Followers: ';
        userData.followers.forEach((userId, index) => {
          if (index > 0) {
            followersElement.appendChild(document.createTextNode(', '));
          }
          const usernameLink = document.createElement('a');
          usernameLink.href = `userProfile.html?userId=${userId}`;
          usernameLink.textContent = userId; 
          usernameLink.style.color = '#000080';
          followersElement.appendChild(usernameLink);
        });
        card.appendChild(followersElement);
        

        const favoritesElement = document.createElement('p');
        favoritesElement.textContent = `Favorites: ${userData.favorites}`;
        card.appendChild(favoritesElement);
      
        console.log('user from createUserCard method: ', userData);
      
        return card;
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
  }
  
