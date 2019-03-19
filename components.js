function get_query(name){
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	var r = window.location.search.substr(1).match(reg);
	if(r!=null)return unescape(r[2]); return null;
}

Vue.component('header-contents',{
	data:function(){
		return{
			username:get_query("username"),
			password:""
		}
	},
	methods:{
		logoutFun:function(){
			this.username = "";
			this.password = "";
			document.location.href = "login.html";
		}
	},
	template:'\
		<div>\
			<div class="container-fluid">\
				<div class="navbar-header">\
					<a class="navbar-brand" href="#">E-Book</a>\
					<form action="" class="pull-right">\
						<input type="text" class="search" placeholder="Search">\
					</form>\
				</div>\
			</div>\
			<div class="container-fluid">\
				<div class="pull-right">\
					<div>{{username}}<a href="#" @click="logoutFun">Log out</a></div>\
				</div>\
			</div>\
		</div>'
});
				
Vue.component('footer-contents',{
	template:'\
		<footer class="navbar-fixed-bottom">\
			<div class="container-fluid pull-right">E-book by dgy.</div>\
		</footer>\
	'
});

Vue.component('book-list',{
	data:function() {
		return{
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
	},
	template:'\
		<div>\
			<table>\
				<tr><th>ID</th><th>Name</th><th>Path</th></tr>\
				<tr v-for="i in bookList">\
					<th>{{i.id}}</th>\
					<th><a href="#">{{i.name}}</a></th>\
					<th>{{i.path}}</th>\
				</tr>\
			</table>\
		</div>\
	'
});

Vue.component('nav-menu',{
	template:'\
		<div class="col-xs-2">\
			<div class="span2">\
				<ul class="nav nav-pills nav-stacked">\
					<li><a class="side_nav_btn" href="./index.html">Home</a></li>\
					<li><a class="side_nav_btn" href="./explore.html">Explore</a></li>\
					<li><a class="side_nav_btn" href="#">Cart</a></li>\
					<li><a class="side_nav_btn" href="#">Wishlist</a></li>\
					<li><a class="side_nav_btn" href="#">Contact</a></li>\
				</ul>\
			</div>\
		</div>\
	'
})
