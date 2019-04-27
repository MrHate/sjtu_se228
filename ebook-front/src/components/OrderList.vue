<template>
<div>
	<b-form-group label-cols-sm="2" label="Search here" class="mb-0">
		<b-input-group>
			<div class="w-25">
				<b-form-input size="sm" class="mr-sm-2" v-model="searchText" placeholder="Type to Search"></b-form-input>
			</div>
		</b-input-group>
	</b-form-group>
	<b-table :items="bookList" :fields="fields" :filter="searchText" striped>
		<template v-if="isAdmin" slot="action" slot-scope="row">
			<b-button size="sm" class="mr-1" @click="removeOrderItem(row.item.bid,row.item.username)">
				Remove
			</b-button>
		</template>	
	</b-table>
</div>
</template>

<script>
export default {
	name: 'OrderList',
	data:function(){
		return{
			searchText:"",
			bookList: [],
			username:"",
			isAdmin:false,
			fields: ['time','bid','quantity','price'],
		}
	},
	mounted:function(){
		this.axios.get('users/current').then((response)=>{
			this.username = response.data;
			this.isAdmin = (this.username == 'admin');
			if(this.isAdmin){
				this.axios.get('orders/all').then((response)=>{
					this.bookList = response.data;
				})
				this.fields.push('username');
				this.fields.push('action');
			}else{
				this.axios.get('orders',{params:{username:this.username}}).then((response)=>{
					this.bookList = response.data;
				})
			}
		});
	},
	methods:{
		removeOrderItem(id,username){
			this.axios.delete('orders',{params:{username:username,bid:id}}).then(()=>{
				this.$router.go(0);
			})
		},
		updateOrderItem(id,quantity){
			var params = new URLSearchParams();
			params.append("username",this.username);
			params.append("bid",id);
			params.append("quantity",quantity);
			this.axios.post('orders',params);
		}
	}
}
</script>



