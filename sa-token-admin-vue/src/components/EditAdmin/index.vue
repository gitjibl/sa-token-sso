<!--
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2023-01-17 13:57:09
 * @LastEditors: jibl
 * @LastEditTime: 2023-01-17 14:48:11
-->
<!--  -->
<template>
    <el-dialog title="提示" :visible.sync="open" width="26%" @closed="close" append-to-body>
        <el-form :model="form" :rules="rules" :label-width="formLabelWidth" ref="ruleForm">
            <el-form-item label="密匙" prop="secretKey">
                <el-input v-model="form.secretKey"></el-input>
            </el-form-item>
            <el-form-item label="新密码" prop="password">
                <el-input v-model="form.password"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button size="small" @click="open = false">取 消</el-button>
            <el-button size="small" type="primary" @click="submitForm">确 定</el-button>
        </div>
    </el-dialog>
</template>

<script>
    const secretKey = "yyds";
    import {
        mapGetters
    } from "vuex";
    export default {
        data() {
            return {
                open: false,
                form: {
                    secretKey: undefined,
                    password: undefined
                },
                formLabelWidth: '90px',
                rules: {
                    secretKey: [{
                        required: true,
                        message: '请输入密匙',
                    }],
                    password: [{
                        required: true,
                        message: '请输入新密码',
                    }, {
                        pattern: /^.{5,20}$/,
                        message: "用户密码长度必须介于 5 和 20 之间",
                    }],
                }

            }
        },
        computed: {
            ...mapGetters(["userInfo"]),
        },
        created() {

        },
        mounted() {

        },
        methods: {
            /** 提交按钮 */
            submitForm: function () {
                this.$refs['ruleForm'].validate((valid) => {
                    if (valid) {
                        if (this.form.secretKey != secretKey) {
                            this.$notify.error({
                                title: '错误',
                                message: '请输入正确的密匙！'
                            });
                            return;
                        }
                        this.$axios({
                            method: "get",
                            url: "/user/resetUserPwd",
                            params: {
                                userId: this.userInfo.userId,
                                password: this.form.password,
                            },
                        }).then((res) => {
                            this.open = false;
                            this.$notify.success({
                                title: "成功",
                                message: "操作成功",
                            });
                        });
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            close(){
                this.$refs['ruleForm'].resetFields()
            }
        }
    }
</script>
<style scoped>
    /* @import url(); 引入css类 */
</style>