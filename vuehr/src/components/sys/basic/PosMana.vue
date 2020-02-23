<template>
    <div>
        <div>
            <el-input class="addPosInput"
            placeholder="添加职位"
            prefix-icon="el-icon-plus"
            v-model="pos.name"
            clearable>
            </el-input>
            <el-button 
            icon="el-icon-plus"
            type="primary"
            @click="addPosition">添加</el-button>
        </div>
        <div class="posManaMain">
            <el-table
                :data="position"
                border
                @selection-change="handleSelectionChange"
                size="small"
                stripe
                style="width: 80%">
                <el-table-column
                type="selection"
                width="55">
                </el-table-column>
                <el-table-column
                prop="id"
                label="编号"
                width="330">
                </el-table-column>
                <el-table-column
                prop="name"
                label="职位名称"
                width="330">
                </el-table-column>
                <el-table-column
                prop="creatDate"
                label="创建日期"
                width="330">
                </el-table-column>
                <el-table-column label="操作">
      <template slot-scope="scope">
        <el-button
          size="mini"
          @click="showEditView(scope.$index, scope.row)">编辑</el-button>
        <el-button
          size="mini"
          type="danger"
          @click="handleDelete(scope.$index, scope.row)">删除</el-button>
      </template>
    </el-table-column>
            </el-table>
            <el-button @click="deleteMany" type="danger" size="small" style="margin-top:10px" :disabled="multipleSelection.length==0">批量删除</el-button>
        </div>
            <el-dialog
            title="修改职位"
            :visible.sync="dialogVisible"
            width="30%">
            <div>
                <el-tag style="margin-left:10px">
                    职位名称
                </el-tag>
                <el-input class="updatePosInput" size="small" v-model="updatePos.name"></el-input>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button size="small" @click="dialogVisible=false">取 消</el-button>
                <el-button size="small" type="primary" @click="doUpdate">确 定</el-button>
            </span>
            </el-dialog>
    </div>
</template>

<script>
export default {
    name:"PosMana",
    data(){
        return{
            pos:{
                name:'',
            },
            updatePos:{
                name:''
            },
            multipleSelection:[],
            dialogVisible:false,
            position:[]
        }
    },
    mounted(){
        this.initPositions();
    },
    methods:{
        addPosition(){
            if(this.pos.name){
                this.postRequest("/system/basic/pos/",this.pos).then(resp=>{
                    if(resp){
                        this.initPositions();
                        this.pos.name='';
                    }
                })
            }else{
                this.$message.error('职位不能为空');
            }
        },
        showEditView(index, data) {
            Object.assign(this.updatePos,data);
            this.dialogVisible = true;
      },
        doUpdate(){
            this.putRequest("system/basic/pos/",this.updatePos).then(resp=>{
                if(resp){
                    this.initPositions();
                    this.updatePos.name='';
                    this.dialogVisible=false;
                }
            })
        },
        handleSelectionChange(val){
            this.multipleSelection=val;
        },
        handleDelete(index, data) {
         this.$confirm('此操作将永久删除【'+data.name+'】, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.deleteRequest("/system/basic/pos/"+data.id).then(resp=>{
              if(resp){
                this.initPositions();
              }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
      },
      deleteMany(){
           this.$confirm('此操作将永久删除【'+this.multipleSelection.length+'】条职位, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          let ids='?ids=';
            this.multipleSelection.forEach(item => {
                ids +='ids='+item.id+'&';
            })
          this.deleteRequest("/system/basic/pos/"+ids).then(resp=>{
              if(resp){
                this.initPositions();
              }
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
      },
        initPositions(){
            this.getRequest("/system/basic/pos/").then(resp=>{
                if(resp){
                    this.position=resp;
                }
            })
        }
    }
}
</script>

<style>
    .updatePosInput{
        widows: 200px;
        margin-left:10px; 
    }
    .addPosInput{
        width: 400px;
        margin-right: 5px    
    }
    .posManaMain{
        margin-top: 10px;
    }

</style>