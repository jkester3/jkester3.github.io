var landingTitle = document.getElementById("landing-title-h1");
var landingCard = document.getElementsByClassName("landing-card");
for (var i = 0; i < landingCard.length; i++) {
    landingCard[i].addEventListener("mouseover", function () {
        landingTitle.style.opacity = 1;
        landingTitle.style.textShadow = "4px 4px #000000";
        $(landingTitle).slideDown('slow');
        this.style.opacity = 0.95;
        this.style.backgroundColor = "black";
        $(this).stop().animate({
            height: '+500px'
        }, 1000, function() {
            var hiddenText = this.getElementsByTagName("span")[0];
            hiddenText.style.display = "";
        });
    }, true);
    landingCard[i].addEventListener("mouseout", function () {
        landingTitle.style.opacity = 0.8;
        landingTitle.style.textShadow = "";
        this.style.opacity = 0.9;
        this.style.backgroundColor = this.style.color;
        var hiddenText = this.getElementsByTagName("span")[0];
        hiddenText.style.display = "none";
        $(this).stop().animate({
            height: '200px'
        }, 1000);
    }, true);
    landingCard[i].addEventListener("click", function() {
        var parentID = this.id;
        var itineraryHeader, itineraryPage, lodgingPage, eventPage, budgetPage,
            mapPage;
        $("#itinerary-header").fadeOut("slow", function() {
            if (parentID === "map-landing-card") {
                itineraryHeader = $("#itinerary-header");
                mapPage = $("#map-page");
                mapPage.addClass("active-page");
                itineraryHeader.removeClass("active-page");
                mapPage.show();
                initialize();
                window.scrollTo(0, 0);
            } else if (parentID === "lodging-landing-card") {
                itineraryHeader = $("#itinerary-header");
                lodgingPage = $("#lodging-page");
                lodgingPage.addClass("active-page");
                itineraryHeader.removeClass("active-page");
                lodgingPage.show();
                window.scrollTo(0, 0);
            } else if (parentID === "itinerary-landing-card") {
                itineraryHeader = $("#itinerary-header");
                itineraryPage = $("#itinerary-overview");
                itineraryPage.addClass("active-page");
                itineraryHeader.removeClass("active-page");
                itineraryPage.show();
                window.scrollTo(0, 0);
            } else if (parentID === "event-landing-card") {
                itineraryHeader = $("#itinerary-header");
                eventPage = $("#event-places-page");
                eventPage.addClass("active-page");
                itineraryHeader.removeClass("active-page");
                eventPage.show();
                window.scrollTo(0, 0);
            } else if (parentID === "budget-landing-card") {
                itineraryHeader = $("#itinerary-header");
                budgetPage = $("#budget-page");
                budgetPage.addClass("active-page");
                itineraryHeader.removeClass("active-page");
                budgetPage.show();
                window.scrollTo(0, 0);
            }
        });
    });
}

function fadeToElement(element, lat, lng) {
    var pageList = ["#itinerary-header", "#map-page", "#lodging-page",
        "#event-places-page", "#budget-page", "#itinerary-overview"];
    var currentPage;
    for (var i = 0; i < pageList.length; i++) {
        if ($(pageList[i]).hasClass("active-page")) {
            currentPage = pageList[i];
            break;
        }
    }
    if (currentPage !== element) {
        $(currentPage).fadeOut("slow", function () {
            if ($(this).hasClass("active-page"))
                $(this).removeClass("active-page");
            if (!$(element).hasClass("active-page"))
                $(element).addClass("active-page");
            $(element).show();
            window.scrollTo(0, 0);
            if (element === "#map-page") {
                initialize(lat, lng);
            }
        });
    }
}

function getCurrentPageSection(element) {
    var elementID = "#" + element;
    if (element === "null") {
        var defaultPage = $("#itinerary-header");
        defaultPage.addClass("active-page");
        defaultPage.show();
    } else {
        hideAllPages();
        $(elementID).addClass("active-page");
        $(elementID).show();
    }
}

function hideAllPages() {
    $("#itinerary-header, #map-page, #lodging-page, #event-places-page," +
        "#budget-page, #itinerary-overview").each(function() {
        $(this).hide();
    });
}


