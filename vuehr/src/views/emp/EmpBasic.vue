<template>
    <div>
        <div>
          <div style="display:flex; justify-content:space-between">
              <div>
                <el-input placeholder="请输入员工姓名..." icon="el-icon-search" 
                clearable
                @clear="initEmps"
                style="width:500px; margin-right:30px" v-model="keyword" @keydown.enter.native="initEmps" :disabled="showSuperSearchView"></el-input>
                <el-button icon="el-icon-search" type="primary" @click="initEmps" :disabled="showSuperSearchView">搜索</el-button>
                <el-button type="primary" @click="showSuperSearchView=!showSuperSearchView">
                    <i :class="showSuperSearchView?'fa fa-angle-double-up':'fa fa-angle-double-down'" aria-hidden="true"></i>
                    高级搜索
                </el-button>
            </div>
            <div>
                <el-button type="success" @click="exportData" icon="el-icon-download">
                    导出数据
                </el-button>
                <el-button icon="el-icon-plus" type="primary" @click="showAddEmpView">
                    添加数据
                </el-button>
            </div>
          </div>
            <div v-show="showSuperSearchView" style="border:1px solid #409eff;border-radius:5px;box-sizing:border-box;padding:5px;margin:10px 0px">
              <el-row>
                <el-col :span="4">
                  <el-tag style="margin-left:5px">政治面貌:</el-tag>
                          <el-select v-model="searchValue.politicId" style="width: 150px; margin-left:5px" size="mini" placeholder="政治面貌">
                            <el-option
                              v-for="item in politicsstatus"
                              :key="item.id"
                              :label="item.name"
                              :value="item.id">
                            </el-option>
                          </el-select>
                </el-col>
                <el-col :span="4">
                  <el-tag>民族:</el-tag>
                  <el-select v-model="searchValue.nationId" style="width: 150px;margin-left:5px" size="mini" placeholder="请选择民族">
                            <el-option
                              v-for="item in nations"
                              :key="item.id"
                              :label="item.name"
                              :value="item.id">
                            </el-option>
                          </el-select>
                </el-col>
                <el-col :span="4">
                  <el-tag>职位:</el-tag>
                  <el-select v-model="searchValue.posId" style="width: 150px;margin-left:5px" size="mini" placeholder="请选择职位">
                            <el-option
                              v-for="item in positions"
                              :key="item.id"
                              :label="item.name"
                              :value="item.id">
                            </el-option>
                          </el-select>
                </el-col>
                <el-col :span="4">
                  <el-tag>职称:</el-tag>
                    <el-select v-model="searchValue.jobLevelId" style="width: 150px;margin-left:5px" size="mini" placeholder="请选择职称">
                            <el-option
                              v-for="item in joblevels"
                              :key="item.id"
                              :label="item.name"
                              :value="item.id">
                            </el-option>
                          </el-select>
                </el-col>
                <el-col :span="6 ">
                  <el-tag style="margin-right:5px">聘用形式:</el-tag>
                  <el-radio-group v-model="searchValue.engageForm">
                            <el-radio label="劳动合同">劳动合同</el-radio>
                            <el-radio style="margin-left: 15px" label="劳务合同">劳务合同</el-radio>
                          </el-radio-group>
                </el-col>
              </el-row>
              <el-row style="margin-top:10px">
                <el-col :span="5">
                  <el-tag style="margin-left:5px;margin-right:5px">所属部门:</el-tag>
                   <el-popover
                            v-model="popVisible1"
                            placement="right"
                            width="200"
                            title="请选择部门"
                            trigger="manual">
                            <el-tree :data="allDeps" :default-expand-all="true" :props="defaultProps" :expand-on-click-node="false"
                                    @node-click="advanceHandleNodeClick"></el-tree>
                            <div slot="reference" 
                                style="width: 150px;height: 26px;display: inline-flex;font-size:13px;border: 1px;border-radius: 5px;border-style: solid;padding-left: 13px;box-sizing:border-box;border-color: #dcdfe6;cursor: pointer;align-items: center margin-left:50px"
                                @click="showDepView2">
                                {{depName}}
                            </div>
                          </el-popover>
                </el-col>
                <el-col :span="10">
                    <el-tag>入职时间</el-tag>
                        <el-date-picker
                          v-model="searchValue.beginDateScope"
                          type="daterange"
                          value-format="yyyy-MM-dd"
                          unlink-panels
                          range-separator="至"
                          start-placeholder="开始日期"
                          end-placeholder="结束日期">
                        </el-date-picker>
                </el-col>
                <el-col :span="5">
                  <el-button @click="cancel">取消</el-button>
                  <el-button type="primary" icon="el-icon-search" @click="initEmps('advanced')">搜索</el-button>
                </el-col>
              </el-row>
            </div>
        </div>
        <div style="margin-top:10px">
             <el-table
                :data="emps"
                stripe
                border
                v-loading="loading"
                element-loading-text="拼命加载中..."
                element-loading-spinner="el-icon-loading"
                element-loading-background="rgba(0, 0, 0, 0.8)"
                style="width: 100%">
                <el-table-column
                type="selection"
                width="55">
                </el-table-column>
                 <el-table-column
              prop="name"
              align="left"
              fixed
              label="姓名"
              width="90">
            </el-table-column>
            <el-table-column
              prop="workID"
              width="85"
              align="left"
              label="工号">
            </el-table-column>
            <el-table-column
              prop="gender"
              label="性别"
              width="50">
            </el-table-column>
            <el-table-column
              prop="birthday"
              width="100"
              align="left"
              label="出生日期">
            </el-table-column>
            <el-table-column
              prop="idCard"
              width="165"
              align="left"
              label="身份证号码">
            </el-table-column>
            <el-table-column
              prop="wedlock"
              width="70"
              label="婚姻状况">
            </el-table-column>
            <el-table-column
              width="50"
              prop="nation.name"
              label="民族">
            </el-table-column>
            <el-table-column
              prop="nativePlace"
              width="80"
              label="籍贯">
            </el-table-column>
            <el-table-column
              prop="politicsstatus.name"
              label="政治面貌">
            </el-table-column>
            <el-table-column
              prop="email"
              width="180"
              align="left"
              label="电子邮件">
            </el-table-column>
            <el-table-column
              prop="phone"
              width="110"
              label="电话号码">
            </el-table-column>
            <el-table-column
              prop="address"
              width="220"
              align="left"
              label="联系地址">
            </el-table-column>
            <el-table-column
              prop="department.name"
              align="left"
              width="100"
              label="所属部门">
            </el-table-column>
            <el-table-column
              width="100"
              align="left"
              prop="position.name"
              label="职位">
            </el-table-column>
            <el-table-column
              prop="jobLevel.name"
              width="100"
              align="left"
              label="职称">
              <template slot-scope="scope">
                  {{scope.row.jobLevel.name}}
              </template>
            </el-table-column>
            <el-table-column
              prop="engageForm"
              label="聘用形式">
            </el-table-column>
            <el-table-column
              prop="beginDate"
              width="85"
              align="left"
              label="入职日期">
            </el-table-column>
            <el-table-column
              prop="conversionTime"
              width="85"
              align="left"
              label="转正日期">
            </el-table-column>
            <el-table-column
              prop="beginContract"
              width="95"
              align="left"
              label="合同起始日期">
            </el-table-column>
            <el-table-column
              prop="endContract"
              width="95"
              align="left"
              label="合同截至日期">
            </el-table-column>
            <el-table-column
              prop="contractTerm"
              align="left"
              width="70"
              label="合同期限">
              <template slot-scope="scope">
                  {{scope.row.contractTerm}}年
              </template>
            </el-table-column>
            <el-table-column
              align="left"
              prop="tiptopDegree"
              label="最高学历">
            </el-table-column>
            <el-table-column
              align="left"
              prop="school"
              label="毕业院校">
            </el-table-column>
            <el-table-column
              align="left"
              prop="specialty"
              label="专业">
            </el-table-column>
            <el-table-column
              fixed="right"
              width="200px"
              label="操作">
            <template slot-scope="scope">
                <el-button style="padding:3px" size="mini" @click="showEditEmpView(scope.row)">编辑</el-button>
                <el-button style="padding:3px" size="mini" type="primary">查看高级资料</el-button>
                <el-button style="padding:3px" size="mini" type="danger" @click="deleteEmp(scope.row)">删除</el-button>
            </template>
            </el-table-column>
            </el-table>
            <div style="">
                <el-pagination
                    background
                    @current-change="currentChange"
                    @size-change="sizeChange"
                    layout="sizes, prev, pager, next, jumper, ->, total, slot"
                    :total="total">
                </el-pagination>
            </div>
             <el-dialog
                :title="title"
                :visible.sync="dialogVisible"
                width="80%">
                <el-form :model="emp" :rules="rules" ref="empForm">
                  <el-row>
                    <el-col :span="6">
                      <div>
                        <el-form-item label="姓名:" prop="name">
                          <el-input prefix-icon="el-icon-edit" v-model="emp.name" size="mini" style="width: 150px"
                                    placeholder="请输入员工姓名"></el-input>
                        </el-form-item>
                      </div>
                    </el-col>
                    <el-col :span="5">
                      <div>
                        <el-form-item label="性别:" prop="gender">
                          <el-radio-group v-model="emp.gender">
                            <el-radio label="男">男</el-radio>
                            <el-radio style="margin-left: 15px" label="女">女</el-radio>
                          </el-radio-group>
                        </el-form-item>
                      </div>
                    </el-col>
                    <el-col :span="6">
                      <div>
                        <el-form-item label="出生日期:" prop="birthday">
                          <el-date-picker
                            v-model="emp.birthday"
                            size="mini"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            style="width: 150px"
                            type="date"
                            placeholder="出生日期">
                          </el-date-picker>
                        </el-form-item>
                      </div>
                    </el-col>
                    <el-col :span="7">
                      <div>
                        <el-form-item label="政治面貌:" prop="politicId">
                          <el-select v-model="emp.politicId" style="width: 200px" size="mini" placeholder="政治面貌">
                            <el-option
                              v-for="item in politicsstatus"
                              :key="item.id"
                              :label="item.name"
                              :value="item.id">
                            </el-option>
                          </el-select>
                        </el-form-item>
                      </div>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="6">
                      <div>
                        <el-form-item label="民族:" prop="nationId">
                          <el-select v-model="emp.nationId" style="width: 150px" size="mini" placeholder="请选择民族">
                            <el-option
                              v-for="item in nations"
                              :key="item.id"
                              :label="item.name"
                              :value="item.id">
                            </el-option>
                          </el-select>
                        </el-form-item>
                      </div>
                    </el-col>
                    <el-col :span="5">
                      <div>
                        <el-form-item label="籍贯:" prop="nativePlace">
                          <el-input v-model="emp.nativePlace" size="mini" style="width: 120px" placeholder="员工籍贯"></el-input>
                        </el-form-item>
                      </div>
                    </el-col>
                    <el-col :span="6">
                      <div>
                        <el-form-item label="电子邮箱:" prop="email">
                          <el-input prefix-icon="el-icon-message" v-model="emp.email" size="mini" style="width: 150px"
                                    placeholder="电子邮箱地址..."></el-input>
                        </el-form-item>
                      </div>
                    </el-col>
                    <el-col :span="7">
                      <div>
                        <el-form-item label="联系地址:" prop="address">
                          <el-input prefix-icon="el-icon-edit" v-model="emp.address" size="mini" style="width: 200px"
                                    placeholder="联系地址..."></el-input>
                        </el-form-item>
                      </div>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="6">
                      <div>
                        <el-form-item label="职位:" prop="posId">
                          <el-select v-model="emp.posId" style="width: 150px" size="mini" placeholder="请选择职位">
                            <el-option
                              v-for="item in positions"
                              :key="item.id"
                              :label="item.name"
                              :value="item.id">
                            </el-option>
                          </el-select>
                        </el-form-item>
                      </div>
                    </el-col>
                    <el-col :span="5">
                      <div>
                        <el-form-item label="职称:" prop="jobLevelId">
                          <el-select v-model="emp.jobLevelId" style="width: 120px" size="mini" placeholder="请选择职称">
                            <el-option
                              v-for="item in joblevels"
                              :key="item.id"
                              :label="item.name"
                              :value="item.id">
                            </el-option>
                          </el-select>
                        </el-form-item>
                      </div>
                    </el-col>
                    <el-col :span="6">
                      <div>
                        <el-form-item label="所属部门:" prop="departmentId">
                          <el-popover
                            v-model="popVisible"
                            placement="right"
                            title="请选择部门"
                            trigger="manual">
                            <el-tree :data="allDeps" :default-expand-all="true" :props="defaultProps" :expand-on-click-node="false"
                                    @node-click="handleNodeClick"></el-tree>
                            <div slot="reference" 
                                style="width: 150px;height: 26px;display: inline-flex;font-size:13px;border: 1px;border-radius: 5px;border-style: solid;padding-left: 13px;box-sizing:border-box;border-color: #dcdfe6;cursor: pointer;align-items: center"
                                @click="showDepView" 
                                >
                                {{depName}}
                            </div>
                          </el-popover>
                        </el-form-item>
                      </div>
                    </el-col>
                    <el-col :span="7">
                      <div>
                        <el-form-item label="电话号码:" prop="phone">
                          <el-input prefix-icon="el-icon-phone" v-model="emp.phone" size="mini" style="width: 200px"
                                    placeholder="电话号码..."></el-input>
                        </el-form-item>
                      </div>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="6">
                      <div>
                        <el-form-item label="工号:" prop="workId">
                          <el-input v-model="emp.workID" disabled size="mini" style="width: 150px"
                                    placeholder="员工工号..."></el-input>
                        </el-form-item>
                      </div>
                    </el-col>
                    <el-col :span="5">
                      <div>
                        <el-form-item label="学历:" prop="tiptopDegree">
                          <el-select v-model="emp.tiptopDegree" style="width: 120px" size="mini" placeholder="最高学历">
                            <el-option
                              v-for="item in tiptopDegree"
                              :key="item"
                              :label="item"
                              :value="item">
                            </el-option>
                          </el-select>
                        </el-form-item>
                      </div>
                    </el-col>
                    <el-col :span="6">
                      <div>
                        <el-form-item label="毕业院校:" prop="school">
                          <el-input prefix-icon="el-icon-edit" v-model="emp.school" size="mini" style="width: 150px"
                                    placeholder="毕业院校名称"></el-input>
                        </el-form-item>
                      </div>
                    </el-col>
                    <el-col :span="7">
                      <div>
                        <el-form-item label="专业名称:" prop="specialty">
                          <el-input prefix-icon="el-icon-edit" v-model="emp.specialty" size="mini" style="width: 200px"
                                    placeholder="专业名称"></el-input>
                        </el-form-item>
                      </div>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="6">
                      <div>
                        <el-form-item label="入职日期:" prop="beginDate">
                          <el-date-picker
                            v-model="emp.beginDate"
                            size="mini"
                            style="width: 130px"
                            type="date"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            placeholder="入职日期">
                          </el-date-picker>
                        </el-form-item>
                      </div>
                    </el-col>
                    <el-col :span="6">
                      <div>
                        <el-form-item label="转正日期:" prop="conversionTime">
                          <el-date-picker
                            v-model="emp.conversionTime"
                            size="mini"
                            style="width: 130px"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            type="date"
                            placeholder="转正日期">
                          </el-date-picker>
                        </el-form-item>
                      </div>
                    </el-col>
                    <el-col :span="6">
                      <div>
                        <el-form-item label="合同起始日期:" prop="beginContract">
                          <el-date-picker
                            v-model="emp.beginContract"
                            size="mini"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            style="width: 135px"
                            type="date"
                            placeholder="合同起始日期">
                          </el-date-picker>
                        </el-form-item>
                      </div>
                    </el-col>
                    <el-col :span="6">
                      <div>
                        <el-form-item label="合同终止日期:" prop="endContract">
                          <el-date-picker
                            v-model="emp.endContract"
                            value-format="yyyy-MM-dd HH:mm:ss"
                            size="mini"
                            style="width: 135px"
                            type="date"
                            placeholder="合同终止日期">
                          </el-date-picker>
                        </el-form-item>
                      </div>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="8">
                      <div>
                        <el-form-item label="身份证号码:" prop="idCard">
                          <el-input prefix-icon="el-icon-edit" v-model="emp.idCard" size="mini" style="width: 180px"
                                    placeholder="请输入员工身份证号码..."></el-input>
                        </el-form-item>
                      </div>
                    </el-col>
                    <el-col :span="8">
                      <div>
                        <el-form-item label="聘用形式:" prop="engageForm">
                          <el-radio-group v-model="emp.engageForm">
                            <el-radio label="劳动合同">劳动合同</el-radio>
                            <el-radio style="margin-left: 15px" label="劳务合同">劳务合同</el-radio>
                          </el-radio-group>
                        </el-form-item>
                      </div>
                    </el-col>
                    <el-col :span="8">
                      <div>
                        <el-form-item label="婚姻状况:" prop="wedlock">
                          <el-radio-group v-model="emp.wedlock">
                            <el-radio label="已婚">已婚</el-radio>
                            <el-radio style="margin-left: 15px" label="未婚">未婚</el-radio>
                            <el-radio style="margin-left: 15px" label="离异">离异</el-radio>
                          </el-radio-group>
                        </el-form-item>
                      </div>
                    </el-col>
                  </el-row>
                </el-form>
                <span slot="footer" class="dialog-footer">
                  <el-button @click="dialogVisible = false">取 消</el-button>
                  <el-button type="primary" @click="doAddEmp">确 定</el-button>
                </span>
            </el-dialog>
        </div>
    </div>
</template>

<script>
export default {
    name:"EmpBasic",
    data(){
        return{
          searchValue:{
            politicId:null,
            nationId:null,
            jobLevelId:null,
            posId:null,
            engageForm:null,
            departmentId:null,
            beginDateScope:null
          },
            title:'',
            emps:[],
            allDeps:[],
            loading:false,
            popVisible:false,
            popVisible1:false,
            showSuperSearchView:false,
            keyword:'',
            total:0,
            page:1,
            size:10,
            dialogVisible:false,
            nations:[],
            joblevels:[],
            politicsstatus:[],
            positions:[],
            tiptopDegree:['小学','初中','高中','专科','本科','硕士','博士','其他'],
            depName:'所属部门',
            emp: {
              name: '',
              gender: '',
              birthday: '',
              idCard: '',
              wedlock: '',
              nationId: '',
              nativePlace: '',
              politicId: '',
              email: '',
              phone: '',
              address: '',
              departmentId: '',
              departmentName: '所属部门...',
              jobLevelId: '',
              posId: '',
              engageForm: '',
              tiptopDegree: '',
              specialty: '',
              school: '',
              beginDate: '',  
              workID: '',
              contractTerm: '',
              conversionTime: '',
              notWorkDate: '',
              beginContract: '',
              endContract: '',
              workAge: ''
        },
        defaultProps: {
          children: 'children',
          label: 'name'
        },
        rules:{
          name: [{required: true, message: '必填:姓名', trigger: 'blur'}],
          gender: [{required: true, message: '必填:性别', trigger: 'blur'}],
          birthday: [{required: true, message: '必填:出生日期', trigger: 'blur'}],
          idCard: [{
            required: true,
            message: '必填:身份证号码',
            trigger: 'blur'
          }, {
            pattern: /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/,
            message: '身份证号码格式不正确',
            trigger: 'blur'
          }],
          wedlock: [{required: true, message: '必填:婚姻状况', trigger: 'blur'}],
          nationId: [{required: true, message: '必填:民族', trigger: 'change'}],
          nativePlace: [{required: true, message: '必填:籍贯', trigger: 'blur'}],
          politicId: [{required: true, message: '必填:政治面貌', trigger: 'blur'}],
          email: [{required: true, message: '必填:电子邮箱', trigger: 'blur'}, {
            type: 'email',
            message: '邮箱格式不正确',
            trigger: 'blur'
          }],
          phone: [{required: true, message: '必填:电话号码', trigger: 'blur'}],
          address: [{required: true, message: '必填:联系地址', trigger: 'blur'}],
          departmentId: [{required: true, message: '必填:部门', trigger: 'change'}],
          jobLevelId: [{required: true, message: '必填:职称', trigger: 'change'}],
          posId: [{required: true, message: '必填:职位', trigger: 'change'}],
          engageForm: [{required: true, message: '必填:聘用形式', trigger: 'blur'}],
          tiptopDegree: [{required: true, message: '必填:最高学历', trigger: 'change'}],
          specialty: [{required: true, message: '必填:专业', trigger: 'blur'}],
          workID: [{required: true, message: '必填:工号', trigger: 'blur'}],
          school: [{required: true, message: '必填:毕业院校', trigger: 'blur'}],
          beginDate: [{required: true, message: '必填:入职日期', trigger: 'blur'}],
          conversionTime: [{required: true, message: '必填:转正日期', trigger: 'blur'}],
          beginContract: [{required: true, message: '必填:合同起始日期', trigger: 'blur'}],
          endContract: [{required: true, message: '必填:合同终止日期', trigger: 'blur'}],
          workAge: [{required: true, message: '必填:工龄', trigger: 'blur'}]
        }
        }
    },
    mounted(){
        this.initEmps();
        this.initData();
        this.initPositions(); 
        // this.showDepView();
    },
    methods:{
      //取消高级搜索
      cancel(){
        this.searchValue.beginDateScope=null;
        this.searchValue.departmentId=null;
        this.searchValue.engageForm=null;
        this.searchValue.posId=null;
        this.searchValue.jobLevelId=null;
        this.searchValue.nationId=null;
        this.searchValue.politicId=null;
        this.showSuperSearchView=false;

      },
      //文件导出
      exportData(){
        window.open('/employee/basic/export','_parent');
      },
      empNull(){
        this.emp= {
              name: '',
              gender: '',
              birthday: '',
              idCard: '',
              wedlock: '',
              nationId: '',
              nativePlace: '',
              politicId: '',
              email: '',
              phone: '',
              address: '',
              departmentId: '',
              departmentName: '所属部门...',
              jobLevelId: '',
              posId: '',
              engageForm: '',
              tiptopDegree: '',
              specialty: '',
              school: '',
              beginDate: '',
              workID: '',
              contractTerm: '',
              conversionTime: '',
              notWorkDate: '',
              beginContract: '',
              endContract: '',
              workAge: ''
        }
        this.depName=''
      },
      //删除
      deleteEmp(data){
        this.$confirm('此操作将永久删除【'+data.name+'】, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteRequest("/employee/basic/"+data.id).then(resp=>{
            if(resp){
              this.initEmps();
            }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
      },
      //员工添加与编辑
      doAddEmp(){
        if(this.emp.id){
          this.$refs['empForm'].validate((valid)=>{
            if(valid){
              this.putRequest("/employee/basic/",this.emp).then(resp=>{
                if(resp){
                  this.dialogVisible=false;
                  this.initEmps();
                }
              })
            }
        })
        }else{
          //表单校验
           this.$refs['empForm'].validate((valid)=>{
            if(valid){
              this.postRequest("/employee/basic/",this.emp).then(resp=>{
                if(resp){
                  this.dialogVisible=false;
                  this.initEmps();
                }
              })
            }
        })
        }
      },
      //部门树的选中显示和关闭
      handleNodeClick(data){
        this.depName=data.name;
        this.emp.departmentId=data.id;
        this.popVisible=!this.popVisible;
      },
      //高级检索树显示与关闭
      advanceHandleNodeClick(data){
        this.depName=data.name;
        this.searchValue.departmentId=data.id;
        this.popVisible1=!this.popVisible1;
      },
      //部门树的显示
      showDepView(){
        this.popVisible=!this.popVisible;
      },
      //高级检索部门树展示
      showDepView2(){
        this.popVisible1=!this.popVisible1;
      },
      //职位
      initPositions(){
        this.getRequest("/employee/basic/position/").then(resp=>{
          if(resp){
            this.positions=resp;
          }
        })
      },
      //工号
      maxWorkId(){
        this.getRequest("/employee/basic/maxworkid/").then(resp=>{
          if(resp){
            this.emp.workID=resp;
          }
        })
      },
      //数据回显
      initData(){
        if(!window.sessionStorage.getItem("nations")){
          this.getRequest("/employee/basic/nation/").then(resp=>{
            if(resp){
              this.nations=resp;
              window.sessionStorage.setItem("nations",JSON.stringify(resp))
            }
          })
        }else{
          this.nations=JSON.parse(window.sessionStorage.getItem("nations"))
        }

        if(!window.sessionStorage.getItem("joblevels")){
          this.getRequest("/employee/basic/joblevel/").then(resp=>{
            if(resp){
              this.joblevels=resp;
              window.sessionStorage.setItem("joblevels",JSON.stringify(resp))
            }
          })
        }
        else{
          this.joblevels=JSON.parse(window.sessionStorage.getItem("joblevels"))
        }

        if(!window.sessionStorage.getItem("politicsstatus")){
          this.getRequest("/employee/basic/politicsstatus/").then(resp=>{
            if(resp){
              this.politicsstatus=resp;
              window.sessionStorage.setItem("politicsstatus",JSON.stringify(resp))
            }
          })
        }
         else{
          this.politicsstatus=JSON.parse(window.sessionStorage.getItem("politicsstatus"))
        }
        if(!window.sessionStorage.getItem("deps")){
          this.getRequest("/employee/basic/dep/").then(resp=>{
            if(resp){
              this.allDeps=resp;
              window.sessionStorage.setItem("deps",JSON.stringify(resp))
            }
          })
        }
         else{
          this.allDeps=JSON.parse(window.sessionStorage.getItem("deps"))
        }
      },
      //点击添加按钮触发数据回显方法
      showAddEmpView(){
        this.title='添加员工';
        this.empNull();
        this.dialogVisible=true;
        this.maxWorkId();
      },
      //点击编辑按钮触发数据回显方法
      showEditEmpView(data){
        this.title='编辑员工信息';
        this.initPositions();
        this.initData();
        this.depName=data.department.name;
        this.emp=data;
        this.dialogVisible=true;
      },
      sizeChange(currentSize){
        this.size=currentSize;
        this.initEmps();
      },
      currentChange(currentPage){
        this.page=currentPage;
        this.initEmps();
      },
      //页面数据
      initEmps(type){
            this.loading=true;
            let url="/employee/basic/?page="+this.page+"&size="+this.size;
            if(type && type=='advanced'){
              if(this.searchValue.politicId){
                  url+="&politicId="+this.searchValue.politicId;
              }
              if(this.searchValue.nationId){
                url+="&nationId="+this.searchValue.nationId;
              }
              if(this.searchValue.jobLevelId){
                url+="&jobLevelId="+this.searchValue.jobLevelId;
              }
              if(this.searchValue.posId){
                url+="&posId="+this.searchValue.posId;
              }
              if(this.searchValue.engageForm){
                url+="&engageForm="+this.searchValue.engageForm;
              }
              if(this.searchValue.departmentId){
                url+="&departmentId="+this.searchValue.departmentId;
              }
              if(this.searchValue.beginDateScope){
                url+="&beginDateScope="+this.searchValue.beginDateScope;
              }
              this.getRequest(url).then(resp=>{
                this.loading=false;
                if(resp){
                    this.emps=resp.data;
                    this.total=resp.total;
                }
              })
            }else{
            this.getRequest("/employee/basic/?page="+this.page+"&size="+this.size+"&name="+this.keyword).then(resp=>{
                this.loading=false;
                if(resp){
                    this.emps=resp.data;
                    // alert(this.emps);
                    this.total=resp.total;
                }
            })
          }
        }
    }
}
</script>

<style scoped>

</style>