showOverlaySpinner = (id) => {
    $('#' + id).show();
};

hideOverlaySpinner = (id) => {
    $('#' + id).hide();
};

$(document).ready(() => {
    $('.cw-i-price:not([readonly])').on({
        'focusin': (e) => {
            $(`#${e.target.id}`).val(numberWithoutCommas(e.target.value));
        },
        'focusout': (e) => {
            let price = e.target.value;
            price = price.replace(/[^0-9]/g, '');
            $(`#${e.target.id}`).val(numberWithCommas(price));
        }
    });

    $('.modal').on('hidden.bs.modal', (e) => {
        if ($(e.target).find('form')[0]) $(e.target).find('form')[0].reset();
        $('.modal-hide-reset').hide();
    });
});