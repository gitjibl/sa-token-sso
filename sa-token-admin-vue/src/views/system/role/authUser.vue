<!--
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-12-08 09:19:16
 * @LastEditors: jibl
 * @LastEditTime: 2022-12-08 16:00:21
-->
<!-- 用户分配 -->
<template>
  <div>
    <el-dialog
      width="60%"
      :title="userTitle"
      :visible.sync="userOpen"
      :close-on-click-modal="false"
      @closed="close"
      append-to-body
    >
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
        <el-form-item label="用户名称">
          <el-input
            v-model="queryParams.username"
            placeholder="请输入用户名称"
            clearable
          />
        </el-form-item>
        <el-form-item label="手机号码">
          <el-input
            v-model="queryParams.telephone"
            placeholder="请输入手机号码"
            clearable
            style="width: 240px"
          />
        </el-form-item>
        <el-form-item label="是否授权">
          <el-select v-model="queryParams.flag" placeholder="授权">
            <el-option label="是" :value="true"></el-option>
            <el-option label="否" :value="false"></el-option>
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

      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            :disabled="multiple || authShow"
            @click="authUser"
            >批量授权</el-button
          >
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="el-icon-circle-close"
            size="mini"
            :disabled="multiple || !authShow"
            @click="cancelAuthUser"
            >批量取消授权</el-button
          >
        </el-col>
      </el-row>

      <el-table
        v-loading="loading"
        :data="userList"
        @selection-change="handleSelectionChange"
        height="500px"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column
          label="用户名称"
          prop="username"
          :show-overflow-tooltip="true"
          align="center"
        />
        <el-table-column
          label="性别"
          prop="sex"
          :show-overflow-tooltip="true"
          align="center"
        >
          <template slot-scope="scope">
            <el-tag
              :type="scope.row.sex === 0 ? 'primary' : 'success'"
              disable-transitions
              >{{ sexformatter(scope.row.sex) }}</el-tag
            >
          </template></el-table-column
        >
        <el-table-column
          label="手机"
          prop="telephone"
          :show-overflow-tooltip="true"
          align="center"
        />
        <el-table-column label="状态" align="center" prop="status">
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
              v-if="authShow"
              style="color: #f56c6c"
              size="mini"
              type="text"
              icon="el-icon-circle-close"
              @click="cancelAuthUser(scope.row)"
              >取消授权</el-button
            >
            <el-button
              v-if="!authShow"
              size="mini"
              type="text"
              icon="el-icon-circle-close"
              @click="authUser(scope.row)"
              >授权</el-button
            >
          </template>
        </el-table-column>
      </el-table>

      <el-pagination
        background
        style="float: right; margin-top: 15px"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="pagination.pageNum"
        :page-sizes="pagination.pageSizes"
        :page-size="pagination.pageSize"
        layout="prev, pager, next, jumper, sizes,total"
        :total="pagination.total"
      >
      </el-pagination>

      <div slot="footer" class="dialog-footer"></div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 用户弹出层标题
      userTitle: "",
      // 用户弹出层
      userOpen: false,
      //当前行
      row_g: null,
      // 查询参数
      queryParams: {
        flag: true,
      },
      // 是否授权
      authShow: true,
      // 选中用户组
      userIds: [],
      // 非多个禁用
      multiple: true,
      // 用户表格数据
      userList: [],
      //遮罩
      loading: false,

      // 分页
      pagination: {
        pageNum: 1,
        total: 0,
        pageSizes: [10, 20, 30, 50],
        pageSize: 10,
      },
    };
  },
  created() {},
  mounted() {},
  methods: {
    handleAuthUser(row) {
      this.row_g = row;
      this.userTitle = "用户分配";
      this.getPageList();
      this.userOpen = true;
    },
    getPageList() {
      this.$axios({
        method: "get",
        url: "/role/authUser/allocatedList",
        params: {
          flag: this.queryParams.flag,
          username: this.queryParams.username,
          telephone: this.queryParams.telephone,
          roleId: this.row_g.roleId,
          pageNum: this.pagination.pageNum,
          pageSize: this.pagination.pageSize,
        },
      }).then((res) => {
        this.userList = res.data.records;
        this.pagination.total = res.data.total;
        this.authShow = this.queryParams.flag;
      });
    },
    //搜索按钮操作
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getPageList();
    },
    //重置搜索
    resetQuery() {
      this.$data.queryParams = this.$options.data().queryParams;
      this.getPageList();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.userIds = selection.map((item) => item.userId);
      this.multiple = !selection.length;
    },
    // 授权用户
    authUser(row) {
      let ids = [];
      if (row.userId != undefined) {
        ids = [row.userId];
      } else {
        ids = this.userIds;
      }
      this.$confirm("确定要授权'" + this.row_g.roleName + "'角色吗", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.$axios({
          method: "post",
          url: "/role/authUser",
          data: JSON.stringify({
            ids: ids,
            roleId: this.row_g.roleId,
          }),
          headers: {
            "Content-Type": "application/json; charset=UTF-8",
          },
        })
          .then((res) => {
            this.$notify.success({
              title: "成功",
              message: "操作成功",
            });
            this.getPageList();
          })
          .catch((err) => {});
      });
    },
    // 取消授权
    cancelAuthUser(row) {
      let ids = [];
      if (row.userId != undefined) {
        ids = [row.userId];
      } else {
        ids = this.userIds;
      }
      this.$confirm(
        "确定要取消授权'" + this.row_g.roleName + "'角色吗",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      ).then(() => {
        this.$axios({
          method: "post",
          url: "/role/cancelAuthUser",
          data: JSON.stringify({
            ids: ids,
            roleId: this.row_g.roleId,
          }),
          headers: {
            "Content-Type": "application/json; charset=UTF-8",
          },
        })
          .then((res) => {
            this.$notify.success({
              title: "成功",
              message: "操作成功",
            });
            this.getPageList();
          })
          .catch((err) => {});
      });
    },
    close() {
      this.$data.queryParams = this.$options.data().queryParams;
      this.userList = [];
    },
    sexformatter(cellValue) {
      if (cellValue == 0) {
        return "男";
      } else if (cellValue == 1) {
        return "女";
      } else {
        return null;
      }
    },
    // ----分页----
    handleSizeChange(val) {
      this.pagination.pageSize = val;
      this.getPageList();
    },
    handleCurrentChange(val) {
      this.pagination.pageNum = val;
      this.getPageList();
    },
    // ------
  },
};
</script>
<style scoped>
/* @import url(); 引入css类 */
</style>