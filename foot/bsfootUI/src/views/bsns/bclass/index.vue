<template>
  <div class="app-container">
    <el-row :gutter="20" class="header">
    
      <el-button type="success" :icon="DocumentAdd" @click="handleDialogValue()"
        >新增</el-button
      >

      <el-popconfirm
        title="您确定批量删除这些记录吗？"
        @confirm="handleDelete(null)"
      >
        <template #reference>
          <el-button type="danger" :disabled="delBtnStatus" :icon="Delete"
            >批量删除</el-button
          >
        </template>
      </el-popconfirm>
    </el-row>

    <el-table
      :data="tableData"
      stripe
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" />

      <el-table-column prop="name" label="分类名" width="100" align="center" />

      
      <el-table-column
        prop="action"
        label="操作"
        width="400"
        fixed="right"
        align="center"
      >
        <template v-slot="scope">
          <el-button
            v-if="scope.row.code != 'admin'"
            type="primary"
            :icon="Edit"
            @click="handleDialogValue(scope.row.id)"
            >修改</el-button
          >
          <el-popconfirm
            v-if="scope.row.code != 'admin'"
            title="您确定要删除这条记录吗？"
            @confirm="handleDelete(scope.row.id)"
          >
            <template #reference>
              <el-button type="danger" :icon="Delete" />
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
  </div>
  <!--使用新增和修改组件--->
  <Dialog
    v-model="dialogVisible"
    :dialogVisible="dialogVisible"
    :id="id"
    :dialogTitle="dialogTitle"
    @initDaleiList="initDaleiList"
  />

</template>

<script setup>
import requestUtil, { getServerUrl } from "@/util/request";
import { Delete, Edit, Tools } from "@element-plus/icons-vue";
import { ref } from "vue";
import Dialog from "./components/dialog";
import { ElMessage, ElMessageBox } from "element-plus";


//分配角色
const sysDaleiList = ref([]);
const menuDialogVisible = ref(false);

const handleMenuDialogValue = (daleiId) => {
  console.log("daleiId=" + daleiId);
  id.value = daleiId;
  menuDialogVisible.value = true;
  console.log(JSON.stringify(sysDaleiList));
};

//批量删除默认不可用
const delBtnStatus = ref(true);
const multipleSelection = ref([]);
const handleSelectionChange = (selection) => {
  console.log("勾选了");
  console.log(selection);
  //获取勾选行的数据
  multipleSelection.value = selection;
  delBtnStatus.value = selection.length == 0;
};

const handleDelete = async (id) => {
  var ids = [];

  if (id) {
    ids.push(id);
  } else {
    multipleSelection.value.forEach((row) => {
      ids.push(row.id);
    });
  }
  const res = await requestUtil.post("foot/delete", ids);
  if (res.data.code == 200) {
    ElMessage({
      type: "success",
      message: "删除成功!",
    });
    initDaleiList();
  } else {
    ElMessage({
      type: "error",
      message: res.data.message,
    });
  }
};

const dialogVisible = ref(false);
const dialogTitle = ref("");
const id = ref(-1);
const handleDialogValue = (daleiId) => {
  if (daleiId) {
    id.value = daleiId;
    dialogTitle.value = "分类修改";
  } else {
    id.value = -1;
    dialogTitle.value = "分类添加";
  }
  dialogVisible.value = true;
};

const tableData = ref([]);
const total = ref(0);
const queryForm = ref({
  query: "",
  pageNum: 1,
  pageSize: 10,
});
//分页查询用户集合
const initDaleiList = async () => {
  const res = await requestUtil.get("dalei/listAll", queryForm.value);
  tableData.value = res.data.data.daleiList;
  total.value = res.data.data.total;
};
initDaleiList();

const handleSizeChange = (pageSize) => {
  queryForm.value.pageNum = 1;
  queryForm.value.pageSize = pageSize;
  initDaleiList();
};
const handleCurrentChange = (pageNum) => {
  queryForm.value.pageNum = pageNum;
  initDaleiList();
};



</script>

<style lang="scss" scoped>
.header {
  padding-bottom: 16px;
  box-sizing: border-box;
}
.el-pagination {
  float: right;
  padding: 20px;
  box-sizing: border-box;
}
::v-deep th.el-table__cell {
  word-break: break-word;
  background-color: #f8f8f9 !important;
  color: #515a6e;
  height: 40px;
  font-size: 13px;
}
.el-tag--small {
  margin-left: 5px;
}
</style>