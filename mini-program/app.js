//app.js
const config = require("config.js");
const userUtil = require("/global-js/userUtil.js")

App({
  onLaunch: function () {
    if (!this.globalData.userInfo) {
      var that = this;
      userUtil.login(function () {
        that.globalData.userInfo = wx.getStorageSync('userInfo');
      });
    }
    if (!this.globalData.serverPath) {
      this.globalData.serverPath = config.service.host;
    }

  },

  //重新获取用户信息
  reloadUser: function (callback) {
    var that = this;
    that.callback = callback;
    userUtil.login(function (callback) {
      that.globalData.userInfo = wx.getStorageSync('userInfo');
      console.log("已获取到用户信息");
      that.callback();
    });
  },
  
  globalData: {
    userInfo: null,
    //服务器地址
    serverPath: null
  }
})