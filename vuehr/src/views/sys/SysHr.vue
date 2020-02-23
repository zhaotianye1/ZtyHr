<template>
    <div>
        <div style="margin-top:10px; display: flex;justify-content: center;">
            <el-input v-model="keywords" placeholder="请输入用户名搜索用户..." prefix-icon="el-icon-search" style="width:400px; margin-right:10px" @keydown.enter.native="search"></el-input>
            <el-button icon="el-icon-search" type="primary" @click="search">搜索</el-button>
        </div>
        <div style="margin-top:10px; display: flex;flex-wrap:wrap; justify-content:space-around">
            <el-card class="hrcard" v-for="(hr,index) in hrs" :key="index">
                <div slot="header" class="clearfix">
                    <span>{{hr.name}}</span>
                    <el-button style="float: right; padding: 3px 0" type="danger" icon="el-icon-delete" @click="doDelete(hr)">删除</el-button>
                </div>
                <div style="width:100%; display:flex; justify-content:center">
                    <img :src="hr.userface" :alt="hr.name" :title="hr.name" style="width:70px; height:70px; border-radius:70px">
                </div>
                <div style="margin-top:20px;">
                    <div style="font-size:12px; color:#409eff;">
                        <div>用户名:{{hr.name}}</div>
                        <div>手机号码:{{hr.phone}}</div>
                        <div>地址:{{hr.address}}</div>
                        <div>用户状态:<el-switch 
                            v-model="hr.enabled"
                            active-text="启用"
                            inactive-text="禁用"
                            @change="enabledChange(hr)"
                            active-color="#13ce66"
                            inactive-color="#ff4949">
                            </el-switch></div>
                        <div>用户角色:<el-tag style="margin-right:3px" type="success" v-for="(role,indexj) in hr.roles" :key="indexj">{{role.nameZh}}</el-tag>
                        <el-popover
                            placement="bottom"
                            title="角色列表"
                            width="230"
                            @show="show(hr)"
                            @hide="hide(hr)"
                            trigger="click">
                            <el-select v-model="selectedRoles" multiple placeholder="请选择">
                                <el-option
                                v-for="(r,indexk) in allroles"
                                :key="indexk"
                                :label="r.nameZh"
                                :value="r.id">
                                </el-option>
                            </el-select>                          
                            <el-button  slot="reference" icon="el-icon-more" type="text"></el-button>
                        </el-popover>
                        </div>
                        <div>备注: {{hr.remark}}</div>
                    </div>
                </div>
            </el-card>
        </div>
    </div>
</template>

<script>
export default {
    name:"SysHr",
    data(){
        return{
            keywords:'',
            hrs:[],
            allroles:[],
            selectedRoles:[]
        }
    },
    mounted(){
        this.initHrs();
    },
    methods:{
        doDelete(hr){
            this.$confirm('此操作将永久删除【'+hr.name+'】, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteRequest("/system/hr/"+hr.id).then(resp=>{
              if(resp){
                this.initHrs();
              }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
        },
        search(){
            this.initHrs();
        },
        hide(hr){
            let roles=[];
            Object.assign(roles,hr.roles);
            let flag=false;
            if(roles.length!=this.selectedRoles.length){
                flag=true;
            }else{
                for(let i=0;i<roles.length;i++){
                    let role=roles[i];
                    for(let j=0;j<this.selectedRoles.length;j++){
                        let sr=this.selectedRoles[j];
                        if(role.id==sr){
                            roles.splice(i,1);
                            i--;
                            break;
                        }
                    }
                }
                if(roles.length!=0){
                    flag=true;
                }
            }
            if(flag){
            let url='/system/hr/role?hrid='+hr.id;
            this.selectedRoles.forEach(sr=>{
                url+='&rids='+sr;
            })
            this.putRequest(url).then(resp=>{
                if(resp){
                    this.initHrs();
                }
            })
            }
        },
        show(hr){
            this.initRoles();
            let roles=hr.roles;
            this.selectedRoles=[];
            roles.forEach(r=> {
                this.selectedRoles.push(r.id);
            });
        },
        initRoles(){
            this.getRequest("/system/hr/role/").then(resp=>{
                if(resp){
                    this.allroles=resp;
                }
            })
        },
        enabledChange(hr){
            delete hr.roles;
            this.putRequest("/system/hr/",hr).then(resp=>{
                if(resp){
                    this.initHrs();
                }
            })
        },
        initHrs(){
            this.getRequest("/system/hr/?keywords="+this.keywords).then(resp=>{
                if(resp){
                    this.hrs=resp;
                }
            })
        }
    }
}
</script>

<style>
    .hrcard{
        width: 350px;
        margin-left:30px;
        margin-top :20px; 
    }
</style>