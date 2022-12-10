<template>
    <div class="app-container">
        <!-- :inline 	行内表单模式，默认false  -->
        <!-- <el-form ref="form" :model="form" size="small" label-width="80px" :inline="true">
            <el-form-item label="活动名称">
                <el-input v-model="form.name"></el-input>
            </el-form-item>
        </el-form> -->
        <!-- <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
            <el-form-item label="项目名称" prop="roleName">
                <el-select v-model="queryParams.projectId" placeholder="请选择项目名称">
                    <el-option v-for="item in projectOptions" :key="item.projectId" :label="item.projectName"
                        :value="item.projectId">
                    </el-option>
                </el-select>
            </el-form-item>
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
        </el-form> -->

        <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
                <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd">新增</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" 
                    @click="handleUpdateSelect">修改</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple"
                    @click="handleDelete">删除</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport">导出</el-button>
            </el-col>
        </el-row>

        <!-- <div style="margin-top: 20px">
            <el-button @click="toggleSelection([deptList[0]])">切换第二、第三行的选中状态</el-button>
            <el-button @click="toggleSelection()">取消选择</el-button>
        </div> -->
        <!-- v-loading="loading" -->
        <el-table ref="multipleTable" 
        :data="deptList" tooltip-effect="dark" style="width: 100%"
            @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55">
            </el-table-column>
            <el-table-column label="部门id" width="120">
                <template slot-scope="scope">{{ scope.row.deptId }}</template>
            </el-table-column>
            <el-table-column prop="parentId" label="父部门id" width="120">
            </el-table-column>
            <el-table-column prop="ancestors" label="祖级列表" show-overflow-tooltip>
            </el-table-column>
            <el-table-column prop="deptName" label="部门名称" show-overflow-tooltip>
            </el-table-column>
            <el-table-column prop="orderNum" label="显示顺序" show-overflow-tooltip>
            </el-table-column>
            <el-table-column prop="leader" label="负责人" show-overflow-tooltip>
            </el-table-column>
            <el-table-column prop="phone" label="联系电话" show-overflow-tooltip>
            </el-table-column>
            <el-table-column prop="email" label="邮箱" show-overflow-tooltip>
            </el-table-column>
            <el-table-column prop="status" label="部门状态" show-overflow-tooltip>
            </el-table-column>
            <el-table-column prop="createBy" label="创建者" show-overflow-tooltip>
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间" show-overflow-tooltip>
            </el-table-column>
            <el-table-column label="操作" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                    <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)">修改</el-button>
                    <el-button size="mini" type="text" icon="el-icon-delete">删除</el-button>
                </template>
            </el-table-column>
        </el-table>


        <!-- 添加或修改部门配置对话框 start-->
        <el-dialog :title="title" :visible.sync="open" width="500px">
            <el-form ref="form" :model="form" :rules="rules" label-width="100px">
                <el-form-item label="部门id" prop="deptId">
                    <el-input v-model="form.deptId" placeholder="请输入部门id" :disabled="!isInsert" />
                </el-form-item>
                <el-form-item label="部门名称" prop="deptName">
                    <el-input v-model="form.deptName" placeholder="请输入部门名称" />
                </el-form-item>
                <el-form-item label="显示顺序" prop="orderNum">
                    <el-input v-model="form.orderNum" placeholder="请输入显示顺序" />
                </el-form-item>
                <el-form-item label="负责人" prop="leader">
                    <el-input v-model="form.leader" placeholder="请输入负责人" />
                </el-form-item>
                <el-form-item label="联系电话" prop="phone">
                    <el-input v-model="form.phone" placeholder="请输入联系电话" />
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="form.email" placeholder="请输入邮箱" />
                </el-form-item>
                <el-form-item label="部门状态" prop="status">
                    <el-radio v-model="form.status" :label="0">正常</el-radio>
                    <el-radio v-model="form.status" :label="1">停用</el-radio>
                </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitForm">确 定</el-button>
                <el-button @click="open = false">取 消</el-button>
            </div>
        </el-dialog>
        <!-- 添加或修改部门配置对话框 end-->


    </div>
</template>
  
<script>
export default {
    data() {
        return {
            multipleSelection: [],
            form: {
                name: ''
            },
                  // 选中数组
      ids: [],
            //操作类型
            isInsert: false,
            deptList: [],
            // 是否显示弹出层
            open: false,
            // 弹出层标题
            title: "",
            // 表单参数
            form: {
                status: 0,
            },
            // 非单个禁用
            single: true,
            // 非多个禁用
            multiple: true,
            // 表单校验
            rules: {},
        }
    },
    // created
    created() {
        this.getList();
    },
    methods: {
        // toggleSelection(rows) {
        //     if (rows) {
        //         rows.forEach(row => {
        //             this.$refs.multipleTable.toggleRowSelection();
        //         });
        //     } else {
        //         this.$refs.multipleTable.clearSelection();
        //     }
        // },
        // 多选框
        handleSelectionChange(val) {
            // console.log(val);
            this.multipleSelection = val;
            this.ids = val.map((item) => item.deptId);
            //单选
            this.single = val.length != 1;
            //多选
            this.multiple = !val.length;
        },
        //查询列表
        getList() {
            this.$axios({
                method: "get",
                url: "/dept/getPageList",
                params: {
                    ...this.queryParams,
                    // pageNum: this.pagination.pageNum,
                    // pageSize: this.pagination.pageSize,
                },
            })
                .then((res) => {
                    console.log("部门列表：" + res);
                    this.deptList = res.data.records;
                    // this.pagination.total = res.data.total;
                })
                .catch((err) => {
                    console.log(err);
                });
        },
        //新增按钮操作
        handleAdd() {
            this.isInsert = true;
            this.open = true;
            this.title = "新增部门";
        },
        //修改按钮操作
        handleUpdateSelect() {
            this.isInsert = false;
            this.open = true;
            this.title = "编辑部门";
            this.form = this.multipleSelection[0];            
            // console.log(this.multipleSelection[0]);

        },
        //修改按钮操作
        handleUpdate(row) {
            this.isInsert = false;
            this.open = true;
            this.title = "编辑部门";
            this.form = { ...row };
            // console.log(row);

        },
        
        //提交按钮
        submitForm() {
            this.$refs["form"].validate((valid) => {
                if (valid) {
                    if (this.isInsert) {
                        this.$axios({
                            method: "get",
                            url: "/pept/insert",
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
                            url: "/dept/update",
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
        // 删除按钮
        handleDelete(row) {
            const ids = [row.deptId] || this.ids;
            this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning",
            })
                .then(() => {
                    this.$axios({
                        method: "post",
                        url: "/dept/delete",
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
                        .catch((err) => { });
                })
                .catch(() => {
                    this.$message({
                        type: "info",
                        message: "已取消删除",
                    });
                });
        },
        handleExport() {
            alert("功能开发中...");
        },
    }
}
</script>