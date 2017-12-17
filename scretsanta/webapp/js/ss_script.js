$(function() {
	$("#FindMyFriendBtn").on('click',function(){
		$.ajax("/service").done(function(oData){
			console.log(oData);
			$("#main_container").hide();
			$("#circle").fadeIn();
	})
	});

	setTimeout(function(){
		$("#loading").fadeOut();
		$("#main_container").show();
	}, 2000);
});