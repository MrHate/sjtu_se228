Vue.component('header-contents',{
	data:function(){
		return{
			showDialog:false,
			isLoggedIn:false,
			showAlert:false,
			username:"",
			password:""
		}
	},
	methods:{
		showModal:function(){
			this.showDialog = true;
		},
		loginFun:function(){
			this.username = this.$refs.username_input.value;
			this.password = this.$refs.password_input.value;
			if(this.username == "" || this.password == ""){
				this.showAlert = true;
				return;
			}
			this.showAlert = false;
			this.showDialog = false;
			this.isLoggedIn = true;
			console.log("user: "+this.username+"\npassword: "+this.password);
		},
		signupFun:function(){
			alert(this.$refs.username_input.value);
		},
		logoutFun:function(){
			this.showDialog = false;
			this.username = "";
			this.password = "";
			this.isLoggedIn = false;
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
					<div>\
						<div v-if="isLoggedIn">{{username}}<a href="#" @click="logoutFun">Log out</a></div>\
						<div v-else=""><a class="nav_btn" href="#" @click="showModal">Sign up/Login</a></div>\
						<div class="login_modal" v-show="showDialog">\
							<p>test dialog</p>\
							<form>\
								 User:<br><input type="text" name="username" ref="username_input">\
								 Password:<br><input type="text" name="password" ref="password_input">\
								<button type="button" @click="loginFun">log in</button>\
								<button type="button" @click="signupFun">sign up</button>\
							</form>\
							<div v-show="showAlert"><br>Username and password can not be empty.</div>\
						</div>\
					</div>\
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
