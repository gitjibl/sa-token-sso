<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--部门数据-->
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input
            v-model="deptName"
            placeholder="请输入部门名称"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container">
          <el-tree
            node-key="id"
            :data="deptOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree"
            default-expand-all
            highlight-current
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>
      <!--用户数据-->
      <el-col :span="20" :xs="24">
        <el-form
          :model="queryParams"
          ref="queryForm"
          size="small"
          :inline="true"
          label-width="68px"
        >
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
            />
          </el-form-item>
          <el-form-item label="状态">
            <el-select
              v-model="queryParams.status"
              placeholder="用户状态"
              clearable
            >
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

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              v-hasPermi="['system:user:add']"
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
              :disabled="single"
              v-hasPermi="['system:user:edit']"
              @click="handleUpdate"
              >修改
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              v-hasPermi="['system:user:delete']"
              @click="handleDelete"
            >
              删除</el-button
            >
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="info"
              plain
              icon="el-icon-upload2"
              size="mini"
              v-hasPermi="['system:user:import']"
              @click="handleImport"
              >导入</el-button
            >
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              v-hasPermi="['system:user:export']"
              @click="handleExport"
              >导出</el-button
            >
          </el-col>
        </el-row>

        <el-table
          v-loading="loading"
          :data="userList"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="用户编号" align="center" prop="userId" />
          <el-table-column label="用户昵称" align="center" prop="nickname" />
          <el-table-column
            label="用户名称"
            align="center"
            prop="username"
            :show-overflow-tooltip="true"
          />
          <el-table-column
            label="部门"
            align="center"
            prop="deptName"
            :show-overflow-tooltip="true"
          />
          <el-table-column label="手机号码" align="center" prop="telephone" />
          <el-table-column label="状态" align="center" key="status">
            <template slot-scope="scope" v-if="scope.row.userId !== 1">
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
            width="160"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope" v-if="scope.row.userId !== 1">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                v-hasPermi="['system:user:edit']"
                @click="handleUpdate(scope.row)"
                >修改</el-button
              >
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                v-hasPermi="['system:user:delete']"
                @click="handleDelete(scope.row)"
                >删除</el-button
              >
              <el-dropdown
                size="mini"
                @command="(command) => handleCommand(command, scope.row)"
                v-hasPermi="['system:user:resetPwd', 'system:user:authRole']"
              >
                <span class="el-dropdown-link">
                  <i class="el-icon-d-arrow-right el-icon--right"></i>更多
                </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item
                    command="handleResetPwd"
                    icon="el-icon-key"
                    v-hasPermi="['system:user:resetPwd']"
                  >
                    重置密码</el-dropdown-item
                  >
                  <el-dropdown-item
                    command="handleAuthRole"
                    icon="el-icon-circle-check"
                    v-hasPermi="['system:user:authRole']"
                    >分配角色</el-dropdown-item
                  >
                </el-dropdown-menu>
              </el-dropdown>
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
      </el-col>
    </el-row>

    <!-- 添加或修改用户配置对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="600px"
      @closed="close"
      append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item
              v-if="form.userId == undefined"
              label="用户名称"
              prop="username"
            >
              <el-input
                v-model="form.username"
                placeholder="请输入用户名称"
                maxlength="30"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item
              v-if="form.userId == undefined"
              label="用户密码"
              prop="password"
            >
              <el-input
                v-model="form.password"
                placeholder="请输入用户密码"
                type="password"
                maxlength="20"
                show-password
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="用户性别" prop="sex">
              <el-select v-model="form.sex" placeholder="请选择性别">
                <el-option label="男" :value="0"></el-option>
                <el-option label="女" :value="1"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号码" prop="telephone">
              <el-input
                v-model="form.telephone"
                placeholder="请输入手机号码"
                maxlength="11"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="用户昵称" prop="nickname">
              <el-input v-model="form.nickname" placeholder="请输入用户昵称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="归属部门" prop="deptId">
              <treeselect
                v-model="form.deptId"
                :options="deptOptions"
                :show-count="true"
                placeholder="请选择归属部门"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12"> </el-col>
          <el-col :span="12">
            <el-form-item label="状态">
              <el-radio-group v-model="form.status">
                <el-radio :label="0">正常</el-radio>
                <el-radio :label="1">停用</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="角色顺序" prop="userSort">
          <el-input-number
            v-model="form.userSort"
            controls-position="right"
            :min="0"
          />
        </el-form-item>

        <el-row>
          <el-col :span="24">
            <el-form-item label="备注">
              <el-input
                v-model="form.remark"
                type="textarea"
                placeholder="请输入内容"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" type="primary" @click="submitForm">确 定</el-button>
        <el-button size="small" @click="open = false">取 消</el-button>
      </div>
    </el-dialog>

    <upload-manage ref="uploadManage" :upload="upload"></upload-manage>

    <!-- 配置角色 -->
    <auth-role ref="auth-role" />
  </div>
</template>

<script>
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import AuthRole from "./authRole";
export default {
  name: "User",
  components: {
    Treeselect,
    AuthRole,
  },
  data() {
    return {
      // 遮罩层
      loading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 用户表格数据
      userList: [],
      // 弹出层标题
      title: "",
      // 部门树选项
      deptOptions: undefined,
      // 是否显示弹出层
      open: false,
      // 部门名称
      deptName: undefined,
      // 默认密码
      initPassword: undefined,
      // 角色选项
      // roleOptions: [],
      // 当前行
      row_: null,
      // 表单参数
      form: {
        password: null,
        status: 0,
        sex: 0,
        userSort: 0,
      },
      defaultProps: {
        children: "children",
        label: "label",
      },
      // 分页
      pagination: {
        pageNum: 1,
        total: 0,
        pageSizes: [10, 20, 30, 50],
        pageSize: 10,
      },
      // 用户导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 设置上传的请求头部
        headers: {"Content-Type": "multipart/form-data"},
        // 上传的地址
        url: "/user/importData",
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        username: undefined,
        telephone: undefined,
        status: undefined,
        deptId: undefined,
      },
      // 表单校验
      rules: {
        username: [
          {
            required: true,
            message: "用户名称不能为空",
            trigger: "blur",
          },
          {
            min: 2,
            max: 20,
            message: "用户名称长度必须介于 2 和 20 之间",
            trigger: "blur",
          },
        ],
        password: [
          {
            required: true,
            message: "用户密码不能为空",
            trigger: "blur",
          },
          {
            min: 5,
            max: 20,
            message: "用户密码长度必须介于 5 和 20 之间",
            trigger: "blur",
          },
        ],
        sex: [
          {
            required: true,
            message: "用户性别不能为空",
            trigger: "blur",
          },
        ],
        nickname: [
          {
            required: true,
            message: "用户昵称不能为空",
            trigger: "blur",
          },
        ],
        telephone: [
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur",
          },
        ],
        userSort: [
          {
            required: true,
            message: "角色顺序不能为空",
            trigger: "blur",
          },
        ],
      },
    };
  },
  watch: {
    // 根据名称筛选部门树
    deptName(val) {
      this.$refs.tree.filter(val);
    },
  },
  created() {
    this.getPageList();
    this.deptTreeSelect();
  },
  methods: {
    /** 查询用户列表 */
    getPageList() {
      this.loading = true;
      this.$axios({
        method: "get",
        url: "/user/getPageList",
        params: {
          ...this.queryParams,
          pageNum: this.pagination.pageNum,
          pageSize: this.pagination.pageSize,
        },
      })
        .then((res) => {
          this.userList = res.data.records;
          this.pagination.total = res.data.total;
          this.loading = false;
        })
        .catch((err) => {
          this.loading = false;
          console.log(err);
        });
    },
    /** 查询部门下拉树结构 */
    deptTreeSelect() {
      this.$axios({
        method: "get",
        url: "/dept/deptTreeSelect",
        params: {},
      }).then((res) => {
        this.deptOptions = res.data;
        this.$nextTick(() => {
          this.$refs.tree.setCurrentKey(2);
        });
      });
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.deptId = data.id;
      this.handleQuery();
    },
    // 用户状态修改
    handleStatusChange(row) {
      let text = row.status === 0 ? "启用" : "停用";
      this.$confirm(
        '确认要"' + text + '""' + row.username + '"用户吗？',
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(() => {
          this.$axios({
            method: "get",
            url: "/user/update",
            params: {
              userId: row.userId,
              status: row.status,
            },
          }).then((res) => {
            this.$notify.success({
              title: "成功",
              message: "操作成功",
            });
          });
        })
        .catch(function () {
          row.status = row.status === 0 ? 1 : 0;
        });
    },
    // 关闭弹窗
    close() {
      this.$data.form = this.$options.data().form;
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getPageList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.$data.queryParams = this.$options.data().queryParams;
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.row_ = selection[selection.length - 1];
      this.ids = selection.map((item) => item.userId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    // 更多操作触发
    handleCommand(command, row) {
      switch (command) {
        case "handleResetPwd":
          this.handleResetPwd(row);
          break;
        case "handleAuthRole":
          this.handleAuthRole(row);
          break;
        default:
          break;
      }
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.open = true;
      this.title = "添加用户";
      this.form.password = this.initPassword;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.open = true;
      this.title = "修改用户";
      this.form = {
        ...row,
        ...this.row_,
      };
    },
    /** 重置密码按钮操作 */
    handleResetPwd(row) {
      this.$prompt('请输入"' + row.username + '"的新密码', "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        closeOnClickModal: false,
        inputPattern: /^.{5,20}$/,
        inputErrorMessage: "用户密码长度必须介于 5 和 20 之间",
      })
        .then(({ value }) => {
          this.$axios({
            method: "get",
            url: "/user/resetUserPwd",
            params: {
              userId: row.userId,
              password: value,
            },
          }).then((res) => {
            this.$notify.success({
              title: "成功",
              message: "操作成功",
            });
          });
        })
        .catch(() => {});
    },
    /** 分配角色操作 */
    handleAuthRole: function (row) {
      this.$refs["auth-role"].authRole(row);
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.userId != undefined) {
            this.$axios({
              method: "get",
              url: "/user/update",
              params: this.form,
            }).then((res) => {
              this.$notify.success({
                title: "成功",
                message: "操作成功",
              });
              this.open = false;
              this.getPageList();
            });
          } else {
            this.$axios({
              method: "get",
              url: "/user/insert",
              params: this.form,
            }).then((res) => {
              this.$notify.success({
                title: "成功",
                message: "操作成功",
              });
              this.open = false;
              this.getPageList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      let userIds = [];
      if (row.userId != undefined) {
        userIds = [row.userId];
      } else {
        userIds = this.ids;
      }
      this.$confirm("此操作将永久删除该数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$axios({
            method: "post",
            url: "/user/delete",
            data: JSON.stringify(userIds),
            headers: {
              "Content-Type": "application/json; charset=UTF-8",
            },
          })
            .then((res) => {
              this.$message({
                type: "success",
                message: "删除成功!",
              });
              this.getPageList();
            })
            .catch((err) => {});
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      import("@utils/Export2Excel").then((excel) => {
        const tHeader = [
          "用户编号",
          "用户昵称",
          "用户名称",
          "部门",
          "手机号码",
        ];
        const filterVal = [
          "userId",
          "nickname",
          "username",
          "deptName",
          "telephone",
        ];
        const list = this.userList;
        const data = this.$common.formatJson(filterVal, list);
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: "用户表",
          autoWidth: true,
          bookType: "xlsx",
        });
      });
    },

    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "用户导入";
      this.upload.open = true;
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
/* 设置树形最外层的背景颜色和字体颜色 */
.el-tree {
  color: rgb(25 120 219);
}

::v-deep .el-tree-node:focus > .el-tree-node__content {
  background-color: rgb(197, 218, 245);
}

::v-deep .el-tree-node.is-current > .el-tree-node__content {
  background-color: rgb(197, 218, 245);
}
</style>