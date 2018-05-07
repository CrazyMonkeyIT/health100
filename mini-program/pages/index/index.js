const config = require("../../config.js")
const userUtil = require("../../global-js/userUtil.js")
//获取应用实例
const app = getApp();
var that = null;
Page({
  data: {
    selected: true, //tab
    selected1: false,//tab
    modalHidden: false,//活动规则弹窗
    index_back_image:'../images/index_back.jpg',
    isScroll:true,
    corpsPanking:[],
    usersPanking:[],
    miniUser:[],     //主页面加载数据user
    animationData: {}
  },
  onLoad: function (param){
    var scene = decodeURIComponent(param.userId);
    if (!scene) {
      this.sharePoint(scene)
    }
  },
  sharePoint:function(userId){
    wx.request({
      url: config.service.sharePoint,
      data: { userId: userId },
      method: 'GET',
      dataType: 'json',
      responseType: 'text',
      success: function (resp) {
        
      }
    })
  },
  onShow: function () {
    that = this;
    userUtil.login(function(){
      var userInfo = wx.getStorageSync('userInfo');
      that.getUser(userInfo.id);
      that.pankingCorps(userInfo.id);
    });
   
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
        if(resp.data.result === true){
          that.setData({
            miniUser: resp.data.obj
          })
          if (resp.data.obj.oneSign==true){
            that.setData({
              index_back_image:'../images/index_back_one.jpg'
            })
          }
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
    wx.navigateTo({
      url: "/pages/sign/sign"
    })
  },
  sign_0ne_click:function(){
    if (that.data.miniUser.corpsId == 0 || that.data.miniUser.corpsId==null){
      wx.navigateTo({
        url: "/pages/joincorps/joincorps"
      })
    }else{
      wx.navigateTo({
        url: "/pages/corps/corps"
      })
    }
    
  },
  corps_click:function(){
    wx.showModal({
      title: '提示',
      content: '加入战队后，个人总积分将减半， 请确认是否继续',
      success: function (res) {
        if (res.confirm) {
          wx.navigateTo({
            url: "/pages/joincorps/joincorps"
          })
        }
      }
    })
  },
  corps_sign_click:function(){
    wx.navigateTo({
      url: "/pages/corps/corps"
    })
  },
  userInfo_click:function(){
    wx.navigateTo({
      url: "/pages/user/user"
    })
  }
})
