<template>
  <el-form
   ref="formRef" 
       :model="form" 
       label-width="100px" 
       style="text-align: center;padding-bottom:10px" 
   > 
    <el-upload
         :headers="headers" 
         class="avatar-uploader" 
         :action="getServerUrl()+'foot/uploadImage'" 
         :show-file-list="false" 
         :on-success="handleAvatarSuccess" 
         :before-upload="beforeAvatarUpload" 
     > 
       <img v-if="imageUrl" :src="imageUrl" class="avatar" /> 
       <el-icon v-else class="avatar-uploader-icon"><Plus /> </el-icon>
      </el-upload>
  
       <el-button @click="handleConfirm">保存图片</el-button>

   
    </el-form>

</template>

<script setup>
import {defineProps, ref,watch} from "vue";
import requestUtil,{getServerUrl} from "@/util/request";
import { ElMessage } from 'element-plus'
import { Plus } from '@element-plus/icons-vue'
import store from "@/store";

const props=defineProps(
   {
      foot:{
        type:Object,
        default:()=>{},
        required:true

     }
   }
)

const headers=ref({
  token:store.getters.GET_TOKEN

})

const form=ref({
  imgs:""
})

const formRef=ref(null);
const imageUrl=ref("")

form.value=props.foot;

imageUrl.value=getServerUrl()+'image/footimgs/'+form.value.imgs;


const handleAvatarSuccess=(res)=>{
  imageUrl.value=getServerUrl()+res.data.src

  form.value.imgs=res.data.title;
}

const beforeAvatarUpload = (file) => {
  const isJPG = file.type === 'image/jpeg'

  const isLt2M = file.size / 1024 / 1024 < 2

  if (!isJPG) {
    ElMessage.error('图片必须是jpg格式')
 }
  if (!isLt2M) {
    ElMessage.error('图片大小不能超过2M!')
 }
  return isJPG && isLt2M

}
  const handleConfirm=async()=>{
          let result=await requestUtil.post("foot/updateAvatar",form.value);
          let data=result.data;
          if(data.code==200){
            ElMessage.success("更换头像成功，重新登录后生效")
            store.commit("SET_USERINFO",form.value)
         }else{
            ElMessage.error(data.message);
         }
 } 

 
/*watch(props.foot, (newdata, olddata) => {
  console.log("newName", newdata);
});*/

watch(
  ()=>{
    let abcd = props.foot;
    form.value=props.foot
    console.log("abcd",abcd.id)
    console.log("footList",form.value.imgs);
    console.log("footList",imageUrl.value);
    imageUrl.value=getServerUrl()+'image/footimgs/'+form.value.imgs;
    
  }

);

</script>

<style lang="scss" scoped>
 
 .avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}

.avatar {
  width: 120px;
  height: 120px;
  display: block;
}     


</style>