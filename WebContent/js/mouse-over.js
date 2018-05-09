$(".movie__info").hover(function(){
	var a_id = $(this).attr("data-movie_id");

	var htmlstr = document.getElementById("pop-up" + a_id).innerHTML;
	htmlstr = htmlstr.replace(/^\s+/, '');
	if (htmlstr == '')
	{
		ajaxHover(a_id, function(id, result){
			var custom_id = "pop-up" + id;
			//console.log(result);
			document.getElementById(custom_id).innerHTML = result;
			
			});
	}
	$(this).find(".fabflix__pop-up").css("display", "block");

}, function(){
	$(this).find(".fabflix__pop-up").css("display", "none");
});

function ajaxHover(id, callback){
	var ajaxRequest;  // The variable that makes Ajax possible!

	try{
		// Opera 8.0+, Firefox, Safari
		ajaxRequest = new XMLHttpRequest();
	} catch (e){
		// Internet Explorer Browsers
		try{
			ajaxRequest = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try{
				ajaxRequest = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e){
				// Something went wrong
				alert("Your browser broke!");
				return false;
			}
		}
	}
	// Create a function that will receive data sent from the server
	ajaxRequest.onreadystatechange = function(){
		if(ajaxRequest.readyState == 4){
			//document.myForm.time.value = ajaxRequest.responseText;
			callback(id, ajaxRequest.responseText);
		}
	}
	ajaxRequest.open("GET", "popup?id=" + id, true);
	ajaxRequest.send(null);
}
