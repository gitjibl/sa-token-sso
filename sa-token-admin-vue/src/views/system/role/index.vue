<!--
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-12-05 10:08:45
 * @LastEditors: jibl
 * @LastEditTime: 2023-01-04 16:25:28
-->
<!--  -->
<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input v-model="projectNameTxt" placeholder="请输入项目名称" clearable size="small" prefix-icon="el-icon-search"
            style="margin-bottom: 20px" />
        </div>
        <div class="head-container">
          <el-tree node-key="id" :data="projectOptions" :props="defaultProps" :expand-on-click-node="false"
            :filter-node-method="filterNode" ref="tree" default-expand-all highlight-current
            @node-click="handleNodeClick" />
        </div>
      </el-col>

      <el-col :span="20" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
          <el-form-item label="角色名称" prop="roleName">
            <el-input v-model="queryParams.roleName" placeholder="请输入角色名称" clearable />
          </el-form-item>
          <el-form-item label="权限字符" prop="roleKey">
            <el-input v-model="queryParams.roleKey" placeholder="请输入权限字符" clearable />
          </el-form-item>
          <el-form-item label="状态" prop="status">
            <el-select v-model="queryParams.status" placeholder="角色状态" clearable>
              <el-option label="正常" :value="0"></el-option>
              <el-option label="停用" :value="1"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
              v-hasPermi="['system:role:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate"
              v-hasPermi="['system:role:edit']">修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
              v-hasPermi="['system:role:delete']">删除</el-button>
          </el-col>
        </el-row>

        <el-table v-loading="loading" :data="roleList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="角色编号" prop="roleId" align="center" />
          <el-table-column label="角色名称" prop="roleName" :show-overflow-tooltip="true" align="center" />
          <el-table-column label="权限字符" prop="roleKey" :show-overflow-tooltip="true" align="center" />
          <el-table-column label="显示顺序" prop="roleSort" align="center" />
          <el-table-column label="状态" align="center">
            <template slot-scope="scope">
              <el-switch v-model="scope.row.status" :active-value="0" :inactive-value="1"
                @change="handleStatusChange(scope.row)"></el-switch>
            </template>
          </el-table-column>
          <el-table-column label="创建时间" align="center" prop="createTime">
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope" v-if="scope.row.roleKey !== 'super-admin'">
              <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                v-hasPermi="['system:role:edit']">修改
              </el-button>
              <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                v-hasPermi="['system:role:delete']">删除
              </el-button>
              <el-dropdown size="mini" @command="(command) => handleCommand(command, scope.row)"
                v-hasPermi="['system:role:dataScope', 'system:role:authUser']">
                <span class="el-dropdown-link">
                  <i class="el-icon-d-arrow-right el-icon--right"></i>更多
                </span>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item command="handleDataScope" icon="el-icon-circle-check"
                    v-hasPermi="['system:role:dataScope']">数据权限
                  </el-dropdown-item>
                  <el-dropdown-item command="handleAuthUser" icon="el-icon-user" v-hasPermi="['system:role:authUser']">
                    分配用户
                  </el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </template>
          </el-table-column>
        </el-table>

        <el-pagination class="pagination-container" background style="float: right" @size-change="handleSizeChange"
          @current-change="handleCurrentChange" :current-page="pagination.pageNum" :page-sizes="pagination.pageSizes"
          :page-size="pagination.pageSize" layout="prev, pager, next, jumper, sizes,total" :total="pagination.total">
        </el-pagination>
      </el-col>
    </el-row>

    <!-- 添加或修改角色配置对话框 -->
    <el-dialog :title="roleTitle" :visible.sync="roleOpen" width="500px" append-to-body @closed="close">
      <el-form ref="roleform" :model="roleform" :rules="roleRules" label-width="100px">
        <el-form-item label="所属项目" prop="projectName">
          <label style="color: #001fff" disabled>{{
            roleform.projectName
          }}</label>
        </el-form-item>
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="roleform.roleName" placeholder="请输入角色名称" />
        </el-form-item>
        <el-form-item prop="roleKey">
          <span slot="label">
            <el-tooltip content="控制器中定义的权限字符，如：@PreAuthorize(`@ss.hasRole('admin')`)" placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
            权限字符
          </span>
          <el-input v-model="roleform.roleKey" placeholder="请输入权限字符" />
        </el-form-item>
        <el-form-item label="角色顺序" prop="roleSort">
          <el-input-number v-model="roleform.roleSort" controls-position="right" :min="0" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="roleform.status">
            <el-radio :label="0">正常</el-radio>
            <el-radio :label="1">停用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="roleform.remark" type="textarea" placeholder="请输入内容"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="roleOpen = false">取 消</el-button>
      </div>
    </el-dialog>

    <data-scope ref="data-scope" />

    <auth-user ref="auth-user" />
  </div>
</template>

<script>
  import DataScope from "./dataScope";
  import AuthUser from "./authUser";
  export default {
    data() {
      return {
        // 查询参数
        queryParams: {
          projectId: null
        },
        // 遮罩层
        loading: false,
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 角色表格数据
        roleList: [],
        // 角色弹出层标题
        roleTitle: "",
        // 角色弹出层
        roleOpen: false,
        // 角色表单参数
        roleform: {
          status: 0,
        },
        // 分页
        pagination: {
          pageNum: 1,
          total: 0,
          pageSizes: [10, 20, 30, 50],
          pageSize: 10,
        },
        defaultProps: {
          children: "children",
          label: "label",
        },
        projectNameTxt: "",
        //项目集合
        projectOptions: [],
        // 表单校验
        roleRules: {
          projectName: [{
            required: true,
            message: "所属项目不能为空",
            trigger: "blur",
          }, ],
          roleName: [{
            required: true,
            message: "角色名称不能为空",
            trigger: "blur",
          }, ],
          roleKey: [{
            required: true,
            message: "权限字符不能为空",
            trigger: "blur",
          }, ],
          roleSort: [{
            required: true,
            message: "角色顺序不能为空",
            trigger: "blur",
          }, ],
        },
      };
    },
    created() {
      this.getProjectList();
    },
    watch: {
      // 根据名称筛选部门树
      projectNameTxt(val) {
        this.$refs.tree.filter(val);
      },
    },
    mounted() {},
    components: {
      DataScope,
      AuthUser,
    },
    methods: {
      // 节点单击事件
      handleNodeClick(data) {
        this.queryParams.projectId = data.id;
        this.handleQuery();
      },
      //搜索按钮操作
      handleQuery() {
        this.pagination.pageNum = 1;
        this.getPageList();
      },
      //重置搜索
      resetQuery() {
        this.$data.queryParams = this.$options.data().queryParams;
        this.getProjectList();
      },
      //查询列表
      getPageList() {
        this.loading = true;
        this.$axios({
            method: "get",
            url: "/role/getPageList",
            params: {
              ...this.queryParams,
              pageNum: this.pagination.pageNum,
              pageSize: this.pagination.pageSize,
            },
          })
          .then((res) => {
            this.roleList = res.data.records;
            this.pagination.total = res.data.total;
            this.loading = false;
          })
          .catch((err) => {
            this.loading = false;
            console.log(err);
          });
      },

      //加载项目列表
      getProjectList() {
        this.$axios({
          method: "get",
          url: "/project/getList",
          params: {},
        }).then((res) => {
          this.projectOptions = res.data.map((e) => {
            return {
              id: e.projectId,
              label: e.projectName,
            };
          });
          this.$nextTick(() => {
            this.$refs.tree.setCurrentKey(this.$projectId);
            this.queryParams.projectId = this.$projectId;
            this.getPageList();
          });
        });
      },

      //状态改变
      handleStatusChange(row) {
        let text = row.status === 0 ? "启用" : "停用";
        this.$confirm(
            '确认要"' + text + '""' + row.roleName + '"角色吗？',
            "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning",
            }
          )
          .then(() => {
            this.$axios({
              method: "get",
              url: "/role/update",
              params: {
                roleId: row.roleId,
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

      // 筛选节点
      filterNode(value, data) {
        if (!value) return true;
        return data.label.indexOf(value) !== -1;
      },
      //新增按钮操作
      handleAdd() {
        this.isInsert = true;
        this.roleOpen = true;
        this.roleform.projectName = this.projectOptions.filter((item) => {
          return this.queryParams.projectId == item.id;
        })[0].label;
        this.roleform.projectId = this.queryParams.projectId;
        this.roleTitle = "新增角色";
      },
      /** 修改按钮操作 */
      handleUpdate(row) {
        this.roleOpen = true;
        this.roleTitle = "编辑角色";
        this.roleform = {
          ...row,
        };
      },
      //删除
      handleDelete(row) {
        let ids = [];
        if (row.roleId != undefined) {
          ids = [row.roleId];
        } else {
          ids = this.ids;
        }
        this.$confirm("此操作将永久删除该数据, 是否继续?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          })
          .then(() => {
            this.$axios({
                method: "post",
                url: "/role/delete",
                data: JSON.stringify(ids),
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
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map((item) => item.roleId);
        this.single = selection.length != 1;
        this.multiple = !selection.length;
      },
      // 更多操作触发
      handleCommand(command, row) {
        switch (command) {
          case "handleDataScope":
            this.$refs["data-scope"].handleDataScope(row);
            break;
          case "handleAuthUser":
            this.$refs["auth-user"].handleAuthUser(row);
            break;
          default:
            break;
        }
      },

      submitForm() {
        this.$refs["roleform"].validate((valid) => {
          if (valid) {
            let url = "";
            if (this.isInsert) {
              url = "/role/insert";
            } else {
              url = "/role/update";
            }
            this.$axios({
              method: "get",
              url: url,
              params: {
                ...this.roleform,
              },
            }).then((res) => {
              this.$notify.success({
                title: "成功",
                message: "操作成功",
              });
              this.roleOpen = false;
              this.getPageList();
            });
          }
        });
      },
      cancel() {},
      close() {
        this.$data.roleform = this.$options.data().roleform;
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
  /* 设置树形最外层的背景颜色和字体颜色 */
  .el-tree {
    color: rgb(25 120 219);
  }

  ::v-deep .el-tree-node:focus>.el-tree-node__content {
    background-color: rgb(197, 218, 245);
  }

  ::v-deep .el-tree-node.is-current>.el-tree-node__content {
    background-color: rgb(197, 218, 245);
  }
</style>