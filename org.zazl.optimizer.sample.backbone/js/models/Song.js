define(['backbone'], function(Backbone){
	var Song = Backbone.Model.extend({
		defaults: {
			title: null,
			track: 0,
			artist: null,
			album: null,
			duration: 0
		}
	});
	
	return Song;
});
