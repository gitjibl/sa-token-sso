<template>
    <div class="app-container">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
            <el-form-item label="部门名称" prop="deptName">
                <el-input v-model="queryParams.deptName" placeholder="请输入部门名称" clearable
                    @keyup.enter.native="handleQuery" />
            </el-form-item>
            <el-form-item label="状态" prop="status">
                <el-select v-model="queryParams.status" placeholder="部门状态" clearable>
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
                    v-hasPermi="['system:dept:add']">新增</el-button>
            </el-col>
            <el-col :span="1.5">
                <el-button type="info" plain icon="el-icon-sort" size="mini" @click="toggleExpandAll">展开/折叠</el-button>
            </el-col>
        </el-row>

        <el-table v-if="refreshTable" v-loading="loading" :data="deptList"  row-key="deptId"
            :default-expand-all="isExpandAll" :tree-props="{ children: 'children', hasChildren: 'hasChildren' }">
            <el-table-column prop="deptName" label="部门名称" width="160"></el-table-column>
            <el-table-column prop="orderNum" label="排序" align="center"></el-table-column>
            <el-table-column prop="status" label="状态" align="center">
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
                        v-hasPermi="['system:dept:edit']">修改</el-button>
                    <el-button size="mini" type="text" icon="el-icon-plus" @click="handleAdd(scope.row)"
                        v-hasPermi="['system:dept:add']">新增</el-button>
                    <el-button v-if="scope.row.parentId != 0" size="mini" type="text" icon="el-icon-delete"
                        @click="handleDelete(scope.row)" v-hasPermi="['system:dept:remove']">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        <!-- 添加或修改部门对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="600px" @closed="close" append-to-body>
            <el-form ref="form" :model="form" :rules="rules" label-width="80px">
                <el-row>
                    <el-col :span="24" v-if="form.parentId !== 0">
                        <el-form-item label="上级部门" prop="parentId">
                            <treeselect v-model="form.parentId" :options="deptOptions" :normalizer="normalizer"
                                placeholder="选择上级部门" />
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="部门名称" prop="deptName">
                            <el-input v-model="form.deptName" placeholder="请输入部门名称" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="显示排序" prop="orderNum">
                            <el-input-number v-model="form.orderNum" controls-position="right" :min="0" />
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="负责人" prop="leader">
                            <el-input v-model="form.leader" placeholder="请输入负责人" maxlength="20" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="联系电话" prop="phone">
                            <el-input v-model="form.phone" placeholder="请输入联系电话" maxlength="11" />
                        </el-form-item>
                    </el-col>
                </el-row>
                <el-row>
                    <el-col :span="12">
                        <el-form-item label="邮箱" prop="email">
                            <el-input v-model="form.email" placeholder="请输入邮箱" maxlength="50" />
                        </el-form-item>
                    </el-col>
                    <el-col :span="12">
                        <el-form-item label="部门状态">
                            <el-radio-group v-model="form.status">
                                <el-radio :label="0">正常</el-radio>
                                <el-radio :label="1">停用</el-radio>
                            </el-radio-group>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitForm">确 定</el-button>
                <el-button @click="cancel">取 消</el-button>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import Treeselect from "@riophae/vue-treeselect";
    import "@riophae/vue-treeselect/dist/vue-treeselect.css";
    export default {
        data() {
            return {
                // 遮罩层
                loading: false,
                form: {
                    name: "",
                },
                // 选中数组
                ids: [],
                //操作类型
                isInsert: false,
                //数据集
                deptList: [],
                // 部门树选项
                deptOptions: [],
                // 是否显示弹出层
                open: false,
                // 弹出层标题
                title: "",
                // 重新渲染表格状态
                refreshTable: true,
                // 是否展开，默认全部折叠
                isExpandAll: false,
                // 查询参数
                queryParams: {},
                // 表单参数
                form: {
                    parentId: undefined,
                    status: 0,
                },
                // 表单校验
                rules: {
                    parentId: [{
                        required: true,
                        message: "上级部门不能为空",
                        trigger: "blur"
                    }],
                    deptName: [{
                        required: true,
                        message: "部门名称不能为空",
                        trigger: "blur"
                    }],
                    orderNum: [{
                        required: true,
                        message: "显示排序不能为空",
                        trigger: "blur"
                    }],
                    email: [{
                        type: "email",
                        message: "请输入正确的邮箱地址",
                        trigger: ["blur", "change"]
                    }],
                    phone: [{
                        pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
                        message: "请输入正确的手机号码",
                        trigger: "blur"
                    }]
                }
            };
        },
        components: {
            Treeselect
        },
        created() {
            this.getList();
        },
        methods: {
            handleQuery() {
                this.getList();
            },
            //查询列表
            getList() {
                this.loading = true;
                this.$axios({
                        method: "get",
                        url: "/dept/getList",
                        params: {
                            ...this.queryParams,
                        },
                    })
                    .then((res) => {
                        this.deptList = this.handleTree(res.data, "deptId");
                        this.loading = false;
                    })
                    .catch((err) => {
                        this.loading = false;
                        console.log(err);
                    });
            },
            //新增按钮操作
            handleAdd(row) {
                this.deptOptions = [...this.deptList];
                if (row != null && row.deptId) {
                    this.form.parentId = row.deptId;
                }
                this.isInsert = true;
                this.open = true;
                this.title = "新增部门";
            },
            //修改按钮操作
            handleUpdate(row) {
                this.deptOptions = [...this.deptList];
                this.isInsert = false;
                this.form = {
                    ...row,
                };
                this.open = true;
                this.title = "编辑部门";
            },

            //提交按钮
            submitForm() {
                this.$refs["form"].validate((valid) => {
                    if (valid) {
                        if (this.isInsert) {
                            this.$axios({
                                method: "get",
                                url: "/dept/insert",
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
                                url: "/dept/update",
                                params: {
                                    ...this.form,
                                    children: null
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
            // 删除按钮
            handleDelete(row) {
                this.$confirm("此操作将永久删除该文件, 是否继续?", "提示", {
                        confirmButtonText: "确定",
                        cancelButtonText: "取消",
                        type: "warning",
                    })
                    .then(() => {
                        this.$axios({
                                method: "get",
                                url: "/dept/delete",
                                params: {
                                    deptId: row.deptId
                                }
                            })
                            .then((res) => {
                                this.$message({
                                    type: "success",
                                    message: "删除成功!",
                                });
                                this.getList();
                            })
                            .catch((err) => {});
                    })
                    .catch(() => {
                        this.$message({
                            type: "info",
                            message: "已取消删除",
                        });
                    });
            },

            /** 转换菜单数据结构 */
            normalizer(node) {
                if (node.children && !node.children.length) {
                    delete node.children;
                }
                return {
                    id: node.deptId,
                    label: node.deptName,
                    children: node.children,
                };
            },
            /** 展开/折叠操作 */
            toggleExpandAll() {
                this.refreshTable = false;
                this.isExpandAll = !this.isExpandAll;
                this.$nextTick(() => {
                    this.refreshTable = true;
                });
            },

            statusformatter(val) {
                if (val == 0) {
                    return "正常";
                } else if (val == 1) {
                    return "停用";
                }
            },
            // 取消按钮
            cancel() {
                this.open = false;
            },
            //重置
            resetQuery() {
                this.$data.queryParams = this.$options.data().queryParams;
                this.handleQuery();
            },

            close() {
                this.$data.form = this.$options.data().form;
                this.$refs.form.clearValidate();
            },
        },
    };
</script>