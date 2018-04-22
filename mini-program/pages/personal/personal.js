// pages/person/person.js

const app = getApp();

Page({

  /**
   * 页面的初始数据
   */
  data: {
    hasUserInfo: false,
    userInfo: {},
    lecturerInfo:null,
    noRegister:true,
    alreadyRegister:true,
    noVip:true,
    alreadyVip:true
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
       
    if (!!app.globalData.user){
      this.setData({
        userInfo: app.globalData.user,
        hasUserInfo:true
      })
      //判断该用户是否是vip
      if (this.data.userInfo.isVip == 0){
        console.log("该用户还未是vip");
        this.setData({ noVip:false});
      }else{
        console.log("该用户已是vip");
        this.setData({ alreadyVip: false });
      }
    }

    if (!!app.globalData.lecturerInfo){
      this.setData({
        lecturerInfo: app.globalData.lecturerInfo,
        noRegister: true,
        alreadyRegister: false
      })
    }else{
      this.getLecturerInfo();
    }
  },
  //获取讲师信息
  getLecturerInfo: function () {
    var that = this;
    wx.request({
      url: app.globalData.serverPath + '/lecturer/getByOpenId',
      data: {
        openId: getApp().globalData.user.openId
      },
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        if (res.data.result) {
          that.setData({
            lecturerInfo: res.data.obj,
            noRegister: true,
            alreadyRegister: false
          })
          app.globalData.lecturerInfo = res.data.obj;
        } else {
          console.log("该用户还不是讲师");
          that.setData({
            noRegister: false
          })
        }
      }
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
  
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
  
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {
  
  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {
  
  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {
  
  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {
  
  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {
  
  },
  /** 前往我的直播 */
  toMylive:function(e){
    wx.navigateTo({
      url: 'mylive/mylive',
    })
  },
  /** 前往我的订阅 */
  toMySubscribe: function (e) {
    wx.navigateTo({
      url: 'subscribe/subscribe',
    })
  },
  /** 前往账户安全 */
  toAccount:function(e){
    wx.navigateTo({
      url: 'account/account',
    })
  }
})