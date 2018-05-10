const config = require("../../config.js")
const userUtil = require("../../global-js/userUtil.js")
var that = null;

Page({
  data: {
    userId:'',
    corps:[]
  },
  onShow: function (param) {
    that = this;
    userUtil.login(function () {
       var userInfo = wx.getStorageSync('userInfo');
       that.setData({
         userId:userInfo.id
       })
    });
    this.getCorpsList();
    var animation = wx.createAnimation({
      duration: 1000,
      timingFunction: 'ease-in-out',
    })
  },
  getCorpsList:function(){
    wx.request({
      url: config.service.getCorpsList,
      data: {},
      method: 'GET',
      dataType: 'json',
      responseType: 'text',
      success: function (resp) {
        that.setData({
          corps: resp.data.obj
        })
      }
    })
  },
  joincorps:function(data){
    var corpsId = data.currentTarget.dataset.id;
    wx.request({
      url: config.service.joinCorps,
      data: { corpsId: corpsId, userId: that.data.userId },
      method: 'GET',
      dataType: 'json',
      responseType: 'text',
      success: function (resp) {
        if (resp.data.result) {
          wx.navigateTo({
            url: "/pages/corps/corps"
          })
        } else {
          wx.showModal({
            title: '提示',
            content: resp.data.message
          })
        }
      }
    })
  },
  sign_click: function () {
    wx.navigateTo({
      url: "/pages/sign/sign"
    })
  },
})