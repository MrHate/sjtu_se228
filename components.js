const books = [
	{id:0,name:"Jscom",price:"$10.99",description:"This is a book in black.Since the Sass port has a separate repo and serves a slightly different audience, the contents of the project differ greatly from the main Bootstrap project. This ensures the Sass port is as compatible with as many Sass-based systems as possible."},
	{id:1,name:"Hacker",price:"$2.99",description:"If you are hacker.Using color to add meaning to a button only provides a visual indication, which will not be conveyed to users of assistive technologies – such as screen readers. Ensure that information denoted by the color is either obvious from the content itself (the visible text of the button), or is included through alternative means, such as additional text hidden with the .sr-only class."},
	{id:2,name:"C++",price:"$13.99",description:"C++++++++++++++++."},
	{id:3,name:"Touhou",price:"$11.99",description:"Where is Gensokyo."},
	{id:4,name:"Hiii",price:"$4.99",description:"Hello world."},
	{id:5,name:"Blue",price:"$9.99",description:"Blue means sadness."}
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
			searchText:"",
			username:get_query("username"),
			password:""
		}
	},
	methods:{
		logoutFun:function(){
			this.username = "";
			this.password = "";
			document.location.href = "login.html";
			//console.log(this.searchText);
		},
		doSearch:function(){
			router.push({
				path:`/all/${this.searchText}`,
			});
		}
	},
	//watch:{
	//    searchText:function(){
	//        //Replace search to search
	//        var o_url = document.location.href;
	//        if(o_url.search('search') != -1){
	//            console.log(get_query("search"))
	//        }
	//        else{
	//        }
	//        //console.log(o_url.search('search') == -1);
	//    }
	//},
	template:'\
		<div>\
			<div class="container-fluid header">\
				<div class="navbar-header">\
					<h3 class="navbar-brand" href="#">E-Book</h3>\
					<input type="text" class="search" placeholder="Search" v-model="searchText"  />\
					<button class="btn btn-default" @click="doSearch">search</button>\
				</div>\
			</div>\
			<div>\
				<ul class="nav navbar-nav pull-right">\
					<li class="active"><a href="#">{{username}}</a></li>\
					<li><a href="#" @click="logoutFun">Log out</a></li>\
				</ul>\
			</div>\
		</div>'
});
				
Vue.component('footer-contents',{
	template:'\
		<footer class="navbar-fixed-bottom">\
			<div class="container-fluid pull-right">E-book by dgy.</div>\
		</footer>'
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
			ent.price = books[i].price;
			t_books.push(ent);
		}
		return{
			bookList: t_books
		}
	},
	template:'\
		<div>\
			<div class="table" v-for="i in bookList">\
				<router-link :to="i.route">\
					<button class="btn btn-default book-entry">\
						<div class="container" style="width:100%">\
							<img :src="i.imgpath" alt="pic" class="col-xs-1 list-img"/>\
							<div class="col-xs-2">\
								<h4>{{i.name}}</h4>\
								<p class="col-xs-1">{{i.price}}</p>\
							</div>\
						</div>\
					</button>\
				</router-link>\
			</div>\
		</div>'
});

Vue.component('nav-menu',{
	template:'\
		<div>\
			<div class="span2">\
				<ul class="nav nav-pills nav-stacked">\
					<li><router-link class="side_nav_btn" to="/">Home</router-link></li>\
					<li><router-link class="side_nav_btn" to="/all">All Books</router-link></li>\
					<li><router-link class="side_nav_btn" to="">Cart</router-link></li>\
					<li><router-link class="side_nav_btn" to="">Wishlist</router-link></li>\
					<li><router-link class="side_nav_btn" to="">Contact</router-link></li>\
				</ul>\
			</div>\
		</div>'
})

Vue.component('book-detail',{
	props:['book_id'],
	data:function(){
		t_id = this.book_id;
		t_book = get_book(t_id);
		t_img = 'images/'+t_id+'.jpeg';
		return {
			name:t_book.name,
			subtitle:t_book.price,
			content:t_book.description,
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
							<img :src="image" alt="picture" class="img_detail" style="width:100%;height:60%"/>\
						</div>\
						<div class="panel-footer text-center">{{name}}</div>\
					</div>\
					<button class="btn btn-default" type="button">Add to Cart</button>\
					<button class="btn btn-default" type="button" @click="onClickBack">Back</button>\
				</div>\
				<div class="col-xs-8">\
					<h2>{{ name }}</h2>\
					<h4>{{subtitle}}</h4>\
					<p>{{content}}</p>\
				</div>\
			</div>\
		</div>'
})

Vue.component('all-list',{
	props:['search_text'],
	data:function(){
		return{
			bookList:books
		}
	},
	template:'\
		<div>\
			<table class="table">\
				<thead>\
				  <tr>\
					<th scope="col">{{this.search_text}}#</th>\
					<th scope="col">Name</th>\
					<th scope="col">Price</th>\
					<th scope="col">Quantity</th>\
				  </tr>\
				</thead>\
				<tbody>\
				  <tr v-for="i in bookList">\
					<th scope="row">{{i.id}}</th>\
					<td>{{i.name}}</td>\
					<td>{{i.price}}</td>\
					<td>1</td>\
				  </tr>\
				</tbody>\
		  </table>\
	</div>'
})
