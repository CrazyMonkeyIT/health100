const config = require("../../config.js")
const userUtil = require("../../global-js/userUtil.js")
var that = null;
Page({
  data: {
    selected: true, //tab
    selected1: false,//tab
    userId:'',
    corpsPanking: [],
    usersPanking:[],
    pankingTopCorp:[],
    animationData: {}
  },
  onShow : function () {
    that = this;
    userUtil.login(function () {
      var userInfo = wx.getStorageSync('userInfo');
      that.userId = userInfo.id;
      that.pankingCorps(that.userId);
    });
    this.pankingUsers();
    this.pankingTopCorps();
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
  pankingUsers: function () {
    wx.request({
      url: config.service.pankingUsers,
      data: {},
      method: 'GET',
      dataType: 'json',
      responseType: 'text',
      success: function (resp) {
        that.setData({
          usersPanking: resp.data.obj
        })
      }
    })
  },
  pankingTopCorps:function(){
    wx.request({
      url: config.service.getTop1Corps,
      data: {},
      method: 'GET',
      dataType: 'json',
      responseType: 'text',
      success: function (resp) {
        console.log(resp)
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
  }
})