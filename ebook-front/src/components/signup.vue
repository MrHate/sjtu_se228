<template>
<div>
	<b-container>
		<b-row align-v="center">
			<b-col></b-col>
			<b-col cols="6">
				<b-form @submit="register">
					<b-form-group class="mt-3">
						<b-form-input placeholder="username" v-model="username"></b-form-input>
					</b-form-group>
					<b-form-group class="mt-3">
						<b-form-input type="password" placeholder="password" v-model="password"></b-form-input>
					</b-form-group>
					<b-form-group class="mt-3">
						<b-form-input type="password" placeholder="repeat the password" v-model="password2"></b-form-input>
					</b-form-group>
					<b-form-group class="mt-3">
						<b-form-input type="email" placeholder="email" v-model="email"></b-form-input>
					</b-form-group>
					<b-button type="submit" variant="primary">Register</b-button>
					<br><br>
					<b-button to="login">I have my account</b-button>
				</b-form>
			</b-col>
			<b-col></b-col>
		</b-row>
	</b-container>
</div>
</template>

<script>
export default {
	name: 'Register',
	data:function(){
		return{
			username:'',
			password:'',
			password2:'',
			email:''
		}
	},
	methods:{
		register(evt){
			evt.preventDefault()
			if(this.password != this.password2){
				alert('passwords diff when repeating');
				return;
			}
			this.axios({
				method:'post',
				url:'users/register',
				params:{
					username:this.username,
					password:this.password,
					email:this.email
				}
			}).then((response)=>{
				if(response.data == true){
					alert("New account created.");
					this.$router.push('login');
				}else{
					alert("Failed to create new account.");
				}
			}).catch(()=>{
				alert("Failed to create new account.");
			});
		}
	}

}
</script>
