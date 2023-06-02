import HookClient from '../api/HookClient';
import BindingClass from "../util/bindingClass";

/**
 * The header component for the website.
 */
export default class Header extends BindingClass {
    constructor() {
        super();

        const methodsToBind = [
            'addHeaderToPage', 'createUserInfoForHeader',
            'createLoginButton', 'createLogoutButton'
        ];
        this.bindClassMethods(methodsToBind, this);

        this.client = new HookClient();
    }

    async addHeaderToPage() {
        const currentUser = await this.client.getIdentity();

        const userInfo = this.createUserInfoForHeader(currentUser);

        const header = document.getElementById('header');
        // Add site title
        const siteTitle = document.createElement('div');
        siteTitle.classList.add('site-title');
        siteTitle.innerText = "Hook";
        header.appendChild(siteTitle);
        
        // Add user info
        header.appendChild(userInfo);
    }


    createUserInfoForHeader(currentUser) {
        const userInfo = document.createElement('div');
        userInfo.classList.add('user');
    
        // Adjust the style to move the buttons to the right
        userInfo.style.cssText = 'position: absolute; right: 0';
    
        const loginButton = this.createLoginButton();
        userInfo.appendChild(loginButton);
    
        // If currentUser is defined, add logout button
        if(currentUser){
            const logoutButton = this.createLogoutButton(currentUser);
            userInfo.appendChild(logoutButton);
        }
    
        return userInfo;
    }
    

    createLoginButton() {
        return this.createButton('Login', this.client.login);
    }

    createLogoutButton(currentUser) {
        return this.createButton(`Logout: ${currentUser.name}`, this.client.logout);
    }

    createButton(text, clickHandler) {
        const button = document.createElement('a');
        button.classList.add('button');
        button.href = '#';
        button.innerText = text;

        button.addEventListener('click', async () => {
            await clickHandler();
        });

        return button;
    }
}

