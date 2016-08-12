(function(){
	var QRating = function(arg){

		if(!(this instanceof QRating)){
			return new QRating(arg);
		}

		this.divId = arg.replace('#',"");
		var self = this;
		var mapStars = new Map();
		var rating = 0


		this.addStar = function(id, star){
			mapStars.set(id, star);
		}

		this.updateStars = function(starId){
			for(var i = 0; i < starId; i++){
				var star = this.getStarById(i);
				star.setChecked(true);
			}
			for(var i = starId+1; i < mapStars.size; i++){
				var star = this.getStarById(i);
				star.setChecked(false);
			}
		}

		this.getStarById = function(id){
			return mapStars.get(id);
		}

		this.clearStarsMap = function(){
			mapStars.clear();
		}

		this.getStarsMap = function(){
			return mapStars;
		}

		this.registStarClickEvent = function(){
			mapStars.forEach(function(star){
				star.getElement().onclick = function(){
					star.onStarClick();
					self.updateStars(star.getId());
				};
			});
		}

		this.getRating = function(){
			rating = 0;
			mapStars.forEach(function(star){
				rating += star.getRating();
			});
			return rating;
		}

	}

	QRating.fn = QRating.prototype = {

		init : function(objInit){

			var $div = document.getElementById(this.divId);
			if(!$div){
				console.error("QRating: The element with id '"+this.divId+"' doesn't exists!");
				return;
			}

			var color = objInit.color ? objInit.color : "black";
			var size = objInit.size ? objInit.size : "20px";
			var quantityStars = objInit.stars ? objInit.stars : 1;
			var filled = objInit.filled ? objInit.filled : false;

			for ( var i = 0; i < quantityStars; i++ ){

				var $span = document.createElement("span");
				$span.setAttribute("id", i);
				$span.setAttribute("class", "glyphicon glyphicon-star-empty");

				$span.style.color = color;
				$span.style.fontSize = size;
				$span.style.cursor = "pointer";

				$div.appendChild($span);

				var tempStar = new Star();
				tempStar.init($span, i);
				tempStar.setChecked(filled);

				this.addStar(i, tempStar);

			}

			this.registStarClickEvent();	
			return this;
		},
		getRating : function(){
			return rating;
		}
	}

	var Star = function(){

		var $element;
		var isChecked = false;
		var id = 0;
		var className = "glyphicon-star-empty";
		var self = this;

		this.init = function(element, id){
			this.setElement(element);
			this.setId(id);
		}

		this.setElement = function(element){
			this.$element = element;
		}

		this.getElement = function(){
			return this.$element;
		}

		this.onStarClick = function(){
			self.toggleChecked();
		}

		this.toggleChecked = function(){
			self.setChecked(!self.getChecked());
			
		}

		this.getRating = function(){
			return (isChecked ? 1 : 0);
		}

		this.setChecked = function(checked){
			isChecked = checked;

			this.className = checked ? "glyphicon-star" : "glyphicon-star-empty";
			this.$element.setAttribute("class", "glyphicon " + this.className);
		}

		this.getChecked = function(){
			return isChecked;
		}

		this.getClassName = function(){
			return this.className;
		}

		this.getId = function(){
			return this.id;
		}

		this.setId = function(id){
			this.id = id;
		}
	}

	window.$q = QRating;
})();