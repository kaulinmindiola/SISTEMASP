document.addEventListener("DOMContentLoaded", function() {
        // Espera 3 segundos y luego oculta los mensajes
        setTimeout(function() {
            document.getElementById("errorMessage").style.display = "none";
            document.getElementById("logoutMessage").style.display = "none";
            document.getElementById("successMessage").style.display = "none";
        }, 3000);
    });