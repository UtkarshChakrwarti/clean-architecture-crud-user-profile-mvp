//onload function to get all users
window.onload = function() {
    getAllUsers();
};

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
    });
});

//APIs Interaction using Fetch


// Function to create new user
function createUser() {
    const createResponse = document.getElementById("createResponse");
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
    const work = document.getElementById("workCreate").value

    //if any of the fields is empty, show error message and dont call the API
    if (name === "" || email === "" || mobile === "" || residence === "" ||
    password === "" || designation === "" || department === "" || roles.length
    === 0 || work === "") {
        createResponse.innerHTML =
        "<div class='alert alert-danger' role='alert'>All fields are required</div>";
        //clear response after 5 seconds
        setTimeout(() => {
            createResponse.innerHTML = "";
        }, 1000);
        return;
    }
    const userData = {
        name: name,
        email: email,
        mobile: mobile,
        work: work,
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
        //show response in bootstrap success alert in createResponse div
        .then(result => {
            createResponse.innerHTML = "<div class='alert alert-success' role='alert'>Success!</div>";
            //clear form fields dom
            document.getElementById("nameCreate").value = "";
            document.getElementById("emailCreate").value = "";
            document.getElementById("mobileCreate").value = "";
            document.getElementById("residenceCreate").value = "";
            document.getElementById("passwordCreate").value = "";
            document.getElementById("designationCreate").value = "";
            document.getElementById("departmentCreate").value = "";
            document.getElementById("rolesCreate").value = "";
            document.getElementById("workCreate").value = "";


            //clear response after 3 seconds
            setTimeout(() => {
                createResponse.innerHTML = "";
            }, 3000);

            getAllUsers();

        }
        //show response in bootstrap fail alert in createResponse div
        ).catch(error => {
            createResponse.innerHTML =
            "<div class='alert alert-danger' role='alert'>Failed</div>";

            //clear response after 5 seconds
            setTimeout(() => {
                createResponse.innerHTML = "";
            }, 1000);
        });

}



//function to get all users

function getAllUsers() {
    const outputAllUsers = document.getElementById("allUsers");
    const requestOptions = {
        method: "GET",
        redirect: "follow"
    };

    fetch("/api/users", requestOptions)
        .then((response) => response.json())
        .then((data) => {
            const users = data.content;
            let table ='<table class="table table-striped table-bordered table-hover">'
            +'<thead class="thead-dark"><tr><th>ID</th><th>Name</th><th>Designation</th>'
            +'<th>Department</th><th>Email</th><th>Mobile</th><th>Roles</th></tr></thead><tbody>';
            users.forEach((user) => {
                table += `<tr>
                            <td>${user.id}</td>
                            <td>${user.name}</td>
                            <td>${user.designation}</td>
                            <td>${user.department}</td>
                            <td>${user.email}</td>
                            <td>${user.mobile}</td>
                            <td>${user.roles.join(", ")}</td>
                          </tr>`;
            });
            table += '</tbody></table>';
            outputAllUsers.innerHTML = table;
        })
        .catch((error) => console.error(error));
}


//function to delete user
function deleteUser() {
    const deleteResponse = document.getElementById("deleteResponse");
    const userId = document.querySelector("#delete-user input").value;
    const requestOptions = {
        method: "DELETE",
        body: "",
        redirect: "follow"
    };

    fetch("/api/users/" + userId, requestOptions)
        .then((response) => response.text())
        .then((result) => {
            deleteResponse.innerHTML = "<div class='alert alert-success' role='alert'>Success!</div>";
            //clear response after 3 seconds
            setTimeout(() => {
                deleteResponse.innerHTML = "";
                }, 3000);
            //clear form fields dom
            document.querySelector("#delete-user input").value = "";
            getAllUsers();
        })
        .catch((error) =>{
            deleteResponse.innerHTML = "<div class='alert alert-danger' role='alert'>Failed</div>";
            console.error(error);}
        );


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
                    {id: "workUpdate", value: data.work},
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
    const updateResponse = document.getElementById("updateResponse"); // Assuming there's a corresponding div for alerts
    const userId = document.getElementById("userIdUpdate").value;
    const name = document.getElementById("nameUpdate").value;
    const email = document.getElementById("emailUpdate").value;
    const mobile = document.getElementById("mobileUpdate").value;
    const work = document.getElementById("workUpdate").value;
    const residence = document.getElementById("residenceUpdate").value;
    const password = document.getElementById("passwordUpdate").value;
    const designation = document.getElementById("designationUpdate").value;
    const department = document.getElementById("departmentUpdate").value;
    const roles = Array.from(document.getElementById("rolesUpdate").options)
        .filter(option => option.selected)
        .map(option => option.text.toUpperCase());

    if (name === "" || email === "" || mobile === "" || residence === "" ||
        password === "" || designation === "" || department === "" || roles
        .length === 0|| work === "") {
        updateResponse.innerHTML = "<div class='alert alert-danger' role='alert'>All fields are required</div>";
        setTimeout(() => {
            updateResponse.innerHTML = "";
        }, 1000);
        return;
    }

    const userData = {
        name: name,
        email: email,
        mobile: mobile,
        work: work,
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
            updateResponse.innerHTML = "<div class='alert alert-success' role='alert'>Success!</div>";
            // Clear form fields after update
            document.getElementById("nameUpdate").value = "";
            document.getElementById("emailUpdate").value = "";
            document.getElementById("mobileUpdate").value = "";
            document.getElementById("residenceUpdate").value = "";
            document.getElementById("passwordUpdate").value = "";
            document.getElementById("designationUpdate").value = "";
            document.getElementById("departmentUpdate").value = "";
            document.getElementById("rolesUpdate").value = "";
            document.getElementById("workUpdate").value = "";

            setTimeout(() => {
                updateResponse.innerHTML = "";
            }, 3000);

            getAllUsers(); // Refresh user list
        })
        .catch(error => {
            updateResponse.innerHTML = "<div class='alert alert-danger' role='alert'>Failed</div>";
            setTimeout(() => {
                updateResponse.innerHTML = "";
            }, 1000);
        });
}

//function to search user or users and display them in the search output

function searchUser() {
    const searchResponse = document.getElementById("searchResponse");
    const searchInput = document.getElementById("searchInput").value;
    //if the search input is empty, show error message and dont call the API
    if (searchInput === "") {
        searchResponse.innerHTML = "<div class='alert alert-danger' role='alert'>Search query is required</div>";
        setTimeout(() => {
            searchResponse.innerHTML = "";
        }, 1000);
        return;
    }
    const requestOptions = {
        method: "GET",
        redirect: "follow"
    };

    fetch("/api/users/search?query=" + searchInput, requestOptions)
        .then((response) => response.json())
        .then((data) => {
            const users = data.content;
            let table ='<table class="table table-striped table-bordered table-hover">'
            +'<thead class="thead-dark"><tr><th>ID</th><th>Name</th><th>Designation</th>'
            +'<th>Department</th><th>Email</th><th>Mobile</th><th>Roles</th></tr></thead><tbody>';
            users.forEach((user) => {
                table += `<tr>
                            <td>${user.id}</td>
                            <td>${user.name}</td>
                            <td>${user.designation}</td>
                            <td>${user.department}</td>
                            <td>${user.email}</td>
                            <td>${user.mobile}</td>
                            <td>${user.roles.join(", ")}</td>
                          </tr>`;
            });
            table += '</tbody></table>';
            searchResponse.innerHTML = table;
        })
        .catch((error) => console.error(error));
}

//search user by id and display the user in the search output reuse get user by id function
function searchUserById() {
    const searchResponse = document.getElementById("searchResponse");
    const userId = document.getElementById("searchInputId").value;
    //if the search input is empty, show error message and dont call the API
    if (userId === "") {
        searchResponse.innerHTML = "<div class='alert alert-danger' role='alert'>User ID is required</div>";
        setTimeout(() => {
            searchResponse.innerHTML = "";
        }, 1000);
        return;
    }
    getUserById(userId)
        .then(data => {
            if (data) {
                let table ='<table class="table table-striped table-bordered table-hover">'
                +'<thead class="thead-dark"><tr><th>ID</th><th>Name</th><th>Designation</th>'
                +'<th>Department</th><th>Email</th><th>Mobile</th><th>Roles</th></tr></thead><tbody>';
                table += `<tr>
                            <td>${data.id}</td>
                            <td>${data.name}</td>
                            <td>${data.designation}</td>
                            <td>${data.department}</td>
                            <td>${data.email}</td>
                            <td>${data.mobile}</td>
                            <td>${data.roles.join(", ")}</td>
                          </tr>`;
                table += '</tbody></table>';
                searchResponse.innerHTML = table;
            } else {
                console.log("No user data received.");
            }
        });
}
