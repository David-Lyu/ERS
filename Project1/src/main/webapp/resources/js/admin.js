const app = new App();
getAllReimbAdmin();
//global variables used to keep track of where logic is at
let alreadyCalled = false;
let allReimb = null;
let pendingReimb = {};
let approvedReimb = {};
let deniedReimb = {};
const navButton = document.getElementById("navButton");
navButton.addEventListener("click",activateModal);
/**
 * This method calls the app's getAllReimbAdmin to retrieve
 * all the pending reimbursements
 */
function getAllReimbAdmin() {

    app.getAllReimbAdmin()
        .then(data => {
            if(allReimb) {
                const navLiArr = document.getElementById("navbarNav").children[0].children;
                for (let i = 0; i < navLiArr.length; i++) {
                    navLiArr[i].children[0].classList.remove("active")
                }
                const viewAllNav = document.getElementById("viewAll");
                viewAllNav.classList.add("active");
            }
            allReimb = data;

            createAdminTable(data);
            changeAdminHeaders();
        })
        .catch(data => {
            console.error(data)
            window.location.href = "http://localhost:8999/Project1";
        });
}

/**
 * Creates a table from the data given from getAllReimbAdmin
 * the data should be an object and uses 2 for-in loops to
 * create the table. The second parameter checks to see if
 * it has been called before and a default value of false.
 */
function createAdminTable(data, alreadyCalled = false) {
    const loadingScreen = document.getElementById("loading");
    const table = document.getElementById("table");
    const tBody = document.getElementById("tbody");
    tBody.innerHTML = "";
    loadingScreen.classList.add("d-none");
    table.classList.remove("d-none");
    for (const reimbKey in data) {
        const reimbObj = data[reimbKey];
        const tr = document.createElement("tr");
        if(!alreadyCalled) {
            filterReimb(data, reimbKey);
        }
        for (const property in reimbObj) {
            const td = document.createElement("td");
            if (property === "authorUsername") continue;
            if (reimbObj[property] === null || reimbObj[property] === "null null") {
            td.textContent = "N/A";
            } else if (property === "reimbursementDescription" || property === "reimbursementReciept") {
                const icon = document.createElement("i");
                icon.classList.add("fa", "fa-sticky-note");
                icon.setAttribute("aria-hidden", "true");
                icon.addEventListener("click", () => { getModal(property, reimbObj[property]) });
                icon.onmouseover = () => {
                    icon.style.color = "blue";
                    icon.style.cursor = "pointer";
                }
                icon.onmouseout = () => {
                    icon.style.color = "";
                }
                td.appendChild(icon);
            } else {
                td.textContent = reimbObj[property];
            }
            tr.appendChild(td);
        }
        tBody.appendChild(tr);
    }
}

/**
 * This method gets the modal from dom and displays it. It is called
 * when the user clicks icons in the table
 */
function getModal(property, data) {
    const outerModal = document.getElementById("getModal");
    const innerModal = outerModal.children[0];
    outerModal.classList.remove("d-none");
    outerModal.addEventListener("click", e => {
        if (e.target != outerModal) {
            return;
        }
        innerModal.innerHTML = '';
        outerModal.classList.add("d-none");
    })
    let childElementCreated = null;
    if (property === 'reimbursementReciept') {
        childElementCreated = document.createElement("img");
        childElementCreated.src = data;
        childElementCreated.alt = "picture of receipt";
    } else {
        const childHeadingCreate = document.createElement("h3");
        childHeadingCreate.textContent = "Reimbursement Description";
        childElementCreated = document.createElement("p");
        childElementCreated.textContent = data;
        innerModal.appendChild(childHeadingCreate);
    }
    innerModal.appendChild(childElementCreated);
}

/**
 * This functions is called user clicks on the dark screen of the modal
 * which will hide the modal.
 */
function handleModalClick(e) {
    if (e.target != e.currentTarget) return;
    e.currentTarget.classList.add("d-none");
}

/**
 * This method takes in the event parameter and runs the create table
 * function. Based on the value of the text content of the event.currentTarget
 * it will create the appropriate table and highlight the nav li accordingly.
 */
function setViewTable(e) {
    const navLiArr = document.getElementById("navbarNav").children[0].children;
    for(let i = 0; i < navLiArr.length; i++) {
        navLiArr[i].children[0].classList.remove("active")
    }
    e.currentTarget.children[0].classList.add("active");
    switch(e.currentTarget.textContent) {
        case "View All" :
            createAdminTable(allReimb);
            break;
        case "View Pending" :
            createAdminTable(pendingReimb);
            break;
        case "View Denied" :
            createAdminTable(deniedReimb);
            break;
        case "View Approved" :
            createAdminTable(approvedReimb);
            break;
        default: console.log("WHAT");

    }
}

/**
 * This function changes the name to "Select Reimbursement"
 * insdie the nav bar. It will check if it has been called before
 * and will add the event listener if it has not
 */
function changeAdminHeaders() {
    const featureHeader = document.getElementById("secondHeaderButton");
    const modal = document.getElementById("formModal");
    const navNavUList = document.getElementById("navbarNav").children[0];
    if(navNavUList.children.length === 1) {
        for(let i = 0; i <= 3; i++) {
            const li = document.createElement("li");
            const anchor = document.createElement("a");
            li.classList.add("nav-item", "pointer");
            anchor.classList.add("nav-link");
            if(i === 0) {
                anchor.textContent = "View All";
                anchor.classList.add("active");
                anchor.setAttribute("id","viewAll");
            }
            if(i === 1) anchor.textContent = "View Pending";
            if(i === 2) anchor.textContent = "View Approved";
            if(i === 3) anchor.textContent = "View Denied";
            li.addEventListener("click", setViewTable);
            li.appendChild(anchor);
            navNavUList.appendChild(li);
        }
    }
    modal.addEventListener("click", handleModalClick);
    featureHeader.textContent = "Approve/Deny";
    if(alreadyCalled) return;
    featureHeader.addEventListener("click", (e) => {
        const formParent = document.getElementById("formGroupParent");
        const form = document.getElementById("addReimbForm");
        modal.classList.remove("d-none");
        modal.children[0].style.height = "auto";
        formParent.classList.remove("d-none");
        formParent.children[0].textContent = "Update Status"
        form.innerHTML = "";
        createUpdateStatusForm(form);
        form.removeEventListener("submit", updateNewReimb);
        form.addEventListener("submit", updateNewReimb)
    })
}

/**
 * This function is called when the form is submitted and will call
 * app's updateReimbStatus method. If the status is good then it will
 * call the getAllPendingreimbAdmin again to refresh and resets all
 * values. If there is an error it will console it out.
 */
function updateNewReimb(e) {
    e.preventDefault();
    e.currentTarget.children[2].disabled = true;
    const modal = document.getElementById("formModal");
    modal.removeEventListener("click", handleModalClick);
    const form = e.currentTarget;
    let reimbursementId = form.children[0].children[1].value;
    let reimbursementStatusId = form.children[1].children[1].value;
    const data = pendingReimb[reimbursementId];
    if(!data) {
        modal.addEventListener("click", handleModalClick);
        modal.classList.add("d-none");
        form.children[0].children[1].value = "";
        form.children[1].children[1].value = "";
    }
    data.reimbursementStatusId = reimbursementStatusId;
    app.updateReimbStatus(data)
        .then(data => {
            modal.addEventListener("click", handleModalClick);
            modal.classList.add("d-none");
            const table = document.getElementById("table");
            const tBody = document.getElementById("tbody");
            const loadingScreen = document.getElementById("loading");
            table.classList.add("d-none");
            tBody.innerHTML = "";
            loadingScreen.classList.remove("d-none");
            delete pendingReimb[form.children[0].children[1].value];
            form.children[0].children[1].value = "";
            form.children[1].children[1].value = "";
            alreadyCalled = true;
            console.log(this);
            console.log(this.data);
            getAllReimbAdmin();
            return;
        })
        .catch(console.error);
}

/**
 * This method creates the form element to submit and update
 * the reimbursement status.
 */
function createUpdateStatusForm(form) {
    let divFormGroupId = document.createElement("div");
    let labelId = document.createElement("label");
    let inputId = document.createElement("input");
    let divFormGroupStatus = document.createElement("div");
    let labelStatus = document.createElement("label");
    let selectStatus = document.createElement("select");
    let option1 = document.createElement("option");
    let option2 = document.createElement("option");
    let option3 = document.createElement("option");
    let button = document.createElement("button");

    divFormGroupId.classList.add("form-group");
    labelId.setAttribute("for", "reimbId");
    labelId.textContent = "Reimbursement Id";
    inputId.classList.add("form-control");
    inputId.setAttribute("id","reimbId");
    inputId.setAttribute("type","number");
    inputId.required = true;
    divFormGroupId.appendChild(labelId);
    divFormGroupId.appendChild(inputId);

    divFormGroupStatus.classList.add("form-group");
    labelStatus.setAttribute("for","updateStatus");
    labelStatus.textContent = "Update Reimbursement Status";
    selectStatus.classList.add("form-control");
    selectStatus.setAttribute("id", "updateStatus");
    selectStatus.required = true;
    option1.value = "";
    option1.textContent = "Pending";
    option1.disabled = true;
    option1.selected = true;
    option2.value = "1";
    option2.textContent = "Approved";
    option3.value = "2";
    option3.textContent = "Denied";
    selectStatus.appendChild(option1);
    selectStatus.appendChild(option2);
    selectStatus.appendChild(option3);
    divFormGroupStatus.appendChild(labelStatus);
    divFormGroupStatus.appendChild(selectStatus);

    button.classList.add("btn","btn-success","mt-1");
    button.textContent = "submit";

    form.appendChild(divFormGroupId);
    form.appendChild(divFormGroupStatus);
    form.appendChild(button);
}


/**
 * This method seperates the data into 3 objects for filtering the data
 * Pending, Approved , Denied.
 */
function filterReimb(data, reimbKey) {
    if (data[reimbKey].reimbursementStatusId === "PENDING") {
        pendingReimb[reimbKey] = data[reimbKey];
    }
    if (data[reimbKey].reimbursementStatusId === "APPROVED") {
        approvedReimb[reimbKey] = data[reimbKey];
    }
    if (data[reimbKey].reimbursementStatusId === "DENIED") {
        deniedReimb[reimbKey] = data[reimbKey];
    }
}

function activateModal(e) {
    e.currentTarget.removeEventListener("click", activateModal);
    const navbar = document.getElementById("navbarNav");
    navbar.classList.remove("collapse");
    navbar.classList.add("cust-show");
    e.currentTarget.addEventListener("click",deactivateModal);
}

function deactivateModal(e) {
    const navbar= document.getElementById("navbarNav");
    const navButton = e.currentTarget;
    navButton.removeEventListener("click", deactivateModal);
    navbar.classList.add("collapse");
    navbar.classList.remove("cust-show");
    navButton.addEventListener("click", activateModal);
}
