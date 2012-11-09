var zazlConfig = {
	baseUrl: 'js/',
	directInject: true,
	paths: {
		jquery: '../lib/jquery/jquery-1.8.2',
		underscore: '../lib/underscore/underscore', // https://github.com/amdjs
		backbone: '../lib/backbone/backbone', // https://github.com/amdjs
		text: '../lib/requirejs/text',
		templates: '../templates'
	}
};

require([
'underscore',
'backbone',
'app'
],
function(_, Backbone, app){
	app.init();
});
