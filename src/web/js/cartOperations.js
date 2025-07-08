function doCartOperation(op, productId) {
    const path = `/RollTheTable/cart?op=${op}&productId=${productId}`
    fetch(path,  {
        method: "GET",
    }).then((response) => {
        if (response.ok) {
            if (op !== "add") {
                location.reload();
            }
        }
    })
}

function addToTheCart(productId) {
    doCartOperation("add", productId);
}

function removeFromTheCart(productId) {
    doCartOperation("remove", productId);
}

function decreaseQuantity(productId) {
    doCartOperation("decrease", productId);
}

function increaseQuantity(productId) {
    doCartOperation("increase", productId);
}
