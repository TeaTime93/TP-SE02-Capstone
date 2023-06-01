import HookClient from '../api/HookClient';
import Header from '../components/header';
import BindingClass from "../util/bindingClass";
import DataStore from "../util/DataStore";
import Feed from "../components/Feed"; // Make sure you import the Feed class

const SEARCH_CRITERIA_KEY = 'search-criteria';
const SEARCH_RESULTS_KEY = 'search-results';
const EMPTY_DATASTORE_STATE = {
    [SEARCH_CRITERIA_KEY]: '',
    [SEARCH_RESULTS_KEY]: [],
};

class IndexPage extends BindingClass {
    constructor() {
        super();

        this.bindClassMethods(['mount'], this);

        this.dataStore = new DataStore(EMPTY_DATASTORE_STATE);
        this.header = new Header(this.dataStore);
        console.log("indexPage constructor");
        this.dataStore.addChangeListener(this.displaySearchResults);

        this.feed = new Feed(); // Create an instance of Feed class here
    }

    mount() {
        this.header.addHeaderToPage();
        this.client = new HookClient();
        console.log("Index page script loaded successfully!");

        this.feed.init(); // Initialize the Feed here
    }
}

const main = async () => {
    const index = new IndexPage();
    index.mount();
};

window.addEventListener('DOMContentLoaded', main);
