<template>
  <div class="containers">
    <div class="canvas" ref="canvas"></div>
    <div id="properties-panel"></div>
  </div>
</template>

<script>

// 引入相关的依赖
import BpmnModeler from 'bpmn-js/lib/Modeler';
import propertiesPanelModule from 'bpmn-js-properties-panel';
import propertiesProviderModule from 'bpmn-js-properties-panel/lib/provider/camunda';
import camundaModdleDescriptor from 'camunda-bpmn-moddle/resources/camunda';
// import {
//   BpmnPropertiesPanelModule,
//   BpmnPropertiesProviderModule,
// } from 'bpmn-js-properties-panel';
import customTranslate from './customTranslate'  // 汉化包引入
import { xmlStr } from './xmlStr'; // 流程图初始配置，编辑的话只需要相同格式的文件替换即可

export default {

  data() {
    return {
      bpmnModeler: null,
      canvas: null
    };
  },

  mounted() {
    this.init();
  },

  computed: {

  },

  methods: {
    init() {
      // 将汉化包包装成一个模块
      const customTranslateMolude = {
        translate: ['value', customTranslate]
      };
      this.bpmnModeler = new BpmnModeler({ // 建模
        container: this.$refs.canvas,
        propertiesPanel: { // 添加控制板
          parent: '#properties-panel'
        },
        additionalModules: [ // 右边的属性栏
          propertiesProviderModule,
          propertiesPanelModule,
          customTranslateMolude // 汉化模块
        ],
        moddleExtensions: {
          camunda: camundaModdleDescriptor
        }
      });
      this.createNewDiagram();
    },
    async createNewDiagram() { // 将字符串转换成图显示出来
      this.bpmnModeler.importXML(xmlStr).then((info) => {
        if (info.error) {
          console.log(info.error);
        } else {
          this.success();
        }
      });
    },
    success() {
      console.log('创建成功!');
      this.bpmnModeler.on('commandStack.changed', () => {
        this.getXML().then(xml => {
          console.log(xml);
        });
      });
    },
    getXML() {
      return new Promise((resolve, reject) => {
        this.bpmnModeler.saveXML({ format: true }, (err, xml) => {
          err ? reject(err) : resolve(xml);
        });
      });
    }
  },
};
</script>

<style lang="scss">
.containers {
  position: absolute;
  background-color: #ffffff;
  width: 100%;
  height: 100%;
}

.canvas {
  width: 100%;
  height: 100%;
}

#properties-panel {
  position: absolute;
  right: 0;
  top: 0;
  width: 300px;
}
</style>
