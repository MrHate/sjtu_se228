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
		<template slot="action" slot-scope="row">
			<b-button size="sm" class="mr-1" @click="viewDetail(row.item.id)">
				View Detail
			</b-button>
			<b-button size="sm" class="mr-1" @click="deleteBook(row.item.id)">
				Delete
			</b-button>
		</template>	
	</b-table>
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
			fields: ['isbn','name','author','price','quantity', 'action'],
		}
	},
	mounted:function(){
		this.axios.get('books/all').then((response)=>{
			this.bookList = response.data.map((e)=>{
				e.image = '';
				return e;
			});
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


