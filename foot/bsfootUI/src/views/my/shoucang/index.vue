<template>
    <div class="app-container">
      <el-row :gutter="20" class="header">
        <el-col :span="7">
          <el-input
            placeholder="请输入菜名或者作者..."
            v-model="queryForm.query"
            clearable
          ></el-input>
        </el-col>
        <el-button type="primary" :icon="Search" @click="initFootList"
          >搜索</el-button
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
       
        <el-table-column prop="imgs" label="图片" width="80" align="center">
          <template v-slot="scope">
            <img
              :src="getServerUrl() + 'image/footimgs/' + scope.row.imgs"
              width="50"
              height="50"
            />
          </template>
        </el-table-column>
          
          <el-table-column/>
  
        <el-table-column
          prop="footname"
          label="美食名称"
          width="200"
          align="center"
        />
  
        <el-table-column
          prop="datatime"
          label="创建时间"
          width="200"
          align="center"
        />
  
        <el-table-column prop="username" label="作者" align="center"/>
  
        <el-table-column
          prop="action"
          label="操作"
          width="400"
          fixed="right"
          align="center"
        >
          <template v-slot="scope">
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
      <el-pagination
        v-model:currentPage="queryForm.pageNum"
        v-model:page-size="queryForm.pageSize"
        :page-sizes="[10, 20, 30, 40]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
    <!--使用新增和修改组件--->
    

  </template>
  
  <script setup>
  import requestUtil, { getServerUrl } from "@/util/request";
  import { Delete, Edit, Tools } from "@element-plus/icons-vue";
  import { ref } from "vue";
  import { ElMessage, ElMessageBox } from "element-plus";
  import store from '@/store'
  
  

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
    const res = await requestUtil.post("shoucang/delete", ids);
    if (res.data.code == 200) {
      ElMessage({
        type: "success",
        message: "删除成功!",
      });
      initFootList();
    } else {
      ElMessage({
        type: "error",
        message: res.data.message,
      });
    }
  };
  
 
  

  const currentUser = ref(store.getters.GET_USERINFO);
  
  const tableData = ref([]);
  const total = ref(0);
  const queryForm = ref({
    query: "",
    pageNum: 1,
    pageSize: 10,
  });
  //分页查询收藏集合
  const initFootList = async () => {
    const res = await requestUtil.post("shoucang/list/"+currentUser.value.id, queryForm.value);
    tableData.value = res.data.data.footList;
    total.value = res.data.data.total;
  };
  initFootList();
  
  const handleSizeChange = (pageSize) => {
    queryForm.value.pageNum = 1;
    queryForm.value.pageSize = pageSize;
    initFootList();
  };
  const handleCurrentChange = (pageNum) => {
    queryForm.value.pageNum = pageNum;
    initFootList();
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