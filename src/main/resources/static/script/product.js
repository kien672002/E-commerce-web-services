const getProducts = async (id) => {
    let url = "http://localhost:8081/api/v1/products";

    if (id >= 1)
        url = url + '/' + id;

    const headers = {};
    if (localStorage.jwtToken)
        headers["Authorization"] = "Bearer " + localStorage.jwtToken;

    const response = await fetch(url, {
        method: "GET",
        headers: headers
    });

    if (response.ok) {
        return await response.json();
    } else
        console.log(response.status);
}

const addProduct = async (formData) => {
    const url = "http://localhost:8081/api/v1/products";
    const headers = {};

    if (localStorage.jwtToken)
        headers["Authorization"] = "Bearer " + localStorage.jwtToken;

    const response = await fetch(url, {
        method: 'POST',
        headers: headers,
        body: formData
    });

    const responseBody = await response.json();
    if (response.ok || response.status === 409)
        alert(responseBody.message)
    if (response.ok)
        return responseBody;
    else
        console.log(response.status);
}

const deleteProduct = async (id) => {
    let url = "http://localhost:8081/api/v1/products";
    if (id >= 1)
        url = url + '/' + id;
    else
        return;

    const headers = {};
    if (localStorage.jwtToken)
        headers["Authorization"] = "Bearer " + localStorage.jwtToken;

    const response = await fetch(url, {
        method: "DELETE",
        headers: headers
    });

    if (response.ok)
        return await response.json();
    else
        console.log(response.status);
}
