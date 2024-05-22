(function(){

    addMemberNumberListener();
    addCloseButtonListener();

}())

function addMemberNumberListener(){
    $('.membershipNumber').click(function(event){
        event.preventDefault();
        let memberNumber = $(this).text();
        $.ajax({
            url:`/api/customer/bio/${memberNumber}`,
            success: function(response){
                populateBio(response);
                $('.modal-layer').addClass('modal-layer--opened');
                $('.info-dialog').addClass('popup-dialog--opened');
            }});
    });
}

function populateBio({membershipNumber,name,birthDate,gender,phone,address}){
    $('.bio-membershipNumber').text(membershipNumber);
    $('.bio-name').text(name);
    $('.bio-birthDate').text(birthDate);
    $('.bio-gender').text(gender);
    $('.bio-phone').text(phone);
    $('.bio-address').text(address);
}

function addCloseButtonListener(){
    $('.close-button').click(function(event){
       $('.modal-layer').removeClass('modal-layer--opened');
       $('.popup-dialog').removeClass('popup-dialog--opened');
    });
}
