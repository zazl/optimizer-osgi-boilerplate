define(['routers/router', 'bootstrap', 'tablescroll'], function(router){
	var init = function(){
		this.router = new router();
	};

	return { init: init};
});
