const config = require("../../config.js")
//index.js
//获取应用实例
const app = getApp();
var that = null;
Page({
  data: {
    selected: true, //tab
    selected1: false,//tab
    modalHidden: false,//活动规则弹窗
    isScroll:true,
    corpsPanking:[],
    usersPanking:[],
    miniUser:[],     //主页面加载数据user
    animationData: {}
  },
  onLoad: function () {
    that = this;
    var userInfo = wx.getStorageSync('userInfo');
    this.getUser(!userInfo.id ? '' : userInfo.id);
    this.pankingCorps(!userInfo.id ? '' : userInfo.id);
   
    var animation = wx.createAnimation({
      duration: 1000,
      timingFunction: 'ease-in-out',
    })
    this.animation = animation

    animation.bottom(50).step().bottom(20).step()
    this.setData({
      animationData: animation.export()
    })

    setInterval(function () {
      animation.bottom(20).step().bottom(50).step()
      this.setData({
        animationData: animation.export()
      })
    }.bind(this), 1000)
  },
  getUser:function(userId){
    wx.request({
      url: config.service.getMiniUser,
      data: {userId:userId},
      method: 'GET',
      dataType: 'json',
      responseType: 'text',
      success: function (resp) {
        if(resp.data.result == true){
          that.setData({
            miniUser: resp.data.obj
          })
        }else{
          wx.showModal({
            title: '提示',
            content: '系统错误'
          })
        }
      }
    })
  },
  pankingCorps:function (userId) {
    wx.request({
      url: config.service.pankingCorps,
      data: {userId:userId},
      method: 'GET',
      dataType: 'json',
      responseType: 'text',
      success: function (resp) {
        that.setData({
          corpsPanking: resp.data.obj
        })
      }
    })
  },
  pankingUsers:function(){
    wx.request({
      url: config.service.pankingUsers,
      data:{},
      method:'GET',
      dataType:'json',
      responseType:'text',
      success:function(resp){
        that.setData({
          usersPanking:resp.data.obj
        })
      }
    })
  },
  scrollBottom: function (e) {
    console.log(e.target)
    if (wx.pageScrollTo) {
      wx.pageScrollTo({
        scrollTop: e.target.offsetTop + 200
      })
    } else {
      wx.showModal({
        title: '提示',
        content: '当前微信版本过低，无法使用该功能，请升级到最新微信版本后重试。'
      })
    }
  },
  selected: function (e) {
    
    this.setData({
      selected1: false,
      selected: true
    })
  },
  selected1: function (e) {
    this.pankingUsers();
    this.setData({
      selected: false,
      selected1: true
    })
  },
  ruleinfo: function (e) {
    this.setData({
      modalHidden: !this.data.modalHidden,
      isScroll: false
    })
  },
  ruleInfoCancel:function(){
    this.setData({
      modalHidden: !this.data.modalHidden,
      isScroll: true
    })
  },
  sign_click:function(){
    console.log(that.data.miniUser)
    wx.navigateTo({
      url: "/pages/sign/sign?userId=" + this.data.miniUser.userId
    })
  }
})
