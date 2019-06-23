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
	<b-table :items="filteredList" :fields="fields" :filter="searchText" :sort-by.sync="sortBy" :sort-desc="sortDesc" striped></b-table>
</div>
</template>

<script>
export default {
	name: 'Purchased',
	data:function(){
		return{
			searchText:"",
			filteredList:[],
			username:"",
			fields: [
				{key:'isbn',sortable:true},
				{key:'name',sortable:true},
				{key:'quantity',sortable:true},
			],
			sortBy:'name',
			sortDesc:false,
			beginDate:null,
			endDate:null
		}
	},
	mounted:function(){
		this.axios.get('users/current').then((response)=>{
			this.username = response.data;
		});
	},
	methods:{
		filterWithTime(){
			var reg = new RegExp('-','g');
			this.axios.get('analyze/user-books',{params:{username:this.username,startDate:this.beginDate.replace(reg,'/'),endDate:this.endDate.replace(reg,'/')}}).then((response)=>{
				this.filteredList = response.data;
			});
		}
	}
}
</script>



