// noinspection t

document.addEventListener("DOMContentLoaded", () => {

    // TODO: add a check to verify that every field of the active "form" is not empty

    function showError(htmlElement, errorMessage) {
        const errorMessageContainer = document.createElement("div");
        errorMessageContainer.classList.add("alert");
        errorMessageContainer.classList.add("alert-danger");
        errorMessageContainer.setAttribute("role", "alert");

        errorMessageContainer.innerHTML = errorMessage;

        htmlElement.parentNode.appendChild(errorMessageContainer);
    }

    function validateTextField(id, fieldName, sendButtonHtmlElement, maxLength = 100) {
        const field = document.getElementById(id);
        if (!field) return;
        field.addEventListener("input", (e) => {
            const value = field.value.trim();
            if ((value === "") || (value.length === 0)) {
                showError(field, `${fieldName} è obbligatorio.`);
                sendButtonHtmlElement.disabled = true;
            } else if (value.length > maxLength) {
                showError(field, `${fieldName} non può superare ${maxLength} caratteri.`);
                sendButtonHtmlElement.disabled = true;
            } else {
                const existingErrors = field.parentNode.querySelectorAll(".alert.alert-danger");
                existingErrors.forEach(e => e.remove());
                sendButtonHtmlElement.disabled = false;
            }
        });
    }

    //! SHIPPING ADDRESS
    const saveShippingAddressBtn = document.getElementById("saveShippingAddress");
    validateTextField("shipping-name", "Nome (spedizione)", saveShippingAddressBtn);
    validateTextField("shipping-surname", "Cognome (spedizione)", saveShippingAddressBtn);
    validateTextField("shipping-street-name", "Via (spedizione)", saveShippingAddressBtn);
    validateTextField(
        "shipping-street-number",
        "Numero civico (spedizione)",
        saveShippingAddressBtn,
        10,
    );
    validateTextField("shipping-postcode", "CAP (spedizione)", saveShippingAddressBtn, 10);
    validateTextField("shipping-city", "Città (spedizione)", saveShippingAddressBtn);
    validateTextField("shipping-province", "Provincia (spedizione)", saveShippingAddressBtn);
    validateTextField("shipping-country", "Paese (spedizione)", saveShippingAddressBtn);

    //! BILLING ADDRESS
    const saveBillingAddressBtn = document.getElementById("saveBillingAddress");
    validateTextField("billing-name", "Nome (fatturazione)", saveBillingAddressBtn);
    validateTextField("billing-surname", "Cognome (fatturazione)", saveBillingAddressBtn);
    validateTextField("billing-street-name", "Via (fatturazione)", saveBillingAddressBtn);
    validateTextField(
        "billing-street-number",
        "Numero civico (fatturazione)",
        saveBillingAddressBtn,
        10,
    );
    validateTextField("billing-postcode", "CAP (fatturazione)", saveBillingAddressBtn, 10,);
    validateTextField("billing-city", "Città (fatturazione)", saveBillingAddressBtn);
    validateTextField("billing-province", "Provincia (fatturazione)", saveBillingAddressBtn);
    validateTextField("billing-country", "Paese (fatturazione)", saveBillingAddressBtn);

    //! PAYMENT METHOD
    const savePaymentMethodBtn = document.getElementById("savePaymentMethod");
    validateTextField("owner", "Titolare della carta", savePaymentMethodBtn);
    const cardNumberField = document.getElementById("card-number");
    const expDateField = document.getElementById("expiration-date");
    if (cardNumberField) {
        cardNumberField.addEventListener("change", () => {
            const value = cardNumberField.value.trim();

            if (!/^\d{13,19}$/.test(value)) {
                showError(cardNumberField, "Il numero della carta deve contenere min: 13 e max: 19 cifre.");
            }
        })
    }
    if (expDateField) {
        expDateField.addEventListener("change", () => {
            const value = expDateField.value.trim();

            if (!/^(0[1-9]|1[0-2])\/\d{4}$/.test(value)) {
                showError(expDateField, "La data di scadenza deve essere nel formato MM/YYYY.")
            }
        })
    }
});
