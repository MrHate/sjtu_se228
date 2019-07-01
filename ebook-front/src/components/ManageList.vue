<template>
<div>
	<b-form-group class="mb-0">
		<b-input-group>
			<div class="w-25">
				<b-form-input size="sm" class="mr-sm-2" v-model="searchText" placeholder="Search manage list"></b-form-input>
			</div>
		</b-input-group>
	</b-form-group>
	<br>
	<b-table :items="bookList" :sort-by.sync="sortBy" :sort-desc="sortDesc" :fields="fields" :filter="searchText" :current-page="currentPage" :perPage="perPage" striped>
		<template slot="action" slot-scope="row">
			<b-button size="sm" class="mr-1" @click="viewDetail(row.item.id)">
				View Detail
			</b-button>
			<b-button size="sm" class="mr-1" @click="deleteBook(row.item.id)">
				Delete
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
	<b-button @click="createEntry">Create New Entry</b-button>
</div>
</template>

<script>
export default {
	name: 'ManageList',
	data:function(){
		return{
			searchText:"",
			bookList: [],
			loadedImage:[],
			currentPage:1,
			perPage:5,
			totalRows:0,
			fields: [
				{key:'id',sortable:true},
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
		deleteBook(id){
			this.axios({
				method:'delete',
				url:'books',
				params:{
					id:id
				}
			}).then(()=>{
				this.$router.go(0);
			})
		},
		createEntry(){
			this.$router.push('modify/-1')
		},
		viewDetail(id){
			this.$router.push('modify/'+id)
		}
	}
}
</script>


