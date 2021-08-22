import Vue from 'vue'
import Vuex from 'vuex'
import {getRequest} from '../utils/api'
// import { Stomp } from '../utils/stomp'
import SockJS from 'sockjs-client'
import Stomp from 'stompjs'
import {Notification} from 'element-ui';


Vue.use(Vuex)

const now = new Date();

const store =  new Vuex.Store({
    state:{
        routes:[],
        sessions:{},
        hrs:[],
        currentSession:null,
        currentHr:JSON.parse(window.sessionStorage.getItem("user")),
        filterKey:'',
        stomp:null,
        isDot:{}
    },
    mutations:{
      INIT_CURRENTHR(state,hr){
        state.currentHr=hr;
      },
        initRoutes(state,data){
            state.routes=data;
        },
        changeCurrentSession (state,currentSession) {
            Vue.set(state.isDot,state.currentHr.username+'#'+currentSession.username,false);
			      state.currentSession = currentSession;
		},
		addMessage (state,msg) {
            let message =state.sessions[state.currentHr.username+"#"+msg.to];
            // console.log(state.sessions[state.currentHr.username+"#"+msg.to])
            if(!message){
                Vue.set(state.sessions,state.currentHr.username+"#"+msg.to,[]);
                // state.sessions[state.currentHr.username+"#"+msg.to]=[];
            }
            state.sessions[state.currentHr.username+"#"+msg.to].push({
              content:msg.content,
              date: new Date(),
              self:!msg.notSelf
			})
		},
    INIT_DATA (state) {
        //浏览器本地历史聊天记录
      let data = localStorage.getItem('vue-chat-session');
      //console.log(data)
      if (data) {
        state.sessions = JSON.parse(data);
      }
    },
    INIT_HR(state,data){
        state.hrs=data;
    }
    },
    actions:{
        connect(context){
            context.state.stomp=Stomp.over(new SockJS('/ws/ep'));
            context.state.stomp.connect({},success=>{
                context.state.stomp.subscribe('/user/queue/chat',msg=>{
                    let receiveMsg=JSON.parse(msg.body);
                    if(!context.state.currentSession || receiveMsg.from!=context.state.currentSession.username){
                      // console.log("11"+receiveMsg.from)
                      // console.log("_______"+msg)
                      // console.log("22"+context.state.currentSession.username)
                      Notification.info({
                        title:'['+receiveMsg.fromName+']发来一条消息',
                        message:receiveMsg.content.length>10?receiveMsg.content.substr(0,10):receiveMsg.content,
                        position:'bottom-right'
                      })
                      Vue.set(context.state.isDot,context.state.currentHr.username+'#'+receiveMsg.from,true);
                    }
                    receiveMsg.notSelf=true;
                    receiveMsg.to=receiveMsg.from;
                    // console.log(localStorage.getItem('vue-chat-session'))
                    context.commit('addMessage',receiveMsg);
                })
            },error=>{

            })
        },
        initData (context) {
          context.commit('INIT_DATA')
          getRequest('/chat/hrs').then(resp=>{
              if(resp){
                context.commit('INIT_HR',resp);
              }
          })
        }
    }
})

store.watch(function (state) {
    return state.sessions
  },function (val) {
    // console.log('CHANGE: ', val);
    localStorage.setItem('vue-chat-session', JSON.stringify(val));
    // console.log('val-----',val['lubenwei#系统管理员'][2].content);
  },{
    
    deep:true/*这个貌似是开启watch监测的判断,官方说明也比较模糊*/
  })
  
  
  export default store;