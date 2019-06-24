<template>
<div>
	<b-navbar type="dark" variant="dark">
		<b-navbar-brand href="#">E-Book</b-navbar-brand>
		<div class="w-25">
			<b-form v-if="isLogged">
			<b-input-group>
				<b-form-input size="sm" class="mr-sm-2" placeholder="Search" v-model="searchText"></b-form-input>
				<b-input-group-append>
					<b-button type="submit" size="sm" class="my-2 my-sm-0" @click="doSearch">Search</b-button>
				</b-input-group-append>
			</b-input-group>
			</b-form>
		</div>
		<b-navbar-nav  v-if="isLogged" >
			<b-nav-item to="/all">All Books</b-nav-item>
			<b-nav-item v-if="isAdmin" to="/book-manage">Book Manage</b-nav-item>
			<b-nav-item v-if="isAdmin" to="/user-manage">User Manage</b-nav-item>
			<b-nav-item v-if="isAdmin" to="/analyze">Analye</b-nav-item>
			<b-nav-item v-if="!isAdmin" to="/purchased">Purchased</b-nav-item>
			<b-nav-item v-if="!isAdmin" to="/cart">Cart</b-nav-item>
			<b-nav-item to="/orders">Orders</b-nav-item>
			<b-nav-item to="/profile" active>
				<b-img width="23" rounded="circle" :src="avatar"></b-img>
				{{username}}
			</b-nav-item>
			<b-nav-item @click="sendLogout">Log out</b-nav-item>
		</b-navbar-nav>
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
			avatar:require('../assets/unknown.png'),
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
				this.axios.get('users/avatar',{params:{username:this.username}}).then((response)=>{
					if(response.data != ''){this.avatar = response.data;}
				})
			}else{
				this.$router.push('/login');
				this.isLogged = false;
				this.isAdmin = false;
			}
		})
	},
	methods:{
		doSearch:function(evt){
			evt.preventDefault()
			this.$router.push({
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
