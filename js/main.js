// Facebook login
(function(d, s, id) {
    var js, fjs = d.getElementsByTagName(s)[0];
    if (d.getElementById(id)) return;
    js = d.createElement(s); js.id = id;
    js.src = "//connect.facebook.net/en_US/sdk.js";
    fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

// Dropdown Login Form
$(function() {
    // Setup drop down menu
    $('.dropdown-toggle').dropdown();

    // Fix input element click problem
    $('.dropdown input, .dropdown label').click(function(e) {
        e.stopPropagation();
    });
});

// Asks user if wants to automatically log into application following successful account creation
var success = "${accountCreateSuccess}";
if (success === "success") {
    var response = window.confirm("Account successfully created. Automatically logging you in.");
    if (response) {
        window.location.replace("/CS2340Servlet/jsp/createLoginSession.jsp");
    } else {
        window.location.replace("/CS2340Servlet/jsp/index.jsp");
    }
}