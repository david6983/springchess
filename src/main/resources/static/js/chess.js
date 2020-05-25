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

// board.find('.figure').draggable({ containment: board, revert: 'invalid' });
//
// board.find('.ui-droppable').droppable({
//     drop: function(ev, ui) {
//         var dropped = ui.draggable;
//         var droppedOn = $(this);
//         $(droppedOn).droppable("disable");
//         $(dropped).parent().droppable("enable");
//         // $(dropped).detach().css({top: 0, left: 0}).appendTo(droppedOn);
//     }
// });

/*
SOLUTION POUR DRAG & DROP
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

            let ID_game = $(location).attr('href'); // Current URL
            ID_game = ID_game.split('/'); // It's case n°5 => http://localhost:8080/game/ID
            // or  URL = URL.substring(32);
            let URL_DELETE;
            if ($(this).attr('class').indexOf('disabled') > 0) { // If cell is not empty
                // Create link to delete
                URL_DELETE = '/game/delete/' + ID_game[5] + '/' + $(this).children().attr('data-id');
                // Delete the piece of the screen
                $(this).children().remove();
                // Delete the piece in the database
                $.ajax({
                    url: URL_DELETE, success: function (result) {
                        console.log("Piece killed");
                    }
                });
            }


            $(droppedOn).css("background-color", "");
            // $(droppedOn).droppable("disable");
            $(this).addClass('disabled');
            $(dropped).parent().removeClass('disabled');
            $(dropped).parent().droppable("enable");
            $(dropped).detach().css({top: 0, left: 0}).appendTo(droppedOn);

            /* CREATE LINK TO UPDATE POSITION OF A PIECE */
            let pawnId = $(droppedOn).children().attr('data-id');
            let x = $(droppedOn).attr('data-x');
            let y = $(droppedOn).attr('data-y');
            let URL_MOVE = '/game/move/' + ID_game[5] + '/' + pawnId + '/' + x + '/' + y;

            /* REPLACE TEXT FOR THE TURN OF THE PLAYER */
            let TEXT_TURN = $('#player-turn').html();
            /* UPDATE POSITION OF A PIECE */
            $.ajax({
                url: URL_MOVE, success: function (result) {
                    console.log("Moves save in bdd");
                    if (TEXT_TURN === 'Black') {
                        $('#player-turn').text(TEXT_TURN.replace(TEXT_TURN, "White"))
                    } else if (TEXT_TURN === 'White') {
                        $('#player-turn').text(TEXT_TURN.replace(TEXT_TURN, "Black"))
                    }
                }
            });
            /****************/
        },
    });
});

// board.find('.figure').not('td:empty').droppable("disable");

console.log("js fully loaded")