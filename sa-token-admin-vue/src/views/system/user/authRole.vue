<!--
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2022-12-05 16:41:37
 * @LastEditors: jibl
 * @LastEditTime: 2022-12-12 13:23:06
-->
<!--  -->
<template>
  <!-- 添加或修改角色配置对话框 -->
  <el-dialog
    :title="roleTitle"
    :visible.sync="roleOpen"
    width="500px"
    append-to-body
  >
    <el-form ref="form" :model="userform" label-width="100px">
      <el-form-item label="用户昵称">
        <label style="color: #001fff" disabled>{{
          userform.nickname
        }}</label>
      </el-form-item>

      <el-form-item label="登录账号">
        <label style="color: rgb(11 181 119)" disabled>{{ userform.username }}</label>
      </el-form-item>

      <el-form-item label="角色权限">
        <el-checkbox
          v-model="roleExpand"
          @change="handleCheckedTreeExpand($event, 'role')"
          >展开/折叠
        </el-checkbox>
        <el-checkbox
          v-model="roleNodeAll"
          @change="handleCheckedTreeNodeAll($event, 'role')"
          >全选/全不选
        </el-checkbox>
        <el-tree
          class="tree-border"
          :data="roleOptions"
          show-checkbox
          ref="role"
          node-key="id"
          empty-text="加载中，请稍候"
          :props="defaultProps"
        ></el-tree>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitUserform">确 定</el-button>
      <el-button @click="roleOpen = false">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      // 角色列表
      roleOptions: [],
      // 部门列表
      deptOptions: [],
      // 展开
      roleExpand: false,
      // 全选
      roleNodeAll: false,
      // 角色弹出层标题
      roleTitle: "",
      // 角色弹出层
      roleOpen: false,
      // 角色表单参数
      userform: {},
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
    /** 根据角色ID查询角色下拉树结构 */
    getRoleTreeselect(userId) {
      this.$axios({
        method: "get",
        url: "/role/roleTreeselect",
        params: { userId: userId},
      }).then((res) => {
        this.roleOptions = res.data.roles;
        console.log(this.roleOptions, "this.roleOptions");
        let checkedKeys = res.data.checkedKeys;
        checkedKeys.forEach((v) => {
          this.$nextTick(() => {
            this.$refs.role.setChecked(v, true, false);
          });
        });
      });
    },

    /**角色分配 */
    authRole(row){
      this.row_g = row;
      this.getRoleTreeselect(row.userId);
      this.userform.username = row.username;
      this.userform.nickname = row.nickname;
      this.roleTitle = "角色分配";
      this.roleOpen = true;
    },

    // 树权限（展开/折叠）
    handleCheckedTreeExpand(value, type) {
      if (type == "role") {
        let treeList = this.roleOptions;
        for (let i = 0; i < treeList.length; i++) {
          this.$refs.role.store.nodesMap[treeList[i].id].expanded = value;
        }
      } 
    },
    // 树权限（全选/全不选）
    handleCheckedTreeNodeAll(value, type) {
      if (type == "role") {
        this.$refs.role.setCheckedNodes(value ? this.roleOptions : []);
      }
    },

    // 所有角色节点数据
    getroleAllCheckedKeys() {
      // 目前被选中的角色节点
      let checkedKeys = this.$refs.role.getCheckedKeys();
      // 半选中的角色节点
      // let halfCheckedKeys = this.$refs.role.getHalfCheckedKeys();
      // checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys);
      return checkedKeys;
    },

    submitUserform() {
      let roleIds = this.getroleAllCheckedKeys();
      this.$axios({
        method: "post",
        url: "/user/updateUserRole",
        data: JSON.stringify({
          userId: this.row_g.userId,
          roleIds: roleIds,
        }),
        headers: {
          "Content-Type": "application/json; charset=UTF-8",
        },
      }).then((res) => {
        this.$notify.success({
          title: "成功",
          message: "操作成功",
        });
        this.roleOpen = false;
      });
    },
  },
};
</script>
<style scoped>
/* @import url(); 引入css类 */
</style>