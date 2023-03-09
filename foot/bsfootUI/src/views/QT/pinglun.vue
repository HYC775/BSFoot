<template>
  <div style="width: 800px; margin: 0 auto;"> 
    
    <div class="comment-reply-form">
        <textarea v-model="replycontent" style="border: 1px solid #ccc;" class="text" rows="3"></textarea>
        <el-button type="primary"  @click="submit()" style="margin-left: 20px;">提交评论</el-button>
      </div>
      <div style="border: 1px solid #ccc; padding:15px 15px 15px 15px ;">
  <h3>评论列表</h3>
  <comment v-for="comment in pinglun" :key="comment.id" :comment="comment"></comment>
  </div>
  </div>
</template>
  
  <script setup>
import { ref, defineProps } from "vue";
import { useStore } from "vuex";
import requestUtil, { getServerUrl } from "@/util/request";
import comment from "./comment.vue"
import store from '@/store'

const currentDate = ref(new Date());
const replycontent=ref();


const props = defineProps({
  foot: {
    type: Array,
    default: [],
    required: true,
  },
  pinglun: Object
});

console.log("props.foot.id" + props.foot.id);

const pinglun = ref({
  nerong: "",
  patentid: "-1",
  uid: -1,
  username: "",
  userimg: "",
  createdate: "",
  children: [],
});

const getComments = async () => {
  const res = await requestUtil.post("/pinglun/getPinglunAll",props.foot.value);
  pinglun.value = res.data.data;
};

getComments();

 const currentUser = ref(store.getters.GET_USERINFO);
        const pinglun1 = ref(
            {
             id:-1,
             username: currentUser.value.username,
             userimg: currentUser.value.avatar,
             uid: currentUser.value.id,
             fid:props.foot.value.id,
             createdate: currentDate,
             neirong: replycontent,
             parentid: 0,
            }
        );

      const  submit = ()=> { 
        const response = async () => {
          const pl = await requestUtil.post("/pinglun/save", pinglun1.value)
        }
        response();
        replycontent.value= "";
        getComments();
      }



</script>

<style scoped>

.comment-reply-form {
  margin-top: 0.5em;
  margin-bottom: 0.5em;
  width: 100%;
}

.comment-replies {
  margin-left: 2em;
}

.text{
  width: 80%;
}
</style>