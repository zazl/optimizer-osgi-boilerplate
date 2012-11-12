define(['backbone'], function(Backbone) {
	var Artist = Backbone.Model.extend({
	    initialize: function(attributes, options) {
	    	this.albums = options.albums;
	    }
	});
	
	return Artist;
});
