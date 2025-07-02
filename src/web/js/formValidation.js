// noinspection t

document.addEventListener("DOMContentLoaded", () => {
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

    // Validates a text field for non-empty and max length
    function validateTextField(id, fieldName, button, maxLength = 100) {
        const field = document.getElementById(id);
        if (!field) return;

        field.addEventListener("input", () => {
            const value = field.value.trim();
            clearErrors(field);

            if (!value) {
                showError(field, `${fieldName} è obbligatorio.`);
                button.disabled = true;
            } else if (value.length > maxLength) {
                showError(field, `${fieldName} non può superare ${maxLength} caratteri.`);
                button.disabled = true;
            } else {
                button.disabled = false;
            }
        });
    }

    // Group field validation setup
    function setupFormValidation(prefix, button) {
        validateTextField(`${prefix}-name`, `Nome (${prefix})`, button);
        validateTextField(`${prefix}-surname`, `Cognome (${prefix})`, button);
        validateTextField(`${prefix}-street-name`, `Via (${prefix})`, button);
        validateTextField(`${prefix}-street-number`, `Numero civico (${prefix})`, button, 10);
        validateTextField(`${prefix}-postcode`, `CAP (${prefix})`, button, 10);
        validateTextField(`${prefix}-city`, `Città (${prefix})`, button);
        validateTextField(`${prefix}-province`, `Provincia (${prefix})`, button);
        validateTextField(`${prefix}-country`, `Paese (${prefix})`, button);
    }

    // Shipping address validation
    const saveShippingAddressBtn = document.getElementById("saveShippingAddress");
    setupFormValidation("shipping", saveShippingAddressBtn);

    // Billing address validation
    const saveBillingAddressBtn = document.getElementById("saveBillingAddress");
    setupFormValidation("billing", saveBillingAddressBtn);

    // Payment method validation
    const savePaymentMethodBtn = document.getElementById("savePaymentMethod");
    validateTextField("owner", "Titolare della carta", savePaymentMethodBtn);

    const cardNumberField = document.getElementById("card-number");
    if (cardNumberField) {
        cardNumberField.addEventListener("input", () => {
            const value = cardNumberField.value.trim();
            clearErrors(cardNumberField);

            // Only allow digits
            if (!/^\d{13,19}$/.test(value)) {
                showError(cardNumberField, "Il numero della carta deve contenere solo cifre (min: 13, max: 19).");
                savePaymentMethodBtn.disabled = true;
            } else {
                savePaymentMethodBtn.disabled = false;
            }
        });
    }

    const expDateField = document.getElementById("expiration-date");
    if (expDateField) {
        // Auto-format input to MM/YYYY
        expDateField.addEventListener("input", () => {
            expDateField.value = expDateField.value.replace(/[^0-9\/]/g, '');
        });

        expDateField.addEventListener("change", () => {
            const value = expDateField.value.trim();
            clearErrors(expDateField);

            const match = /^(0[1-9]|1[0-2])\/(\d{4})$/.exec(value);
            if (!match) {
                showError(expDateField, "La data di scadenza deve essere nel formato MM/YYYY.");
                savePaymentMethodBtn.disabled = true;
                return;
            }

            const month = parseInt(match[1], 10);
            const year = parseInt(match[2], 10);
            const now = new Date();
            const currentMonth = now.getMonth() + 1;
            const currentYear = now.getFullYear();

            if (year < currentYear || (year === currentYear && month < currentMonth)) {
                showError(expDateField, "La data di scadenza non può essere nel passato.");
                savePaymentMethodBtn.disabled = true;
            } else {
                savePaymentMethodBtn.disabled = false;
            }
        });
    }
});
