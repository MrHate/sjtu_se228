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
		<template slot="quantity" slot-scope="row">
			<b-container>
				<b-row align-v="center">
				<b-col></b-col>
				<b-col cols="2">
					<b-form-input size="sm" type="number" v-model="row.item.quantity" @change="updateCartItem(row.item.bid,row.item.quantity)"></b-form-input>
				</b-col>
				<b-col></b-col>
				</b-row>
			</b-container>
		</template>	
		<template slot="action" slot-scope="row">
			<b-button size="sm" class="mr-1" @click="removeCartItem(row.item.bid)">
				Remove
			</b-button>
		</template>	
	</b-table>
	<b-button @click="onButtonBuy">Buy</b-button>
</div>
</template>

<script>
export default {
	name: 'CartList',
	data:function(){
		return{
			searchText:"",
			bookList: [],
			username:"",
			fields: ['bid','quantity', 'action'],
		}
	},
	mounted:function(){
		this.axios.get('users/current').then((response)=>{
			this.username = response.data;
			this.axios.get('cart',{params:{username:this.username}}).then((response)=>{
				this.bookList = response.data;
			})
		});
	},
	methods:{
		removeCartItem(id){
			this.axios.delete('cart',{params:{username:this.username,bid:id}}).then(()=>{
				this.$router.go(0);
			})
		},
		onButtonBuy(){
			this.axios.get('cart/clear',{params:{username:this.username}}).then(()=>{
				this.$router.go(0);
			})
		},
		updateCartItem(id,quantity){
			var params = new URLSearchParams();
			params.append("username",this.username);
			params.append("bid",id);
			params.append("quantity",quantity);
			this.axios.post('cart',params);
		}
	}
}
</script>


