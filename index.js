var slider = new Vue({
	el: "#slider_app",
	data: {
		imgsArray: ['slide1.png','slide1.png','slide1.png','slide1.png','slide1.png','slide1.png'],
		imgsLens: 0
	},
	mounted: function() {
		this.imgsLens = this.imgsArray.length 
	}
})

var header_contents = new Vue({
	el:"#header_contents"
})

var nav_menu = new Vue({
	el:"#nav_menu"
})

window.onload = function() {
	var imgsWrp = document.querySelector(".imgsWrp"),
		imgsLi = imgsWrp.querySelectorAll("li"),
		markSpan = document.querySelector(".carousel span"),
		i = 0;

	imgsWrp.style.width = slider.imgsLens * 100 + "%";
	var widthLi = 100 / slider.imgsLens + "%";
	for (var ils = 0; ils < slider.imgsLens; ils++) {
		imgsLi[ils].style.width = widthLi;
	}
	
	setInterval(function() {
		i < slider.imgsLens - 1 ? i++ : i = 0;
		imgsWrp.style.transform = "translateX(-" + 100 / slider.imgsLens * i + "%)";
		markSpan.textContent = i + 1
	}, 3000)
}
