<!--
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-11-28 13:31:34
 * @LastEditors: jibl
 * @LastEditTime: 2022-11-28 15:09:58
-->
<!--  -->
<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
      <el-form-item label="项目名称" prop="projectName">
        <el-input
          v-model="queryParams.projectName"
          placeholder="请输入项目名称"
          clearable
          style="width: 240px"
        />
      </el-form-item>

      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态">
          <el-option label="正常" :value="0"></el-option>
          <el-option label="停用" :value="1"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <el-row :gutter="10" style="margin-bottom: 8px">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          @click="handleUpdate"
          >修改</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          @click="handleDelete"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          >导出</el-button
        >
      </el-col>
    </el-row>

    <el-table
      v-loading="loading"
      :data="projectList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="项目编号" prop="projectId" align="center" />
      <el-table-column
        label="项目名称"
        prop="projectName"
        align="center"
        show-overflow-tooltip
      />
      <el-table-column
        label="项目地址"
        prop="projectUrl"
        align="center"
        show-overflow-tooltip
      />
      <el-table-column label="状态" align="center" width="140">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            :active-value="0"
            :inactive-value="1"
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime">
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      class="pagination-container"
      background
      style="float: right"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pagination.pageNum"
      :page-sizes="pagination.pageSizes"
      :page-size="pagination.pageSize"
      layout="prev, pager, next, jumper, sizes,total"
      :total="pagination.total"
    ></el-pagination>
  </div>
</template>

<script>
export default {
  data() {
    return {
      queryParams: {},
      loading: false,
      projectList: [],
      pagination: {
        pageNum: 1,
        total: 0,
        pageSizes: [100, 200, 300, 400],
        pageSize: 100,
      },
    };
  },
  created() {
    this.getList();
  },
  mounted() {},
  methods: {
    handleQuery() {},
    resetQuery() {},
    handleAdd() {},
    handleUpdate() {},
    handleDelete() {},
    handleExport() {},
    handleSizeChange() {},
    handleCurrentChange() {},
    handleStatusChange(){},
    handleSelectionChange(selection) {
      let ids = selection.map((item) => item.roleId);
    },
    getList() {
      this.$axios({
        method: "get",
        url: "/project/getList",
        params: {
          ...this.queryParams,
          pageNum: this.pagination.pageNum,
          pageSize: this.pagination.pageSize,
        },
      })
        .then((res) => {
          this.projectList = res.data.records;
          this.pagination.total = res.data.total;
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
</script>
<style scoped>
/* @import url(); 引入css类 */
</style>