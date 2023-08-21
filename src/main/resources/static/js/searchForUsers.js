const userSearchForm = document.querySelector(".searchbar-form");
const friendsList = document.querySelector(".friends-list");

// Retrieve CSRF Token from cookies
const getCsrfToken = () => {
    const csrfCookie = document.cookie.split('; ')
        .find(row => row.startsWith('XSRF-TOKEN='));
    if (csrfCookie) {
        return csrfCookie.split('=')[1];
    } else {
        return null;
    }
}

// Retrieve a list of FriendDto from the server endpoint 
const fetchUsersFromDatabase = async (query) => {
    try {
        const response = await fetch(`/users/search?query=${query}`);
        const data = await response.json();

        return data;
    } catch (error) {
        console.log(error);
        return [];
    }
}

// Send an invitation to the user and give hime a specific right in the current space
const sendInvitToDatabase = async (username, aclType) => {
    const csrfToken = getCsrfToken();
    const currentUrl = window.location.href;
    const parts = currentUrl.split('/');
    const spaceId = parts[parts.length - 2];

    try {
        const response = await fetch(currentUrl, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "X-XSRF-TOKEN": csrfToken
            },
            body: JSON.stringify({
                username: username,
                space_id: spaceId,
                type: aclType
            })
        });
    } catch (error) {
        console.log(error);
    }
}

// If data is NOT_NULL display the list of users in the client 
const displayToTemplateData = async (query) => {
    const data = await fetchUsersFromDatabase(query);

    if(data[0]) {
        // Reset DOM element
        friendsList.innerHTML = "";

        data.forEach((user) => {
            const html = `<li class="friend-item flex justify-around items-center">
                <p class="item-username">${user.username}</p>
                <div class="flex justify-around">
                    <label for="acls-${user.id}" class="sr-only"></label>
                    <select id="acls-${user.id}" class="acl-select" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5">
                        <option value="" selected>Choose an access right</option>
                        <option value="READ_AND_WRITE">Read and Write</option>
                        <option value="READ_ONLY">Read only</option>
                    </select>
                    <form class="result-li-form" method="POST">
                        <input type="submit" value="add to space" class="block px-4 py-2 cursor-pointer hover:bg-blue-500 hover:text-white rounded-lg" />
                    </form>
                </div>
            </li>`;

            friendsList.insertAdjacentHTML("beforeend", html);    
        });

        // Assign the variable for being able to retrieve it in the DOM & send invitation request
        usersListItems = document.querySelectorAll(".friend-item");

        usersListItems.forEach(item => {
            const username = item.querySelector(".item-username").innerText;
            const aclType = item.querySelector(".acl-select");
            const form = item.querySelector(".result-li-form");

            form.addEventListener("submit", (e) => {
                e.preventDefault();
                if(aclType.value) {
                    sendInvitToDatabase(username, aclType.value, e.target);
                }
            });
        });
    }else {
        // Reset DOM element
        friendsList.innerHTML = "";

        const empty = `<span>No users found with: ${query}...</span>`;
        friendsList.insertAdjacentHTML("beforeend", empty);
    }
}


// Events Listeners:
userSearchForm.addEventListener("submit", (e) => {
    e.preventDefault();
    const query = e.target[0].value;
    displayToTemplateData(query)
});
