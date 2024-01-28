async function createTask() {
    let chosenUsers = [];
    let taskTitle = document.getElementById('taskTitle');
    let taskDescription = document.getElementById('taskDescribe');
    let users = document.querySelectorAll('.form-check-input');
    let projectId = Number.parseInt(document.getElementById('project-id').innerText);

    console.log('ProjectID: ' + projectId);
    console.log('Title: ' + taskTitle.value);
    console.log('Description: ' + taskDescription.value);
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