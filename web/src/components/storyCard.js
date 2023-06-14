import HookClient from '../api/HookClient';
import BindingClass from "../util/bindingClass";

export default class StoryCard extends BindingClass {
    constructor() {
        super();

        const methodsToBind = [
            'addCardToPage'
        ];
        this.bindClassMethods(methodsToBind, this);

        this.client = new HookClient();
    }

    async addCardToPage(storyId) {
        this.displayLoadingElement();
        const story = await this.client.getStory(storyId);
        console.log('addCardToPage story: ', story);
        const id = await story.userId;
        console.log('addCardtoPage story.userId: ', id);
        const user = await this.client.getUser(id);
        const authorName = user.userName;

        const cardContainer = document.createElement('div');
        
        cardContainer.classList.add('story-card');
    
        // Create the title element
        const titleElement = document.createElement('h2');
        titleElement.style.textAlign = 'center';
        titleElement.style.fontWeight = 'bold';

        const titleContent = story.title;

        // Create the storyLink element
        const storyLink = document.createElement('a');
        storyLink.href = `fullStory.html?storyId=${story.storyId}`;
        storyLink.textContent = titleContent;
        storyLink.classList.add('story-link');

        titleElement.appendChild(storyLink);
        cardContainer.appendChild(titleElement);
    
        // Create the author element
        const authorElement = document.createElement('p');
        authorElement.style.textAlign = 'center';
        
        const textContent = `by ${authorName}`;

        // Create the author link element
        const authorLink = document.createElement('a');
        authorLink.href = `userProfile.html?userId=${user.userId}`;
        authorLink.textContent = textContent;
        authorLink.classList.add('author-link');

        authorElement.appendChild(authorLink);
        cardContainer.appendChild(authorElement);

    
        // Create the content element
        const storyContent = document.createElement('p');
        storyContent.textContent = story.content;
        cardContainer.appendChild(storyContent);
    
        const parentElement = document.getElementById('story-card-container');
        parentElement.innerHTML = '';
        parentElement.appendChild(cardContainer);
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
