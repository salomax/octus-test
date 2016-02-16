$('#login-form').bind('submit', function(event ) {
	event.preventDefault();
	
    $.ajax({
        url: 'api/login',
    	type: 'POST',
    	contentType : 'application/x-www-form-urlencoded',
    	data: {
    		username : $('#username').val(),
    		password : $('#password').val()
    	}
    }).then(function(response) {
    	$.cookie("authToken", response.token);
    	// similar behavior as an HTTP redirect
		window.location.replace("./");
    }, function(xhr, ajaxOptions, thrownError) {
    	alert('Login fail!')
    });	
	
});	
