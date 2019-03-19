const books = [
	{id:0,name:"Jscom"},
	{id:1,name:"Hacker"},
	{id:2,name:"C++"},
	{id:3,name:"Touhou"},
	{id:4,name:"Hiii"},
	{id:5,name:"Blue"}
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
			<table class="table" v-for="i in bookList">\
				<router-link :to="i.route">\
					<button class="btn btn-default book-entry">\
						<div class="container" style="width:100%">\
							<img :src="i.imgpath" alt="pic" class="col-xs-1 list-img"/>\
							<p class="col-xs-1">{{i.name}}</p>\
						</div>\
					</button>\
				</router-link>\
			</table>\
		</div>\
	'
});

Vue.component('nav-menu',{
	template:'\
		<div>\
			<div class="span2">\
				<ul class="nav nav-pills nav-stacked">\
					<li><router-link class="side_nav_btn" to="">Home</router-link></li>\
					<li><router-link class="side_nav_btn" to="">Explore</router-link></li>\
					<li><router-link class="side_nav_btn" to="">Cart</router-link></li>\
					<li><router-link class="side_nav_btn" to="">Wishlist</router-link></li>\
					<li><router-link class="side_nav_btn" to="">Contact</router-link></li>\
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
