(function(){
    addDetailButtonListener();
    addCloseButtonListener();
    addInsertButtonListener();
    addSubmitFormListener();
    addEditLoanButtonListener();
}())

function addDetailButtonListener(){
    $('.detail-button').click(function(event){
        event.preventDefault();
        let bookCode = $(this).attr('data-bookCode');
        let customerNumber = $(this).attr('data-customerNumber');
        $.ajax({
            url:`/api/book/${bookCode}`,
            success: function(response){
                populateBook(response);
                $('.modal-layer').addClass('modal-layer--opened');
                $('.detail-dialog').addClass('popup-dialog--opened');
            }
        });
        $.ajax({
            url:`/api/customer/${customerNumber}`,
            success: function(response){
                populateCustomer(response);
            }
        });

    });
}

function populateBook({title,categoryName,author,floor,isle,bay}){
    $('.detail-dialog .detail-title').text(title);
    $('.detail-dialog .detail-category').text(categoryName);
    $('.detail-dialog .detail-author').text(author);
    $('.detail-dialog .detail-floor').text(floor);
    $('.detail-dialog .detail-isle').text(isle);
    $('.detail-dialog .detail-bay').text(bay);

}
function populateCustomer({membershipNumber,firstName,lastName,phone,membershipExpireDate}){
    $('.detail-dialog .detail-membershipNumber').text(membershipNumber);
    $('.detail-dialog .detail-fullName').text(`${firstName} ${lastName}`);
    $('.detail-dialog .detail-phone').text(phone);
    $('.detail-dialog .detail-expireDate').text(membershipExpireDate);
}

function addCloseButtonListener(){
    $('.close-button').click(function(event){
       $('.modal-layer').removeClass('modal-layer--opened');
       $('.popup-dialog').removeClass('popup-dialog--opened');
       $('.form-dialog input').val("");
       $('.form-dialog textarea').val("");
       $('.form-dialog .validation-message').text("");
    });
}

function addInsertButtonListener(){
    $('.create-container .create-button').click(function(event){
       event.preventDefault();
       $('.modal-layer').addClass('modal-layer--opened');
       $('.form-dialog').addClass('popup-dialog--opened');
    });
}

function addEditLoanButtonListener(){

    $('.edit-button').click(function(event){
        event.preventDefault();
        let id = $(this).attr('data-id');
        $.ajax({
            url:`/api/loan/${id}`,
            success: function(response){
                 populateInputForm(response);
                 $('.modal-layer').addClass('modal-layer--opened');
                 $('.form-dialog').addClass('popup-dialog--opened');
            }
        });
    });
}

function populateInputForm({customerNumber,bookCode,loanDate,note}){
    $('.form-dialog .customerNumber').val(customerNumber);
    $('.form-dialog .bookCode').val(bookCode);
    $('.form-dialog .loanDate').val(loanDate);
    $('.form-dialog .note').val(note);
}


function writeValidationMessage(errorMessages){
    for (let error of errorMessages){
        let {field,message} = error;
        $(`.form-dialog [data-for=${field}]`).text(message);
    }
}

function collectInputForm(){
   let loanId =$('.edit-button').attr('data-id');
   let dto = {
        id: (loanId === null)? null : loanId,
        customerNumber : $('.form-dialog .customerNumber').val(),
        bookCode : $('.form-dialog .bookCode').val(),
        loanDate: $('.form-dialog .loanDate').val(),
        note:$('.form-dialog .note').val()
        };
   return dto;
}


function addSubmitFormListener(){
    $('.form-dialog button').click(function(event){
        event.preventDefault();
        let dto = collectInputForm();
        $.ajax({
            method: 'PUT',
            url:'/api/loan',
            data: JSON.stringify(dto),
            contentType : 'application/json',
            success : function(response){
                location.reload();
            }
        });
    });
}
