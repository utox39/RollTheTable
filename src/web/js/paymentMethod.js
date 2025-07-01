// noinspection t

document.addEventListener("DOMContentLoaded", () => {
  function togglePaymentMethodForm(show) {
    const formContainer = document.getElementById("paymentMethodFormContainer");
    const inputs = formContainer.querySelectorAll("input, textarea");

    if (show) {
      formContainer.style.display = "block";
      inputs.forEach((i) => (i.disabled = false));
    } else {
      formContainer.style.display = "none";
      inputs.forEach((i) => (i.disabled = true));
    }
  }

  togglePaymentMethodForm(false);

  document
    .getElementById("showPaymentMethodFormBtn")
    ?.addEventListener("click", function () {
      const formContainer = document.getElementById(
        "paymentMethodFormContainer",
      );

      if (formContainer) {
        togglePaymentMethodForm(true);
        this.style.display = "none";
      }
    });

  document
    .getElementById("hidePaymentMethodFormContainer")
    ?.addEventListener("click", function () {
      const formContainer = document.getElementById(
        "paymentMethodFormContainer",
      );

      if (formContainer) {
        togglePaymentMethodForm(false);
        document.getElementById("showPaymentMethodFormBtn").style.display =
          "inline-block";
      }
    });

  document
    .getElementById("savePaymentMethod")
    .addEventListener("click", function () {
      const data = {
        owner: document.getElementById("owner").value,
        cardNumber: document.getElementById("card-number").value,
        expireDate: document.getElementById("expiration-date").value,
      };

      fetch("/RollTheTable/secure/customer/payment-methods", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data),
      }).then((response) => {
        if (response.ok) {
          location.reload();
        }
      });
    });
}); // Close addEventListener "DOMContentLoaded"
