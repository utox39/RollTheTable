document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("submit-order");

    form.addEventListener("submit", (e) => {
        let isValid = true;
        let errors = [];

        // Helper function to check required text fields
        function validateTextField(id, fieldName, maxLength = 100) {
            const field = document.getElementById(id);
            if (!field) return;

            const value = field.value.trim();
            if (value === "") {
                isValid = false;
                errors.push(`${fieldName} è obbligatorio.`);
            } else if (value.length > maxLength) {
                isValid = false;
                errors.push(`${fieldName} non può superare ${maxLength} caratteri.`);
            }
        }

        // Validate all required shipping inputs (if visible)
        if (document.getElementById("shippingAddressFormContainer").style.display !== "none") {
            validateTextField("shipping-name", "Nome (spedizione)");
            validateTextField("shipping-surname", "Cognome (spedizione)");
            validateTextField("shipping-street-name", "Via (spedizione)");
            validateTextField("shipping-street-number", "Numero civico (spedizione)", 10);
            validateTextField("shipping-postcode", "CAP (spedizione)", 10);
            validateTextField("shipping-city", "Città (spedizione)");
            validateTextField("shipping-province", "Provincia (spedizione)");
            validateTextField("shipping-country", "Paese (spedizione)");
        }

        // Validate all required billing inputs (if visible)
        if (document.getElementById("billingAddressFormContainer").style.display !== "none") {
            validateTextField("billing-name", "Nome (fatturazione)");
            validateTextField("billing-surname", "Cognome (fatturazione)");
            validateTextField("billing-street-name", "Via (fatturazione)");
            validateTextField("billing-street-number", "Numero civico (fatturazione)", 10);
            validateTextField("billing-postcode", "CAP (fatturazione)", 10);
            validateTextField("billing-city", "Città (fatturazione)");
            validateTextField("billing-province", "Provincia (fatturazione)");
            validateTextField("billing-country", "Paese (fatturazione)");
        }

        // Validate payment method inputs (if visible)
        if (document.getElementById("paymentMethodFormContainer").style.display !== "none") {
            validateTextField("owner", "Titolare della carta");
            const cardNumber = document.getElementById("card-number").value.trim();
            const expDate = document.getElementById("expiration-date").value.trim();

            if (!/^\d{13,19}$/.test(cardNumber)) {
                isValid = false;
                errors.push("Il numero della carta deve contenere solo cifre (13-19).");
            }

            if (!/^(0[1-9]|1[0-2])\/\d{4}$/.test(expDate)) {
                isValid = false;
                errors.push("La data di scadenza deve essere nel formato MM/YYYY.");
            }
        }

        // Ensure shipping and billing addresses are selected if dropdowns exist
        const shippingSelect = document.getElementById("shippingAddress");
        const billingSelect = document.getElementById("billingAddress");

        if (shippingSelect && shippingSelect.options.length === 0) {
            isValid = false;
            errors.push("Seleziona o aggiungi un indirizzo di spedizione.");
        }

        if (billingSelect && billingSelect.options.length === 0) {
            isValid = false;
            errors.push("Seleziona o aggiungi un indirizzo di fatturazione.");
        }

        // Ensure a payment method is selected
        const paymentSelect = document.getElementById("paymentMethod");
        if (paymentSelect && paymentSelect.options.length === 0) {
            isValid = false;
            errors.push("Seleziona o aggiungi un metodo di pagamento.");
        }

        if (!isValid) {
            e.preventDefault();
            alert("Correggi i seguenti errori:\n\n" + errors.join("\n"));
        }
    });
});
