<template>
<div>
	<b-form-group class="mb-0">
		<b-container>
			<b-row>
				<b-col>
					<b-form-input type="date" size="sm" class="mr-sm-2" v-model="beginDate"></b-form-input>
				</b-col>
				<b-col>
					<b-form-input type="date" size="sm" class="mr-sm-2" v-model="endDate"></b-form-input>
				</b-col>
				<b-col cols="2">
					<b-button size="sm" class="mr-sm-2" @click="filterWithTime">Filter</b-button>
				</b-col>
			</b-row>
		</b-container>
	</b-form-group>
	<br>
	<b-table :items="filteredList" :fields="fields" :sort-by.sync="sortBy" :sort-desc="sortDesc" striped></b-table>
	<br>
	<p>Total: {{filteredList.length}} books, {{totalSpending}} dollars.</p>
</div>
</template>

<script>
export default {
	name: 'Purchased',
	data:function(){
		return{
			filteredList:[],
			username:"",
			fields: [
				{key:'isbn',sortable:true},
				{key:'name',sortable:true},
				{key:'price',sortable:true},
			],
			sortBy:'name',
			sortDesc:false,
			beginDate:null,
			endDate:null
		}
	},
	computed:{
		totalSpending:function(){
			var res = 0;
			for(var i in this.filteredList){
				res += this.filteredList[i].price;
			}
			return res;
		}
	},
	mounted:function(){
		this.axios.get('users/current').then((response)=>{
			this.username = response.data;
		});
	},
	methods:{
		filterWithTime(){
			if(!this.beginDate || !this.endDate || this.beginDate >= this.endDate){
				alert('input date error');
				return;
			}
			var reg = new RegExp('-','g');
			this.axios.get('analyze/user-books',{params:{username:this.username,startDate:this.beginDate.replace(reg,'/'),endDate:this.endDate.replace(reg,'/')}}).then((response)=>{
				this.filteredList = response.data;
			});
		}
	}
}
</script>



