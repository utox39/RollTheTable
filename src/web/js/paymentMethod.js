document.addEventListener("DOMContentLoaded", () => {
  document
    .getElementById("showPaymentMethodFormBtn")
    ?.addEventListener("click", function () {
      const formContainer = document.getElementById(
        "paymentMethodFormContainer",
      );

      if (formContainer) {
        formContainer.style.display = "block";
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
        formContainer.style.display = "none";
        document.getElementById("showShippingAddressFormBtn").style.display =
          "inline-block";
      }
    });

  document.addEventListener("submit", function (e) {
    const form = e.target;
    if (form && form.id === "submit-payment-method") {
      e.preventDefault();

      const formData = new FormData(form);

      const params = new URLSearchParams();
      for (const [key, value] of formData.entries()) {
        params.append(key, value.toString());
      }

      fetch(form.action, {
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
        body: params.toString(),
      })
        .then((response) => {
          if (response.ok) {
            location.reload();
          }
        })
        .catch((error) => {
          console.log(error);
        });
    }
  });
}); // Close addEventListener "DOMContentLoaded"
