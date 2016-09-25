function onEventClick() {
    var selected = $(this);
    var target = $(".next-target").removeClass("next-target");
    var current = target.data("event");
    if (current) {
      $("#" + current).show()
    }
    target.data("event", selected.attr('id')).find("p").text(selected.find("p").text());
    var siblings = target.parent().children();
    siblings.eq((siblings.index(target) + 1) % siblings.length).addClass("next-target");
    selected.hide()
}

function onSubmitClick() {
    $("#order").val($("div.preference").map(function() { return $(this).data("event") }).get().join())
    $("#selection-input").submit()
}