document.querySelectorAll(".sidebar-navigation ul li").forEach((item) => {
    item.addEventListener("click", function() {
        document
            .querySelectorAll(".sidebar-navigation ul li")
            .forEach((innerItem) => {
                innerItem.classList.remove("active");
            });
        this.classList.add("active");

        document.querySelectorAll(".section-data").forEach((section) => {
            section.style.display = "none";
        });

        const sectionId = this.getAttribute("data-target");
        document.getElementById(sectionId).style.display = "block";
    });
});

// Initialize first tab and section visibility
document.getElementById("dashboard").style.display = "block";


// script for the all form which makes the form work and return the output in the output terminal section
document.querySelectorAll("form").forEach((form) => {
    form.addEventListener("submit", function(event) {
        event.preventDefault();
        // Perform form submission logic here

        // Get the output terminal element
        const outputTerminal = document.getElementById("outputTerminal");

        // Update the output content
        const outputContent = outputTerminal.querySelector(".output-content");
        outputContent.innerHTML = "<h3>Output</h3><p>Output will be displayed here...</p>";

        // Display the output terminal section
        outputTerminal.style.display = "block";
    });
});

//APIs Interaction using Fetch
const outputContent = document.querySelector("#outputTerminal .output-content");

function getAllUsers() {
    const requestOptions = {
        method: "GET",
        redirect: "follow"
    };

    fetch("/api/users", requestOptions)
        .then((response) => response.text())
        .then((result) => {
            outputContent.innerHTML += "<p>" + result + "</p>";
        })
        .catch((error) => console.error(error));
}

//function to delete user
function deleteUser() {
    const userId = document.querySelector("#delete-user input").value;
    const requestOptions = {
        method: "DELETE",
        body: "",
        redirect: "follow"
    };

    fetch("/api/users/" + userId, requestOptions)
        .then((response) => response.text())
        .then((result) => {
            outputContent.innerHTML += "<p>" + result + "</p>";
        })
        .catch((error) => console.error(error));
}

//function to search user
function searchUser() {
    const searchType = document.getElementById("searchType").value;
    const searchQuery = document.getElementById("searchQuery").value;
    const requestOptions = {
        method: "GET",
        redirect: "follow"
    };

    let url = "/api/users/search";
    if (searchType === "name") {
        url += "?name=" + searchQuery;
    } else if (searchType === "designation") {
        url += "/designation/" + searchQuery;
    } else if (searchType === "department") {
        url += "/department/" + searchQuery;
    }
    fetch(url, requestOptions)
        .then(response => response.text())
        .then((result) => {
            outputContent.innerHTML += "<p>" + result + "</p>";
        })
        .catch(error => console.log('error', error));

}

// Function to fetch user data by ID
function getUserById(userId) {
    const requestOptions = {
        method: "GET",
        headers: {"Content-Type": "application/json"},
        redirect: "follow"
    };

    return fetch("/api/users/" + userId, requestOptions)
        .then(response => response.json())  // Assuming the API returns JSON
        .catch(error => console.error('Error:', error));
}

// Function to fetch user data by ID
function getUserById(userId) {
    const requestOptions = {
        method: "GET",
        headers: {"Content-Type": "application/json"},
        redirect: "follow"
    };

    return fetch("/api/users/" + userId, requestOptions)
        .then(response => response.json())  // Assuming the API returns JSON
        .catch(error => console.error('Error:', error));
}

// Function to load user data into HTML elements and enable them for update if needed
function loadData() {
    const userId = document.getElementById("userIdUpdate").value;
    getUserById(userId)
        .then(data => {
            if (data) {
                // Enabling fields and filling data
                const fields = [
                    {id: "nameUpdate", value: data.name},
                    {id: "emailUpdate", value: data.email},
                    {id: "mobileUpdate", value: data.mobile},
                    {id: "residenceUpdate", value: data.residence},
                    {id: "passwordUpdate", value: data.password} // Handle with caution
                ];

                fields.forEach(field => {
                    const input = document.getElementById(field.id);
                    if (input) {
                        input.value = field.value;
                        input.disabled = false; // Enable the field if you need to allow updates
                    }
                });

                // Handling select fields separately as they might require matching values
                document.getElementById("designationUpdate").value = data.designation;
                document.getElementById("departmentUpdate").value = data.department;
                document.getElementById("designationUpdate").disabled = false;
                document.getElementById("departmentUpdate").disabled = false;

                // Handling roles select
                let roleSelect = document.getElementById("rolesUpdate");
                roleSelect.disabled = false;
                document.getElementById("updateButton").disabled = false;
                Array.from(roleSelect.options).forEach(option => {
                    if (data.roles.includes(option.text.toUpperCase())) {
                        option.selected = true;
                    }
                });

            } else {
                console.log("No user data received.");
            }
        });
}

// Function to update user data
function updateUser() {
    const userId = document.getElementById("userIdUpdate").value;
    const name = document.getElementById("nameUpdate").value;
    const email = document.getElementById("emailUpdate").value;
    const mobile = document.getElementById("mobileUpdate").value;
    const residence = document.getElementById("residenceUpdate").value;
    const password = document.getElementById("passwordUpdate").value;
    const designation = document.getElementById("designationUpdate").value;
    const department = document.getElementById("departmentUpdate").value;
    const roles = Array.from(document.getElementById("rolesUpdate").options)
        .filter(option => option.selected)
        .map(option => option.text.toUpperCase());

    const userData = {
        name: name,
        email: email,
        mobile: mobile,
        residence: residence,
        password: password,
        designation: designation,
        department: department,
        roles: roles
    };

    const requestOptions = {
        method: "PUT",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(userData),
        redirect: "follow"
    };

    fetch("/api/users/" + userId, requestOptions)
        .then(response => response.text())
        .then(result => {
            outputContent.innerHTML += "<p>" + result + "</p>";
        })
        .catch(error => console.error('Error:', error));
}

// Function to create new user
function createUser() {
    const name = document.getElementById("nameCreate").value;
    const email = document.getElementById("emailCreate").value;
    const mobile = document.getElementById("mobileCreate").value;
    const residence = document.getElementById("residenceCreate").value;
    const password = document.getElementById("passwordCreate").value;
    const designation = document.getElementById("designationCreate").value;
    const department = document.getElementById("departmentCreate").value;
    const roles = Array.from(document.getElementById("rolesCreate").options)
        .filter(option => option.selected)
        .map(option => option.text.toUpperCase());

    const userData = {
        name: name,
        email: email,
        mobile: mobile,
        residence: residence,
        password: password,
        designation: designation,
        department: department,
        roles: roles
    };

    const requestOptions = {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(userData),
        redirect: "follow"
    };

    fetch("/api/users", requestOptions)
        .then(response => response.text())
        .then(result => {
            outputContent.innerHTML += "<p>" + result + "</p>";
        })
        .catch(error => console.error('Error:', error));
}
