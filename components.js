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
			<div class="container">\
				<div class="navbar-header">\
					<a class="navbar-brand" href="./index.html">E-Book</a>\
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
})
