<template>
    <div class="app-container">
        <!-- :inline 	行内表单模式，默认false  -->
        <el-form ref="form" :model="form" size="small" label-width="80px" :inline="true">
            <el-form-item label="活动名称">
                <el-input v-model="form.name"></el-input>
            </el-form-item>
        </el-form>

        <div style="margin-top: 20px">
            <el-button @click="toggleSelection([deptList[0]])">切换第二、第三行的选中状态</el-button>
            <el-button @click="toggleSelection()">取消选择</el-button>
        </div>

        <el-table ref="multipleTable" :data="deptList" tooltip-effect="dark" style="width: 100%"
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
                    <el-button size="mini" type="text" icon="el-icon-edit">修改</el-button>
                    <el-button size="mini" type="text" icon="el-icon-delete">删除</el-button>
                </template>
            </el-table-column>
        </el-table>

        
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
            deptList: [],
        }
    },
    // created
    created() {
        this.getList();
    },
    methods: {
        toggleSelection(rows) {
            if (rows) {
                rows.forEach(row => {
                    this.$refs.multipleTable.toggleRowSelection();
                });
            } else {
                this.$refs.multipleTable.clearSelection();
            }
        },
        handleSelectionChange(val) {
            this.multipleSelection = val;
        },

        //查询列表
        getList() {
            this.$axios({
                method: "get",
                url: "/dept/getList",
                params: {
                    ...this.queryParams,
                    // pageNum: this.pagination.pageNum,
                    // pageSize: this.pagination.pageSize,
                },
            })
                .then((res) => {
                    this.deptList = res.data.records;
                    // this.pagination.total = res.data.total;
                })
                .catch((err) => {
                    console.log(err);
                });
        }
    }
}
</script>