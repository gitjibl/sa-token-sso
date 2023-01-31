
<!--  -->
<template>
  <!-- 添加或修改菜单配置对话框 -->
  <el-dialog
    :title="menuTitle"
    :visible.sync="menuOpen"
    width="500px"
    append-to-body
  >
    <el-form ref="form" :model="menuform" label-width="100px">
      <el-form-item label="所属项目">
        <label style="color: #001fff" disabled>{{
          menuform.projectName
        }}</label>
      </el-form-item>

      <el-form-item label="角色名称">
        <label style="color: rgb(11 181 119)" disabled>{{
          menuform.roleName
        }}</label>
      </el-form-item>

      <el-form-item label="菜单权限">
        <el-checkbox
          v-model="menuExpand"
          @change="handleCheckedTreeExpand($event, 'menu')"
          >展开/折叠
        </el-checkbox>
        <el-checkbox
          v-model="menuNodeAll"
          @change="handleCheckedTreeNodeAll($event, 'menu')"
          >全选/全不选
        </el-checkbox>
        <el-tree
          class="tree-border"
          :data="menuOptions"
          show-checkbox
          ref="menu"
          node-key="id"
          empty-text="加载中，请稍候"
          :props="defaultProps"
        ></el-tree>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button size="small" type="primary" @click="submitMenuForm"
        >确 定</el-button
      >
      <el-button size="small" @click="menuOpen = false">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      // 菜单列表
      menuOptions: [],
      // 部门列表
      deptOptions: [],
      // 展开
      menuExpand: false,
      // 全选
      menuNodeAll: false,
      // 菜单弹出层标题
      menuTitle: "",
      // 菜单弹出层
      menuOpen: false,
      // 菜单表单参数
      menuform: {},
      //树
      defaultProps: {
        children: "children",
        label: "label",
      },
      //当前行
      row_g: null,
    };
  },
  created() {},
  mounted() {},
  methods: {
    /** 根据角色ID查询菜单下拉树结构 */
    getRoleMenuTreeselect(roleId, projectId) {
      this.$axios({
        method: "get",
        url: "/menu/roleMenuTreeselect",
        params: { roleId: roleId, projectId: projectId },
      }).then((res) => {
        this.menuOptions = res.data.menus;
        let checkedKeys = res.data.checkedKeys;
        checkedKeys.forEach((v) => {
          this.$nextTick(() => {
            this.$refs.menu.setChecked(v, true, false);
          });
        });
      });
    },

    /** 分配数据权限操作 */
    handleDataScope(row) {
      this.row_g = row;
      this.getRoleMenuTreeselect(row.roleId, row.projectId);
      this.menuform.roleName = row.roleName;
      this.menuform.projectName = row.projectName;
      this.menuTitle = "分配数据权限";
      this.menuOpen = true;
    },

    // 树权限（展开/折叠）
    handleCheckedTreeExpand(value, type) {
      if (type == "menu") {
        let treeList = this.menuOptions;
        for (let i = 0; i < treeList.length; i++) {
          this.$refs.menu.store.nodesMap[treeList[i].id].expanded = value;
        }
      } else if (type == "dept") {
        let treeList = this.deptOptions;
        for (let i = 0; i < treeList.length; i++) {
          this.$refs.dept.store.nodesMap[treeList[i].id].expanded = value;
        }
      }
    },
    // 树权限（全选/全不选）
    handleCheckedTreeNodeAll(value, type) {
      if (type == "menu") {
        this.$refs.menu.setCheckedNodes(value ? this.menuOptions : []);
      } else if (type == "dept") {
        this.$refs.dept.setCheckedNodes(value ? this.deptOptions : []);
      }
    },

    // 所有菜单节点数据
    getMenuAllCheckedKeys() {
      // 目前被选中的菜单节点
      let checkedKeys = this.$refs.menu.getCheckedKeys();
      // 半选中的菜单节点
      let halfCheckedKeys = this.$refs.menu.getHalfCheckedKeys();
      checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys);
      return checkedKeys;
    },

    submitMenuForm() {
      let menuIds = this.getMenuAllCheckedKeys();
      this.$axios({
        method: "post",
        url: "/role/updateRoleMenu",
        data: JSON.stringify({
          roleId: this.row_g.roleId,
          menuIds: menuIds,
        }),
        headers: {
          "Content-Type": "application/json; charset=UTF-8",
        },
      }).then((res) => {
        this.$notify.success({
          title: "成功",
          message: "操作成功",
        });
        this.menuOpen = false;
      });
    },
  },
};
</script>
<style scoped>
/* @import url(); 引入css类 */
</style>