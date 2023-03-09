<template>
    <el-carousel :interval="4000" type="card" height="350px" style="margin-top: 100px;">
      <el-carousel-item   v-for="item in footData" :key="item">
       <img
           :src="getServerUrl() + 'image/footimgs/' + item.imgs" style="width: 100%;"/>
        <!-- <h3 text="2xl" justify="center">{{ item }}</h3>-->
      </el-carousel-item>
    </el-carousel>
  </template>
  
 <script setup>
import requestUtil, { getServerUrl } from "@/util/request";
import { ref } from "vue";
import { ElMessage, ElMessageBox } from "element-plus";


 let  footData =ref([]);
const initFootList = async () => {
 
  const res = await requestUtil.post("foot/listAll");
  footData.value= res.data.data.footList;
  console.log("footData"+Object.keys(footData.value));
};
initFootList();
console.log("footData22"+footData.value);

</script>

  <style scoped>
  .el-carousel__item h3 {
    color: #475669;
    opacity: 0.75;
    line-height: 200px;
    margin: 0;
    text-align: center;
  }
  
  .el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
  }
  
  .el-carousel__item:nth-child(2n + 1) {
    background-color: #d3dce6;
  }
  </style>
  