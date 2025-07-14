// noinspection t

// Reusable function to show an error below a field
function showError(field, message) {
    clearErrors(field);
    const errorDiv = document.createElement("div");
    errorDiv.className = "alert alert-danger";
    errorDiv.setAttribute("role", "alert");
    errorDiv.textContent = message;
    field.parentNode.appendChild(errorDiv);
}

// Remove existing errors near a field
function clearErrors(field) {
    const errors = field.parentNode.querySelectorAll(".alert.alert-danger");
    errors.forEach(e => e.remove());
}

function checkEmail() {
    const emailInput = document.getElementById("email");
    const registerButton = document.getElementById("register-button");
    emailInput.addEventListener("change", function () {
        const emailInputValue = emailInput.value.trim();

        clearErrors(emailInput);

        if (emailInputValue === "") return;

        const xhr = new XMLHttpRequest();
        xhr.open("GET", `/RollTheTable/register?checkEmail=${encodeURIComponent(emailInputValue)}`, true);

        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                const response = JSON.parse(xhr.responseText);

                // if true the email is already in the database
                if (response.email === "true") {
                    showError(emailInput, "L'Email è già in uso")
                    registerButton.disabled = true;
                } else if (response.email === "false") {
                    registerButton.disabled = false;
                }
            }
        };
        xhr.send();
    })
}

function checkPassword() {
    const passwordInput = document.getElementById("password");
    const confirmPasswordInput = document.getElementById("confirmPassword");
    const registerButton = document.getElementById("register-button");

    passwordInput.addEventListener("input", function () {
        clearErrors(passwordInput);

        if (passwordInput.value.toString().length < 8) {
            showError(passwordInput, "La password deve avere un minimo di 8 caratteri");
            registerButton.disabled = true;
        } else {
            registerButton.disabled = false;
        }
    })
    confirmPasswordInput.addEventListener("input", function () {
        clearErrors(confirmPasswordInput);

        if (passwordInput.value !== confirmPasswordInput.value) {
            showError(confirmPasswordInput, "Le password non coincidono");
            registerButton.disabled = true;
        } else {
            registerButton.disabled = false;
        }
    })
}

document.addEventListener("DOMContentLoaded", function () {
    checkEmail();
    checkPassword();
});