const formSubmitter = (form, url, resolve) => {
    form.addEventListener("submit", async (event) => {
        event.preventDefault();
        const formData = new FormData(form);
        const jsonBody = Object.fromEntries(formData);
        try {
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(jsonBody)
            });
            resolve(await response);
        } catch (error) {
            console.error(error);
        }
    })
}

const isAuthorize = async (authority) => {
    const jwtToken = localStorage?.jwtToken
    if (jwtToken === undefined)
        return false;
    try {
        const response = await fetch("http://localhost:8081/api/v1/auth", {
            method: 'GET',
            headers: {
                'Content-Type': 'text/plain',
                'Authorization': "Bearer " + jwtToken
            },
            mode: "cors"
        });
        const responseBody = await response.json();
        if (!response.ok)
            return false;
        return responseBody?.data.includes(authority);
    } catch (exception) {
        console.log(exception);
        return false;
    }
}

const redirectToHome = (home = "index.html") => {
    if (window.location.pathname.slice(window.location.pathname.length - home.length) !== home)
        window.location.href = home;
}

const logoutRef = document.getElementById("logout");
const loginRef = document.getElementById("login");
const body = document.querySelector("body");

const renderLogoutHref = () => {
    if (logoutRef) logoutRef.style.display = ""
};
const renderLoginHref = () => {
    if (loginRef) loginRef.style.display = ""
};
const hideLogoutHref = () => {
    if (logoutRef)
        logoutRef.style.display = "none";
}
const hideLoginHref = () => {
    if (loginRef) {
        loginRef.style.display = "none";
    }

}
const renderBody = () => {
    if (body) body.style.display = "";
}
if (logoutRef) {
    logoutRef.onclick = () => {
        localStorage.removeItem("jwtToken");
        alert("Logout successfully")
        redirectToHome();
    }
}
// if (body) {
//     body.style.display = "none";
// }
/**
 *
 * @param renderBodyIfLoggedIn
 * @param renderBodyIfLoggedOut
 * @param roleRequired
 */
const doIfLoginOrLogout = (renderBodyIfLoggedIn = false, renderBodyIfLoggedOut = false, roleRequired = "USER") => {
    const jwtToken = localStorage.jwtToken;
    if (jwtToken === undefined) { // if user has not logged in yet
        if (renderBodyIfLoggedOut)
            renderBody();
        else
            redirectToHome();
        renderLoginHref();
        hideLogoutHref();
        return;
    }

    // and if user has been logged in
    isAuthorize(roleRequired).then(ok => {
        // if user has valid role
        if (ok === true) {
            if (renderBodyIfLoggedIn)
                renderBody();
            else
                redirectToHome();
            renderLogoutHref();
            hideLoginHref();
        } // or not have enough privilege, or login token expired
        else {
            localStorage.clear(jwtToken);
            redirectToHome();
        }
    })
}


