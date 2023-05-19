# Design Document

## Instructions

_Replace italicized text (including this text!) with details of the design you are proposing for your Capstone project. (Your replacement text shouldn't be in italics)._

_You should take a look at the [example design document](example-design-document.md) in the same folder as this template for more guidance on the types of information to capture, and the level of detail to aim for._

## _Project Title_ Design

## 1. Problem Statement

The goal of this project is to develop a Tinder-like app specifically designed for connecting writers. The app will allow users to upload the first few sentences of a short story or chapter as a way to "hook" potential readers. Other users can then swipe through these story snippets, click on the ones that intrigue them, and read the full story. They can also provide quick feedback to the writers regarding their writing.

The app will provide a platform for writers to showcase their work and receive constructive feedback from a community of fellow writers and readers. It aims to facilitate connections between writers, encourage collaboration, and foster improvement in writing skills through "follow" and "message" features that allow for long lasting relationships.

## 2. Top Questions to Resolve in Review

_List the most important questions you have about your design, or things that you are still debating internally that you might like help working through._

1. What are the best ways to categorize and filter the story snippets to ensure that users see the types of stories they're most interested in?
2. What features should be in place to ensure feedback is constructive?
3. What filters or code needs to be in place to make sure stories are appropriate?
4. How can the "follow" and "message" features be designed in a way that feels appropriate?
5. What mechanisms can we put in place to prevent plagiarism or misuse of writers' work?
6. Should there be a limit to the number of story snippets a writer can upload?

## 3. Use Cases

_This is where we work backwards from the customer and define what our customers would like to do (and why). You may also include use cases for yourselves (as developers), or for the organization providing the product to customers._

U1. As a Hook customer, I want to create a profile that includes a profile picture, a short bio, some interests, and my first piece (if available) when I sign up so that I can connect with others.

U2. As a Hook customer, I want to upload a snippet of my story when I have finished writing so that I can attract potential readers and receive feedback.

U3. As a Hook customer, I want to "swipe" through story snippets when I open the app so that I can discover new stories and authors.

U4. As a Hook customer, I want to provide feedback on the stories I read so that I can engage with the authors and contribute to the community.

U5. As a Hook customer, I want to follow authors whose work I enjoy when I come across their stories so that I can easily find their future work.

U6. As a Hook customer, I want to view all the stories I've liked when I visit my profile so that I can easily find them again.

U7. As a Hook customer, I want to message authors directly when I have detailed feedback or collaboration ideas.

U8. As a Hook customer, I want to filter and categorize story snippets.

U9. As a Hook developer, I want to monitor user engagement metrics when I review the app's performance so that I can make data-driven improvements.

U10. As a Hook developer, I want to use content moderation tools to make sure creators have both the freedom to upload any genre while protecting those who should not see it.

## 4. Project Scope

_Clarify which parts of the problem you intend to solve. It helps reviewers know what questions to ask to make sure you are solving for what you say and stops discussions from getting sidetracked by aspects you do not intend to handle in your design._

### 4.1. In Scope

_Which parts of the problem defined in Sections 1 and 2 will you solve with this design? This should include the base functionality of your product. What pieces are required for your product to work?_

1. I will design a user-friendly interface for users to register and create their profiles. 
2. I will design a feature that allows writers to upload the first few sentences of their stories. 
3. I will include a feature that allows users to provide feedback on the stories they read. 
4. I will design mechanisms for users to follow their favorite authors.
5. I will design a system that allows users to filter and categorize story snippets according to their preferences. 
6. I will design a robust profile creation system that allows users to connect with others and have their stories go to the feed.

_The functionality described above should be what your design is focused on. You do not need to include the design for any out of scope features or expansions._

### 4.2. Out of Scope

_Based on your problem description in Sections 1 and 2, are there any aspects you are not planning to solve? Do potential expansions or related problems occur to you that you want to explicitly say you are not worrying about now? Feel free to put anything here that you think your team can't accomplish in the unit, but would love to do with more time._

_The functionality here does not need to be accounted for in your design._

1. Messaging each other is currently out of scope.
2. Connecting your profile to Google Docs is currently out of scope.
3. A full text editor is out of scope. 
4. Actual "swiping" for the interface is out of scope.

# 5. Proposed Architecture Overview

_Describe broadly how you are proposing to solve for the requirements you described in Section 2. This may include class diagram(s) showing what components you are planning to build. You should argue why this architecture (organization of components) is reasonable. That is, why it represents a good data flow and a good separation of concerns. Where applicable, argue why this architecture satisfies the stated requirements._
![image](https://github.com/TeaTime93/TP-SE02-Capstone/assets/10236355/df7d9a3c-b421-4c4f-a94a-42226cd4bd7f)

# 6. API

## 6.1. Public Models

_Define the data models your service will expose in its responses via your *`-Model`* package. These will be equivalent to the *`PlaylistModel`* and *`SongModel`* from the Unit 3 project._

UserModel
{
  "userID": "String",
  "userName": "String",
  "email": "String",
  "bio": "String"
  "followingList" : List<User>
  "favorites" : List<Story>
}

StoryModel:
{
  "storyID": "String",
  "userID": "String",
  "title": "String",
  "snippet": "String",
  "content": "String"
}



## 6.2. _First Endpoint_

_Describe the behavior of the first endpoint you will build into your service API. This should include what data it requires, what data it returns, and how it will handle any known failure cases. You should also include a sequence diagram showing how a user interaction goes from user to website to service to database, and back. This first endpoint can serve as a template for subsequent endpoints. (If there is a significant difference on a subsequent endpoint, review that with your team before building it!)_

_(You should have a separate section for each of the endpoints you are expecting to build...)_
  
  POST /users/register
  Creates a new user when they register.
  

## 6.3 _Second Endpoint_
  POST /stories/upload
  Uploads a new story to the system.
  
## 6.4 _Third Endpoint_
  GET /stories/{storyID}
  Gets the content of an entire story.
  
## 6.5 _Fourth Endpoint_
  PUT /stories/{storyID}
  Updates a Story.
  
## 6.6 _Fifth Endpoint_
  DELETE /stories/{storyID}
  Deletes a story.
  
## 6.7 _Sixth Endpoint_
  GET /users/{userID}/stories
  Get's stories associated with a user.
  
## 6.8 _Seventh Endpoint_
  POST /users/{userID}/follow
  Follows a user.
  
## 6.9 _Eighth Endpoint_
  DELETE /users/{userID}/unfollow
  Unfollows a user.
  
## 6.10 _Ninth Endpoint_
  POST /stories/{storyID}/comment
  Adds a comment and associates it with story.
  
## 6.11 _Tenth Endpoint_
  GET /stories/{storyID}/comments
  Gets the comments for a story.
  
## 6.12 _Eleventh Endpoint_
  PUT /comments/{commentID}
  Updates the comment for a story.
  
## 6.13 _Twelfth Endpoint_
  DELETE /comments/{commentID}
  Deletes the comment for a story.
  
## 6.14 _Thirteenth Endpoint_
  GET /users/{userID}/feed
  Gets the feed for a user.
 



# 7. Tables

_Define the DynamoDB tables you will need for the data your service will use. It may be helpful to first think of what objects your service will need, then translate that to a table structure, like with the *`Playlist` POJO* versus the `playlists` table in the Unit 3 project._
  ### 1. Users Table

| userID (PK) | userName | email | password | bio | follows|
|-------------|----------|-------|----------|-----|----------|
| String      | String   | String| String   | String | List<String> |
  
  ### 2. Stories Table

| storyID (PK) | userID (GSI PK) | title | snippet | content |
|--------------|-----------------|-------|---------|---------|
| String       | String          | String| String  | String  |
  
  ### 3. Comments Table

| commentID (PK) | storyID (GSI PK) | userID | content |
|----------------|------------------|--------|---------|
| String         | String           | String | String  |
  

# 8. Pages

_Include mock-ups of the web pages you expect to build. These can be as sophisticated as mockups/wireframes using drawing software, or as simple as hand-drawn pictures that represent the key customer-facing components of the pages. It should be clear what the interactions will be on the page, especially where customers enter and submit data. You may want to accompany the mockups with some description of behaviors of the page (e.g. “When customer submits the submit-dog-photo button, the customer is sent to the doggie detail page”)_
