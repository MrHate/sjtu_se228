<template>
<div>
	<b-form-group class="mb-0">
		<b-input-group>
			<div class="w-25">
				<b-form-input size="sm" class="mr-sm-2" v-model="searchText" placeholder="Search cart"></b-form-input>
			</div>
		</b-input-group>
	</b-form-group>
	<br>
	<b-table :items="bookList" :fields="fields" :filter="searchText" :current-page="currentPage" :perPage="perPage" striped>
		<template slot="quantity" slot-scope="row">
			<b-row class="text-align:center">
				<b-col cols="4"></b-col>
				<b-col cols="4">
					<b-form-input size="sm" type="number" v-model="row.item.quantity" @change="updateCartItem(row.item.bid,row.item.quantity)"></b-form-input>
				</b-col>
			</b-row>
		</template>	
		<template slot="price" slot-scope="row">
			<p>{{row.item.price}}{{row.item.quantity > 1 ? '*'+row.item.quantity+'='+row.item.quantity * row.item.price : '' }}</p>
		</template>
		<template slot="action" slot-scope="row">
			<b-button size="sm" class="mr-1" @click="removeCartItem(row.item.bid)">
				Remove
			</b-button>
		</template>	
	</b-table>
	<b-row>
		<b-col md="6" class="my-1">
			<b-pagination
			v-model="currentPage"
			:total-rows="totalRows"
			:per-page="perPage"
			class="my-0"
			></b-pagination>
		</b-col>
	</b-row>
	<b-button @click="onButtonBuy">Buy</b-button>
	<p>Total Price: {{totalPrice}}</p>
</div>
</template>

<script>
export default {
	name: 'CartList',
	data:function(){
		return{
			searchText:"",
			bookList: [],
			currentPage:1,
			perPage:5,
			totalRows:0,
			totalPrice:0,
			username:"",
			fields: ['bookname','isbn','quantity','price', 'action'],
		}
	},
	mounted:function(){
		this.getList();
	},
	methods:{
		getList(){
			this.axios.get('users/current').then((response)=>{
				this.username = response.data;
				this.axios.get('cart',{params:{username:this.username}}).then((response)=>{
					this.bookList = response.data;
					this.totalRows = this.bookList.length;
					this.totalPrice = 0;
					for(var i in this.bookList){
						this.totalPrice += this.bookList[i].quantity * this.bookList[i].price;
					}
				})
			});
		},
		removeCartItem(id){
			this.axios.delete('cart',{params:{username:this.username,bid:id}}).then(()=>{
				this.getList();
			})
		},
		onButtonBuy(){
			this.axios.get('cart/clear',{params:{username:this.username}}).then((response)=>{
				alert(response.data);
				this.getList();
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


