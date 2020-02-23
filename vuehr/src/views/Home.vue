<template>
    <div>
  <el-container>
  <el-header class="homeHeader">
    <div class="title">
      天哥人事管理系统
    </div>
    <el-dropdown class="userInfo" @command="commandHandler">
  <span class="el-dropdown-link">
    <div class="uname">
    {{user.name}}
    </div>
    <i><img :src="user.userface" :alt="user.name"></i>
  </span>
  <el-dropdown-menu slot="dropdown">
    <el-dropdown-item command="userInfo">个人中心</el-dropdown-item>
    <el-dropdown-item command="setting">设置</el-dropdown-item>
    <el-dropdown-item command="logout" divided>注销登录</el-dropdown-item>
  </el-dropdown-menu>
</el-dropdown>
  </el-header>
  <el-container>
    <el-aside width="200px">
          <el-menu 
          @select="menuClick"
      background-color="#545c64"
      text-color="#fff"
      active-text-color="#ffd04b" router unique-opened>
      <el-submenu :index="index+''"  v-for="(item,index) in routes" v-if="!item.hidden" :key="index">
        <template slot="title">
          <i style="color:#409eff;margin-right:10px" :class="item.iconCls"></i>
          <span>{{item.name}}</span>
        </template>
          <el-menu-item :index="child.path" v-for="(child,indexj) in item.children" :key="indexj">{{child.name}}</el-menu-item>        </el-submenu>
    </el-menu>
    </el-aside>
    <el-main>
      <el-breadcrumb separator-class="el-icon-arrow-right" v-if="this.$router.currentRoute.path!='/home'">
  <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
  <el-breadcrumb-item>{{this.$router.currentRoute.name}}</el-breadcrumb-item>
</el-breadcrumb>
<div class="homeWelcome" v-if="this.$router.currentRoute.path=='/home'">
  欢迎来到天哥人事管理系统
</div>
      <router-view class="homeRouterView"/>
    </el-main>
  </el-container>
</el-container>
    </div>
</template>

<script>
export default {
    name:"Home",
    data(){
      return{
        user:JSON.parse(window.sessionStorage.getItem("user"))
      }
    },
    computed:{
      routes(){
        return this.$store.state.routes;
      }
    },
    methods:{
      menuClick(index,indexPath){
         this.$router.push(index);
      },
      commandHandler(cmd){
        if(cmd=='logout'){
          this.$confirm('此操作将注销登录, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {  
            this.getRequest("/logout");
            window.sessionStorage.removeItem("user")
            this.$store.commit('initRoutes',[]);
            this.$router.replace("/");
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消'
          });          
        });
        }
      }
    }
}
</script>

<style>
.homeRouterView{
   margin-top: 10px;
}
.homeWelcome{
  text-align: center;
  font-size: 30px;
  font-family:cursive;
  color: chocolate;
  padding-top: 50px;
}
.homeHeader{
  background-color:#545c64;
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0px 15px;
  box-sizing: border-box;
}
.title{
    font-size: 30px;
    font-family: cursive;
    color:#ffffff;
}
.userInfo{
  cursor: pointer;
}
.el-dropdown-link img{
  width: 48px;
  height: 48px;
  border-radius: 24px;
  margin-left: 10px;
}
.el-dropdown-link{
  display: flex;
  align-items: center;
}
.uname{
  color: #ffffff
}
</style>