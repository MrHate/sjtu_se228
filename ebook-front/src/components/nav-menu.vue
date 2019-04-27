<template>
<div>
	<b-list-group class="nav nav-pills nav-stacked">
		<b-list-group-item to="/">Home</b-list-group-item>
		<b-list-group-item to="/all">All Books</b-list-group-item>
		<b-list-group-item v-if="isAdmin" to="/manage">Manage</b-list-group-item>
		<b-list-group-item v-if="!isAdmin" to="/cart">Cart</b-list-group-item>
		<b-list-group-item to="/orders">Orders</b-list-group-item>
	</b-list-group>
</div>
</template>

<script>
export default {
	name: 'NavMenu',
	data:function(){
		return{
			isAdmin: false
		}
	},
	mounted:function(){
		//this.axios.get('users/current').then((response)=>{
		//	this.isAdmin = response.data == 'admin';
		//})
	}
}
</script>

