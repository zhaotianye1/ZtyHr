<template>
  <div>
    <div>
        <el-button icon="el-icon-plus" type="primary" size="mini" @click="showAddSalary">添加账套</el-button>
        <el-button size="mini" type="success" @click="initSalaries" icon="el-icon-refresh"></el-button>
    </div>
        <div>
          <el-table
            :data="salaries"
            stripe
            size="mini"
            style="width: 100%">
            <el-table-column
              type="selection"
              width="40">
            </el-table-column>
            <el-table-column
              width="120"
              prop="name"
              label="账套名称">
            </el-table-column>
            <el-table-column
              width="70"
              prop="basicSalary"
              label="基本工资">
            </el-table-column>
            <el-table-column
              width="70"
              prop="trafficSalary"
              label="交通补助">
            </el-table-column>
            <el-table-column
              width="70"
              prop="lunchSalary"
              label="午餐补助">
            </el-table-column>
            <el-table-column
              prop="bonus"
              width="70"
              label="奖金">
            </el-table-column>
            <el-table-column
              width="100"
              label="启用时间">
              <template slot-scope="scope">{{ scope.row.createDate | formatDate}}</template>
            </el-table-column>
            <el-table-column label="养老金" align="center">
              <el-table-column
                prop="pensionPer"
                width="70"
                label="比率">
              </el-table-column>
              <el-table-column
                width="70"
                prop="pensionBase"
                label="基数">
              </el-table-column>
            </el-table-column>
            <el-table-column label="医疗保险" align="center">
              <el-table-column
                width="70"
                prop="medicalPer"
                label="比率">
              </el-table-column>
              <el-table-column
                prop="medicalBase"
                width="70"
                label="基数">
              </el-table-column>
            </el-table-column>
            <el-table-column label="公积金" align="center">
              <el-table-column
                width="70"
                prop="accumulationFundPer"
                label="比率">
              </el-table-column>
              <el-table-column
                prop="accumulationFundBase"
                width="70"
                label="基数">
              </el-table-column>
            </el-table-column>
            <el-table-column label="操作" align="center">
              <el-table-column label="编辑" align="center">
                <template slot-scope="scope">
                  <el-button size="mini" @click="editSalary(scope.row)">编辑</el-button>
                </template>
              </el-table-column>
              <el-table-column label="删除" align="center">
                <template slot-scope="scope">
                  <el-button size="mini" type="danger" @click="deleteSalary(scope.row)">删除</el-button>
                </template>
              </el-table-column>
            </el-table-column>
          </el-table>
        </div>
        <!-- <div style="text-align: left;margin-top: 10px" v-if="salaries!=0">
          <el-button type="danger" round size="mini" >批量删除
          </el-button>
        </div> -->
        <el-dialog
        :title="dialogTitle"
        :visible.sync="dialogVisible"
        width="50%"
       >
        <div style="display: flex;justify-content: flex-around;">
          <el-steps :active="actionName" direction="vertical">
            <el-step :title="name" size="mini" v-for="(name,index) in salaryName" :key="index"></el-step>
          </el-steps>
          <el-input v-model="salary[title]" :placeholder="'请输入'+salaryName[index]+'...'" v-for="(value,title,index) in salary" :key="index" v-show="actionName==index" style="width:500px"></el-input>
          </div>
          <span slot="footer" class="dialog-footer">
          <el-button size="mini" @click="cancel">取消</el-button>
          <el-button size="mini" @click="last" v-show="actionName!=0">上一步</el-button>
          <el-button type="primary"  size="mini" @click="next">{{actionName==10?'完成':'下一步'}}</el-button> 
          </span>
      </el-dialog>
  </div>
</template>
<script>
  export default {
    name:"SalSob",
    data() {
      return {
        salaries: [],
        dialogTitle:'添加工资账套',
        actionName:0,
        dialogVisible:false,
        xiaoshi:false,
        salary: {
          basicSalary: '',
          trafficSalary: '',
          lunchSalary: '',
          bonus: '',
          pensionBase: '',
          pensionPer: '',
          medicalBase: '',
          medicalPer: '',
          accumulationFundBase: '',
          accumulationFundPer: '',
          name:''
        },
        salaryName:[
          '基本工资',
          '交通补助',
          '午餐补助',
          '奖金',
          '养老金基数',
          '养老金比率',
          '医疗保险基数',
          '医疗保险比率',
          '公积金基数',
          '公积金比率',
          '账套名称'

        ]
      }
    },
    mounted(){
      this.initSalaries();
    },
    methods: {
      editSalary(data){
        this.dialogTitle='编辑工资账套';
        this.dialogVisible=true;
        this.salary.basicSalary=data.basicSalary;
        this.salary.trafficSalary=data.trafficSalary;
        this.salary.lunchSalary=data.lunchSalary;
        this.salary.bonus=data.bonus;
        this.salary.pensionPer=data.pensionPer;
        this.salary.pensionBase=data.pensionBase;
        this.salary.medicalPer=data.medicalPer;
        this.salary.medicalBase=data.medicalBase;
        this.salary.accumulationFundPer=data.accumulationFundPer;
        this.salary.accumulationFundBase=data.accumulationFundBase;
        this.salary.name=data.name;
        this.salary.id=data.id;
      },
      deleteSalary(data){
          this.$confirm('此操作将删除【'+data.name+'】账套，是否继续','提示',{
            cancelButtonText:'取消',
            confirmButtonText:'确认'
          }).then(()=>{
            this.deleteRequest("/salary/sob/"+data.id).then(resp=>{
              this.initSalaries();
            })
          }).catch(()=>{
            this.$message.info("用户取消删除!");
          })
      },
      cancel(){
        this.dialogVisible=false;
      },
      last(){
        this.actionName--;
        },
      
      next(){
        
        if(this.actionName==10){
          if(this.salary.id){
              this.putRequest("/salary/sob/",this.salary).then(resp=>{
                if(resp){
                  this.initSalaries();
                  this.dialogVisible=false;
                  this.actionName=0;
                   this. salary={
          basicSalary: '',
          trafficSalary: '',
          lunchSalary: '',
          bonus: '',
          pensionBase: '',
          pensionPer: '',
          medicalBase: '',
          medicalPer: '',
          accumulationFundBase: '',
          accumulationFundPer: '',
          name:''
               }
                }
              })
          }else{
          this.postRequest("/salary/sob/",this.salary).then(resp=>{
            if(resp){
              this.initSalaries();
              this.dialogVisible=false;
              this.actionName=0;
            }
          })
          }
          return;
        }
        this.actionName++;
      },
      showAddSalary(){
         //数据初始化
         this. salary={
          basicSalary: '',
          trafficSalary: '',
          lunchSalary: '',
          bonus: '',
          pensionBase: '',
          pensionPer: '',
          medicalBase: '',
          medicalPer: '',
          accumulationFundBase: '',
          accumulationFundPer: '',
          name:''
               }
        this.dialogVisible=true;
        this.actionName=0;
        this.dialogTitle='添加工资账套'
      },
      //账套显示
      initSalaries(){
        this.getRequest("/salary/sob/").then(resp=>{
          if(resp){
            this.salaries=resp;
            console.log(salaries)
          }
        })
      }
    },
  };
</script>
<style>

</style>
