//Vue.component('recommended', {
//  props: ['entry'],
//  template: '<li class="entry_item"><img :src={entry.text} alt="book" /></li>'
//})

var explore_app = new Vue({
  el: '#explore_app',
  data: {
    recommendedList: [
      { id: 0, path: 'images/b1.jpeg',name:'a' },
      { id: 1, path: 'images/b2.jpeg',name:'b' },
      { id: 2, path: 'images/b3.jpeg',name:'c' },
      { id: 3, path: 'images/b3.jpeg',name:'c' },
      { id: 4, path: 'images/b3.jpeg',name:'c' },
      { id: 5, path: 'images/b3.jpeg',name:'c' },
      { id: 6, path: 'images/b3.jpeg',name:'c' },
      { id: 7, path: 'images/b3.jpeg',name:'c' }
    ]
  }
})

