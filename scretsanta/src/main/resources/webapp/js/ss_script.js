$(function() {
	setTimeout(function(){
		$("#loading").fadeOut(1000);
	}, 2000);
	
	var scratch = new Scratch({
		canvasId: 'js-scratch-canvas',
		canvasWidth: 250,
        canvasHeight: 250,
		imageBackground: './media/Santa-hat-icon.png',
		pictureOver: './media/foreground.jpg',
		cursor: {
			png: './media/Magic-wand-icon.png',
			x: '17',
			y: '20'
		},
		radius: 20,
		nPoints: 100,
		percent: 50,
		callback: function () {
			$.ajax("/service").done(function(oData){
				console.log(oData);
				$(".scratch_picture-under").effect( "shake",{times:4}, 1000 );
				$('.scratch_viewport').append("<div class='centered textFriend'>"+oData.MyFriend+"</div>");
			}).fail(function(oError){
				$(".scratch_picture-under").effect( "shake",{times:4}, 1000 );
				$('.scratch_viewport').append("<div class='centered textFriend'>"+JSON.parse(oError.responseText).errorcode+"</div>");
			})
		},
		pointSize: { x: 3, y: 3}
	});
});