async function login() {
    let username = document.getElementById('login').value;
    let password = document.getElementById('password').value;

    let userData = {
        username: username,
        password: password
    };

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