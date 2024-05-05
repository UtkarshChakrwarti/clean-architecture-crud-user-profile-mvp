document.querySelectorAll(".sidebar-navigation ul li").forEach((item) => {
    item.addEventListener("click", function () {
      document
        .querySelectorAll(".sidebar-navigation ul li")
        .forEach((innerItem) => {
          innerItem.classList.remove("active");
        });
      this.classList.add("active");

      document.querySelectorAll(".section-data").forEach((section) => {
        section.style.display = "none";
      });

      const sectionId = this.getAttribute("data-target");
      document.getElementById(sectionId).style.display = "block";
    });
  });

  // Initialize first tab and section visibility
  document.getElementById("dashboard").style.display = "block";


// script for the all form which makes the form work and return the output in the output terminal section
document.querySelectorAll("form").forEach((form) => {
    form.addEventListener("submit", function (event) {
        event.preventDefault();
        // Perform form submission logic here

        // Get the output terminal element
        const outputTerminal = document.getElementById("outputTerminal");

        // Update the output content
        const outputContent = outputTerminal.querySelector(".output-content");
        outputContent.innerHTML = "<h3>Output</h3><p>Output will be displayed here...</p>";

        // Display the output terminal section
        outputTerminal.style.display = "block";
    });
});

