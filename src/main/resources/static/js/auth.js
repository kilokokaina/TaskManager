async function login() {
    let username = document.getElementById('login').value;
    let password = document.getElementById('password').value;

    let userData = {
        username: username,
        password: password
    };

    console.log(userData);

    let response = await fetch('/auth/rest', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json; charset=utf-8'
        },
        body: JSON.stringify(userData)
    });

    let result = response.status;

    if (result === 200) {
        location.reload();
    }
}

document.body.onload = () => {
    let projectTaskCount = document.getElementsByClassName('task-span');
    for (let i = 0; i < projectTaskCount.length; i++) {
        let projectId = projectTaskCount.item(i).id.split('-')[1];
        fetch(`/api/task/find_by_project/${projectId}?status=`, {
            method: 'GET'
        }).then(async response => {
            let tasks = await response.json();
            projectTaskCount.item(i).innerHTML = tasks.length;
        });
    }

    let authorTasks = document.getElementById('author-tasks');
    let assignedTasks = document.getElementById('assigned-tasks');
    sendRequest(true, authorTasks);
    sendRequest(false, assignedTasks);

    function sendRequest(isAuthor, spanElement) {
        fetch(`/api/task/find_by_user?is_author=${isAuthor}`, {
            method: 'GET'
        }).then(async response => {
            let tasks = await response.json();
            spanElement.innerHTML = tasks.length;
        });
    }

};