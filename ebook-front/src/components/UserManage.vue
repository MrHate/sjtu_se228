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
	<b-table :items="bookList" :sort-by.sync="sortBy" :sort-desc="sortDesc" :fields="fields" :filter="searchText" striped>
		<template slot="action" slot-scope="row">
			<b-button size="sm" class="mr-1" @click="toggleEnabled(row.item.username,!row.item.enabled)">
				Toggle Enabled
			</b-button>
		</template>	
	</b-table>
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
			fields: [
				{key:'id',sortable:true},
				{key:'username',sortable:true},
				{key:'enabled',sortable:false},
				{key:'action',sortable:false}
			],
			sortBy:'time',
			sortDesc:false,
		}
	},
	mounted:function(){
		this.axios.get('users/all').then((response)=>{
			this.bookList = response.data;
		});
	},
	methods:{
		toggleEnabled(username,enabled){
			this.axios({
				method:'get',
				url:'users/set-user-enabled',
				params:{
					username:username,
					enabled:enabled
				}
			}).then(()=>{
				this.$router.go(0);
			})
		}
	}
}
</script>


