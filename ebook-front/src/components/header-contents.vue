<template>
<div>
	<b-navbar variant="light">
		<b-navbar-brand href="#">E-Book</b-navbar-brand>
		<div class="w-50">
			<b-form v-if="isLogged">
			<b-input-group>
				<b-form-input size="sm" class="mr-sm-2" placeholder="Search" v-model="searchText"></b-form-input>
				<b-input-group-append>
					<b-button type="submit" size="sm" class="my-2 my-sm-0" @click="doSearch">Search</b-button>
				</b-input-group-append>
			</b-input-group>
			</b-form>
		</div>
		<b-nav  v-if="isLogged" pills>
			<b-nav-item to="/all">All Books</b-nav-item>
			<b-nav-item v-if="isAdmin" to="/manage">Manage</b-nav-item>
			<b-nav-item v-if="!isAdmin" to="/cart">Cart</b-nav-item>
			<b-nav-item to="/orders">Orders</b-nav-item>
			<b-nav-item disabled>{{username}}</b-nav-item>
			<b-nav-item @click="sendLogout">Log out</b-nav-item>
		</b-nav>
	</b-navbar>
</div>
</template>

<script>
export default {
	name: 'HeaderContents',
	data:function(){
		return{
			searchText:"",
			username:"",
			isAdmin:false,
			isLogged:false
		}
	},
	mounted:function(){
		this.axios.get('users/current').then((response)=>{
			if(response.data != 'unauthorized'){
				this.username = response.data;
				this.isLogged = true;
				this.isAdmin = (this.username == 'admin');
			}else{
				this.$router.to('login');
				this.isLogged = false;
				this.isAdmin = false;
			}
		})
	},
	methods:{
		doSearch:function(evt){
			evt.preventDefault()
			this.$router.to({
				path:`/all/${this.searchText}`,
			});
		},
		sendLogout(){
			this.axios.post('logout').then(()=>{
				this.$router.go(0);
			});
		}
	}
}
</script>
