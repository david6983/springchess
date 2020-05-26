console.log("started javascript loading");

// board component
let board = $("#board");

/**
 * Manage drag and drop
 */
$(function () {
    $('.figure').draggable({
        containment: board,
        zIndex: 1,
        snap: ".cell",
        revert: function () {
            if ($(this).hasClass('drag-revert')) {
                $(this).removeClass('drag-revert');
                return true;
            }
        }
    });

    $('.cell').droppable({
        accept: ".figure",
        tolerance: 'intersect',
        over: function () {
            $(this).css("background-color", "green");
        },
        out: function () {
            $(this).css("background-color", "")
        },
        drop: function (event, ui) {
            let dropped = ui.draggable;
            let droppedOn = $(this);

            let gameId = $(location).attr('href').split('/')[5]; // http://localhost:8080/game/{id}

            let deleteUrl;

            // if the cell is not empty
            if ($(this).attr('class').indexOf('disabled') > 0) {
                // Create link to delete
                deleteUrl = '/game/delete/' + gameId + '/' + $(this).children().attr('data-id');
                // Delete the piece of the screen
                $(this).children().remove();
                // Delete the piece in the database
                $.ajax({
                    url: deleteUrl, success: function (result) {
                        console.log("Piece killed");
                    }
                });
            }

            $(droppedOn).css("background-color", "");

            $(this).addClass('disabled');
            $(dropped).parent().removeClass('disabled');
            $(dropped).parent().droppable("enable");
            $(dropped).detach().css({top: 0, left: 0}).appendTo(droppedOn);

            // create link to update a pawn's position
            let pawnId = $(droppedOn).children().attr('data-id');
            let x = $(droppedOn).attr('data-x');
            let y = $(droppedOn).attr('data-y');
            let moveUrl = '/game/move/' + gameId + '/' + pawnId + '/' + x + '/' + y;

            // replace text for the player's turn.
            let turnText = $('#player-turn').html();

            // update the pawn's position
            $.ajax({
                url: moveUrl, success: function (result) {
                    console.log("Moves save in bdd");
                    if (turnText === 'Black') {
                        $('#player-turn').text(turnText.replace(turnText, "White"))
                    } else if (turnText === 'White') {
                        $('#player-turn').text(turnText.replace(turnText, "Black"))
                    }
                }
            });

        },
    });
});

console.log("js fully loaded");