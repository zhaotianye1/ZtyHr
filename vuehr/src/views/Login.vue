<template>
    <div>
        <el-form :rules="rules" ref="loginForm" :model="loginForm"  v-loading="loading"
         element-loading-text="正在登陆..." class="loginContainer">
            <h3 class="LoginTitle">系统登陆</h3>
            <el-form-item prop="username">
                <el-input type="text" v-model="loginForm.username" auto-complete="off" placeholder="请输入用户名"></el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input type="text" v-model="loginForm.password" auto-complete="off" placeholder="请输入密码" @keydown.enter.native="Submit"></el-input>
            </el-form-item>
            <el-checkbox v-model="checked" class="LoginRemember"></el-checkbox>
            <el-button type="primary" style="width:100%" @click="Submit">登录</el-button>
        </el-form>
    </div>
</template>

<script>
export default {
    name:"Login",
    data(){
        return{
            loading:false,
            loginForm:{
                username:'admin',
                password:'123'
            },
            checked:true,
            rules:{
                username:[{required: true,message:'请输入用户名',trigger:'blur'}],
                password:[{required: true,message:'请输入密码',trigger:'blur'}]
                
            }
        }
    },
    methods:{
        Submit() {
        this.loading=true;
        this.$refs.loginForm.validate((valid) => {
          if (valid) {
              this.postKeyValueRequest('/doLogin',this.loginForm).then(resp=>{
                  this.loading=false;
                  if(resp){
                      window.sessionStorage.setItem("user",JSON.stringify(resp.obj));
                      let path=this.$route.query.redirect;
                      this.$router.replace((path=='/' || path== undefined) ? '/home' : path);
                  }
              })
            // alert('成功！');
          } else {
            this.$message.error('请补全所有字段');
            return false;
          }
        });
      },
    }
}
</script>

<style>
    .loginContainer{
        border-radius: 15px;
        background-clip:padding-box;
        margin: 180px auto;
        width:350px;
        padding: 35px 35px 15px 35px;
        background:white;
        border: 1px solid dimgray;
        box-shadow: 0 0 25px darkslateblue;
    }
    .LoginTitle{
        margin: 15px auto 15px auto;
        text-align: center;
        color: black;
    }
    .LoginRemember{
        text-align: left;
        margin: 0px 0px 20px 0px;
    }
</style>