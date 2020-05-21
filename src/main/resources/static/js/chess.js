console.log("hello chess");

let board = $("#board");

board.find('.figure')
    .draggable({
        revert: 'invalid',
        containment: board
    })
    .on('dragstart', (ev, ui) => {

    })
    .on("dragover", function(event) {
        event.preventDefault();
        event.stopPropagation();
        $(this).addClass('dragging');
    })
    .on("dragleave", function(event) {
        event.preventDefault();
        event.stopPropagation();
        $(this).removeClass('dragging');
    })
    .on('dragstop', (ev, ui) => {

    })
    .on('drop', (ev, ui) => {
        ev.preventDefault();
        ev.stopPropagation()
        console.log("dropped");
        console.log(ev.target);
    });
//https://codepen.io/Sulzh/pen/gmmjpQ