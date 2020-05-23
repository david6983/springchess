console.log("hello chess");

let board = $("#board");
/*
board.find('.figure')
    .draggable({
        revert: 'invalid',
        containment: board
    })
    .on('dragstart', (ev, ui) => {
        console.log("you are dragging ");
    })
    .on("dragover", function(event) {
        $(this).addClass('dragging');
        let id = $(this).dataset.id;
        console.log(id)
    })
    .on("dragleave", function(event) {
        $(this).removeClass('dragging');
    })
    .on('dragstop', (ev, ui) => {

    })
    .on('drop', (ev, ui) => {
        console.log("dropped");
        console.log(ev.target);
    });*/
//https://codepen.io/Sulzh/pen/gmmjpQ

board.find('.figure').draggable({ containment: board, revert: 'invalid' });

board.find('.figure').droppable({
    drop: function(ev, ui) {
        var dropped = ui.draggable;
        var droppedOn = $(this);
        $(droppedOn).droppable("disable");
        $(dropped).parent().droppable("enable");
        $(dropped).detach().css({top: 0, left: 0}).appendTo(droppedOn);
    }
});

board.find('.figure').not('td:empty').droppable("disable");

console.log("js fully loaded")