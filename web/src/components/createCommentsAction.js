import HookClient from "../api/HookClient";
import BindingClass from "../util/bindingClass";
import { Auth } from "@aws-amplify/auth";

export default class CreateCommentsAction extends BindingClass {
  constructor() {
    super();

    const methodsToBind = ["createComments", "createNegComment"];
    this.bindClassMethods(methodsToBind, this);
    this.client = new HookClient();
  }

  async createComments(posComment, negComment) {
    console.log("createComments called");
  
    const storyId = window.storyId;
    const cognitoUser = await Auth.currentAuthenticatedUser();
    const { email, name } = cognitoUser.signInUserSession.idToken.payload;
    const commentsData = await this.client.getComments(storyId);
  
    try {
      const updatedPosComments = [...commentsData.posComments, posComment];
      const updatedNegComments = [...commentsData.negComments, negComment];
      console.log("Updating comments with: ", updatedPosComments);
  
      return this.client.editComments(
        commentsData.storyId,
        updatedPosComments,
        updatedNegComments
      );
      
    } catch (error) {
      console.error("An error occurred while editing user: ", error);
    }
  }

  async createNegComment(newComment) {
    console.log("createNegComment called");
  
    const storyId = window.storyId;
    const cognitoUser = await Auth.currentAuthenticatedUser();
    const { email, name } = cognitoUser.signInUserSession.idToken.payload;
    const commentsData = await this.client.getComments(storyId);
  
    try {
      const updatedNegComments = [...commentsData.negComments, newComment];
      console.log("Updating comments with: ", updatedNegComments);
  
      return this.client.editComments(
        commentsData.storyId,
        commentsData.posComments,
        updatedNegComments
      );
      
    } catch (error) {
      console.error("An error occurred while editing user: ", error);
    }
  }
}