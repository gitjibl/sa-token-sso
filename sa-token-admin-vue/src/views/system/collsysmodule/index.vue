
<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
      <el-form-item label="网站名称" prop="collsysmoduleName">
        <el-input v-model="queryParams.name" placeholder="请输入网站名称" clearable style="width: 240px" />
      </el-form-item>

      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态">
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
          v-hasPermi="['system:collsysmodule:add']">新增</el-button>
      </el-col>
    <!-- <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" @click="handleUpdate"
          v-hasPermi="['system:collsysmodule:edit']">修改</el-button>
                          </el-col> -->
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete"
          v-hasPermi="['system:collsysmodule:delete']">删除</el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="collsysmoduleList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" prop="id" align="center" />
      <el-table-column label="网站名称" prop="name" align="center" show-overflow-tooltip />
      <el-table-column label="链接地址" prop="link" align="center" show-overflow-tooltip />
      <el-table-column label="类别" align="center" width="140" prop="category"></el-table-column>
      <el-table-column label="备注" prop="remark" align="center" show-overflow-tooltip />
      <el-table-column label="状态" align="center" width="140">
        <template slot-scope="scope">
          <el-switch v-if="!collsysmoduleFormatter(scope.row)" v-hasPermi="['system:collsysmodule:edit']"
            v-model="scope.row.status" :active-value="0" :inactive-value="1"
            @change="handleStatusChange(scope.row)"></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime">
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" v-hasPermi="['system:collsysmodule:edit']"
            @click="handleUpdate(scope.row)">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" v-hasPermi="['system:collsysmodule:delete']"
            v-if="!collsysmoduleFormatter(scope.row)" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination class="pagination-container" background style="float: right" @size-change="handleSizeChange"
      @current-change="handleCurrentChange" :current-page="pagination.pageNum" :page-sizes="pagination.pageSizes"
      :page-size="pagination.pageSize" layout="prev, pager, next, jumper, sizes,total" :total="pagination.total">
    </el-pagination>

    <!-- 添加或修改项目配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" @closed="close" width="500px">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="id" prop="id">
          <el-input v-model="form.id" placeholder="请输入项目编码" :disabled="!isInsert" />
        </el-form-item>
        <el-form-item label="网站名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入项目名称" />
        </el-form-item>
        <el-form-item label="链接地址">
          <el-input v-model="form.link" placeholder="请输入项目地址" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio v-model="form.status" :label="0">正常</el-radio>
          <el-radio v-model="form.status" :label="1">停用</el-radio>
        </el-form-item>
        <el-form-item label="类别" prop="category">
          <el-select v-model="form.category" placeholder="请选择类别">
            <el-option label="工具" :value="1">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注">
          <el-input type="remark" :rows="3" placeholder="请输入内容" v-model="form.remark">
          </el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" type="primary" @click="submitForm">确 定</el-button>
        <el-button size="small" @click="open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data() {
    return {
      //操作类型
      isInsert: false,
      // 查询参数
      queryParams: {
        projectName: null,
        status: null,
      },
      // 遮罩层
      loading: false,
      // 选中数组
      ids: [],
      collsysmoduleList: [],
      // 分页
      pagination: {
        pageNum: 1,
        total: 0,
        pageSizes: [10, 20, 30, 50],
        pageSize: 10,
      },
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 是否显示弹出层
      open: false,
      // 表单校验
      rules: {},
      // 弹出层标题
      title: "",
      // 表单参数
      form: {
        status: 0,
      },
    };
  },
  created() {
    this.getPageList();
  },
  mounted() {
    
  },
  methods: {
    //搜索按钮操作
    handleQuery() {
      this.pagination.pageNum = 1;
      this.getPageList();
    },
    //重置搜索
    resetQuery() {
      this.$data.queryParams = this.$options.data().queryParams;
      this.getPageList();
    },
    //新增按钮操作
    handleAdd() {
      this.isInsert = true;
      this.open = true;
      this.title = "新增模块";
    },
    //修改按钮操作
    handleUpdate(row) {
      this.isInsert = false;
      this.open = true;
      this.title = "编辑模块";
      this.form = {
        ...row,
      };
    },
    // 删除按钮
    handleDelete(row) {
      const ids = [row.id] || this.ids;
      this.$confirm("此操作将永久删除该数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.$axios({
          method: "post",
          url: "/collsysmodule/delete",
          data: JSON.stringify(ids),
          headers: {
            "Content-Type": "application/json; charset=UTF-8",
          },
        }).then((res) => {
          this.$message({
            type: "success",
            message: "删除成功!",
          });
          this.getPageList();
        })
          .catch((err) => { });
      }).catch(() => {
        this.$message({
          type: "info",
          message: "已取消删除",
        });
      });
    },

    //状态改变
    handleStatusChange(row) {
      let text = row.status === 0 ? "启用" : "停用";
      this.$confirm(
        '确认要"' + text + '""' + row.name + '"模块吗？',
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
            url: "/collsysmodule/update",
            params: {
              id: row.id,
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.collsysmoduleId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    //查询列表
    getPageList() {
      //请求
      this.$axios({
        method: "get",
        url: "/collsysmodule/getPageList",
        params: {
          ...this.queryParams,
          pageNum: this.pagination.pageNum,
          pageSize: this.pagination.pageSize,
        },
      }).then((res) => {
        this.collsysmoduleList = res.data.records;
        this.pagination.total = res.data.total;
      }).catch((err) => {
        console.log(err);
      });
    },
    //提交按钮
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          //判断的添加还是编辑
          if (this.isInsert) {
            this.$axios({
              method: "get",
              url: "/collsysmodule/insert",
              params: {
                ...this.form,
              },
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
              url: "/collsysmodule/update",
              params: {
                ...this.form,
              },
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
    collsysmoduleFormatter(row) {
      return this.$settings.id == row.id;
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
    close() {
      this.$data.form = this.$options.data().form;
      this.$refs.form.clearValidate();
    },
  },
};
</script>
<style scoped>
/* @import url(); 引入css类 */
</style>