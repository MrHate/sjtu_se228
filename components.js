const books = [
	{id:0,name:"jscom"},
	{id:1,name:"hacker"},
	{id:2,name:"C++"},
	{id:3,name:"touhou"}
];

function get_book(id){
	return books[id];
};

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
		var t_books = [];
		for(var i in books){
			var ent = {};
			ent.id = books[i].id;
			ent.name = books[i].name;
			ent.imgpath = 'images/'+ent.id+'.jpeg';
			ent.route = '/detail/'+ent.id;
			t_books.push(ent);
		}
		return{
			bookList: t_books
		}
	},
	template:'\
		<div>\
			<table>\
				<tr v-for="i in bookList">\
					<th><td><img :src="i.imgpath" alt="pic" class="list-img"/></td><td><router-link :to="i.route">{{i.name}}</router-link></td></th>\
				</tr>\
			</table>\
		</div>\
	'
});

Vue.component('nav-menu',{
	template:'\
		<div>\
			<div class="span2">\
				<ul class="nav nav-pills nav-stacked">\
					<li><router-link class="side_nav_btn" to="">Home</a></li>\
					<li><router-link class="side_nav_btn" to="">Explore</a></li>\
					<li><router-link class="side_nav_btn" to="">Cart</a></li>\
					<li><router-link class="side_nav_btn" to="">Wishlist</a></li>\
					<li><router-link class="side_nav_btn" to="">Contact</a></li>\
				</ul>\
			</div>\
		</div>\
	'
})

Vue.component('book-detail',{
	props:['book_id'],
	data:function(){
		t_id = this.book_id;
		t_book = get_book(t_id);
		t_img = 'images/'+t_id+'.jpeg';
		return {
			name:t_book.name,
			subtitle:"sub",
			content:"content",
			image: t_img
		}
	},
	methods:{
		onClickBack:function(){
			router.go(-1);
		}
	},
	template:'\
		<div class="container-fluid">\
			<div class="row">\
				<div class="col-xs-3">\
					<div class="panel panel-default">\
						<div class="panel-body">\
							<img :src="image" alt="picture" class="img_detail"/>\
						</div>\
						<div class="panel-footer">picture</div>\
					</div>\
					<button type="button">Add to Cart</button>\
					<button type="button" @click="onClickBack">Back</button>\
				</div>\
				<div class="col-xs-8">\
					<h2>{{ name }}</h2>\
					<h4>{{subtitle}}</h4>\
					<p>{{content}}</p>\
				</div>\
			</div>\
		</div>\
	'
})
