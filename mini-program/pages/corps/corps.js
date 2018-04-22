const config = require("../../config.js")
var that = null;
Page({
  data: {
    selected: true, //tab
    selected1: false,//tab
    userInfo: {},
    corpsPanking: [],
    pankingTopCorp:[],
    animationData: {}
  },
  onLoad: function () {
    that = this;
    this.pankingCorps();
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
      data: {},
      method: 'POST',
      dataType: 'json',
      responseType: 'text',
      success: function (resp) {
        that.setData({
          corpsPanking: resp.data.obj
        })
      }
    })
  },
  pankingTopCorps(){
    that.setData({
      pankingTopCorp:{
        banner:"http://localhost:9090/health/minifile/temp1523606436299/07.png",
        msg1:'像割韭菜一样割肉！',
        msg2: '康宝莱战队欢迎您！'
      }
    })
  }

})