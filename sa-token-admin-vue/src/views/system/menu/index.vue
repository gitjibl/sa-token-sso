<!--
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-12-12 13:46:38
 * @LastEditors: jibl
 * @LastEditTime: 2023-01-13 17:00:11
-->
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
          <el-form-item label="菜单名称">
            <el-input v-model="queryParams.menuName" placeholder="请输入菜单名称" clearable />
          </el-form-item>
          <el-form-item label="状态">
            <el-select v-model="queryParams.status" placeholder="菜单状态" clearable>
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
              v-hasPermi="['system:menu:add']">新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button type="info" plain icon="el-icon-sort" size="mini" @click="toggleExpandAll">展开/折叠</el-button>
          </el-col>
        </el-row>

        <el-table v-if="refreshTable" v-loading="loading" :data="menuList" row-key="menuId"
          :default-expand-all="isExpandAll" :tree-props="{ children: 'children', hasChildren: 'hasChildren' }">
          <el-table-column label="菜单名称" :show-overflow-tooltip="true" width="160">
            <template slot-scope="scope">
              <svg-icon v-if="scope.row.menuType == 'M'" icon-class="listings" />
              <svg-icon v-if="scope.row.menuType == 'C'" icon-class="menus" />
              <svg-icon v-if="scope.row.menuType == 'F'" icon-class="button" />
              <span style="margin-left: 5px">{{ scope.row.menuName }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="icon" label="图标" align="center" :show-overflow-tooltip="true">
          </el-table-column>
          <el-table-column prop="orderNum" align="center" label="排序" width="60"></el-table-column>
          <el-table-column prop="perms" label="权限标识" align="center" :show-overflow-tooltip="true"></el-table-column>
          <el-table-column prop="component" label="组件路径" align="center" :show-overflow-tooltip="true"></el-table-column>
          <el-table-column prop="status" label="状态" align="center" width="80">
            <template slot-scope="scope">
              <el-tag :type="scope.row.status === 0 ? 'primary' : 'warning'" disable-transitions>
                {{ statusformatter(scope.row.status) }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="创建时间" align="center" prop="createTime">
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
                v-hasPermi="['system:menu:edit']">修改</el-button>
              <el-button size="mini" type="text" icon="el-icon-plus" @click="handleAdd(scope.row)"
                v-hasPermi="['system:menu:add']">新增</el-button>
              <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                v-hasPermi="['system:menu:delete']">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>

    <!-- 添加或修改菜单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" @closed="close" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="上级菜单">
              <treeselect v-if="isInsert" v-model="form.parentId" :options="menuOptions" :normalizer="normalizer"
                :show-count="true" placeholder="选择上级菜单" />
              <el-input v-if="!isInsert" v-model="form.parentName" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="菜单类型" prop="menuType">
              <el-radio-group v-model="form.menuType" @input="menuTypeInput">
                <el-radio label="M">目录</el-radio>
                <el-radio label="C">菜单</el-radio>
                <el-radio label="F">按钮</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24" v-if="form.menuType != 'F'">
            <el-form-item label="菜单图标" prop="icon">
              <span slot="label">
                <el-tooltip content="菜单图标名称" placement="top">
                  <i class="el-icon-question"></i>
                </el-tooltip>
                菜单图标
              </span>
              <el-input v-model="form.icon" placeholder="请输入图标名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="菜单名称" prop="menuName">
              <el-input v-model="form.menuName" placeholder="请输入菜单名称" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="显示排序" prop="orderNum">
              <el-input-number v-model="form.orderNum" controls-position="right" :min="0" />
            </el-form-item>
          </el-col>

          <el-col :span="12" v-if="form.menuType != 'F'">
            <el-form-item>
              <span slot="label">
                <el-tooltip content="选择是外链则路由地址需要以`http(s)://`开头" placement="top">
                  <i class="el-icon-question"></i>
                </el-tooltip>
                是否外链
              </span>
              <el-radio-group v-model="form.isFrame">
                <el-radio :label="0">是</el-radio>
                <el-radio :label="1">否</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.menuType != 'F'">
            <el-form-item prop="path">
              <span slot="label">
                <el-tooltip content="访问的路由地址，如：`user`，如外网地址需内链访问则以`http(s)://`开头" placement="top">
                  <i class="el-icon-question"></i>
                </el-tooltip>
                路由地址
              </span>
              <el-input v-model="form.path" placeholder="请输入路由地址" />
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.menuType == 'C'">
            <el-form-item prop="component">
              <span slot="label">
                <el-tooltip content="访问的组件路径，如：`system/user/index`，默认在`views`目录下" placement="top">
                  <i class="el-icon-question"></i>
                </el-tooltip>
                组件路径
              </span>
              <el-input v-model="form.component" placeholder="请输入组件路径" />
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.menuType != 'M'">
            <el-form-item>
              <el-input v-model="form.perms" placeholder="请输入权限标识" maxlength="100" />
              <span slot="label">
                <el-tooltip content="控制器中定义的权限字符，如：@PreAuthorize(`@ss.hasPermi('system:user:list')`)" placement="top">
                  <i class="el-icon-question"></i>
                </el-tooltip>
                权限字符
              </span>
            </el-form-item>
          </el-col>

          <el-col :span="12" v-if="form.menuType != 'F'">
            <el-form-item>
              <span slot="label">
                <el-tooltip content="选择停用则路由将不会出现在侧边栏，也不能被访问" placement="top">
                  <i class="el-icon-question"></i>
                </el-tooltip>
                菜单状态
              </span>
              <el-radio-group v-model="form.status">
                <el-radio :label="0">正常</el-radio>
                <el-radio :label="1">停用</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>

          <el-col :span="12" v-if="form.menuType != 'F'">
            <el-form-item>
              <span slot="label">
                <el-tooltip content="选择隐藏则路由将不会出现在侧边栏，但仍然可以访问" placement="top">
                  <i class="el-icon-question"></i>
                </el-tooltip>
                显示状态
              </span>
              <el-radio-group v-model="form.visible">
                <el-radio :label="0">显示</el-radio>
                <el-radio :label="1">隐藏</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>

          <el-col :span="12" v-if="form.menuType == 'C'">
            <el-form-item>
              <el-input v-model="form.query" placeholder="请输入路由参数" maxlength="255" />
              <span slot="label">
                <el-tooltip content='访问路由的默认传递参数，如：`{"id": 1, "name": "ry"}`' placement="top">
                  <i class="el-icon-question"></i>
                </el-tooltip>
                路由参数
              </span>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.menuType == 'C'">
            <el-form-item>
              <span slot="label">
                <el-tooltip content="选择是则会被`keep-alive`缓存，需要匹配组件的`name`和地址保持一致" placement="top">
                  <i class="el-icon-question"></i>
                </el-tooltip>
                是否缓存
              </span>
              <el-radio-group v-model="form.isCache">
                <el-radio :label="0">缓存</el-radio>
                <el-radio :label="1">不缓存</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" type="primary" @click="submitForm">确 定</el-button>
        <el-button size="small" @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import Treeselect from "@riophae/vue-treeselect";
  import "@riophae/vue-treeselect/dist/vue-treeselect.css";

  export default {
    name: "Menu",
    components: {
      Treeselect
    },
    data() {
      return {
        // 遮罩层
        loading: false,
        // 数据集
        menuData: [],
        // 菜单表格树数据
        menuList: [],
        // 菜单树选项
        menuOptions: [],
        //操作类型
        isInsert: false,
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 是否展开，默认全部折叠
        isExpandAll: false,
        // 重新渲染表格状态
        refreshTable: true,
        // 查询参数
        queryParams: {
          projectId: null,
          menuName: undefined,
          visible: undefined,
        },
        // 表单参数
        form: {
          menuId: undefined,
          parentId: 0,
          parentName: undefined,
          menuName: undefined,
          icon: undefined,
          menuType: "M",
          orderNum: undefined,
          isFrame: 1,
          isCache: 0,
          visible: 0,
          status: 0,
        },
        // 表单校验
        rules: {
          menuName: [{
            required: true,
            message: "菜单名称不能为空",
            trigger: "blur"
          }, ],
          orderNum: [{
            required: true,
            message: "菜单顺序不能为空",
            trigger: "blur"
          }, ],
          path: [{
            required: true,
            message: "路由地址不能为空",
            trigger: "blur"
          }, ],
        },
        defaultProps: {
          children: "children",
          label: "label",
        },
        projectNameTxt: "",
        //项目列表
        projectOptions: [],
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
    methods: {
      // 节点单击事件
      handleNodeClick(data) {
        this.queryParams.projectId = data.id;
        this.handleQuery();
      },
      // 选择图标
      selected(name) {
        this.form.icon = name;
      },
      // 筛选节点
      filterNode(value, data) {
        if (!value) return true;
        return data.label.indexOf(value) !== -1;
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
            this.$refs.tree.setCurrentKey(this.$settings.projectId);
            this.queryParams.projectId = this.$settings.projectId;
            this.getList();
          });
        });
      },
      /** 查询菜单列表 */
      getList() {
        this.loading = true;
        this.$axios({
            method: "get",
            url: "/menu/getList",
            params: {
              ...this.queryParams,
            },
          })
          .then((res) => {
            this.menuData = res.data;
            this.menuList = this.handleTree(res.data, "menuId");
            this.loading = false;
          })
          .catch((err) => {
            this.loading = false;
            console.log(err);
          });
      },
      /** 转换菜单数据结构 */
      normalizer(node) {
        if (node.children && !node.children.length) {
          delete node.children;
        }
        return {
          id: node.menuId,
          label: node.menuName,
          children: node.children,
        };
      },
      // 取消按钮
      cancel() {
        this.open = false;
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {
        this.$data.queryParams = this.$options.data().queryParams;
        this.handleQuery();
      },
      /** 新增按钮操作 */
      handleAdd(row) {
        this.isInsert = true;
        this.menuOptions = [];
        const menu = {
          menuId: 0,
          menuName: "主类目",
          children: []
        };
        menu.children = this.menuList;
        this.menuOptions = [menu]
        if (row != null && row.menuId) {
          this.form.parentId = row.menuId;
        } else {
          this.form.parentId = 0;
        }
        this.open = true;
        this.title = "添加菜单";
      },

      /** 修改按钮操作 */
      handleUpdate(row) {
        this.isInsert = false;
        this.form = {
          ...row,
          children: null
        };
        this.getParentName(row.parentId)
        this.open = true;
        this.title = "修改菜单";
      },
      //获取上级部门名称
      getParentName(parentId) {
        if (parentId == 0) {
          this.form.parentName = "主类目"
        } else {
          let item = this.menuData.filter(item => {
            return item.menuId == parentId
          })[0];
          this.form.parentName = item && item.menuName
        }
      },

      /** 展开/折叠操作 */
      toggleExpandAll() {
        this.refreshTable = false;
        this.isExpandAll = !this.isExpandAll;
        this.$nextTick(() => {
          this.refreshTable = true;
        });
      },
      /**菜单类型改变事件 */
      menuTypeInput(val) {
        let parentId = this.form.parentId;
        let parentName = this.form.parentName;
        this.$data.form = this.$options.data().form;
        this.form.menuType = val;
        this.form.parentId = parentId;
        this.form.parentName = parentName;
      },
      /** 提交按钮 */
      submitForm: function () {
        this.$refs["form"].validate((valid) => {
          if (valid) {
            if (!this.isInsert) {
              this.$axios({
                method: "get",
                url: "/menu/update",
                params: {
                  ...this.form,
                },
              }).then((res) => {
                this.$notify.success({
                  title: "成功",
                  message: "操作成功",
                });
                this.open = false;
                this.getList();
              });
            } else {
              this.$axios({
                method: "get",
                url: "/menu/insert",
                params: {
                  ...this.form,
                  projectId: this.queryParams.projectId,
                },
              }).then((res) => {
                this.$notify.success({
                  title: "成功",
                  message: "操作成功",
                });
                this.open = false;
                this.getList();
              });
            }
          }
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        this.$confirm("此操作将永久删除该数据, 是否继续?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          })
          .then(() => {
            this.$axios({
              method: "get",
              url: "/menu/delete",
              params: {
                menuId: row.menuId,
              },
            }).then((res) => {
              this.$message({
                type: "success",
                message: "删除成功!",
              });
              this.getList();
            });
          })
      },

      close() {
        this.$data.form = this.$options.data().form;
        this.$refs.form.clearValidate();
      },
      statusformatter(val) {
        if (val == 0) {
          return "正常";
        } else if (val == 1) {
          return "停用";
        }
      },
    },
  };
</script>
<style scoped>
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