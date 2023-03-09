<template>
    <el-row>
      <el-scrollbar>
      <div class="scrollbar-flex-content">
      <el-col
        v-for="(o, index) in props.footList8"
        :key="o"
        :span="8"
        :offset="index > 0 ? 2 : 0"
        class="scrollbar-demo-item"
        >
       <router-link to="/xiangqing" @click="sendData(o)">
        <el-card :body-style="{ padding: '0px' }">
          <img
            :src="getServerUrl() + 'image/footimgs/' + o.imgs"
            class="image"
          />
          <div style="padding: 14px">
            <span>{{o.footname}}</span>
            <div class="bottom">
              <time class="time">{{ currentDate }}</time>
              <el-button text class="button"> to="/xiangqing">美食详情</el-button> 
            </div>
          </div>
        </el-card>
     </router-link>
      </el-col> 
      </div> 
    </el-scrollbar>
    </el-row>
  </template>
  
  <script setup >
  import requestUtil, { getServerUrl } from "@/util/request";
  import { ref,defineProps } from 'vue'
  import { useStore } from 'vuex'


    const store = useStore();
    const sendData = (o) => {
      store.dispatch('updateData', o)
    }
    sendData();
  


  const currentDate = ref(new Date())
  
  const props = defineProps({
  footList8: {
    type: Array,
    default: [],
    required: true,
  }
});


  
  
  </script>
  
  <style>
  .time {
    font-size: 12px;
    color: #999;
  }
  
  .bottom {
    margin-top: 13px;
    line-height: 12px;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  
  .button {
    padding: 0;
    min-height: auto;
  }
  
  .image {
    width: 100%;
    display: block;
  }

  .scrollbar-flex-content {
     display: flex;
}
.scrollbar-demo-item {
  flex-shrink: 0;
  display: flex;
  align-items: center;
  justify-content: center;
  
  margin: 10px;
  text-align: center;
  border-radius: 4px;
  background: var(--el-color-danger-light-9);
  color: var(--el-color-danger);
}
  </style>
  