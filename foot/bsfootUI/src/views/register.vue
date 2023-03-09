<template>
   <div class="register">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px" class="register-form">
        <el-form-item label="用户名" prop="username">
          <el-input
            v-model="form.username"
            :disabled="form.id == -1 ? false : 'disabled'"
          />
          <el-alert
            v-if="form.id == -1"
            title="默认初始密码：123456"
            :closable="false"
            style="line-height: 10px"
            type="success"
          >
          </el-alert>
        </el-form-item>
        <el-form-item label="手机号" prop="phonenumber">
          <el-input v-model="form.phonenumber" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="form.email" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" :rows="4" />
        </el-form-item>
          
        <el-form-item style="width:100%;">
            <el-button
                size="large"
                type="primary"
                style="width:100%;"
                @click="handleConfirm"
            >   
              <span>注册</span>
            </el-button>
          </el-form-item>

    </el-form>
    </div>
  </template>
  
  <script setup>
  import { defineEmits, defineProps, ref, watch } from "vue";
  import requestUtil, { getServerUrl } from "@/util/request";
  import { ElMessage } from "element-plus";
 
  const form = ref({
    id: -1,
    username: "",
    password: "123456",
    status: "0",
    phonenumber: "",
    email: "",
    remark: "",
  });
  const checkUsername = async (rule, value, callback) => {
    if (form.value.id == -1) {
      const res = await requestUtil.post("register/checkUserName", {
        username: form.value.username,
      });
      if (res.data.code == 500) {
        callback(new Error("用户名已存在！"));
      } else {
        callback();
      }
    } else {
      callback();
    }
  };
  const rules = ref({
    username: [
      { required: true, message: "请输入用户名" },
      { required: true, validator: checkUsername, trigger: "blur" },
    ],
    email: [
      { required: true, message: "邮箱地址不能为空", trigger: "blur" },
      {
        type: "email",
        message: "请输入正确的邮箱地址",
        trigger: ["blur", "change"],
      },
    ],
    phonenumber: [
      { required: true, message: "手机号码不能为空", trigger: "blur" },
      {
        pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
        message: "请输入正确的手机号码",
        trigger: "blur",
      },
    ],
  });
  const formRef = ref(null);
  
  watch(
     ()=>{
        form.value = {
          id: -1,
          username: "",
          password: "123456",
          status: "0",
          phonenumber: "",
          email: "",
          remark: "",
        };
      }
    
  );
 
  
  const handleConfirm = () => {
    formRef.value.validate(async (valid) => {
      if (valid) {
        let result = await requestUtil.post("register/save", form.value);
        let data = result.data;
        console.log(data);
        if (data.code == 200) {
          ElMessage.success("注册成功！");
          formRef.value.resetFields();
          emits("initUserList");
          handleClose();
        } else {
          ElMessage.error(data.message);
        }
      } else {
        console.log("fail");
      }
    });
  };
  </script>
  
  <style >
.register {
      display: flex;
      justify-content: center;
      align-items: center;
      height: 100%;
      background-image: url("../assets/images/login-background.jpg");
      background-size: cover;
    }
    .register-form {
      border-radius: 6px;
      background: #ffffff;
      width: 400px;
      padding: 25px 25px 5px 5px;
    }

    .footer{
      height: 40px;
      line-height: 40px;
      position: fixed;
      bottom: 0;
      width: 100%;
      text-align: center;
      color: #fff;
      font-family: Arial;
      font-size: 12px;
      letter-spacing: 1px;

    }
  </style>