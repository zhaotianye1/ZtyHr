<template>
    <div style="width:500px">
        <el-input
            placeholder="请输入部门名称进行搜索..."
            prefix-icon="el-icon-search"
            v-model="filterText">
        </el-input>

        <el-tree
            class="filter-tree"
            :data="deps"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree">
            <span class="custom-tree-node" style="display: flex; justify-content: space-between; width:100%" slot-scope="{ node, data }">
            <span>{{ data.name }}</span>
            <span>
            <el-button
                type="primary"
                size="mini"
                style="padding:2px"
                @click="() => showAddDepView(data)">
                添加部门
            </el-button>
            <el-button
                type="danger"
                size="mini"
                style="padding:2px"
                @click="() => deleteDep(data)">
                删除部门
            </el-button>
            </span>
      </span>
        </el-tree>
        <el-dialog
            title="添加部门"
            :visible.sync="dialogVisible"
            width="30%">
            <span style="display: flex; justify-content: space-between; width:100%">
                <div>
                    <el-tag>上级部门</el-tag>
                    <span>{{pname}}</span>
                </div>
                <div>
                    <el-tag>部门名称</el-tag>
                    <el-input v-model="dep.name" placeholder="请输入部门名称" prefix-icon="el-icon-plus"></el-input>
                </div>
            </span>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="doAddDep()">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
export default {
    name:"DepMana",
    data(){
        return{
            filterText:'',
            dialogVisible:false,
            dep:{
                name:'',
                parentId:-1,
            },
            pname:'',
            deps:[],
            defaultProps: {
                children: 'children',
                label: 'name'
            }
        }
    },
    watch: {
      filterText(val) {
        this.$refs.tree.filter(val);
      }
    },
    mounted(){
        this.initDeps();
    },
    methods:{
        initDep(){
            this.dep={
                name:'',
                parentId:-1
            };
            this.pname='';
        },
        addDep2Deps(deps,dep){
            for(let i=0;i<deps.length;i++){
                let d=deps[i];
                if(d.id==dep.parentId){
                    d.children=d.children.concat(dep);
                    if(d.children.length>0){
                        d.parent=true;
                    }
                    return;
                }else{
                    this.addDep2Deps(d.children,dep);
                }
            }
        },
        doAddDep(){
            this.postRequest("/system/basic/department/",this.dep).then(resp=>{
                if(resp){
                    this.addDep2Deps(this.deps,resp.obj)
                    //初始化变量
                    this.initDep();
                    this.dialogVisible=false;
                }
            })
        },
        showAddDepView(data){
            this.pname=data.name;
            this.dialogVisible=true;
            this.dep.parentId=data.id;
        },
        removeDepFromDeps(p,deps,id){
            for(let i;i<deps.length;i++){
                let d=deps[i];
                if(d.id==id){
                    deps.splice(i,1);
                    if(deps.length==0){
                        p.parent=false;
                    }
                    return;
                }else{
                    this.removeDepFromDeps(d,d.children,id);
                }
            }
        },
        deleteDep(data){
            if(data.parent){
                this.$message.error("父部门删除失败！")
            }else{
            this.$confirm('此操作将永久删除【'+data.name+'】部门, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
            }).then(() => {
                this.deleteRequest("/system/basic/department/"+data.id).then(resp=>{
                    if(resp){
                        this.initDeps();
                        this.removeDepFromDeps(null,this.deps,data.id);
                    }
                })
            }).catch(() => {
            this.$message({
                type: 'info',
                message: '已取消删除'
            });          
            });
            }
        },
        filterNode(value, data) {
            if (!value) return true;
            return data.name.indexOf(value) !== -1;
        },
        initDeps(){
            this.getRequest("/system/basic/department/").then(resp=>{
                if(resp){
                    this.deps=resp;
                }
            })
        }
    }
}
</script>

<style scoped>

</style>