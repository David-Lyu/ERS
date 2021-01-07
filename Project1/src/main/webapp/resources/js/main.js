let app = new App();
// console.log(app);
// let loginData = app.login("david","asdf");
const form = document.getElementById("formLogin");
form.addEventListener("submit", login);
function login (e) {
  e.preventDefault();
  let username = form.children[0].children[1].value;
  let password = form.children[1].children[1].value;
  let badCred = app.login(username, password);
  if(!badCred) {
    console.log(badCred);
  }
}
