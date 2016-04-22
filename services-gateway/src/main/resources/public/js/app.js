$(document).ready(function() {

    $("form input").change(function() {
        launchSearch();
    });

    function launchSearch() {
        var baseUrl = "/search/city";
        var city = $("#city").val();
        var fullUrl = baseUrl + "/" + city;

        $.ajax({
            url : fullUrl,
            type: "GET",
            dataType: "json",
            crossDomain: true,
            success: function(data)
            {
                $("#search-results").jsonViewer(data);
            },
            error: function (jqXHR) {
                console.log(jqXHR.responseText);
                $("#search-results").html(jqXHR.responseText);
            }
        });
    };

    function startSuggest() {
        new Suggest.Local(
            "city",    // input element id.
            "suggest", // suggestion area id.
            [],      // suggest candidates list
            {dispMax: 10, interval: 1000}); // options
    }

    window.addEventListener ?
        window.addEventListener('load', startSuggest, false) :
        window.attachEvent('onload', startSuggest);
});