$("#auto-search").keyup(function(){
	if ($(this).val() != "")
	{
		$("#drop-down").css('display', 'block');
		// $("#drop-down").empty();
		var q = $(this).val();
		dropDown(q, function(result){
			//console.log(result);
			document.getElementById("drop-down").innerHTML = result;
			// document.getElementById("drop-down").innerHTML = "Hello";
		})
	}
	else if ($(this).val() == "")
	{
		$("#drop-down").css('display', 'none');
		$("#drop-down").empty();
	}
});

$("#auto-search").focus(function(){
	if ($(this).val() != "")
	{
		$("#drop-down").css('display', 'block');
	}
});

$(document).mouseup(function(e){
	var container = $("#drop-down");
	var search = $("#auto-search");
	if (search.is(e.target))
	{
		if ($(this).val() != "")
		{
			$("#drop-down").css('display', 'block');
		}
	}
	else if (!container.is(e.target) && container.has(e.target).length == 0)
	{
		$("#drop-down").css('display', 'none');
	}
	else if (search.is(e.target))
	{
		$("#drop-down").css('display', 'block');
	}


});


function dropDown(query, callback){
	var ajaxRequest;  // The variable that makes Ajax possible!

	try{
		ajaxRequest = new XMLHttpRequest();
	} catch (e){
		try{
			ajaxRequest = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try{
				ajaxRequest = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e){
				alert("Your browser broke!");
				return false;
			}
		}
	}
	// Create a function that will receive data sent from the server
	ajaxRequest.onreadystatechange = function(){
		if(ajaxRequest.readyState == 4){
			// console.log(ajaxRequest.responseText);
			callback(ajaxRequest.responseText);
		}
	}
	ajaxRequest.open("GET", "auto?q=" + query, true);
	ajaxRequest.send(null);

}


