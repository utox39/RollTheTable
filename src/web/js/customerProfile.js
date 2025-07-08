function enableField(fieldId) {
    const input = document.getElementById(fieldId);
    const text = document.getElementById(fieldId + "Text");
    const saveBtn = document.getElementById("saveBtn");

    input.classList.remove("d-none");
    text.classList.add("d-none");
    input.disabled = false;
    saveBtn.classList.remove("d-none");
}

document.getElementById("profileForm").addEventListener("submit", function (e) {
    let valid = true;

    // Email check
    const emailInput = document.getElementById("email");
    if (!emailInput.classList.contains("d-none")) {
        const emailError = document.getElementById("emailError");
        const emailRegex = /^[^@\s]+@[^@\s]+\.[^@\s]+$/;
        if (!emailRegex.test(emailInput.value)) {
            emailError.classList.remove("d-none");
            valid = false;
        } else {
            emailError.classList.add("d-none");
        }
    }

    // Phone check
    const phoneInput = document.getElementById("phone");
    if (!phoneInput.classList.contains("d-none")) {
        const phoneError = document.getElementById("phoneError");
        const phoneRegex = /^[0-9]{8,15}$/;
        if (!phoneRegex.test(phoneInput.value)) {
            phoneError.classList.remove("d-none");
            valid = false;
        } else {
            phoneError.classList.add("d-none");
        }
    }

    if (!valid) {
        e.preventDefault();
    }
});
