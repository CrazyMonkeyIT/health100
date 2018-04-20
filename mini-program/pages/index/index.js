const userUtil = require("../global-js/userUtil.js")
const config = require("../../config.js")
//index.js
//获取应用实例
const app = getApp();
var currentUser = null, that = null;
Page({
  data: {
    selected: true, //tab
    selected1: false,//tab
    one: true, //第一次登录  第二次登录false
    modalHidden: false,//活动规则弹窗
    isScroll:true,
    corpsPanking:[],
    animationData: {}
  },
  //事件处理函数
  bindViewTap: function () {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onLoad: function () {
    that = this;
    this.pankingCorps();
    console.log(this.data.corpsPanking);
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
    this.setData({
      selected: false,
      selected1: true
    })
  },
  pankingCorps:function () {
    wx.request({
      url: config.service.pankingCorps,
      data: {},
      method: 'POST',
      dataType: 'json',
      responseType: 'text',
      success: function (resp) {
        console.log(resp);
        that.setData({
          corpsPanking: resp.data.obj
        },function(){
          console.log(that.data);
        })
      }
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
  }
})
