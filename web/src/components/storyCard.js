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
        const story = await this.client.getStory(storyId); 
    
        const cardContainer = document.createElement('div');
        cardContainer.classList.add('story-card');
    
        // Create the title element
        const titleElement = document.createElement('h2');
        titleElement.textContent = story.title;
        titleElement.style.textAlign = 'center';
        titleElement.style.fontWeight = 'bold';
        cardContainer.appendChild(titleElement);
    
        // Create the author element
        const authorElement = document.createElement('p');
        authorElement.textContent = `by ${story.userId}`;
        authorElement.style.textAlign = 'center';
        cardContainer.appendChild(authorElement);
    
        // Create the content element
        const storyContent = document.createElement('p');
        storyContent.textContent = story.content;
        cardContainer.appendChild(storyContent);
    
        const parentElement = document.getElementById('story-card-container');
        parentElement.appendChild(cardContainer);
    }
    
}
