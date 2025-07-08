function doCartOperation(op, productId) {
    const path = `/RollTheTable/cart?op=${op}&productId=${productId}`
    fetch(path,  {
        method: "GET",
    }).then(() => {})
}

function addToTheCart(productId) {
    doCartOperation("add", productId);
}

function removeFromTheCart(productId) {
    doCartOperation("remove", productId);
    location.reload();
}
