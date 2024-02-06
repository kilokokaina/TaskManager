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

function test(element) {
    const testModal = new bootstrap.Modal(document.getElementById('taskModal'));

    let taskId = element.getAttribute('id');
    selectedTaskId = taskId;

    fetch(`/api/task/${taskId}`, { method: 'GET' }).then(async response => {
        let result = await response.json();

        let taskStatus = document.getElementById('taskStatus');
        switch (result.status) {
            case 'ASSIGNED':
                taskStatus.innerHTML = '<span class="badge text-bg-warning">Назначенно</span>';
                break;
            case 'IN_WORK':
                taskStatus.innerHTML = '<span class="badge text-bg-info">В работе</span>';
                break;
            case 'DONE':
                taskStatus.innerHTML = '<span class="badge text-bg-success">Выполненно</span>';
                break;
        }

        let date = new Date(result.creationDate);
        let dateDDMMYY = date.getDay() + '.' + date.getMonth() + '.' + date.getFullYear();
        let dateHHMM = date.getHours() + ':' + date.getMinutes();

        document.getElementById('taskModalTitle').innerText = result.title;
        let taskDialog = document.getElementById('taskModalText');
        taskDialog.innerHTML = `
            <span class="badge text-bg-dark mt-1">${dateDDMMYY}</span>
            <div class="d-flex flex-row justify-content-start">
                <div class="p-2">
                    <img src="/img/user-profile.png" width="30" height="30">
                </div>
                <div class="p-2 card mt-1 border-info" style="max-width: 18rem;">
                  <div class="card-header">
                    ${result.author}
                  </div>
                  <div class="card-body">
                    <blockquote class="blockquote mb-0">
                      <p style="font-size: 1rem">${result.description}</p>
                      <footer class="blockquote-footer" style="font-size: 0.8rem">${dateHHMM}</footer>
                    </blockquote>
                  </div>
                </div>
            </div>`;

        result.commentList.forEach(comment => {
            let commentDate = new Date(comment.creationData);
            let commentDateDDMMYY = commentDate.getDay() + '.' + commentDate.getMonth() + '.' + commentDate.getFullYear();
            let commentDateHHMM = commentDate.getHours() + ':' + commentDate.getMinutes();

            if (commentDateDDMMYY !== dateDDMMYY) {
                taskDialog.innerHTML += `<span class="badge text-bg-dark mt-1">${commentDateDDMMYY}</span>`;
            }

            if (comment.author === document.getElementById('username').innerHTML) {
                taskDialog.innerHTML += `
                    <div class="d-flex flex-row justify-content-end">
                        <div class="p-2 card mt-1 border-success" style="max-width: 18rem;">
                          <div class="card-header">
                            ${comment.author}
                          </div>
                          <div class="card-body">
                            <blockquote class="blockquote mb-0">
                              <p style="font-size: 1rem">${comment.commentText}</p>
                              <footer class="blockquote-footer" style="font-size: 0.8rem">${commentDateHHMM}</footer>
                            </blockquote>
                          </div>
                        </div>
                        <div class="p-2">
                            <img src="/img/user-profile.png" width="30" height="30">
                        </div>
                    </div>`;

            } else {
                taskDialog.innerHTML += `
                    <div class="p-2 d-flex flex-row justify-content-start">
                        <div class="p-2">
                            <img src="/img/user-profile.png" width="30" height="30">
                        </div>
                        <div class="p-2 card mt-1 border-info" style="max-width: 18rem;">
                          <div class="card-header">
                            ${comment.author}
                          </div>
                          <div class="card-body">
                            <blockquote class="blockquote mb-0">
                              <p style="font-size: 1rem">${comment.commentText}</p>
                              <footer class="blockquote-footer" style="font-size: 0.8rem">${commentDateHHMM}</footer>
                            </blockquote>
                          </div>
                        </div>
                    </div>`;
            }
        });
    });

    testModal.show();
}

function sendComment() {
    let commentAuthor = document.getElementById('username').innerHTML;
    let commentText = document.getElementById('comment-input').value;
    let taskId = selectedTaskId;

    let comment = {
        commentText: commentText,
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

            document.getElementById('taskModalText').innerHTML += `
                <span class="badge text-bg-dark mt-1">${dateDDMMYY}</span>
                <div class="d-flex flex-row justify-content-end">
                    <div class="p-2 card mt-1 border-success" style="max-width: 18rem;">
                      <div class="card-header">
                        ${result.author}
                      </div>
                      <div class="card-body">
                        <blockquote class="blockquote mb-0">
                          <p style="font-size: 1rem">${result.commentText}</p>
                          <footer class="blockquote-footer" style="font-size: 0.8rem">${dateHHMM}</footer>
                        </blockquote>
                      </div>
                    </div>
                    <div class="p-2">
                        <img src="/img/user-profile.png" width="30" height="30">
                    </div>
                </div>`;

            document.getElementById('comment-input').innerText = '';
        }
    })
}

async function changeStatus(event) {
    let statusSpan = event.getElementsByClassName('badge');
    switch (statusSpan.item(0).innerHTML) {
        case 'Назначенно':
            sendRequest('IN_WORK', '<span class="badge text-bg-info">В работе</span>');
            break;
        case 'В работе':
            sendRequest('ASSIGNED', '<span class="badge text-bg-warning">Назначенно</span>');
            break;
    }

    function sendRequest(status, element) {
        fetch(`/api/task/status/${selectedTaskId}?status=${status}`, {
            method: 'PUT'
        }).then(async response => {
            let result = response.status;
            if (result === 200) {
                event.innerHTML = element;
                location.reload();
            }
        })
    }
}