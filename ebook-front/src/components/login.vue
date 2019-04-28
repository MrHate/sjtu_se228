<template>
<div>
	<b-container>
		<b-row align-v="center">
			<b-col></b-col>
			<b-col cols="6">
				<b-form @submit="login">
					<b-form-group class="mt-3">
						<b-form-input placeholder="username" v-model="username"></b-form-input>
					</b-form-group>
					<b-form-group class="mt-3">
						<b-form-input type="password" placeholder="password" v-model="password"></b-form-input>
					</b-form-group>
					<b-button type="submit" variant="primary">Login</b-button>
					<br><br>
					<b-button to="register">Sign Up</b-button>
				</b-form>
			</b-col>
			<b-col></b-col>
		</b-row>
	</b-container>
</div>
</template>

<script>
export default {
	name: 'Login',
	data:function(){
		return{
			username:"",
			password:""
		}
	},
	methods:{
		login(evt){
			evt.preventDefault()
			this.axios({
				method:'post',
				url:'login',
				headers:{'Content-Type': 'application/x-www-form-urlencoded'},
				data:this.$qs.stringify({'username':this.username,'password':this.password}),
			}).then((response)=>{
				if(response.data == 'success'){
					this.axios.get('users/enabled').then((response)=>{
						console.log(response);
						if(response.data == false){
							alert('this account is banned');
							this.axios.post('logout').then(()=>{
								this.$router.go(0);
							});
						}else{
							this.$router.push('all');
							this.$router.go(0);
						}
					});
				}else{
					alert("log in failed");
				}
			});
		}
	}
}
</script>



