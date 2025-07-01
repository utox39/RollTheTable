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
        toggleAddressForm(false, "billingAddressFormContainer");
        document.getElementById("showBillingAddressFormBtn").style.display =
          "inline-block";
      }
    });

  function sendAddressData(addressType) {
    const data = {
      type: document.getElementById(`${addressType}-address-type`).value,
      name: document.getElementById(`${addressType}-name`).value,
      surname: document.getElementById(`${addressType}-surname`).value,
      streetName: document.getElementById(`${addressType}-street-name`).value,
      streetNumber: document.getElementById(`${addressType}-street-number`)
        .value,
      postcode: document.getElementById(`${addressType}-postcode`).value,
      city: document.getElementById(`${addressType}-city`).value,
      province: document.getElementById(`${addressType}-province`).value,
      country: document.getElementById(`${addressType}-country`).value,
      notes:
        addressType === "billing"
          ? ""
          : document.getElementById(`${addressType}-notes`).value,
    };

    fetch("/RollTheTable/secure/customer/address", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data),
    }).then((response) => {
      if (response.ok) {
        location.reload();
      }
    });
  }
  document
    .getElementById("saveShippingAddress")
    .addEventListener("click", function () {
      sendAddressData("shipping");
    });

  document
    .getElementById("saveBillingAddress")
    .addEventListener("click", function () {
      sendAddressData("billing");
    });
}); // Close addEventListener "DOMContentLoaded"
