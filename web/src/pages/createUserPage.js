import HookClient from '../api/HookClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import { Auth } from '@aws-amplify/auth';

const SEARCH_CRITERIA_KEY = 'search-criteria';
const SEARCH_RESULTS_KEY = 'search-results';
const EMPTY_DATASTORE_STATE = {
    [SEARCH_CRITERIA_KEY]: '',
    [SEARCH_RESULTS_KEY]: [],
};

class CreateUserPage extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount'], this);

        this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
        this.header = new Header(this.dataStore);
        console.log("CreateUserPage constructor");
        this.dataStore.addChangeListener(this.displaySearchResults);
    }

    async mount() {
        this.header.addHeaderToPage();
        this.client = new HookClient();
        console.log("Create User page script loaded successfully!");

        this.createUserForm();
    }
    createUserForm() {
        const formContainer = document.getElementById('form-container');
        
        // Create form element
        const form = document.createElement('form');
        form.addEventListener('submit', this.submitForm.bind(this));
        
        // Create input elements
        const userNameInput = this.createInputField('userName', 'Username');
        const bioInput = this.createInputField('bio', 'Bio');
        const ageInput = this.createInputField('age', 'Age', 'number');
        
        // Create submit button
        const submitButton = document.createElement('button');
        submitButton.type = 'submit';
        submitButton.textContent = 'Submit';

        // Append elements to form
        form.append(userNameInput, bioInput, ageInput, submitButton);
        
        // Append form to formContainer
        formContainer.append(form);
    }

    createInputField(id, labelText, type = 'text') {
        const inputGroup = document.createElement('div');
        
        // Create label
        const label = document.createElement('label');
        label.for = id;
        label.textContent = labelText;
        
        // Create input
        const input = document.createElement('input');
        input.id = id;
        input.type = type;
        
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

const main = async () => {
    const createUserPage = new CreateUserPage();
    createUserPage.mount();
};

window.addEventListener('DOMContentLoaded', main);