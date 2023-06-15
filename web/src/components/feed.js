import HookClient from '../api/HookClient';
import BindingClass from "../util/bindingClass";
import StoryCard from "../components/storyCard";
import { Auth } from 'aws-amplify';

export default class Feed extends BindingClass {
    constructor() {
        super();

        const methodsToBind = [
            'getFeed', 'createButton', 'createLikeButton', 'getStory',
            'nextStory', 'prevStory', 'displayStory', 'submitLike'
        ];
        this.bindClassMethods(methodsToBind, this);

        this.client = new HookClient();
        this.feed = [];
        this.storyIndex = 0;
        this.currentUser = null;
        this.currentStory = null;
    }

    async getFeed(userId) {
        return await this.client.getFeed(userId);
    }

    async getStory(storyId) {
        if(!storyId) {
            throw new Error('Invalid storyId passed to getStory');
        }
        return await this.client.getStory(storyId);
    }

    async nextStory() {
        if (this.storyIndex < this.feed.length - 1) {
            this.storyIndex++;
            this.displayLoadingMessage();
            await this.displayStory();
        } else {
            console.log("Loading...");
            this.displayLoadingMessage();
            await new Promise(resolve => setTimeout(resolve, 2000));
            await this.init();
        }
    }
    

    async prevStory() {
        if (this.storyIndex > 0) {
            this.storyIndex--;
            await this.displayStory();
        }
    }

    async displayStory() {
        const parentElement = document.getElementById('story-card-container');
        parentElement.innerHTML = ''; // clear the container before displaying a new story
    
        const storyCard = new StoryCard();
        await storyCard.addCardToPage(this.feed[this.storyIndex]);
        this.currentStory = this.feed[this.storyIndex];
        console.log('currentStory = ', this.currentStory);
    }

    createButton(text, clickHandler) {
        const button = document.createElement('button');
        button.innerText = text;
        button.addEventListener('click', clickHandler);
        return button;
    }
    

    createLikeButton() {
        const button = this.createButton('Like', this.submitLike);
        button.classList.add('like_button'); 
        return button;
    }

    createDislikeButton() {
        const button = this.createButton('Dislike', this.prevStory);
        button.classList.add('dislike_button'); 
        return button;
    }
    

    async displayLoadingMessage() {
        const parentElement = document.getElementById('story-card-container');
        parentElement.innerHTML = ''; // Clear the container
    
        const loadingCard = document.createElement('div');
        loadingCard.textContent = "Loading...";
        loadingCard.className = "loading-card";
    
        parentElement.appendChild(loadingCard);
    }
    
    async init() {
        this.displayLoadingMessage();
    
        this.currentUser = await this.client.getIdentity();
        if (!this.currentUser) {
            this.currentUser = '0000';
        }
        this.user = await this.client.getUserByEmail(this.currentUser.email);
        console.log('The userId is: ', this.user.userId);
        
        this.feed = await this.getFeed(this.user.userId);
    
        if (!this.feed || this.feed.length === 0) {
            console.log("No more stories!");
            this.displayNoMoreStoriesCard();
            return;
        }
    
        await this.displayStory();
    
        const feedParentElement = document.getElementById('feed-card-container');
        const buttonContainer = document.createElement('div');
        buttonContainer.classList.add('button-group');
        feedParentElement.appendChild(buttonContainer);
    
        const dislikeButton = this.createDislikeButton();
        const likeButton = this.createLikeButton();
    
        buttonContainer.appendChild(dislikeButton);
        buttonContainer.appendChild(likeButton);
    }
    

    async submitLike() {
        this.displayLoadingMessage(); // Show the loading message as soon as the Like button is clicked
        console.log('submitLike this.feed: ', this.feed);
        console.log('submitLike this.storyIndex: ', this.storyIndex);
        const cognitoUser = await Auth.currentAuthenticatedUser();
        const { email, name } = cognitoUser.signInUserSession.idToken.payload;
        const userData = await this.client.getUserByEmail(email);
        const storyData = await this.client.getStory(this.currentStory);
    
        try {
            const updatedFavorites = userData.favorites.includes(storyData.storyId) 
                                     ? userData.favorites 
                                     : [...userData.favorites, storyData.storyId];
            
            const updatedLikes = ++storyData.likes;
            await this.client.editUser(
                userData.userId,
                userData.userName,
                email,
                userData.bio,
                userData.age,
                userData.follows,
                userData.followers,
                updatedFavorites,
                userData.userScore,
                userData.storiesWritten,
                userData.featured
            );
    
            await this.client.editStory(
                storyData.storyId,
                storyData.userId,
                storyData.title,
                storyData.content,
                storyData.snippet,
                storyData.tags,
                updatedLikes
            )
            await this.nextStory();
        } catch (error) {
            console.error("An error occurred while editing user: ", error);
        }
    }
    
    
    async displayNoMoreStoriesCard() {
        const parentElement = document.getElementById('story-card-container');
        parentElement.innerHTML = ''; // Clear the container
        
        const noStoriesCard = document.createElement('div');
        noStoriesCard.textContent = "No more stories!";
        noStoriesCard.className = "no-stories-card"; // You can define the styling in your CSS
    
        const refreshButton = document.createElement('button');
        refreshButton.textContent = 'Refresh';
        refreshButton.addEventListener('click', () => location.reload());
    
        noStoriesCard.appendChild(refreshButton);
        parentElement.appendChild(noStoriesCard);
    }
}
