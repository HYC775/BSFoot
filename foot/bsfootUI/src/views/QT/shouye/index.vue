<template>
  <Lunbotu/>
  <el-divider/>
<el-row :gutter="20" >
  <el-col :span="20" style="margin: 0 auto; text-align: center;">
     <el-card>
       <spen class="newcp">最新菜品</spen>
       <div style="height: 20px;"></div>
        <el-tabs>
        <el-tab-pane label="秘制汤品" name="a">  
           <Card :footList8="footList8"/>
        </el-tab-pane>
        <el-tab-pane label="特色美食" name="b">
          <Card9 :footList9="footList9"/>
        </el-tab-pane>
        <el-tab-pane label="精选蒸菜" name="c">
          <Card10 :footList10="footList10"/>
        </el-tab-pane>
        <el-tab-pane label="寿司大卷" name="d">
          <Card11 :footList11="footList11"/>
        </el-tab-pane>
        </el-tabs>
     </el-card>
</el-col>
</el-row>
<el-divider />
<el-card class="box-card">
    <template #header>
      <div class="card-header">
        <span>Card name</span>
        <el-button class="button" text>Operation button</el-button>
      </div>
    </template>
    <Card/>
    <div v-for="o in 4" :key="o" class="text item">{{ 'List item ' + o }}</div>
  </el-card>
</template>

<script setup>
import Lunbotu from "./companies/lunbotu.vue"
import Card from "./companies/card.vue"
import Card9 from "./companies/card9.vue"
import Card10 from "./companies/card10.vue"
import Card11 from "./companies/card11.vue"
import { ref } from 'vue'
import requestUtil, { getServerUrl } from "@/util/request";


const footList8=ref([]);
const footList9=ref([]);
const footList10=ref([]);
const footList11=ref([]);

const tableData = ref([]);
const total = ref(0);
const queryForm = ref({
  query: "",
  pageNum: 1,
  pageSize: 10,
});
//分页查询用户集合
const initFootList = async () => {
  const res = await requestUtil.post("foot/list", queryForm.value);
  tableData.value = res.data.data.footList;
  total.value = res.data.data.total;
};
initFootList();


const footList_8 = async () => {
  const res = await requestUtil.post("foot/dalei/"+8);
  footList8.value = res.data.data.footList;
  total.value = res.data.data.total;
  console.log("footList_81"+footList8.value);
};
footList_8();
 console.log("footList_82"+footList8.value);
 
const footList_9 = async () => {
  const res = await requestUtil.post("foot/dalei/"+9);
  footList9.value = res.data.data.footList;
  total.value = res.data.data.total;
};
footList_9();

const footList_10 = async () => {
  const res = await requestUtil.post("foot/dalei/"+10);
  footList10.value = res.data.data.footList;
  total.value = res.data.data.total;
};
footList_10();

const footList_11 = async () => {
  const res = await requestUtil.post("foot/dalei/"+11);
  footList11.value = res.data.data.footList;
  total.value = res.data.data.total;
};
footList_11();




</script>

<style>
.newcp{
  font-size: 50px;
  font-weight: 700;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  color:#eebe77;
  margin-bottom: 50px;
}

 .card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.text {
  font-size: 14px;
}

.item {
  margin-bottom: 18px;
  float: left;
  }

 
</style>