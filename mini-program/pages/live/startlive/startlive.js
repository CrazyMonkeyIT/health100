//live.js
//获取应用实例
var app = getApp()
Page({
  data: {
    motto: 'Hello World',
    userInfo: {}
  },
  //事件处理函数
  bindViewTap: function () {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  onLoad: function () {
    console.log('onLoad')
    var that = this;
    wx.getSetting({
      success: (response) => {
        console.log(response)
        if (!response.authSetting['scope.record']) {
          wx.authorize({
            scope: 'scope.record',
            success: () => {
              console.log('scope.record -- > auth');
            }
          })
        }

        if (!response.authSetting['scope.camera']) {
          wx.authorize({
            scope: 'scope.camera',
            success: () => {
              console.log('scope.camera -- > auth');
            }
          })
        }
      }
    })

  },
  statechange :function(e){
    console.log('live-pusher code:', e.detail.code);
  }
})
