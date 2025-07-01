// noinspection t

document.addEventListener("DOMContentLoaded", () => {
  function toggleAddressForm(show, formContainerId) {
    const formContainer = document.getElementById(formContainerId);
    const inputs = formContainer.querySelectorAll("input, textarea");

    if (show) {
      formContainer.style.display = "block";
      inputs.forEach((i) => (i.disabled = false));
    } else {
      formContainer.style.display = "none";
      inputs.forEach((i) => (i.disabled = true));
    }
  }

  toggleAddressForm(false, "shippingAddressFormContainer");
  toggleAddressForm(false, "billingAddressFormContainer");

  document
    .getElementById("showShippingAddressFormBtn")
    ?.addEventListener("click", function () {
      const formContainer = document.getElementById(
        "shippingAddressFormContainer",
      );
      if (formContainer) {
        toggleAddressForm(true, "shippingAddressFormContainer");
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
        toggleAddressForm(false, "shippingAddressFormContainer");
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
        toggleAddressForm(true, "billingAddressFormContainer");
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
