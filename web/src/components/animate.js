import HookClient from "../api/HookClient";
import BindingClass from "../util/bindingClass";
import anime from "animejs";

export default class Animate extends BindingClass {
  constructor() {
    super();

    const methodsToBind = ["addCardAnimations"];
    this.bindClassMethods(methodsToBind, this);
    this.client = new HookClient();
  }

  addCardAnimations() {
    const cards = document.querySelectorAll('.card');
    cards.forEach((card, index) => {
      anime({
        targets: card,
        opacity: 1,
        delay: index * 200,
        duration: 800,
        easing: 'easeOutExpo',
      });
    });
  }
}