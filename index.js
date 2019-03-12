var slider = new Vue({
	el: "#slider_app",
	data: {
		// 这里放的都是vm对象的数据，通过vm.xx得到
		imgsArray: ['slide1.png','slide1.png','slide1.png','slide1.png','slide1.png','slide1.png'],
		imgsLens: 0
	},
	mounted: function() {
		this.imgsLens = this.imgsArray.length // this指向自己也就是vm，这里把图片数组的长度赋给了vm.imgsLens，自然它就从0变成了6
	}
})

var login_modal = new Vue({
	el:"#login",
	data:{
		showDialog:false,
		isLoggedIn:false,
		username:"",
		password:""
	},
	methods:{
		showModal:function(){
			this.showDialog = true;
		},
		closeModal:function(){
			this.showDialog = false;
			this.username = this.$refs.username_input.value;
			this.password = this.$refs.password_input.value;
			this.isLoggedIn = true;
			console.log("user: "+this.username+"\npassword: "+this.password);
		}
	}
})
//  接着就是当页面onload后执行轮播的函数了
window.onload = function() {
	var imgsWrp = document.querySelector(".imgsWrp"),
		imgsLi = imgsWrp.querySelectorAll("li"),
		markSpan = document.querySelector(".carousel span"),
		i = 0;

	// 这里根据图片数量分别算出li的宽度和ul的宽度
	imgsWrp.style.width = slider.imgsLens * 100 + "%";
	var widthLi = 100 / slider.imgsLens + "%";
	for (var ils = 0; ils < slider.imgsLens; ils++) {
		imgsLi[ils].style.width = widthLi;
	}
	// 定时函数，每3秒变更i，同时变换ul要transform的值
	setInterval(function() {
		i < slider.imgsLens - 1 ? i++ : i = 0;
		imgsWrp.style.transform = "translateX(-" + 100 / slider.imgsLens * i + "%)";
		markSpan.textContent = i + 1
	}, 3000)
}
