console.log("hello chess");

let board = $("#board");

board.find('.figure')
    .draggable({
        revert: 'invalid',
        containment: board
    })
    .on('dragstart', (ev, ui) => {
        console.log("get the figure code")
    })
    .on('dragstop', (ev, ui) => {
        console.log("get the target coords and send json serialized data to server with ajax for verificaton")
        console.log("when the response is ready, do the corresponding ui task")
    })
    .on('drop', (ev, ui) => {
        console.log("drop")
    });