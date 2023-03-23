const getCategories = async (id) => {
    let url = "http://127.0.0.1:8081/api/v1/categories";
    if (id >= 1)
        url = url + "/" + id;
    const headers = {}

    if (localStorage.jwtToken)
        headers["Authorization"] = "Bearer " + localStorage.jwtToken;

    const response = await fetch(url, {
        method: "GET",
        headers: headers
    });

    if (response.ok)
        return await response.json();
    else
        console.log(response.status);
}

const addCategory = async (jsonBody) => {
    const url = "http://127.0.0.1:8081/api/v1/categories";
    const headers = {
        'Content-Type': 'application/json'
    };

    if (localStorage.jwtToken)
        headers["Authorization"] = "Bearer " + localStorage.jwtToken;

    const response = await fetch(url, {
        method: 'POST',
        headers: headers,
        body: JSON.stringify(jsonBody)
    });

    const responseBody = await response.json();
    if (response.ok || response.status === 409)
        alert(responseBody.message)
    if (response.ok)
        return await responseBody;
}

const deleteCategory = async (id) => {
    let url = "http://127.0.0.1:8081/api/v1/categories";
    if (id >= 1)
        url = url + "/" + id;
    else
        return;

    const headers = {};
    if (localStorage.jwtToken)
        headers["Authorization"] = "Bearer " + localStorage.jwtToken;

    const response = await fetch(url, {
        method: 'DELETE',
        headers: headers
    });
    if (response.ok) {
        return await response.json();
    } else
        console.log(response.status)
}