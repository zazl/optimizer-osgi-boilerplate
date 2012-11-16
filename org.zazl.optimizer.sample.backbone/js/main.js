var zazlConfig = {
	baseUrl: 'js/',
	directInject: true,
	paths: {
		jquery: '../lib/jquery/jquery-1.8.2',
		//jquery: '../lib/jquery/jquery-1.8.2.min',
		underscore: '../lib/underscore/underscore',
		backbone: '../lib/backbone/backbone',
		text: '../lib/requirejs/text',
		templates: '../templates'
	},
	shim: {
		'backbone' : {
			deps: ['underscore', 'jquery'],
			exports: 'Backbone'
		},
		'underscore' : {
			exports: '_'
		}
	}
};

require(['app'],
function(app){
	app.init();
});
