<template>
    <div class="comment" :class="{ 'comment-collapsed': isCollapsed }">
      <div class="comment-header" @click="isCollapsed = !isCollapsed">
        <div class="comment-user">
         <img :src="userimgurl+comment.userimg" class="userimg" alt="User avatar"/>
          <div class="comment-username">{{ comment.username }}</div>
        </div>
        <div class="comment-timestamp">{{ comment.createdate }}</div>
      </div>
      <div class="comment-content">{{ comment.neirong }}</div>
      <div class="comment-actions">
        <button @click="reply()">回复</button>
      </div>
      <div class="comment-reply-form" v-if="replying">
        <textarea v-model="replyContent" style="border: 1px solid #ccc;"></textarea>
        <el-button type="danger"  @click="cancel()">取消</el-button>
        <el-button type="primary"  @click="submit()">提交回复</el-button>
      </div>
      <div class="comment-replies">
        <comment v-for="reply in comment.children" :key="reply.id" :comment="reply"></comment>
      </div>
    </div>
  </template>
  
  <script >
  import axios from 'axios';
  import requestUtil,{getServerUrl} from "@/util/request";
  import {defineProps, ref} from "vue";
  import store from '@/store'

  export default {
    props: {
      comment: Object
    },
    data() {
      return {
        replying: false,
        replyContent: '',
        isCollapsed: false,
        userimgurl: getServerUrl() + 'image/userAvatar/'
      }
    },
    methods: {
      formatDate(timestamp) {
        // Format the timestamp however you like
      },
      reply() {
        this.replying = true;
      },
      cancel() {
        this.replying = false;
        this.replyContent = '';
      },
      async submit() {
        const currentUser = ref(store.getters.GET_USERINFO);
        const currentDate = ref(new Date());
        const pinglun = ref(
            {
             id:-1,
             username: currentUser.value.username,
             userimg: currentUser.value.avatar,
             uid: currentUser.value.id,
             fid:this.comment.fid,
             createdate: currentDate,
             neirong: this.replyContent,
             parentid: this.comment.id,
            }
        );
        const response = async () => {
          const pl = await requestUtil.post("/pinglun/save", pinglun.value)
          this.comment.children.push(pl.data.data);
        }
        response();
        this.replying = false;
        this.replyContent = '';
      }
    }
  }
  </script>
  
  <style scoped>

  .userimg{
    width: 50px;
    height: 50px;
  }
  .comment {
    margin-top: 1em;
    border: 1px solid #ccc;
    padding: 0.5em;
    transition: height 0.5s ease;
  }
  
  .comment-collapsed {
  height: 3em;
  overflow: hidden;
}

  .comment-header {
    display: flex;
    justify-content: space-between;
    cursor: pointer;
  }
  
  .comment-header:hover {
  background-color: #f0f0f0;
}

  .comment-user {
    display: flex;
    align-items: center;
  }
  
  .comment-username {
    margin-left: 0.5em;
  }
  
  .comment-timestamp {
    font-size: 0.8em;
    color: #999;
  }
  
  .comment-content {
    margin-top: 0.5em;
    margin-bottom: 0.5em;
  }
  
  .comment-actions {
    display: flex;
    justify-content: flex-end;
    margin-top: 0.5em;
  }
  
  .comment-reply-form {
    margin-top: 0.5em;
  }
  
  .comment-replies {
    margin-left: 2em;
  }
  </style>