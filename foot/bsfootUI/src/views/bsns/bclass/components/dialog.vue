<template>
  <el-dialog
    v-model="dialogVisible"
    :title="dialogTitle"
    width="30%"
    @close="handleClose"
  >
    <el-form ref="formRef" :model="form" :rules="rules" label-width="100px">
      <el-form-item label="分类名称" prop="name">
        <el-input
          v-model="form.name"  
        />
      </el-form-item>
     
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button type="primary" @click="handleConfirm">确认</el-button>
        <el-button @click="handleClose">取消</el-button>
      </span>
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
  dialogTitle: {
    type: String,
    default: "",
    required: true,
  },
  dialogVisible: {
    type: Boolean,
    default: false,
    required: true,
  },
});
const form = ref({
  id: -1,
  name: "",
  code: "",
  remark: "",
});

const rules = ref({
  name: [
    { required: true, message: "请输入分类名称" }
  ]  
});

const formRef = ref(null);
const initFormData = async (id) => {
  const res = await requestUtil.get("dalei/" + id);
  form.value = res.data.data.dalei;
};

watch(
  () => props.dialogVisible,
  () => {
    let id = props.id;
    console.log("id=" + id);
    if (id != -1) {
      initFormData(id);
    } else {
      form.value = {
        id: -1,
        name: "",
        code: "",
        remark: "",
      };
    }
  }
);
const emits = defineEmits(["update:modelValue", "initDaleiList"]);
const handleClose = () => {
  emits("update:modelValue", false);
};

const handleConfirm = () => {
  formRef.value.validate(async (valid) => {
    if (valid) {
      let result = await requestUtil.post("dalei/save", form.value);
      let data = result.data;
      console.log(data);
      if (data.code == 200) {
        ElMessage.success("执行成功！");
        formRef.value.resetFields();
        emits("initDaleiList");
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
</style>