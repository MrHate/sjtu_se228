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
	template:'\
		<div>\
			<div class="container-fluid header">\
				<div class="navbar-header">\
					<h3 class="navbar-brand" href="#">E-Book</h3>\
					<input type="text" class="search" placeholder="Search" v-model="searchText"  />\
					<button class="search-btn" @click="doSearch"><span class="glyphicon glyphicon-search" aria-hidden="true"></span></button>\
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
		return{
			bookList: [],
			list_len:0,
			fetch_err:false
		}
	},
	mounted:function(){
		var i = 0;
		this.fetchBook(-1).then(()=>{
			while(i < 3 && this.fetch_err == false){
				this.fetchBook(i);
				++i;
			}
		});
		this.fetch_err = false;
	},
	methods:{
		fetchBook:function(id){
			var self = this
			return axios.get('ebookServlet',{
				params:{
					id:id
				}
			}).then((response)=>{
				var id = response.data.id;
				if(id == "-2"){
					self.fetch_err = true;
				}else if(id == "-1"){
					var book_num = parseInt(response.data.book_num);
					self.list_len = book_num;
				}else{
					var book = {};
					book.id = response.data.id;
					book.name = response.data.name;
					book.price = response.data.price;
					book.imgpath = 'images/'+book.id+'.jpeg';
					book.route = '/detail/'+book.id;
					this.bookList.push(book);
				}
			}).catch((error)=>{
				console.log(error);
				self.fetch_err = true;
			});
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
								<p class="col-xs-1">$ {{i.price}}</p>\
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
					<li><router-link class="side_nav_btn" to="/cart">Cart</router-link></li>\
					<li><router-link class="side_nav_btn" to="/orders">Orders</router-link></li>\
				</ul>\
			</div>\
		</div>'
})

Vue.component('book-detail',{
	props:['book_id'],
	data:function(){
		t_id = this.book_id;
		//t_book = get_book(t_id);
		t_img = 'images/'+t_id+'.jpeg';
		return {
			name:"name",
			price:0,
			content:"content",
			image: t_img
		}
	},
	mounted:function(){
		var self = this;
		self.fetchBook(this.book_id);
	},
	methods:{
		onClickBack:function(){
			router.go(-1);
		},
		onClickCart:function(){
			this.fetchBook(-1);
		},
		fetchBook:function(id){
			var self = this
			return axios.get('ebookServlet',{
				params:{
					id:id
				}
			}).then((response)=>{
				self.name = response.data.name;
				self.price = response.data.price;
				self.content = response.data.description;
			}).catch((error)=>{
				console.log(error);
			});
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
					<button class="btn btn-default" type="button" @click="onClickCart">Add to Cart</button>\
					<button class="btn btn-default" type="button" @click="onClickBack">Back</button>\
				</div>\
				<div class="col-xs-8">\
					<h2>{{ name }}</h2>\
					<h4>$ {{price}}</h4>\
					<p>{{content}}</p>\
				</div>\
			</div>\
		</div>'
})

Vue.component('all-list',{
	props:['search_text'],
	data:function(){
		return{
			fetch_err:false,
			list_len:0,
			bookList: [],
			fff: function(text,e){
				//console.log(text);
				if(typeof(text) == "undefined" || text == "")return true;
				return e.name.includes(text);
			}
		}
	},
	mounted:function(){
		var i = 0;
		this.fetchBook(-1).then(()=>{
			while(i < this.list_len && this.fetch_err == false){
				this.fetchBook(i);
				++i;
			}
		});
		this.fetch_err = false;
	},
	methods:{
		fetchBook:function(id){
			var self = this
			return axios.get('ebookServlet',{
				params:{
					id:id
				}
			}).then((response)=>{
				var id = response.data.id;
				if(id == "-2"){
					self.fetch_err = true;
				}else if(id == "-1"){
					var book_num = parseInt(response.data.book_num);
					self.list_len = book_num;
				}else{
					//console.log(response);
					var book = {};
					book.id = response.data.id;
					book.name = response.data.name;
					book.price = response.data.price;
					book.quantity = response.data.quantity;
					book.imgpath = 'images/'+book.id+'.jpeg';
					book.route = '/detail/'+book.id;
					this.bookList.push(book);
				}
			}).catch((error)=>{
				console.log(error);
				self.fetch_err = true;
			});
		}
	},
	template:'\
		<div>\
			<table class="table">\
				<thead>\
				  <tr>\
					<th scope="col">#</th>\
					<th scope="col">Name</th>\
					<th scope="col">Price</th>\
					<th scope="col">Quantity</th>\
				  </tr>\
				</thead>\
				<tbody>\
				  <tr v-for="i in bookList.filter(this.fff.bind(null,this.search_text))">\
					<th scope="row">{{i.id}}</th>\
					<td><router-link :to="i.route">{{i.name}}</router-link></td>\
					<td>$ {{i.price}}</td>\
					<td>{{i.quantity}}</td>\
				  </tr>\
				</tbody>\
		  </table>\
	</div>'
})

Vue.component('cart',{
	data:function(){
		var t_books = [];
		return{
			bookList: t_books,
		}
	},
	template:'\
		<div>\
			<table class="table">\
				<thead>\
				  <tr>\
					<th scope="col">#</th>\
					<th scope="col">Name</th>\
					<th scope="col">Price</th>\
					<th scope="col">Quantity</th>\
				  </tr>\
				</thead>\
		  </table>\
		  <button class="btn btn-default">Buy</button>\
	</div>'
})
