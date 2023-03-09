<template>
  <el-dialog
    model-value="roleDialogVisible"
    title="分配角色"
    width="30%"
    @close="handleClose"
  >
    <el-form ref="formRef" :model="form" label-width="100px">
      <el-checkbox-group v-model="form.checkedRoles">
        <el-checkbox
          v-for="role in form.roleList"
          :id="role.id"
          :key="role.id"
          :label="role.id"
          name="checkedRoles"
          >{{ role.name }}</el-checkbox>
        </el-checkbox-group>
      </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="handleConfirm">确认</el-button>
        <el-button @click="handleClose">取消</el-button> </span>
      </template>
  </el-dialog>
</template>
<script setup>
import { defineEmits, defineProps, ref, watch } from "vue";
import requestUtil, { getServerUrl } from "@/util/request";
import { ElMessage } from "element-plus";
const props = defineProps({
  id: {
    type: Number,
    default: -1,
    required: true,
  },
  roleDialogVisible: {
    type: Boolean,
    default: false,
    required: true,
  },
  sysRoleList: {
    type: Array,
    default: [],
    required: true,
  },
});

const form = ref({
  id: -1,
  roleList: [],
  checkedRoles: [],
});

const formRef = ref(null);
//查询权限列表
const initFormData = async (id) => {
  const res = await requestUtil.get("role/listAll");
  form.value.roleList = res.data.data.roleList;
  form.value.id = id;
};

//监视器，监视用户原有的角色进行回显
watch(
  () => props.roleDialogVisible,
  () => {
    let id = props.id;
    console.log("id=**" + id);
    if (id != -1) {
      form.value.checkedRoles = [];
      props.sysRoleList.forEach((item) => {
        console.log(item.id)
        form.value.checkedRoles.push(item.id);
      });
      initFormData(id);
    }
  }
);


const emits = defineEmits(["update:modelValue", "initUserList"]);
const handleClose = () => {
  emits("update:modelValue", false);
};

//确认修改用户权限
const handleConfirm = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      let result = await requestUtil.post("user/grantRole/" + form.value.id,form.value.checkedRoles);
      let data = result.data;
      if (data.code == 200) {
        ElMessage.success("执行成功！");
        emits("initUserList");
        handleClose();
      } else {
        ElMessage.error(data.massge);
      }
    } else {
      console.log("fail");
    }
  });
};
</script>
<style scoped>
</style>