<template>
    <div class="a">
     <img :src="getServerUrl() + 'image/footimgs/' + data.imgs" alt="food image" class="img" />
      <table>
        <tr>
          <td><p>名称</p></td>
          <td><h1>{{ data.footname }}</h1></td>
      </tr>
       <tr>
          <td><p>美食描述</p></td>
          <td>{{ data.content }}</td>
      </tr>
      <tr>
          <td><h2>材料清单</h2></td>
          <td><p>{{ data.cailiao }}</p></td>
      </tr>
      <tr>
          <td><h2>制作步骤</h2></td>
          <td><p>{{ data.zhizuofangfa }}</p> </td>
      </tr>
     </table>
  </div>
  <div class="pimglun">
      <Pinglun :foot="foot"/>
  </div>
 
  </template>
  
  <script setup>
   import { ref } from 'vue'
   import { useStore } from 'vuex'
   import { computed } from 'vue'
   import requestUtil, { getServerUrl } from "@/util/request";
   import Pinglun from "./pinglun.vue"

    const foot=ref([]);
    const store = useStore()
    const data = computed(() => store.state.data);
    console.log("data===="+Object.keys(data));
    foot.value=data;

    
  </script>
  
  <style>

.a{
  margin: 0 auto;
  text-align: center;
}



table,tr,td{
  padding: 10px;
  border: 1px solid #ccc;
  margin: 0 auto;
  text-align: center;
}

table{
  width: 54%;
}

.pimglun{
  widows: 800px;
}
.img{
  margin-bottom: 20px;
  widows: 500px;
  height: 400px;
}
</style>