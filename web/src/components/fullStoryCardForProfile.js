import HookClient from '../api/HookClient';
import BindingClass from "../util/bindingClass";
import { Auth } from '@aws-amplify/auth';

export default class FullStoryCardForProfile extends BindingClass {
    constructor() {
      super();
  
      const methodsToBind = ['fullStory', 'createFullStoryCard', 'createInputField', 'createCard'];
      this.bindClassMethods(methodsToBind, this);
  
      this.client = new HookClient();
    }
  
    async fullStory() {
        const fullStoryContainer = document.getElementById('full-story-container');
        const userId = window.userId;
        const userData = await this.client.getUser(userId);
        let storyData = userData.featured ? await this.client.getStory(userData.featured) : null;
    
        if (!storyData) {
            // If there's no featured story
            if (userData.storiesWritten && userData.storiesWritten.length > 0) {
                // If there are written stories
                await this.autoFeatureStory();
                storyData = await this.client.getStory(userData.storiesWritten[0]); // get the newly featured story
            } else {
                // If there are no written stories
                const messageContainer = document.createElement('div');
                messageContainer.classList.add('card-content');
                const message = document.createElement('p');
                message.innerHTML = 'No stories to feature. Want to feature one? <a href="createStory.html" target="_blank">Get writing!</a>';
                messageContainer.appendChild(message);
                fullStoryContainer.appendChild(messageContainer);
                return; // return early as there's no story to display
            }
        } 
    
        // If there is a featured story
        const form = document.createElement('form');
        const author = await this.client.getUser(storyData.userId);
        const storyCard = this.createFullStoryCard(storyData, author);
        form.append(storyCard);
        fullStoryContainer.append(form);
        fullStoryContainer.classList.add("card-content");
    }
    
  
    createFullStoryCard(story, author) {
        const card = document.createElement('div');
        card.classList.add('card');
      
        const titleElement = document.createElement('h1');
        titleElement.textContent = story.title;
        card.appendChild(titleElement);

        const aboutElement = document.createElement('h2');
        aboutElement.textContent = `by ${author.userName}`;
        card.appendChild(aboutElement);
      
        const contentElement = document.createElement('p');
        contentElement.textContent = story.content;
        card.appendChild(contentElement);
      
        const tagsElement = document.createElement('p');
        tagsElement.textContent = `Tags: ${story.tags.join(', ')}`;
        card.appendChild(tagsElement);

        const likesElement = document.createElement('p');
        likesElement.textContent = `Likes: ${story.likes}`;
        card.appendChild(likesElement);
      
        console.log('story from createFullStoryCard method:', story);
      
        return card;
      }
      
  
    createInputField(id, labelText, type = 'text') {
      const card = document.createElement('div');
      card.classList.add('card');
  
      const label = document.createElement('label');
      label.innerText = labelText;
      const input = document.createElement('input');
      input.id = id;
      input.type = type;
  
      card.append(label, input);
  
      return card;
    }
  
    createCard(id, labelText, type = 'text') {
      const card = document.createElement('div');
      card.classList.add('card');
  
      const inputField = this.createInputField(id, labelText, type);
      card.append(inputField);
  
      return card;
    }

    async autoFeatureStory() {
        this.client = new HookClient();
        const cognitoUser = await Auth.currentAuthenticatedUser();
        const { email, name } = cognitoUser.signInUserSession.idToken.payload;
        const userData = await this.client.getUserByEmail(email);
      
        const viewedUserId = window.userId;
      
        try {
          // Get the first story from storiesWritten
          const firstStory = userData.storiesWritten[0];
    
          // Update the current user's information
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
            firstStory // set the first story as the featured story
          );
      
          const userId = viewedUserData.userId;
          window.location.href = `userProfile.html?userId=${userId}`;
        } catch (error) {
          console.error("An error occurred while editing user: ", error);
        }
      }
    }    