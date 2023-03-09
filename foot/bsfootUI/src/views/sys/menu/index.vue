<template>
  <div class="app-container">
    <el-row :gutter="20" class="header">

      <el-button type="success" :icon="DocumentAdd" @click="handleDialogValue()">新增</el-button>
    </el-row>

    <el-table 
    :data="tableData"
    row-key="id" 
    stripe style="width:100%; margin-bottom: 20px" 
    broder default-expand-all
    :tree-props="{ children: 'children', hasChildren: 'hasChildren' }">

      <el-table-column prop="name" label="菜单名称" align="center" width="200" />
      <el-table-column prop="icon" label="图标" width="70" aling="center">
        <template v-slot="scope">
          <el-icon><svg-icon :icon="scope.row.icon" /></el-icon>
        </template>
      </el-table-column>
      <el-table-column prop="orderNum" label="排序" width="70" aling="center" />
      <el-table-column prop="perms" label="权限标识" width="200"  aling="center"/>
      <el-table-column prop="path" label="组件路径" width="180" aling="center" />
      <el-table-column prop="menuType" label="菜单类型" width="120" aling="center">
        <template v-slot="scope">
          <el-tag size="small" v-if="scope.row.menuType === 'M'" type="danger" effect="dark">目录</el-tag>
          <el-tag size="small" v-else-if="scope.row.menuType === 'C'" type="success" effect="dark">菜单</el-tag>
          <el-tag size="small" v-else-if="scope.row.menuType === 'F'" type="warning" effect="dark">按钮</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" aling="center" />

      <el-table-column prop="action" label="操作" width="150" fixed="right" aling="center">
        <template v-slot="scope">
          <el-button type="primary" :icon="Edit" @click="handleDialogValue(scope.row.id)" />
          <el-popconfirm title="您确定要删除这条记录吗？" @confirm="handleDelete(scope.row.id)">
            <template #reference>
              <el-button type="danger" :icon="Delete" />
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
  </div>
  <!--使用新增和修改组件--->
  <Dialog v-model="dialogVisible" :tableData="tableData" :dialogVisible="dialogVisible" :id="id" :dialogTitle="dialogTitle" @initMenuList="initMenuList"/>


</template>

<script setup>
import requestUtil, { getServerUrl } from "@/util/request";
import { Delete, Edit, Tools } from "@element-plus/icons-vue";
import { ref } from "vue";
import Dialog from "./components/dialog";
import { ElMessage, ElMessageBox } from "element-plus";

const dialogVisible = ref(false);
const dialogTitle = ref("");
const id = ref(-1);

const handleDelete=async (id)=>{
const res=await requestUtil.get("menu/delete/"+id)
    if(res.data.code==200){
    ElMessage({
    type: 'success',
    message: '执行成功!'
 })
    initMenuList();
  }else{
   ElMessage({
   type: 'error',
   message: res.data.message,
    })
  }
}



const handleDialogValue = (menuId) => {
  if (menuId) {
    id.value = menuId;
    dialogTitle.value = "菜单修改";
  } else {
    id.value = -1;
    dialogTitle.value = "菜单添加";
  }
  dialogVisible.value = true;
};

const tableData = ref([]);
const total = ref(0);

//查询权限集合
const initMenuList = async () => {
  const res = await requestUtil.get("menu/treeList");
  tableData.value = res.data.data.treeMenu;
};
initMenuList();

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