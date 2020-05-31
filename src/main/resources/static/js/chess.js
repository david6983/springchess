console.log("started javascript loading");

// board component
let board = $("#board");


/**
 * Manage drag and drop
 */
$(function (e) {
    // replace text for the player's turn.
    const turnText = $('#player-turn').html();

    $('.figure').draggable({
        containment: board,
        zIndex: 1,
        snap: ".cell",
        revert: 'invalid'
    });

    $('.cell').droppable({
        accept: function (el) {
            return el.hasClass(turnText.toLowerCase()) && ($('#mate').length <= 0);
        },
        tolerance: 'intersect',
        over: function () {
            $(this).css("background-color", "green");
            console.log($(this).attr('class'));
        },
        out: function () {
            $(this).css("background-color", "")
        },
        drop: function (event, ui) {
            let dropped = ui.draggable;
            let droppedOn = $(this);

            let gameId = $(location).attr('href').split('/')[5]; // http://localhost:8080/game/play/{id}

            let deleteUrl;
            const oldPawnId = $(droppedOn).children();
            const oldClass = $(this).attr('class').indexOf('disabled');

            $(droppedOn).css("background-color", "");
            $(this).addClass('disabled');
            $(dropped).parent().removeClass('disabled');
            $(dropped).parent().droppable("enable");
            $(dropped).detach().css({top: 0, left: 0}).appendTo(droppedOn);
            let newPawnId = $(dropped).attr('data-id');

            let x = $(droppedOn).attr('data-x');
            let y = $(droppedOn).attr('data-y');
            let moveUrl = '/game/move/' + gameId + '/' + newPawnId + '/' + x + '/' + y;


            // create link to update a pawn's position
            // if the cell is not empty
            if (oldClass > 0) {
                // Create link to delete
                deleteUrl = '/game/move/' + gameId + '/' + newPawnId + '/' + oldPawnId.attr('data-id');
                // Delete the piece of the screen
                oldPawnId.remove();
                // Delete the piece in the database
                window.location.href = deleteUrl;
            } else {
                // update the pawn's position
                window.location.href = moveUrl;
            }
        },
    });
});

if ($(location).attr('href').split('/')[4] === "promote") {
    $('#promotionModal').modal('toggle');
}

function leaveGame() {
    let gameId = $(location).attr('href').split('/')[5]; // http://localhost:8080/game/play/{id}
    window.location.href = '/game/resigning/' + gameId;
}

console.log("js fully loaded");