//initialize script tag
const windowLocation = new URL(window.location.href);
if (windowLocation.pathname == "/Project1/resources/html/loggedIn.html") {
  getScript(windowLocation);
  const logoutButton = document.getElementById("logout");
  logoutButton.addEventListener("click", logout)
}

//////Sending backend
class App {

  constructor() {
    this.xhttp = new XMLHttpRequest();

  }

  //Login
  login(username, password) {
    let xhttp = this.xhttp;
    xhttp.onreadystatechange = function () {

      if (xhttp.readyState == 4 && xhttp.status >= 200 && xhttp.status < 400) {
        let url = xhttp.responseURL;
        if (url) {
          window.location.href = url;
        } else {
          return null;
        }
      }
    }

    xhttp.open("POST", "http://localhost:8999/Project1/api/login");
    xhttp.setRequestHeader("Content-type", "application/json");
    xhttp.send(JSON.stringify({ username: username, password: password }));
  }
  //get all reimbursement for specified employee. create a promise manually
  getEmployeeReimb() {
    return new Promise((res, rej) => {
      let xhttp = new XMLHttpRequest();
      xhttp.onreadystatechange = function () {

        if (xhttp.readyState == 4) {

          let data = JSON.parse(xhttp.response);
          if (data) res(data)
          if (!data) rej(data)
        }
      }

      xhttp.open("GET", "http://localhost:8999/Project1/json/employee")
      xhttp.send(null);
    })
  }
  //adds reimbursement for employee
  async addEmployeeReimb(data) {
    let init = {
      method: "post",
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(data)
    }
    const response = await fetch('http://localhost:8999/Project1/json/add', init)
    return response.json();
  }
  /** This method gets all pending reimbursements for the finance manager or admin*/
  getAllReimbAdmin() {
    return new Promise((res, rej) => {
      let xhttp = new XMLHttpRequest();
      xhttp.onreadystatechange = function () {

        if (xhttp.readyState == 4) {
          let data = null;
          try {
            data = JSON.parse(xhttp.response);
            res(data)
          } catch{
            rej(data)
          }
        }
      }

      xhttp.open("GET", "http://localhost:8999/Project1/json/view-pending")
      xhttp.send(null);
    })
  }

  /**This method will update the targeted reimbursement with its id and
   * change the status of it. Can only be sent through admin page.
   */
  async updateReimbStatus(data) {
    let init = {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(data)
    }
    const response = await fetch('http://localhost:8999/Project1/json/update',init);
    return response;
  }
  //logout
  logout() {
    fetch("http://localhost:8999/Project1/api/logout")
      .then(response => response)
      .then(data => window.location.href = data.url)
      .catch(console.error);
  }
}

/**This function creates the script tag for the login/html page
 *
 * windowLoaction: the path of the URL minus any query;
 */
function getScript(windowLocation) {
  const type = windowLocation.searchParams.get("type");
  let scriptPath = "";
  switch (type) {

    case "employee":
      scriptPath = "../js/employee.js";
      break;
    case "admin":
      scriptPath = "../js/admin.js";
      break;
    default:
      window.location.href = "http://localhost:8999/Project1"
      break;
  }
  let scriptTag = document.createElement("script");
  scriptTag.setAttribute("src", scriptPath);
  const bodyChild = document.body.children;
  bodyChild[bodyChild.length - 1].appendChild(scriptTag);
  // body.appendChild(scriptTag);
}

function logout() {
  app.logout();
}
