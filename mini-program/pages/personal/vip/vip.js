// pages/personal/vip/vip.js
const app = getApp();
const paymentUtils = require("../../../global-js/paymentUtil.js");
const userUtils = require("../../../global-js/userUtil.js")
Page({

  /**
   * 页面的初始数据
   */
  data: {
    userInfo: {},
    vipList:null,
    isVipDiv:true,
    isNotVipDiv:true
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!!app.globalData.user) {
      this.setData({
        userInfo: app.globalData.user,
      })
      if(this.data.userInfo.isVip == 0){
        this.setData({ isNotVipDiv:false}) 
      }else{
        this.setData({ isVipDiv: false })
      }
    }
    this.loadVipList();
  }, 
  //加载会员类型列表
  loadVipList:function(){
    var that = this;
    wx.request({
      url: app.globalData.serverPath + '/vip/all',
      success: function (res) {
        if (res.data.result) {
          that.setData({
            vipList: res.data.obj
          })
        }else{
          console.log(res.data.message);
        }
      }
    })
  },
  //开通会员-开启支付
  openMember:function(e){
    var vipid = e.target.dataset.vipid;
    var money = e.target.dataset.money;
    paymentUtils.pay("开通VIP", (money * 100), function(){
      handleOpenMember(vipid);
    });
  },
  //处理用户开通会员
  handleOpenMember:function(vipId){
    var that = this;
    wx.request({
      url: app.globalData.serverPath + '/vip/openMember',
      data:{
        vipId: vipId,
        openId: app.globalData.user.openId
      },
      success:function(res){
        if (res.data.result){
          //此处更新用户的信息
          app.reloadUser(function(){
            that.setData({
              userInfo: app.globalData.user
            })
            if (this.data.userInfo.isVip == 0) {
              this.setData({ isNotVipDiv: false, isVipDiv:true })
            } else {
              this.setData({ isNotVipDiv: true, isVipDiv: false })
            }
          });
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
  
  }
})