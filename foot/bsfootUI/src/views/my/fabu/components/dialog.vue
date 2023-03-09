<template>
  <el-dialog
    v-model="dialogVisible"
    :title="dialogTitle"
    width="30%"
    @close="handleClose"
  >
 

    <el-form ref="formRef" :model="form.value" :rules="rules" label-width="100px">
         <avatar :foot="form"></avatar>
      <el-form-item label="美食名称" prop="footname">
        <el-input
          v-model="form.footname"  
        />
      </el-form-item>

      <el-form-item label="美食分类" >
        <div>
          <el-radio-group v-model="classfrom.checkedDalei0" class="ml-4">
                    <el-radio 
                    v-for="role in classfrom.daleiList"
                    :id="role.id"
                    :key="role.id"
                    :label="role.id"
                    name="checkedDalei0"
                    label="1" size="large">{{ role.name }}</el-radio>
            </el-radio-group>
       </div>
      </el-form-item>

      <el-form-item label="美食细类" >
         <div>
          <el-checkbox-group v-model="classfrom.checkedXiaoleis">
          <el-checkbox
          v-for="role in classfrom.xileiList"
          :id="role.id"
          :key="role.id"
          :label="role.id"
          name="checkedXiaoleis"
          >{{ role.name }}</el-checkbox>
        </el-checkbox-group>
       </div>
      </el-form-item>
      
      <el-form-item label="描述" prop="content">
        <el-input v-model="form.content" type="textarea" :rows="4" />
      </el-form-item>

      <el-form-item label="材料清单" prop="cailiao">
        <el-input v-model="form.cailiao" type="textarea" :rows="4" />
      </el-form-item>
      
      <el-form-item label="制作步骤" prop="zhizuofangfa">
        <el-input v-model="form.zhizuofangfa" type="textarea" :rows="4" />
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

<script  setup>
import { defineEmits, defineProps, getCurrentInstance, ref, watch} from "vue";
import requestUtil, { getServerUrl } from "@/util/request";
import { ElMessage } from "element-plus";
import avatar from "@/views/bsns/foots/components/avatar.vue"

const newdata = new Date().getTime();

const propsconfig0 = {
  value: 'name',
  label: 'name',
}

const propsconfig = {
  value: 'name',
  label: 'name',
  multiple: true,
  emitPath: false
}


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
  xiaoleis:{
    type: Array,
    default: [],
    required: true,
  },
  foots:{
    type: Array,
    default: [],
    required: true,
  }
});


const form = ref({
  id: -1,
  footname: "",
  daleiId: -1,
  dalei:{},
  imgs:"",
  xiaoleiids:[],
  fxiaoleiList:[],
  content: "",
  guid:"",
 
});


const selectedClass= ref({
  selectedDalei:"",
  selectedXiaolei:[],
})


//分类列表
const classfrom = ref({
  daleiList: [],
  xileiList: [],
  checkedDalei0:-1,
  checkedXiaoleis:[],
})
//查询分类列表
const initFormDaleiData = async (id) => {
  const res = await requestUtil.get("dalei/listAll");
  classfrom.value.daleiList = res.data.data.daleiList;
  classfrom.value.id = id;
};

//查询细类列表
const initFormXileiData = async (id) => {
  const res = await requestUtil.get("xilei/listAll");
  classfrom.value.xileiList = res.data.data.xileiList;
  classfrom.value.id = id;
};




const formRef = ref(null);
const initFormData = async (id) => {
  const res = await requestUtil.get("foot/" + id);
  form.value = res.data.data.sysFoot;
  console.log("aaa"+form.value.fxiaoleiList);
  
};
 



watch(
  () => props.dialogVisible,
  () => {
    let id = props.id;
    initFormDaleiData(id);
    initFormXileiData(id);
    console.log("id=" + id);
    if (id != -1) {
      initFormData(id); 
      classfrom.value.checkedXiaoleis = [];
      classfrom.value.checkedDalei0=-1;
      
      props.xiaoleis.forEach((item) => {
        console.log(item.id)
        classfrom.value.checkedXiaoleis.push(item.xiaoleiId);
      });
      classfrom.value.checkedDalei0=props.foots.daleiId;
      initFormData(id); 
    
    } else {
      classfrom.value.checkedXiaoleis = [];
      classfrom.value.checkedDalei0=-1;
      form.value = {
        id: -1,
        footname: "",
        daleiId:"",
        dalei:{},
        imgs:"",
        guid:"",
        xiaoleiids:[],
        fxioaleiList: [],
        content: "",
      };
    }
  }
);
const emits = defineEmits(["update:modelValue", "initFootList"]);
const handleClose = () => {
  emits("update:modelValue", false);
};


const {proxy} = getCurrentInstance();



const handleConfirm =async () => {
    console.log(2)
      console.log(3)
      form.value.daleiId=classfrom.value.checkedDalei0;
      form.value.xiaoleiids=classfrom.value.checkedXiaoleis;
      form.value.guid = guid();
        let result = await requestUtil.post("foot/save",form.value);
        let data = result.data;
      console.log(data);
      if (data.code == 200) {
        ElMessage.success("执行成功!");
        formRef.value.resetFields();
        emits("initFootList");
        handleClose();
      } else {
        ElMessage.error(data.message);
      }
    }
  


//生成唯一标识码
function guid() {
return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
    var r = Math.random() * 16 | 0,
        v = c == 'x' ? r : (r & 0x3 | 0x8);            
    return v.toString(16);
});
}

</script>

<style >
</style>