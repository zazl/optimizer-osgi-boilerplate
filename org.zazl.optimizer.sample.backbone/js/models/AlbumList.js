define(['backbone', './Album'], function(Backbone, Album){
	var AlbumList = Backbone.Collection.extend({
		model: Album,
		url: "/music/albums"
	});
	return AlbumList;
});
