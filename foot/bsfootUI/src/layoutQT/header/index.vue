<template>
    <div class="navbar">
      <Breadcrumb/>
    <div class="sousuo">
      <el-input
      v-model="input2"
      class="w-50 m-2"
      placeholder="请输入美食名称或作者..."
      :suffix-icon="Search" 
      change="mssousuo"
    /> 
  </div>
    
       <div class="navbar-right">
               <Avatar/>
      </div>

      </div>
     <div class="navigation">
        <ul>
          <li>联系客服</li>
          <li><router-link to="/newfoot">个人专属</router-link></li>
          <li><router-link to="/about">新闻资讯</router-link></li>
          <li><router-link to="/gyWe">关于我们</router-link></li>
          <li><router-link to="/newfoot">最新菜品</router-link></li>
          <li><router-link to="/index">首页</router-link></li>
        </ul>
  </div> 
 
</template>

<script setup >
import { ref } from 'vue'
import { Search,Star } from '@element-plus/icons-vue'
import Breadcrumb from './components/breadcrumb.vue'
import Avatar from './components/avatar.vue'

const input2 = ref('')

</script>


<style lang="scss" scoped>
 .navbar {
    width: 100%;
    height: 60px;
    overflow: hidden;
    background-color: #F5F5F5;
    box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
    padding: 0 16px;
    display: flex;
    align-items: center;
    box-sizing: border-box;
    position: relative;
 .navbar-right {
        flex: 1;
        display: flex;
        align-items: center;
        justify-content: flex-end;
        :deep(.navbar-item) {
            display: inline-block;
            margin-left: 18px;
            font-size: 22px;
            color: #5a5e66;
            box-sizing: border-box;
            cursor: pointer;
       }
    }
}

.grid-content ep-bg-purple{

}


.sousuo{
  margin-left: 40%;
}


.row {
  padding: 20px 0;
}

.el-col{

}

.navigation{
  background-color: #959191;
  height: 50px;
  width: 100%;
  font-size: 18px;
  margin-bottom:30px;
}
.navigation ul li{
  text-decoration: none;
  float:right;
  margin-right:50px;
  margin-top:20px;
  color: #ece8e8;
  
}

a{
  text-decoration: none;
  color: white;
}
</style>
