const addToCart = (product) => {
    // console.log(product)
    let arr = []
    if (localStorage.products !== undefined)
        arr = JSON.parse(localStorage.products)
    arr.push(product)
    localStorage.products = JSON.stringify(arr);
}

const getFromCart = () => {
    return localStorage.products ? JSON.parse(localStorage.products) : [];
}

const removeFromCart = (id) => {
    const products = getFromCart();
    for (let index = 0; index < products.length; ++index) {
        const product = products[index];
        if (product.id === id) {
            products.splice(index, 1)
            break;
        }
    }
    localStorage.products = JSON.stringify(products);
}