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
  },
  methods: {
	  booknamePressed:function(e){
		  //console.log(e.target);
		  if(e.target.style.color == "rgb(0, 0, 0)"){
			  e.target.style.color = "#f00";
		  }
		  else{
			  console.log(e.target.style.color);
			  e.target.style.color = "#000";
		  }
	  }
  }
})