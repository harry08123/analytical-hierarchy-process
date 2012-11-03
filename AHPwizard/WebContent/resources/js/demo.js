$("#filter").slider({
        orientation: "horizontal",
        range: "min",
        min: 0,
        max: 1000,
        value: 500,
        slide: function (event, ui) {
            $("#echo").html(ui.value);
        }
    });
    $("#echo").html($("#filter").slider("value"));
