/**
 * 
 * To Activate and hide Modal
 * 
 */

var activate = function(modalId) { $('#' + modalId).modal(); }

var hide = function(modalId) {
    var $modal = $('#' + modalId);

    if ($modal.length > 0) {
        // Hide will trigger removal.
        $modal.modal('hide');
    }
    else {
        // The modal's already gone, but the backdrop may still be there.
        $('body').removeClass('modal-open');
        $('.modal-backdrop').remove();
    }
}
