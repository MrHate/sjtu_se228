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
			<b-button size="sm" class="mr-1" @click="toggleEnabled(row.item.username,!row.item.enabled)">
				Toggle Enabled
			</b-button>
			<b-button size="sm" class="mr-1" @click="toggleAdmin(row.item.username,!row.item.admin)">
				Toggle Admin
			</b-button>
		</template>	
	</b-table>
	<b-col md="6" class="my-1">
		<b-pagination
		v-model="currentPage"
		:total-rows="totalRows"
		:per-page="perPage"
		class="my-0"
		></b-pagination>
	</b-col>
</div>
</template>

<script>
export default {
	name: 'UserManage',
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
				{key:'username',sortable:true},
				{key:'enabled',sortable:false},
				{key:'admin',sortable:false},
				{key:'action',sortable:false}
			],
			sortBy:'time',
			sortDesc:false,
		}
	},
	mounted:function(){
		this.getList();
	},
	methods:{
		getList:function(){
			this.axios.get('users/all').then((response)=>{
				this.bookList = response.data;
				this.totalRows = this.bookList.length;
			});
		},
		toggleEnabled:function(username,enabled){
			this.axios({
				method:'get',
				url:'users/set-user-enabled',
				params:{
					username:username,
					enabled:enabled
				}
			}).then(()=>{
				this.getList();
			})
		},
		toggleAdmin:function(username,enabled){
			this.axios({
				method:'get',
				url:'users/set-user-admin',
				params:{
					username:username,
					isAdmin:enabled
				}
			}).then(()=>{
				this.getList();
			})
		}
	}
}
</script>


