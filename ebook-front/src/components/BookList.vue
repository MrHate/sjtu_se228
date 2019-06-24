<template>
<div>
	<b-table :items="bookList" :sort-by.sync="sortBy" :sort-desc="sortDesc" :fields="fields" :filter="searchText" :current-page="currentPage" :perPage="perPage" striped>
		<template slot="action" slot-scope="row">
			<b-button size="sm" class="mr-1" @click="row.toggleDetails" @mouseover="fetchImage(row.item.id)">
				{{ row.detailsShowing ? 'Hide' : 'Show' }} Details
			</b-button>
			<b-button size="sm" class="mr-1" @click="addToCart(row.item.id)">
				Add to Cart
			</b-button>
		</template>	
		<template slot="row-details" slot-scope="row">
			<b-card class="pull-left">
				<b-container>
					<b-row>
						<b-col cols="4">
							<img style="width:30%;height:100%" :src="row.item.image"/>
						</b-col>
						<b-col>
							<p>{{row.item.desp}}</p>
						</b-col>
					</b-row>
				</b-container>
			</b-card>
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
	name: 'BookList',
	props:['searchText'],
	data:function(){
		return{
			bookList: [],
			loadedImage:[],
			currentPage:1,
			perPage:5,
			totalRows:0,
			fields: [
				{key:'isbn',sortable:true},
				{key:'name',sortable:true},
				{key:'author',sortable:true},
				{key:'price',sortable:true},
				{key:'quantity',sortable:true},
				{key:'action',sortable:false}
			],
			sortBy:'time',
			sortDesc:false,
		}
	},
	mounted:function(){
		this.axios.get('books/all').then((response)=>{
			this.bookList = response.data.map((e)=>{
				e.image = '';
				return e;
			});
			this.totalRows = this.bookList.length;
		});
	},
	methods:{
		fetchImage(id){
			if(this.loadedImage.indexOf(id) != -1)return;
			this.loadedImage.push(id);
			this.axios.get('books/image',{params:{id:id}}).then((response)=>{
				this.bookList.forEach((e)=>{
					if(e.id == id){
						e.image = response.data;
						return;
					}
				})
			})
		},
		addToCart(id){
			this.axios.get('users/current').then((response)=>{
				this.axios.get('cart/add',{params:{username:response.data,bid:id}}).then(()=>{
					alert("added to cart")
				})
			})
		}
	}
}
</script>

