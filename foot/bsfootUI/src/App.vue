<template>

  <router-view/>
  <!--<el-button type="primary" @click="handleLogin">测试登录</el-button>

  <el-button type="danger" @click="handleUserList">测试获取用户请求</el-button>-->
</template>
<script setup>
 import { ref,watch } from 'vue'
 import { useRoute,useRouter } from 'vue-router'
 import store from "@/store";


 const route = useRoute();
 const router = useRouter();

 //白名单
 const whitePath = ['/login','index','/']


 watch(route,(to,feom)=>{
  if(whitePath.indexOf(to.path)===-1){
    let obj = {
      name: to.name,
      path: to.path
    }
    store.commit("ADD_TABS",obj)
  }
 },{depp:true,immediate:true})


 

</script>
<style>
html,body,#app{
height: 100%;
}

.app-container{
padding:20px
}
</style>
