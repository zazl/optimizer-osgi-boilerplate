define(['routers/router'], function(router){
	var init = function(){
		this.router = new router();
	};

	return { init: init};
});