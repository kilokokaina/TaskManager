const projectId = Number.parseInt(document.getElementById('project-id').innerText);
let selectedTaskId = 0;

async function createTask() {
    let chosenUsers = [];
    let taskTitle = document.getElementById('taskTitle');
    let taskDescription = document.getElementById('taskDescribe');
    let users = document.querySelectorAll('.form-check-input');

    users.forEach(user => {
        if (user.value === '1') {
            let userId = user.getAttribute('id').split('-')[1];
            chosenUsers.push(Number.parseInt(userId));
            console.log('UserID: ' + userId);
        }
    });

    let task = {
        title: taskTitle.value,
        description: taskDescription.value,
        targetUser: chosenUsers,
        projectId: projectId
    }

    let response = await fetch('/api/task', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(task)
    });

    let result = response.status
    if (result === 200) {
        location.reload();
    }
}

function offcanvasTest(element) {
    let taskOffcanvas = new bootstrap.Offcanvas('#taskOffcanvas');
    let taskId = element.getAttribute('id');
    selectedTaskId = taskId;

    fetch(`/api/task/${taskId}`, { method: 'GET' }).then(async response => {
        let result = await response.json();

        let taskStatus = document.getElementById('offcanvasTaskStatus');
        switch (result.status) {
            case 'ASSIGNED':
                taskStatus.innerHTML = '<span class="badge text-bg-primary">Назначенно</span>';
                break;
            case 'IN_WORK':
                taskStatus.innerHTML = '<span class="badge text-bg-warning">В работе</span>';
                break;
            case 'DONE':
                taskStatus.innerHTML = '<span class="badge text-bg-success">Выполненно</span>';
                break;
        }

        let date = new Date(result.creationDate);
        let dateDDMMYY = date.getDay() + '.' + date.getMonth() + '.' + date.getFullYear();
        let dateHHMM = date.getHours() + ':' + date.getMinutes();

        document.getElementById('offcanvasTaskTitle').innerText = result.title;
        document.getElementById('offcanvasTaskDescribe').innerText = result.description;
        document.getElementById('offcanvasTaskAuthor').innerText = `${result.author}\n${dateDDMMYY}, ${dateHHMM}`;

        let chatBox = document.getElementById('chat');
        chatBox.innerHTML = '';

        result.commentList.forEach(comment => {
            let commentDate = new Date(comment.creationData);
            let commentDateDDMMYY = commentDate.getDay() + '.' + commentDate.getMonth() + '.' + commentDate.getFullYear();
            let commentDateHHMM = commentDate.getHours() + ':' + commentDate.getMinutes();

            if (comment.author === document.getElementById('username').innerHTML) {
                chatBox.innerHTML += `
                    <div class="d-flex flex-row justify-content-end">
                        <div class="chat-msg-out">
                            <span class="msg-author">${comment.author}</span>
                            <p class="chat-msg-out">${comment.commentText}</p>
                            <span class="msg-time">${commentDateHHMM}, ${commentDateDDMMYY}</span>
                        </div>
                        <img src="/img/user-profile.png" width="25" height="25">
                    </div>`;
            } else {
                chatBox.innerHTML += `
                    <div class="d-flex flex-row justify-content-start">
                        <img src="/img/user-profile.png" width="25" height="25">
                        <div class="chat-msg-in">
                            <span class="msg-author">${comment.author}</span>
                            <p class="chat-msg-in">${comment.commentText}</p>
                            <span class="msg-time">${commentDateHHMM}, ${commentDateDDMMYY}</span>
                        </div>
                    </div>`;
            }
        });
    });

    fetch(`/api/task/user_list/${taskId}`, {method: 'GET'}).then(async response => {
        let result = await response.json();
        result.forEach(user => console.log(user.username));
    })

    taskOffcanvas.show();
}

function sendComment() {
    let commentAuthor = document.getElementById('username').innerHTML;
    let commentText = document.getElementById('comment-input');
    let taskId = selectedTaskId;

    let comment = {
        commentText: commentText.value,
        author: commentAuthor
    }

    fetch(`/api/task/comment/${taskId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        body: JSON.stringify(comment)
    }).then(async response => {
        if (response.status === 200) {
            const result = await response.json();

            let date = new Date(result.creationData);
            let dateDDMMYY = date.getDay() + '.' + date.getMonth() + '.' + date.getFullYear();
            let dateHHMM = date.getHours() + ':' + date.getMinutes();

            document.getElementById('chat').innerHTML += `
                <div class="d-flex flex-row justify-content-end">
                    <div class="chat-msg-out">
                        <span class="msg-author">${commentAuthor}</span>
                        <p class="chat-msg-out">${commentText.value}</p>
                        <span class="msg-time">${dateHHMM}, ${dateDDMMYY}</span>
                    </div>
                    <img src="/img/user-profile.png" width="25" height="25">
                </div>`;

            commentText.value = '';
        }
    })
}



async function changeStatus(event) {
    let statusSpan = event.getElementsByClassName('badge');
    switch (statusSpan.item(0).innerHTML) {
        case 'Назначенно':
            sendRequest('IN_WORK', '<span class="badge text-bg-warning">В работе</span>');
            break;
        case 'В работе':
            sendRequest('ASSIGNED', '<span class="badge text-bg-secondary">Назначенно</span>');
            break;
    }

    function sendRequest(status, element) {
        fetch(`/api/task/status/${selectedTaskId}?status=${status}`, {
            method: 'GET'
        }).then(async response => {
            let result = response.status;
            if (result === 200) {
                event.innerHTML = element;
                location.reload();
            }
        })
    }
}
