//index.js

//获取应用实例
const app = getApp()

Page({
  data: {
    motto: 'Hello World',
    userInfo: {},
    canIUse: wx.canIUse('button.open-type.getUserInfo'),
    imgUrls: [
      'http://image.tupian114.com/20151104/10282395.jpg',
      'http://image.tupian114.com/20120410/19234926.jpg'
    ],
    autoplay: true,
    interval: 3000,
    duration: 500,
    indicatorDots: false,
    roadShowImages: [
      '../../images/home/test/1.jpg',
      '../../images/home/test/2.jpg',
      '../../images/home/test/3.jpg',
      '../../images/home/test/4.jpg'],
    chiefInfoList: null
  },
  //事件处理函数
  bindViewTap: function () {
    wx.navigateTo({
      url: '../logs/logs'
    })
  },
  //初始化首页数据
  onLoad: function () {
    var that = this;
    wx.request({
      url: app.globalData.serverPath + '/lecturer/chief/list',
      method: 'POST',
      dataType: 'json',
      success: function (result) {
        let obj = result.data.obj;
        if(obj.length > 2){
          obj = [obj[0], obj[1]];
        }
        that.setData({
          'chiefInfoList': obj
        })
      }
    })
  },

  //更多首席
  moreChiefInfo: function(){
    wx.redirectTo({
      url: 'chief/chief',
    })
  },

  //键盘完成按钮时触发(用来搜索)
  bindconfirm: function(event){
    wx.showToast({
      title: '搜索小哥正在快马加鞭的查找，请稍后!',
      icon: 'none'
    })
  }
});
