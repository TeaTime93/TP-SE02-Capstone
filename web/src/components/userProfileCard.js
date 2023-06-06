import HookClient from '../api/HookClient';
import BindingClass from "../util/bindingClass";

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

        const form = document.createElement('form');
    
        const userData = await this.client.getUser(userId);
        const userCard = this.createUserCard(userData);
    
        form.append(userCard);
        userProfileContainer.append(form);
        userProfileContainer.classList.add("card-content");
    }
    
      
  
    createUserCard(userData) {
        const card = document.createElement('div');
        card.classList.add('card');

        const aboutElement = document.createElement('h2');
        aboutElement.textContent = `About... ${userData.userName}`;
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
      
        console.log('user from createUserCard method: ', userData);
      
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
  
