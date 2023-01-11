<!--
 * @Descripttion: 
 * @Author: jibl
 * @Date: 2023-01-09 09:30:04
 * @LastEditors: jibl
 * @LastEditTime: 2023-01-09 14:29:12
-->
<!--  -->
<template>
  <el-dialog
    :title="upload.title"
    :visible.sync="upload.open"
    width="400px"
    append-to-body
  >
    <el-upload
      ref="upload"
      :limit="1"
      accept=".xlsx, .xls"
      :headers="upload.headers"
      :action="upload.url"
      :auto-upload="false"
      :http-request="uploadFile"
      drag
    >
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      <div class="el-upload__tip text-center" slot="tip">
        <span>仅允许导入xls、xlsx格式文件。</span>
        <el-link
          type="primary"
          :underline="false"
          style="font-size: 12px; vertical-align: baseline"
          @click="importTemplate"
          >下载模板</el-link
        >
      </div>
    </el-upload>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitFileForm">确 定</el-button>
      <el-button @click="upload.open = false">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
export default {
  data() {
    return {
      file: null,
    };
  },
  props: ["upload"],
  created() {},
  mounted() {},
  methods: {
    uploadFile(file) {
      this.file = file.file;
    },
    /** 下载模板操作 */
    importTemplate() {
      alert("开发中")
      return
      this.download(
        "system/user/importTemplate",
        {},
        `user_template_${new Date().getTime()}.xlsx`
      );
    },
    // 提交上传文件
    submitFileForm() {
      const loading = this.$loading({
        text: "正在上传数据，请稍候",
        spinner: "el-icon-loading",
        background: "rgba(0, 0, 0, 0.7)",
      });
      this.$refs.upload.submit();
      var formDate = new FormData();
      formDate.append("file", this.file);
      formDate.append("updateSupport", true);
      let config = {
        headers: {
          "Content-Type": "multipart/form-data",
        },
      };
      this.$axios
        .post(this.upload.url, formDate, config)
        .then((res) => {
          this.upload.open = false;
          this.$refs.upload.clearFiles();
          this.$alert(
            "<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" +
              res.data +
              "</div>",
            "导入结果",
            { dangerouslyUseHTMLString: true }
          );
          loading.close();
        })
        .catch((err) => {
          console.log(err);
          loading.close();
        });
    },
  },
};
</script>
<style scoped>
/* @import url(); 引入css类 */
</style>