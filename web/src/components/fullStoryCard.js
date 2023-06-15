import HookClient from '../api/HookClient';
import BindingClass from "../util/bindingClass";

export default class FullStoryCard extends BindingClass {
    constructor() {
      super();
  
      const methodsToBind = ['fullStory', 'createFullStoryCard', 'createInputField', 'createCard'];
      this.bindClassMethods(methodsToBind, this);
  
      this.client = new HookClient();
    }
  
    async fullStory() {
        const fullStoryContainer = document.getElementById('full-story-container');
        
        const storyId = window.storyId;
        console.log('storyId from fullStory:', storyId);


        const form = document.createElement('form');
    
        const storyData = await this.client.getStory(storyId);
        console.log('storyData from fullStoryCard: ', storyData);
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
  }