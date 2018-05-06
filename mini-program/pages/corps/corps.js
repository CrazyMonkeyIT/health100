const config = require("../../config.js")
const userUtil = require("../../global-js/userUtil.js")
var that = null;
Page({
  data: {
    selected: true, //tab
    selected1: false,//tab
    modalHidden: false,//
    userId:'',
    userCorps:{},
    corpsPanking: [],
    corpsUsersPanking:[],
    pankingTopCorp:[],
    animationData: {}
  },
  onShow : function () {
    that = this;
    userUtil.login(function () {
      var userInfo = wx.getStorageSync('userInfo');
      that.setData({
        userId: userInfo.id
      })
      that.pankingCorps(that.userId);
      that.getCropsUserRanking(that.userId);
    });
    this.pankingTopCorps();
    this.getMiniUserCorps();
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
  getMiniUserCorps: function () {
    wx.request({
      url: config.service.getMiniUserCorps,
      data: { userId: this.data.userId },
      method: 'GET',
      dataType: 'json',
      responseType: 'text',
      success: function (resp) {
        that.setData({
          userCorps: resp.data.obj
        })
      }
    })
  },
  pankingCorps: function () {
    wx.request({
      url: config.service.pankingCorps,
      data: {userId:this.data.userId},
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
  getCropsUserRanking: function () {
    wx.request({
      url: config.service.getCropsUserRanking,
      data: {userId: this.data.userId},
      method: 'GET',
      dataType: 'json',
      responseType: 'text',
      success: function (resp) {
        that.setData({
          corpsUsersPanking: resp.data.obj
        })
      }
    })
  },
  pankingTopCorps:function(){
    wx.request({
      url: config.service.getTop1Corps,
      data: { userId: this.data.userId },
      method: 'GET',
      dataType: 'json',
      responseType: 'text',
      success: function (resp) {
        that.setData({
          pankingTopCorp: resp.data.obj
        })
      }
    })
  },
  sign_click:function(){
    wx.navigateTo({
      url: "/pages/sign/sign"
    })
  },
  corps_info:function(){
    this.setData({
      modalHidden: !this.data.modalHidden,
    })
  },
  cancel_corps_info:function(){
    this.setData({
      modalHidden: !this.data.modalHidden,
    })
  }
})