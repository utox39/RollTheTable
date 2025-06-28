// noinspection t

document.addEventListener("DOMContentLoaded", () => {
  document
    .getElementById("showShippingAddressFormBtn")
    ?.addEventListener("click", function () {
      const formContainer = document.getElementById(
        "shippingAddressFormContainer",
      );
      if (formContainer) {
        formContainer.style.display = "block";
        this.style.display = "none";
      }
    });

  document
    .getElementById("hideShippingAddressFormContainer")
    ?.addEventListener("click", function () {
      const formContainer = document.getElementById(
        "shippingAddressFormContainer",
      );
      if (formContainer) {
        formContainer.style.display = "none";
        document.getElementById("showShippingAddressFormBtn").style.display =
          "inline-block";
      }
    });

  document
    .getElementById("showBillingAddressFormBtn")
    ?.addEventListener("click", function () {
      const formContainer = document.getElementById(
        "billingAddressFormContainer",
      );
      if (formContainer) {
        formContainer.style.display = "block";
        this.style.display = "none";
      }
    });

  document
    .getElementById("hideBillingAddressFormContainer")
    ?.addEventListener("click", function () {
      const formContainer = document.getElementById(
        "billingAddressFormContainer",
      );
      if (formContainer) {
        formContainer.style.display = "none";
        document.getElementById("showBillingAddressFormBtn").style.display =
          "inline-block";
      }
    });
  document.addEventListener("submit", function (e) {
    const form = e.target;
    if (
      form &&
      (form.id === "submit-shipping-address" ||
        form.id === "submit-billing-address")
    ) {
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
