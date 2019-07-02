<template>
<div>
	<b-form-group class="mb-0">
		<b-container>
			<b-row>
				<b-col>
					<b-form-input size="sm" class="mr-sm-2" v-model="searchText" placeholder="Search order list"></b-form-input>
				</b-col>
				<b-col>
					<b-form-input type="date" size="sm" class="mr-sm-2" v-model="beginDate"></b-form-input>
				</b-col>
				<b-col>
					<b-form-input type="date" size="sm" class="mr-sm-2" v-model="endDate"></b-form-input>
				</b-col>
				<b-col cols="2">
					<b-button size="sm" class="mr-sm-2" @click="filterWithTime">filter with time</b-button>
				</b-col>
			</b-row>
		</b-container>
	</b-form-group>
	<br>
	<b-table :items="filteredList" :fields="fields" :filter="searchText" :sort-by.sync="sortBy" :sort-desc="sortDesc" :current-page="currentPage" :perPage="perPage" striped>
		<template v-if="isAdmin" slot="action" slot-scope="row">
			<b-button size="sm" class="mr-1" @click="removeOrderItem(row.item.id)">
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
</div>
</template>

<script>
export default {
	name: 'OrderList',
	data:function(){
		return{
			searchText:"",
			bookList: [],
			filteredList:[],
			username:"",
			currentPage:1,
			perPage:5,
			totalRows:0,
			isAdmin:false,
			fields: [
				{key:'time',sortable:true},
				{key:'isbn',sortable:true},
				{key:'bookname',sortable:true},
				{key:'quantity',sortable:true},
				{key:'price',sortable:true},
				{key:'orderid',sortable:true},
			],
			sortBy:'time',
			sortDesc:true,
			beginDate:null,
			endDate:null
		}
	},
	mounted:function(){
		this.axios.get('users/current').then((response)=>{
			this.username = response.data;
			this.axios.get('users/isAdmin').then((response)=>{
				this.isAdmin = response.data;
				if(this.isAdmin){
					this.axios.get('orders/all').then((response)=>{
						this.bookList = response.data;
						this.filteredList = this.bookList;
						this.totalRows = this.filteredList.length;
					})
					this.fields.push({key:'username',sortable:true});
					this.fields.push({key:'action',sortable:false});
				}else{
					this.axios.get('orders',{params:{username:this.username}}).then((response)=>{
						this.bookList = response.data;
						this.filteredList = this.bookList;
						this.totalRows = this.filteredList.length;
					})
				}
			})
		});
	},
	methods:{
		removeOrderItem(id){
			this.axios.delete('orders',{params:{id:id}}).then(()=>{
				this.$router.go(0);
			})
		},
		updateOrderItem(id,quantity){
			var params = new URLSearchParams();
			params.append("username",this.username);
			params.append("bid",id);
			params.append("quantity",quantity);
			this.axios.post('orders',params);
		},
		filterWithTime(){
			this.filteredList = this.bookList.filter((e)=>{
				let d = e.time.substring(0,10);
				return (d >= this.beginDate) && (d <= this.endDate);
			});
			this.totalRows = this.filteredList.length;
		}
	}
}
</script>



