var zazlConfig = {
	baseUrl: 'js/',
	directInject: true,
	paths: {
		jquery: '../lib/jquery/jquery-1.8.2',
		underscore: '../lib/underscore/underscore',
		backbone: '../lib/backbone/backbone',
		bootstrap: "../lib/bootstrap/bootstrap",
		tablescroll: "../lib/tablescroll/jquery.tablescroll",
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
		},
		'bootstrap' : {
			deps: ['jquery']
		},
		'tablescroll' : {
			deps: ['jquery']
		}
	}
};

require(['app'],
function(app){
	app.init();
});
