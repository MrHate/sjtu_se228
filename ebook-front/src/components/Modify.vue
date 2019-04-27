<template>
	<div>
		<b-form>
			<b-container>
				<b-row v-if="id!=-1" align-v="start">
					<b-col align-self="start" cols="1">
						<p>No.{{id}}</p>
					</b-col>
				</b-row>
				<b-row>
					<b-col cols="7">
						<b-row class="my-1">
							<b-col sm="4">
								<label for="name">Name:</label>
							</b-col>
							<b-col sm="5">
								<b-form-input id="name" v-model="name"></b-form-input>
							</b-col>
						</b-row>
						<b-row class="my-1">
							<b-col sm="4">
								<label for="author">Author:</label>
							</b-col>
							<b-col sm="5">
								<b-form-input id="author" v-model="author"></b-form-input>
							</b-col>
						</b-row>
						<b-row class="my-1">
							<b-col sm="4">
								<label for="isbn">Isbn:</label>
							</b-col>
							<b-col sm="5">
								<b-form-input id="isbn" v-model="isbn"></b-form-input>
							</b-col>
						</b-row>
						<b-row class="my-1">
							<b-col sm="4">
								<label for="price">Price:</label>
							</b-col>
							<b-col sm="5">
								<b-form-input id="price" v-model="price"></b-form-input>
							</b-col>
						</b-row>
						<b-row class="my-1">
							<b-col sm="4">
								<label for="quantity">Quantity:</label>
							</b-col>
							<b-col sm="5">
								<b-form-input id="quantity" v-model="quantity"></b-form-input>
							</b-col>
						</b-row>
					</b-col>
					<b-col sm="2">
						<b-card style="height:180px">
							<img :src="image" style="width:100%;height:100%" />
						</b-card>
						<input type="file" accept="image/*" @change="onImgChange"/>
					</b-col>
				</b-row>
				<br>
				<b-row class="my-1">
					<b-col sm="2">
						<label :for="description">description:</label>
					</b-col>
					<b-col sm="7">
						<b-form-textarea :id="description" rows="3" max-rows="6" v-model="description"></b-form-textarea>
					</b-col>
				</b-row>
			</b-container>
			<b-button @click="submitInfo">Submit</b-button>
		</b-form>
	</div>
</template>

<script>
	export default{
		name:'Modify',
		props:['bookid'],
		data(){
			return{
				id:-1,
				name:'',
				isbn:'',
				author:'',
				price:0,
				quantity:0,
				description:'',
				image:''
			}
		},
		mounted(){
			this.id = this.bookid;
			if(this.id != -1){
				this.axios.get('books',{params:{id:this.id,withImg:true}}).then((response)=>{
					this.name = response.data.name;
					this.isbn = response.data.isbn;
					this.author = response.data.author;
					this.price = response.data.price;
					this.quantity = response.data.quantity;
					this.description = response.data.description;
				});
				this.axios.get('books/image',{params:{id:this.id}}).then((response)=>{
					this.image = response.data;
				})
			}
		},
		methods:{
			onImgChange:function(event){
				let file = event.target.files[0];
				let reader = new FileReader();
				reader.readAsDataURL(file);
				reader.onloadend = ()=>{
					this.image = reader.result;
				}
			},
			submitInfo(){
				var params = new URLSearchParams();
				params.append("id",this.id);
				params.append("name",this.name);
				params.append("isbn",this.isbn);
				params.append("author",this.author);
				params.append("price",this.price);
				params.append("quantity",this.quantity);
				params.append("desp",this.description);
				params.append("img",this.image);
				this.axios.post('books',params).then(()=>{
					this.$router.push('/manage');
				});
			}
		}
	}
</script>

