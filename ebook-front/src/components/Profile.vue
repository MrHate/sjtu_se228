<template>
	<div>
		<b-form>
			<b-container>
				<b-row>
					<b-col cols="7">
						<b-row class="my-1">
							<b-col sm="4">
								<p>User Id: {{user_info.id}}</p>
							</b-col>
						</b-row>
						<b-row class="my-1">
							<b-col sm="4">
								<p>Username: {{user_info.username}}</p>
							</b-col>
						</b-row>
						<b-row class="my-1">
							<b-col sm="4">
								<label for="old_pwd">Password: </label>
							</b-col>
							<b-col sm="5">
								<b-form-input id="old_pwd" v-model="password"></b-form-input>
							</b-col>
						</b-row>
						<b-row class="my-1">
							<b-col sm="4">
								<label for="new_pwd">New Password:</label>
							</b-col>
							<b-col sm="5">
								<b-form-input id="new_pwd" v-model="new_password"></b-form-input>
							</b-col>
						</b-row>
						<b-row class="my-1">
							<b-col sm="4">
								<label for="repeat_pwd">Repeat new password: </label>
							</b-col>
							<b-col sm="5">
								<b-form-input id="repeat_pwd" v-model="repeat_password"></b-form-input>
							</b-col>
						</b-row>
						<br>
						<b-row class="my-1">
							<b-col sm="8">
								<b-button @click="updatePassword">Update Password</b-button>
							</b-col>
						</b-row>
					</b-col>
					<b-col sm="2">
						<b-card style="height:180px">
							<img :src="avatar" style="width:120px;height:120px" />
						</b-card>
						<input type="file" accept="image/*" @change="onImgChange"/>
					</b-col>
				</b-row>
				<br>
			</b-container>
		</b-form>
	</div>
</template>

<script>
	export default{
		name:'Profile',
		data(){
			return{
				user_info:{},
				password:'',
				new_password:'',
				repeat_password:'',
				image:''
			}
		},
		computed:{
			avatar(){
				if(this.image != ''){return this.image;}
				if(this.user_info.avatar){return this.user_info.avatar;}
				return require('../assets/unknown.png');
			}
		},
		mounted(){
			this.axios.get('users/current').then((response)=>{
				this.axios.get('users/profile',{params:{username:response.data}}).then((response)=>{
					this.user_info = response.data;
				});
			});
		},
		methods:{
			onImgChange:function(event){
				let file = event.target.files[0];
				let reader = new FileReader();
				reader.readAsDataURL(file);
				reader.onloadend = ()=>{
					this.image = reader.result;
					this.axios({
						method:'post',
						url:'users/avatar',
						headers:{'Content-Type': 'application/json'},
						params:{
							username:this.user_info.username
						},
						data:{
							avatar:this.image
						},
					});
				}
			},
			updatePassword(){
				this.axios({
					method:"post",
					url:'users/profile',
					headers:{'Content-Type': 'application/json'},
					params:{
						username:this.user_info.username,
						password:this.password,
						newPassword:this.new_password,
						repeatPassword:this.repeat_password,
					}
				}).then((response)=>{
					alert(response.data);
					this.$router.go(0);
				});
			}
		}
	}
</script>

