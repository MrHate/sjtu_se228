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
			while(i < 1 && this.fetch_err == false){
				this.fetchBook(i);
				++i;
			}
		});
		this.fetch_err = false;
	},
	methods:{
		fetchBook:function(id){
			var self = this
			return axios.get('bookManager',{
				params:{
					id:id,
					img:true
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
					book.img = response.data.img;
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
							<img :src="i.img" alt="pic" class="col-xs-1 list-img"/>\
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
	data:function(){
		return{
			isAdmin: get_query("username") == 'admin'
		}
	},
	template:'\
		<div>\
			<div class="span2">\
				<ul class="nav nav-pills nav-stacked">\
					<li><router-link class="side_nav_btn" to="/">Home</router-link></li>\
					<li><router-link class="side_nav_btn" to="/all">All Books</router-link></li>\
					<li><router-link v-if="isAdmin" class="side_nav_btn" to="/manage">Manage</router-link></li>\
					<li><router-link v-if="!isAdmin" class="side_nav_btn" to="/cart">Cart</router-link></li>\
					<li><router-link v-if="!isAdmin" class="side_nav_btn" to="/orders">Orders</router-link></li>\
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
		this.fetchBook(this.book_id);
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
			return axios.get('bookManager',{
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
			return axios.get('bookManager',{
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

Vue.component('manage-list',{
	data:function(){
		return{
			fetch_err:false,
			list_len:0,
			bookList: [],
			search_text: "",
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
			return axios.get('bookManager',{
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
					book.route = '/modify/'+book.id;
					this.bookList.push(book);
				}
			}).catch((error)=>{
				console.log(error);
				self.fetch_err = true;
			});
		},
		createNewEntry(){
			router.push('/modify/-1');
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
					<th scope="col"></th>\
				  </tr>\
				</thead>\
				<tbody>\
				  <tr v-for="i in bookList.filter(this.fff.bind(null,this.search_text))">\
					<th scope="row">{{i.id}}</th>\
					<td>{{i.name}}</td>\
					<td>$ {{i.price}}</td>\
					<td>{{i.quantity}}</td>\
					<td><router-link :to="i.route">Modify</router-link></td>\
				  </tr>\
				</tbody>\
		  </table>\
		  <button class="btn btn-default" @click="createNewEntry">Create New Entry</button>\
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

Vue.component('modify',{
	props:['book_id'],
	data:function(){
		return{
			bookname:'',
			price:'',
			desp:'',
			quantity:'',
			imgBase64:'',
			isUploadedImg:false
		}
	},
	mounted:function(){
		if(this.book_id != -1)this.fetchBook(this.book_id);
	},
	methods:{
		fetchBook:function(id){
			var self = this
			return axios.get('bookManager',{
				params:{
					id:id
				}
			}).then((response)=>{
				self.bookname = response.data.name;
				self.price = response.data.price;
				self.desp = response.data.description;
				self.quantity = response.data.quantity;
			}).catch((error)=>{
				console.log(error);
			});
		},
		onClickSubmit:function(){
			console.log("on button submit");
			var params = new URLSearchParams();
			params.append("id",this.book_id);
			params.append("name",this.bookname);
			params.append("price",this.price);
			params.append("quantity",this.quantity);
			params.append("desp",this.desp);
			params.append("isUploadedImg",this.isUploadedImg);
			params.append("img",this.imgBase64);
			if(this.book_id != -1){
				axios.post('bookManager',params).then((response)=>{
					console.log(response);
					router.push('/manage');
				}).catch((error)=>{
					console.log(error);
				});
			}else{
				axios.put('bookManager',params).then((response)=>{
					console.log(response);
					router.push('/manage');
				}).catch((error)=>{
					console.log(error);
				});
			}
		},
		onClickDelete:function(){
			var params = new URLSearchParams();
			params.append("id",this.book_id);
			axios.delete('bookManager',{params:{id:this.book_id}}).then((response)=>{
				console.log(response);
				router.push('/manage');
			}).catch((error)=>{
				console.log(error);
			});
		},
		onImgChange:function(event){
			let file = event.target.files[0];
			let reader = new FileReader();
			let img = new Image();
			reader.readAsDataURL(file);
			reader.onloadend = (e)=>{
				img.src = e.target.result;
				this.imgBase64 = reader.result;
			}
			let canvas = this.$refs['imgPreview']
            let context = canvas.getContext('2d')
            img.onload = () => {
				this.isUploadedImg = true;
                img.width = 120
                img.height = 160
                canvas.width = 120
                canvas.height = 160
                context.clearRect(0, 0, 120, 160)
                context.drawImage(img, 0, 0, 120, 160)
            }
		}
	},
	template:'\
		<div>\
		  <form class="bs-example bs-example-form" role="form">\
			<div class="container">\
				<div class="col-xs-6">\
					<div class="input-group">\
						<p>Book Name:</p>\
						<input type="text" class="form-control" v-model="bookname">\
					</div>\
					<br>\
					<div class="input-group">\
						<p>Price:</p>\
						<input type="text" class="form-control" v-model="price">\
					</div>\
					<br>\
					<div class="input-group">\
						<p>Quantity:</p>\
						<input type="text" class="form-control" v-model="quantity">\
					</div>\
					<br>\
					<div class="input-group">\
						<p>Description:</p>\
						<textarea class="form-control" v-model="desp" style="resize:none" rows="8" cols="60"></textarea>\
					</div>\
					<br>\
					<button type="button" class="btn btn-default" @click="onClickSubmit">Submit</button>\
					<button type="button" class="btn btn-default" v-if="this.book_id != -1" @click="onClickDelete">Delete</button>\
				</div>\
				<div class="col-xs-4">\
					<br>\
					<p>Book Image:</p>\
					<div class="panel panel-default" style="width:130px;height:170px">\
						<canvas ref="imgPreview" height="100" width="100" style="padding:5px"></canvas>\
					</div>\
					<input type="file" accept="image/*" @change="onImgChange"/>\
				</div>\
				<br>\
			</div>\
		  </form>\
		</div>'
})
