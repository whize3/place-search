const main = {
    init : function () {
        let _this = this;
        $('#loginButton').on('click', function () {
            _this.login();
        });
    },
    login : function () {
        let data = {
            id: $('#id').val(),
            password: $('#password').val()
        };

        $.ajax({
            type: 'POST',
            url: '/v1/signin',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(result) {
            localStorage.token = result.data;
            location.href = "/view/search"
        }).fail(function (error) {
            if(typeof(error.responseJSON) == "undefined") {
                alert(error);
            } else {
                alert(error.responseJSON.msg);
            }

        });
    }

};

main.init();