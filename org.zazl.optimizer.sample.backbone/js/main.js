var zazlConfig = {
	baseUrl: 'js/',
	directInject: true,
	paths: {
		jquery: '../lib/jquery/jquery-1.8.2',
		//jquery: '../lib/jquery/jquery-1.8.2.min',
		underscore: '../lib/underscore/underscore',
		backbone: '../lib/backbone/backbone',
		//backbone: '../lib/backbone/backbone-min',
		text: '../lib/requirejs/text',
		templates: '../templates'
	}
};

require(['app'],
function(app){
	app.init();
});
