import HookClient from '../api/HookClient';
import BindingClass from "../util/bindingClass";
import StoryCard from "../components/storyCard";

export default class Feed extends BindingClass {
    constructor() {
        super();

        const methodsToBind = [
            'getFeed', 'createButton', 'createLikeButton', 'getStory',
            'nextStory', 'prevStory', 'displayStory'
        ];
        this.bindClassMethods(methodsToBind, this);

        this.client = new HookClient();
        this.feed = [];
        this.storyIndex = 0;
        this.currentUser = null;
    }

    async getFeed(userId) {
        return await this.client.getFeed(userId);
    }

    async getStory(storyId) {
        return await this.client.getStory(storyId);
    }

    async nextStory() {
        if (this.storyIndex < this.feed.length - 1) {
            this.storyIndex++;
            await this.displayStory();
        }
    }

    async prevStory() {
        if (this.storyIndex > 0) {
            this.storyIndex--;
            await this.displayStory();
        }
    }

    async displayStory() {
        const storyCard = new StoryCard();
        const parentElement = document.getElementById('story-card-container');
        parentElement.innerHTML = '';
        await storyCard.addCardToPage(this.feed[this.storyIndex]);
    }

    createButton(text, clickHandler) {
        const button = document.createElement('button');
        button.innerText = text;
        button.addEventListener('click', clickHandler);
        return button;
    }
    

    createLikeButton() {
        const button = this.createButton('Like', this.nextStory);
        button.classList.add('like_button'); 
        return button;
    }

    createDislikeButton() {
        const button = this.createButton('Dislike', this.prevStory);
        button.classList.add('dislike_button'); 
        return button;
    }
    

    async init() {
        this.currentUser = await this.client.getIdentity();
        if (!this.currentUser) {
            this.currentUser = '0000';
        }
        this.user = await this.client.getUserByEmail(this.currentUser.email);
        console.log('The userId is: ', this.user.userId);
        
        this.feed = await this.getFeed(this.user.userId);
        await this.displayStory();
    
        const parentElement = document.getElementById('feed-card-container');
        const buttonContainer = document.createElement('div');
        buttonContainer.classList.add('button-group');
        parentElement.appendChild(buttonContainer);
    
        const dislikeButton = this.createDislikeButton();
        const likeButton = this.createLikeButton();
    
        buttonContainer.appendChild(dislikeButton);
        buttonContainer.appendChild(likeButton);
    }
}
