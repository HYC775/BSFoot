<template>

<el-icon><HomeFilled/></el-icon>

  <el-breadcrumb separator="/">
  <el-breadcrumb-item v-for="(item,index) in breadcrumbList">
    <spen v-if="parentName && index>0">{{parentName}}&nbsp;&nbsp;/&nbsp;&nbsp;</spen>
    <spen v-if="index==breadcrumbList.length-1">{{ item.name }}</spen> 
    <spen v-else>{{ item.name }}</spen>
  </el-breadcrumb-item>

</el-breadcrumb>
</template>

<script setup>
import { ref,watch } from 'vue'
import {HomeFilled} from '@element-plus/icons-vue'
import {useRoute} from 'vue-router'
import store from "@/store";

const route=useRoute();
const breadcrumbList=ref([]);
const parentName=ref("")

const initBreadcrumbList=()=>{
    breadcrumbList.value=route.matched;
    parentName.value=route.meta.parentName;
}

watch(route,()=>{
   initBreadcrumbList();
},{deep:true,immediate:true})


</script>

<style>

</style>