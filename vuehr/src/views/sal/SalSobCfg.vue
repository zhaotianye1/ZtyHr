<template>
    <div>
        <div>
            <el-table :data="emps" border stripe>
                <el-table-column type="selection" align="left" width="55"></el-table-column>
                <el-table-column prop="name" label="姓名" fixed width="120" align="left"></el-table-column>
                <el-table-column prop="workId" label="工号" fixed width="120" align="left"></el-table-column>
                <el-table-column prop="email" label="电子邮件" fixed width="200" align="left"></el-table-column>
                <el-table-column prop="phone" label="电话号码" fixed width="150" align="left"></el-table-column>
                <el-table-column prop="department.name" label="所属部门" fixed width="150" align="left"></el-table-column>
                <el-table-column label="所属部门" align="left">
                    <template slot-scope="scope">
                        <el-tooltip placement="right" v-if="scope.row.salary">
                            <div slot="content">
                                <table>
                                    <tr>
                                        <td>工资账套名称:</td>
                                        <td>{{scope.row.salary.name}}</td>
                                    </tr>
                                    <tr>
                                        <td>基本工资:</td>
                                        <td>{{scope.row.salary.basicSalary}}</td>
                                    </tr>
                                    <tr>
                                        <td>交通补助:</td>
                                        <td>{{scope.row.salary.trafficSalary}}</td>
                                    </tr>
                                    <tr>
                                        <td>午餐补助:</td>
                                        <td>{{scope.row.salary.lunchSalary}}</td>
                                    </tr>
                                    <tr>
                                        <td>奖金:</td>
                                        <td>{{scope.row.salary.bonus}}</td>
                                    </tr>
                                    <tr>
                                        <td>启用时间:</td>
                                        <td>{{scope.row.salary.createDate}}</td>
                                    </tr>
                                    <tr>
                                        <td>养老金比率:</td>
                                        <td>{{scope.row.salary.pensionPer}}</td>
                                    </tr>
                                    <tr>
                                        <td>养老金基数:</td>
                                        <td>{{scope.row.salary.pensionBase}}</td>
                                    </tr>
                                    <tr>
                                        <td>医疗保险比率:</td>
                                        <td>{{scope.row.salary.medicalPer}}</td>
                                    </tr>
                                    <tr>
                                        <td>医疗保险基数:</td>
                                        <td>{{scope.row.salary.medicalBase}}</td>
                                    </tr>
                                    <tr>
                                        <td>公积金比率:</td>
                                        <td>{{scope.row.salary.accumulationFundPer}}</td>
                                    </tr>
                                    <tr>
                                        <td>公积金基数:</td>
                                        <td>{{scope.row.salary.accumulationFundBase}}</td>
                                    </tr>
                                </table>
                            </div>
                        <el-tag>{{scope.row.salary.name}}</el-tag>
                        </el-tooltip>
                        <el-tag v-else>暂未设置</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center">
                    <template slot-scope="scope">
                         <el-popover
                            placement="left"
                            title="修改工资账套"
                            width="200"
                            @show="showView(scope.row.salary)"
                            @hide="hideView(scope.row)"
                            trigger="click">
                            <div>
                                 <el-select v-model="currentSalary" placeholder="请选择">
                                    <el-option
                                    v-for="item in salary"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.id">
                                    </el-option>
                                </el-select>
                            </div>
                            <el-button slot="reference" type="danger">修改工资账套</el-button>
                        </el-popover>
                    </template>
                </el-table-column>
            </el-table>
            <div style="display: flex; justify-content:flex-end">
                <el-pagination
                        background
                        @current-change="currentChange"
                        @size-change="sizeChange"
                        layout="sizes, prev, pager, next, jumper, ->, total, slot"
                        :total="total">
                </el-pagination>
            </div>
        </div>
    </div>  
</template>

<script>
export default {
    name:"SalSobCfg",
    data(){
        return{
            emps:[],
            total:0,
            currentPage:1,
            currentSize:10,
            salary:[],
            currentSalary:null
        }
    },
    mounted(){
        this.initEmps();
        this.initSalary();
    },
    methods:{
        sizeChange(size){
            this.currentSize=size;
            this.initEmps();
        },
        currentChange(page){
            this.currentPage=page;
            this.initEmps();
        },
        hideView(data){
            if(this.currentSalary){
            this.putRequest("/salary/sobcfg/?eid="+data.id+"&sid="+this.currentSalary).then(resp=>{
                if(resp){
                    this.initEmps();
                }
            })
            }
        },
        showView(data){
            if(data){
                this.currentSalary=data.id;
            }else{
                this.currentSalary=null;
            }
        },
        initSalary(){
            this.getRequest("/salary/sobcfg/salary/").then(resp=>{
                if(resp){
                    this.salary=resp;
                }
            })
        },
        initEmps(){
            this.getRequest("/salary/sobcfg/?page="+this.currentPage+"&size="+this.currentSize).then(resp=>{
                if(resp){
                    this.emps=resp.data;
                    this.total=resp.total;
                }
            })
        }
    }
}
</script>

<style scoped>

</style>