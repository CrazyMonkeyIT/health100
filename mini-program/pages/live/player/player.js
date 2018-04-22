// pages/player/player.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    videoContext:{},
    playUrl : ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
  
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {
    /*var self = this;

    this.data.videoContext = wx.createLivePlayerContext("video-livePlayer");
    console.log(this.data.videoContext);
    this.setData({

      playUrl: "rtmp://47.96.184.236:1935/live/test",

    }, function () {

      self.data.videoContext.stop();

      self.data.videoContext.play();

    })*/
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
  statechange: function (e) {
    console.log('live-player code:', e.detail.code);
  }
})