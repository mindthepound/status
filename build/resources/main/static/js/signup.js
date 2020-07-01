$(document).ready(() => {

    $('#signupForm').submit((event) => {

        event.preventDefault();

        let $form = $('#signupForm')
            , url = $form.attr('action')
            , formData = $form.serialize()
        ;

        $.ajax({
            url: url,
            type: 'POST',
            data: formData,
            success: (result) => {

                window.location.href = '/signin';
            },
            error: (request, status, error) => {

                alert(`request : ${JSON.stringify(request)}, status : ${JSON.stringify(status)}, error : ${error}`);
            }
        });
    });
});