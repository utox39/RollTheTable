const numericFields = [
  "purchase_price",
  "sale_price",
  "iva",
  "stock_quantity",
  "discount",
  "minimum_age",
];

const submitButton = document.getElementById("submitButton");

numericFields.forEach((fieldId) => {
  const input = document.getElementById(fieldId);

  const messageContainer = document.createElement("div");
  messageContainer.classList.add("alert");
  messageContainer.classList.add("alert-danger");
  messageContainer.setAttribute("role", "alert");

  input.addEventListener("input", () => {
    const number = Number(input.value);

    if (number < 0) {
      messageContainer.innerHTML = "Non sono ammessi valori negativi";
      input.parentNode.appendChild(messageContainer);
      submitButton.disabled = true;
    } else {
      input.parentNode.removeChild(messageContainer);
      submitButton.disabled = false;
    }
  });
});
