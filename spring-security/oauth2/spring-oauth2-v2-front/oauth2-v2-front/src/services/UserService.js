import showAlert from '../helpers/alertService';

export const handleSocialLogin = (provider) => {
    window.location.href =
        `http://localhost:8080/oauth2/login?provider=${provider}`;
}

export const postCallbackReq = async (token) => {
    try {
        const response = await fetch('http://localhost:8080/users/me/post-callback', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message);
        }

        if (data.token)
            localStorage.setItem("token", data.token);
    }
    catch(err) {
        showAlert({
            title: 'Error de inicio de sesión con red social',
            text: err.message,
            icon: 'error',
            confirmButtonColor: '#3085d6',
            confirmButtonText: "Aceptar"
        });
    }

}

export const getUserInfo = async () => {
    const token = localStorage.getItem('token');
    try {
        const response = await fetch('http://localhost:8080/users/me', {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        const data = await response.json();

        if (!response.ok) {
            if(response.status === 403) {
                throw new Error("redirect");
            }
            throw new Error(data.message);
        }

        return data;
    }
    catch(err) {
        if(err.message == "redirect")
            console.log(err.message)
        else
        showAlert({
            title: 'Error al obtener datos de usuario',
            text: err.message,
            icon: 'error',
            confirmButtonColor: '#3085d6',
            confirmButtonText: "Aceptar"
        });
    }
}

export const changeUserInfo = async(userData) => {
    const token = localStorage.getItem('token');
    try {
        const info = {
            username: '',
            web: userData.web,
            email: userData.email,
            avatarUrl: ''
        }
            const response = await fetch('http://localhost:8080/users/me', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(info)
            });

            const data = await response.json();

            if (!response.ok) {
                throw new Error(data.message || "Error desconocido");
            }

            return data;
        }
        catch (err) {
            showAlert({
                title: 'Error al modificar datos',
                text: err.message,
                icon: 'error',
                confirmButtonColor: '#3085d6',
                confirmButtonText: "Aceptar"
            });
        }

}

export const loginUser = async (username, password) => {
    try {
        const response = await fetch('http://localhost:8080/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({username, password})
        });

        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message || "Error desconocido");
        }

        return data;
    }
    catch (err) {
        showAlert({
            title: 'Error de inicio de sesión',
            text: err.message,
            icon: 'error',
            confirmButtonColor: '#3085d6',
            confirmButtonText: "Aceptar"
        });
        return "error";
    }
}

export const registerUser = async (registerData) => {
    try {
        const response = await fetch('http://localhost:8080/auth/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(registerData)
        });

        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message || "Error desconocido");
        }

        return data;
    }
    catch (err) {
        showAlert({
            title: 'Error de registro de usuario',
            text: err.message,
            icon: 'error',
            confirmButtonColor: '#3085d6',
            confirmButtonText: "Aceptar"
        });
        return "error";
    }
}


export const changePassword = async (passwordData) => {
    const token = localStorage.getItem('token');
    try {
        const response = await fetch('http://localhost:8080/users/me/password', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify({
                currentPassword: passwordData.currentPassword,
                newPassWord: passwordData.newPassword
            })
        });

        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message);
        }

        return data;
    }
    catch(err) {
        showAlert({
            title: 'Error al cambiar la contraseña del usuario',
            text: err.message,
            icon: 'error',
            confirmButtonColor: '#3085d6',
            confirmButtonText: "Aceptar"
        });
    }
}

export const changeAvatar = async (file) => {
    const formData = new FormData();
    formData.append('file', file);
    const token = localStorage.getItem('token');
    try {
        const response = await fetch('http://localhost:8080/users/me/avatar', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}`
            },
            body: formData,
        });

        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message);
        }

        return data;
    }
    catch(err) {
        showAlert({
            title: 'Error al subir avatar',
            text: err.message,
            icon: 'error',
            confirmButtonColor: '#3085d6',
            confirmButtonText: "Aceptar"
        });
    }
}
export const getAvatar = async (filename) => {
        const token = localStorage.getItem('token');
        try {
            const response = await fetch(`http://localhost:8080/users/me/avatar/${filename}`, {
                method: "GET",
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            });
            if (response.ok) {
                const imageBlob = await response.blob();
                const imageObjectURL = URL.createObjectURL(imageBlob);
                return imageObjectURL;
            } else {
                const data = await response.json();
                throw new Error(data.message);
            }
        } catch (error) {
            showAlert({
                title: 'Error al obtener el avatar',
                text: error.message,
                icon: 'error',
                confirmButtonColor: '#3085d6',
                confirmButtonText: "Aceptar"
            });
        }
    }

