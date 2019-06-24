<template>
<div>
	<b-container>
		<b-row>
			<b-col cols='5'>
				<b-card title="Book Sales">
					<canvas id="myChart" width="400" height="400"></canvas>
					<br>
					<b-form-group>
						<b-form-input type="number" size="sm" class="mr-sm-2" v-model="bid" placeholder="Enter book ID"></b-form-input>
					</b-form-group>
					<b-form-group>
						<b-form-input type="date" size="sm" class="mr-sm-2" v-model="bookBeginDate"></b-form-input>
					</b-form-group>
					<b-form-group>
						<b-form-input type="date" size="sm" class="mr-sm-2" v-model="bookEndDate"></b-form-input>
					</b-form-group>
					<b-form-group>
						<b-button size="sm" class="mr-sm-2" @click="analyzeBookSales">Analye</b-button>
					</b-form-group>
				</b-card>
			</b-col>
		</b-row>
	</b-container>
</div>
</template>

<script>
import Chart from 'chart.js';

export default {
	name: 'Analye',
	data:function(){
		return{
			bid:"",
			filteredList:[],
			bookSalesChart:null,
			bookSalesData:[],
			bookSalesLabels:[],
			username:"",
			bookBeginDate:null,
			bookEndDate:null
		}
	},
	mounted:function(){
		this.axios.get('users/current').then((response)=>{
			this.username = response.data;
		});
		var ctx = document.getElementById('myChart');
		this.bookSalesChart = new Chart(ctx, {
			type: 'bar',
			data: {
				labels: this.bookSalesLabels,
				datasets: [{
					label: 'Amount of sales',
					data: this.bookSalesData,
					backgroundColor: [
						'rgba(255, 99, 132, 0.2)',
						'rgba(54, 162, 235, 0.2)',
						'rgba(255, 206, 86, 0.2)',
						'rgba(75, 192, 192, 0.2)',
						'rgba(153, 102, 255, 0.2)',
						'rgba(255, 159, 64, 0.2)'
					],
					borderColor: [
						'rgba(255, 99, 132, 1)',
						'rgba(54, 162, 235, 1)',
						'rgba(255, 206, 86, 1)',
						'rgba(75, 192, 192, 1)',
						'rgba(153, 102, 255, 1)',
						'rgba(255, 159, 64, 1)'
					],
					borderWidth: 1
				}]
			},
			options: {
			}
		});
	},
	methods:{
		analyzeBookSales(){
			var reg = new RegExp('-','g');
			this.axios.get('/analyze/book-sale',{params:{bookId:this.bid,startDate:this.bookBeginDate.replace(reg,'/'),endDate:this.bookEndDate.replace(reg,'/')}}).then((response)=>{
				if(!response.data)return;
				this.bookSalesData.push(response.data);
				var label = this.bid+'('+this.bookBeginDate+' - '+this.bookEndDate+')';
				this.bookSalesLabels.push(label);
				this.bookSalesChart.update();
			});
		}
	}
}
</script>



