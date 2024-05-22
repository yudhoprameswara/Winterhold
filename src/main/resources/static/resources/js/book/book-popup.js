(function(){
        addCloseButtonListener();
        addInsertButtonListener();
        addSubmitFormListener();
        addEditCategoryButtonListener();
        addSummaryButtonListener();
        deleteButtonListener();
    }())

    function deleteButtonListener(){
        $('.delete-category').click(function(event){
            event.preventDefault();
            let category = $(this).attr('data-id');
            $.ajax({
                url:`/api/book/delete/category/${category}`,
                method:'DELETE',
                success: function(response){
                    
                }
                ,
                error: function(dependencies){
                    console.log(dependencies)
                    $('.dependencies').text(dependencies.responseText)
                    $('.modal-layer').addClass('modal-layer--opened');
                    $('.delete-dialog').addClass('popup-dialog--opened');
                }
            });
        })
    }

    function addSummaryButtonListener(){
        $('.summary-button').click(function(event){
            event.preventDefault();
            let code = $(this).attr('data-id');
            $.ajax({
                url:`/api/book/summary/${code}`,
                success: function(response){
                    populateSummary(response);
                    $('.modal-layer').addClass('modal-layer--opened');
                    $('.summary-dialog').addClass('popup-dialog--opened');
                }
            });
        });
    }

    function populateSummary({summary}){
        $('.summary-dialog .content-summary').text(summary);
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

    function addEditCategoryButtonListener(){

        $('.category-button .edit-button').click(function(event){
            event.preventDefault();
            let category = $(this).attr('data-id');
            $.ajax({
                url:`/api/book/category/${category}`,
                success: function(response){
                     populateInputForm(response);
                     $('.modal-layer').addClass('modal-layer--opened');
                     $('.form-dialog').addClass('popup-dialog--opened');
                }
            });
        });
    }

    function populateInputForm({categoryName,floor,isle,bay}){
        $('.form-dialog .categoryName').val(categoryName);
        $('.form-dialog .floor').val(floor);
        $('.form-dialog .isle').val(isle);
        $('.form-dialog .bay').val(bay);
    }

    function addSubmitFormListener(){
        $('.form-dialog button').click(function(event){
            event.preventDefault();
            let dto = collectInputForm();
            let requestMethod = (dto.categoryName == null) ? 'PUT' : 'POST';
            $.ajax({
                method: requestMethod,
                url:'/api/book',
                data: JSON.stringify(dto),
                contentType : 'application/json',
                success : function(response){
                    location.reload();
                }
                ,
                error: function({status, responseJSON}){
                    if(status === 422){
                    writeValidationMessage(responseJSON);
                    }
                    else{
                        console.log(responseJSON);
                    }
                }
            });
        });
    }

    function writeValidationMessage(errorMessages){
        for (let error of errorMessages){
            let {field,message} = error;
            $(`.form-dialog [data-for=${field}]`).text(message);
        }
    }

    function collectInputForm(){
       let category =$('.form-dialog .categoryName').val();
       let dto = {
            categoryName: (category === "")? null : category,
            floor : $('.form-dialog .floor').val(),
            isle : $('.form-dialog .isle').val(),
            bay: $('.form-dialog .bay').val()
            };
       return dto;
    }

    function escapeClose(){
        $('document').keydown(function(event){
        if (event.key === "Escape") {
           $('.modal-layer').removeClass('modal-layer--opened');
           $('.popup-dialog').removeClass('popup-dialog--opened');
           $('.form-dialog input').val("");
           $('.form-dialog textarea').val("");
           $('.form-dialog .validation-message').text("");
           }
        });
    }

