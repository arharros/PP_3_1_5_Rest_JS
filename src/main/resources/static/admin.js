const url = '/admin/api/users/'

async function tableAdmin() {
    const container = $('#adminTable');
    const method = {
        dataType: 'json',
        type: 'GET'
    }

    fetch(url, method).then(response => response.json()).then(users => {
        let html = ''
        let rolesList = ''
        users.forEach(user => {
            for (const rolesListElement of user.userRoles) {
                rolesList = rolesList + rolesListElement.roleName.replace(/ROLE_/g, '') + ' '
            }
            let trHtml =
                `<tr>
                    <td>${user.idUser}</td>
                    <td>${user.userLogin}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td>${user.email}</td>
                    <td>${rolesList}</td>
                    <td>
                        <button id="btnEditUser" onclick="fillModelEditUserById(${user.idUser})" type="button" 
                        class="btn btn-primary btn-sm" data-bs-toggle="modal">
                              Edit
                        </button>
                        <button id="btnDeleteUser" onclick="fillModelDeleteUserById(${user.idUser})" type="button" 
                        class="btn btn-primary btn-sm btn-danger" data-bs-toggle="modal">
                              Delete
                        </button>
                    </td>
                </tr>`
            html += trHtml
            container.html(html)
            rolesList = ''
        })
    })
}

tableAdmin()


function addUser() {
    let roleList = $('#inputUserRoles').val()
    let temp = []
    for (const role of roleList) {
        temp.push({idRole: role})
    }

    let user = {
        userLogin: $('#inputUserLogin').val(),
        password: $('#inputPassword').val(),
        firstName: $('#inputFirstName').val(),
        lastName: $('#inputLastName').val(),
        email: $('#inputEmail').val(),
        userRoles: temp
    }

    const method = {
        method: 'POST',
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        },
        body: JSON.stringify(user)
    }
    fetch(url, method)
}


function fillModelDeleteUserById(id) {
    const urluserByid = url + id
    const method = {
        dataType: 'json',
        type: 'GET'
    }
    fetch(urluserByid, method).then(response => response.json()).then(user => {
        console.log(user)
        $('#deleteId').val(`${user.idUser}`)
        $('#deleteUserLogin').val(`${user.userLogin}`)
        $('#deleteFirstName').val(`${user.firstName}`)
        $('#deleteLastName').val(`${user.lastName}`)
        $('#deleteEmail').val(`${user.email}`)
        let role = ''
        let role2;
        for (const role1 of user.userRoles) {
            role2=role1.roleName.replace(/ROLE_/g, '')
            role = role + `<option> ${role2} </option>`
        }
        $('#deleteRoles').html(role)
        viewOpenModalDeleteUser()
    })
}

function deleteUserById(id) {
    const urlById = url + id
    const method = {
        method: 'DELETE',
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        }
    }
    fetch(urlById, method);
}

function viewOpenModalDeleteUser() {
    const viewHtmlDelete = document.getElementById('deleteModal')
    viewHtmlDelete.style.display = "block"
}

function closeOpenModalDeleteUser() {
    const viewHtmlDelete = document.getElementById('deleteModal')
    viewHtmlDelete.style.display = "none"
}


function fillModelEditUserById(id) {
    const urluserByid = url + id
    const method = {
        dataType: 'json',
        type: 'GET'
    }
    fetch(urluserByid, method).then(response => response.json()).then(user => {
        console.log(user)
        $('#editIdUser').val(`${user.idUser}`)
        $('#editUserLogin').val(`${user.userLogin}`)
        $('#editFirstName').val(`${user.firstName}`)
        $('#editLastName').val(`${user.lastName}`)
        $('#editEmail').val(`${user.email}`)
        let role = ''
        viewOpenModalEditUser()
    })
}

function viewOpenModalEditUser() {
    const viewHtmlDelete = document.getElementById('editModal')
    viewHtmlDelete.style.display = "block"
}
function closeOpenModalEditUser() {
    const viewHtmlDelete = document.getElementById('editModal')
    viewHtmlDelete.style.display = "none"
}


function updateUser() {
    let roleList = $('#editRoles').val()
    let temp = []
    for (const role of roleList) {
        temp.push({idRole: role})
    }
    let user = {
        idUser: $('#editIdUser').val(),
        userLogin: $('#editUserLogin').val(),
        password: $('#editPassword').val(),
        firstName: $('#editFirstName').val(),
        lastName: $('#editLastName').val(),
        email: $('#editEmail').val(),
        userRoles: temp
    }
    const method = {
        method: 'PUT',
        headers: {
            "Content-Type": "application/json;charset=utf-8"
        },
        body: JSON.stringify(user)
    }
    fetch(url, method).then(() => tableAdmin())
}






