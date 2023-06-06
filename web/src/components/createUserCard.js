import HookClient from '../api/HookClient';
import BindingClass from "../util/bindingClass";
import { Auth } from '@aws-amplify/auth';


export default class CreateUserCard extends BindingClass {
    constructor() {
        super();

        const methodsToBind = [
            'createUserForm', 'createInputField', 'submitForm' 
        ];
        this.bindClassMethods(methodsToBind, this);

        this.client = new HookClient();
    }

    createUserForm() {
        const formContainer = document.getElementById('form-container');
        
        // Create form element
        const form = document.createElement('form');
        form.addEventListener('submit', this.submitForm.bind(this));
        
        
        // Create input elements
        const userNameInput = this.createCard('userName', 'Username');
        const bioInput = this.createCard('bio', 'Bio');
        const ageInput = this.createCard('age', 'Age', 'number');
        
        // Create submit button
        const submitButton = document.createElement('button');
        submitButton.type = 'submit';
        submitButton.textContent = 'Submit';
        submitButton.classList.add("button"); // use existing "button" class
        submitButton.addEventListener('click', this.submitForm.bind(this));

    
        // Append elements to form
        form.append(userNameInput, bioInput, ageInput, submitButton);
        
        // Append form to formContainer
        formContainer.append(form);
        formContainer.classList.add("card-content"); 
    }
    
    createCard(id, labelText, type = 'text') {
        const card = document.createElement('div');
        card.classList.add("card"); 
    
        const inputField = this.createInputField(id, labelText, type);
        card.append(inputField);
    
        return card;
    }
    
    createInputField(id, labelText, type = 'text') {
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
        input.classList.add("validated-field");
    
        // Append elements to inputGroup
        inputGroup.append(label, input);
        
        return inputGroup;
    }
    

    async submitForm(event) {
        event.preventDefault();
        const cognitoUser = await Auth.currentAuthenticatedUser();
        const { email, name } = cognitoUser.signInUserSession.idToken.payload;
        const userName = document.getElementById('userName').value;
        const bio = document.getElementById('bio').value;
        const age = document.getElementById('age').value;
        
        try {
            await this.client.createUser(userName, email, bio, age);
            window.location.href = "/index.html";
        } catch (error) {
            console.error("An error occurred while creating user: ", error);
        }
    }
    

    async getCurrentUserInfo() {
        const cognitoUser = await Auth.currentAuthenticatedUser();
        const { email, name } = cognitoUser.signInUserSession.idToken.payload;
        return { email, name };
    }
}
    