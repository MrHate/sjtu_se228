<template>
<div id="app">
	<HeaderContents/>
	<br>
	<b-container fluid>
		<b-card>
		<router-view></router-view>			
		</b-card>
	</b-container>
</div>
</template>

<script>
import HeaderContents from './components/header-contents.vue'

export default {
	name: 'app',
	components: {
		HeaderContents,
	}
}
</script>

<style>
#app {
	font-family: 'Avenir', Helvetica, Arial, sans-serif;
	-webkit-font-smoothing: antialiased;
	-moz-osx-font-smoothing: grayscale;
	text-align: center;
	color: #2c3e50;
}
</style>
