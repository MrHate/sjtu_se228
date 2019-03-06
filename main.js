//Vue.component('recommended', {
//  props: ['entry'],
//  template: '<li class="entry_item"><img :src={entry.text} alt="book" /></li>'
//})

var explore_app = new Vue({
  el: '#explore_app',
  data: {
    bookList: [
      { id: 0, path: 'images/b1.jpeg',name:'ZEN' },
      { id: 1, path: 'images/b2.jpeg',name:'Animal God' },
      { id: 2, path: 'images/b3.jpeg',name:'Zen & Motor' },
      { id: 3, path: 'images/b3.jpeg',name:'Noooo' },
      { id: 4, path: 'images/b3.jpeg',name:'Pie Cook' },
      { id: 5, path: 'images/b3.jpeg',name:'I Wish' },
      { id: 6, path: 'images/b3.jpeg',name:'Wisky' },
      { id: 7, path: 'images/b3.jpeg',name:'Doki' }
    ]
  }
})
var vm = new Vue({
	el: "#app",// 瞧，这里注明vue起作用的地方是div#app内
	data: {
		// 这里放的都是vm对象的数据，通过vm.xx得到
		imgsArray: ['miao01.jpg', 'miao02.jpg', 'miao03.jpg', 'miao04.jpg', 'miao05.jpg', 'miao06.jpg'],
		imgsLens: 0
	},
	mounted: function() {
		// 这里是vue在名为mounted的生命周期时，执行对应的函数
		this.imgsLens = this.imgsArray.length // this指向自己也就是vm，这里把图片数组的长度赋给了vm.imgsLens，自然它就从0变成了6
	}
})

//  接着就是当页面onload后执行轮播的函数了
window.onload = function() {
	var imgsWrp = document.querySelector(".imgsWrp"),
		imgsLi = imgsWrp.querySelectorAll("li"),
		markSpan = document.querySelector(".carousel span"),
		i = 0;

	// 这里根据图片数量分别算出li的宽度和ul的宽度
	imgsWrp.style.width = vm.imgsLens * 100 + "%";
	var widthLi = 100 / vm.imgsLens + "%";
	for (var ils = 0; ils < vm.imgsLens; ils++) {
		imgsLi[ils].style.width = widthLi;
	}
	// 定时函数，每3秒变更i，同时变换ul要transform的值
	setInterval(function() {
		i < vm.imgsLens - 1 ? i++ : i = 0;
		imgsWrp.style.transform = "translateX(-" + 100 / vm.imgsLens * i + "%)";
		markSpan.textContent = i + 1
	}, 3000)
}
