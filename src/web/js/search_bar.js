const appName = "/RollTheTable";

document.addEventListener("DOMContentLoaded", function () {
    const input = document.querySelector("input[type='search']");
    if (!input) return;

    const container = document.createElement("div");
    container.classList.add("search-suggestions");
    container.style.position = "fixed"; // <-- KEY FIX
    container.style.display = "none";
    document.body.appendChild(container);

    input.addEventListener("input", function () {
        const query = input.value.trim();
        if (query.length === 0) {
            container.innerHTML = "";
            container.style.display = "none";
            return;
        }

        const rect = input.getBoundingClientRect();
        container.style.left = `${rect.left}px`;
        container.style.top = `${rect.bottom}px`;
        container.style.width = `${rect.width}px`;
        container.style.display = "block";

        const xhr = new XMLHttpRequest();
        xhr.open("GET", `${appName}/search?query=${encodeURIComponent(query)}`, true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                try {
                    const suggestions = JSON.parse(xhr.responseText);
                    container.innerHTML = "";

                    if (suggestions.length === 0) {
                        container.innerHTML = "<div class='suggestion-item'>Nessun risultato</div>";
                        return;
                    }

                    suggestions.forEach(item => {
                        const div = document.createElement("div");
                        div.className = "suggestion-item";

                        const regex = new RegExp(`(${query})`, 'ig');
                        const highlighted = item.name.replace(regex, "<span class='suggestion-highlight'>$1</span>");
                        div.innerHTML = `<span>${highlighted}</span>`;

                        div.addEventListener("click", () => {
                            window.location.href = `${appName}/product?productId=${item.productId}`;
                        });

                        container.appendChild(div);
                    });
                } catch (e) {
                    console.error("JSON error:", e);
                }
            }
        };
        xhr.send();
    });

    // Hide on click outside
    document.addEventListener("click", function (e) {
        if (!input.contains(e.target) && !container.contains(e.target)) {
            container.style.display = "none";
        }
    });
});
