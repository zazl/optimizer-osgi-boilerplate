define([
		'jquery', 
		'backbone',
		'underscore', 
		'text!templates/ArtistList.html'], 
function($, Backbone, _, template){
	var View = Backbone.View.extend({
		el: '#container',
		initialize: function(options) {
			this.template = _.template( template, { artists: options.artists.toJSON() } );
		},
		render: function(){
			$(this.el).html( this.template );
			$('#musicartists').tableScroll({width: 600, height: 500});
		}
	});
	
	return View;
});
