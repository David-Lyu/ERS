console.log("employee");
const app = new App();
app.getEmployeeReimb()
  .then(data => {
    //selecting logic here
    createTable(data);
    changeHeaders();
    return;
  })
  .catch(err => {
  console.error("error in getting data")
  // window.location.href = "http://localhost:8999/Project1";
});



/**
 * This function handles the data that is returned from the method getEmployeeReimb in App Class
 * with that data it will create a table with all the populated data from the back-end
 */
function createTable(data) {
  const loadingScreen = document.getElementById("loading")
  const table = document.getElementById("table")
  const tBody = document.getElementById("tbody");
  loadingScreen.classList.add("d-none");
  table.classList.remove("d-none");

  for (let i = 0; i < data.length; i++) {
    const tr = document.createElement("tr");
    for (const property in data[i]) {
      const td = document.createElement("td");
      if (property === "authorUsername") continue;

      if (data[i][property] === null || data[i][property] === "null null") {
        td.textContent = "N/A";
      } else if (property === "reimbursementDescription" || property === "reimbursementReciept") {
        const icon = document.createElement("i");
        icon.classList.add("fa", "fa-sticky-note");
        icon.setAttribute("aria-hidden", "true");
        icon.addEventListener("click", ()=>{getModal(property,data[i][property])});
        icon.onmouseover = () => {
          icon.style.color = "blue";
          icon.style.cursor = "pointer";
        }
        icon.onmouseout = () => {
          icon.style.color = "";
        }
        td.appendChild(icon);
      } else {
        td.textContent = data[i][property];
      }
      tr.appendChild(td);
    }
    if(i === 0 ) {
      tBody.insertAdjacentElement("afterbegin", tr);
      continue;
    }
    tBody.appendChild(tr);
  }
}

// function addToExistingTable(data) {
//   if(data == null || typeof data !== "object")
//   const tr = document.createElement("tr");
//   for(const property in data) {
//     const td = document.createElement("td");
//     td.textContent = data[property];
//   }
// }


/**
 * This function deals with the receipt and description portion of the table
 * A modal will pop out showing the contents of the data. Only called if there is
 * data for these two.
 */
function getModal(property, data) {
  const outerModal = document.getElementById("getModal");
  const innerModal = outerModal.children[0];
  outerModal.classList.remove("d-none");
  outerModal.addEventListener("click", e => {
    if (e.target != outerModal) {
      return;
    }
    outerModal.classList.add("d-none");
    innerModal.innerHTML = '';
  })
  let childElementCreated = null;
  console.log(property);
  if (property === 'reimbursementReciept') {
    console.log("receipt")
    childElementCreated = document.createElement("img");
    childElementCreated.src = data;
    childElementCreated.alt = "picture of receipt";
  } else {
    console.log("des")
    const childHeadingCreate = document.createElement("h3");
    childHeadingCreate.textContent = "Reimbursement Description";
    childElementCreated = document.createElement("p");
    childElementCreated.textContent = data;
    innerModal.appendChild(childHeadingCreate);
  }
  innerModal.appendChild(childElementCreated);
}

function handleModalClick(e) {
  if (e.target != e.currentTarget) return;
  e.currentTarget.classList.add("d-none");
}

function changeHeaders() {
  const featureHeader = document.getElementById("secondHeaderButton");
  const modal = document.getElementById("formModal");
  modal.addEventListener("click", handleModalClick);
  featureHeader.textContent = "Add Reimbursement";
  featureHeader.addEventListener("click", (e) => {
    const formParent = document.getElementById("formGroupParent");
    const form = document.getElementById("addReimbForm");
    modal.classList.remove("d-none");
    formParent.classList.remove("d-none");
    form.addEventListener("submit", (e) => {sendNewReimb(e,modal)})
  })
}

/**
 * This function takes 2 parameters. First one is an event from the callback fuction
 * of a submit for the forms in changeHeader function.
 */
function sendNewReimb(e,modal) {
  e.preventDefault();
  modal.removeEventListener("click", handleModalClick);
  const form = e.currentTarget;
  let data = {
    reimbursementAmount: form.children[0].children[1].value,
    reimbursementTypeId: form.children[1].children[1].value,
    reimbursementDescription: !form.children[2].children[1].value ? null : form.children[2].children[1].value,
    reimbursementReceipt: !form.children[3].children[1].value ? null : form.children[3].children[1].value
  };
  app.addEmployeeReimb(data)
  .then(response => response)
  .then(data => {
    console.log(data)
    modal.addEventListener("click",handleModalClick);
    createTable([data])
    modal.classList.add("d-none");
    form.children[0].children[1].value = "";
    form.children[1].children[1].value = "";
    form.children[2].children[1].value = "";
    form.children[3].children[1].value = "";
    return;
  })
  .catch(console.error);
}
