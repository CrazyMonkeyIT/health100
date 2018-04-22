// pages/personal/wallet/detail/detail.js
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    list:[],
    tradeRecordDiv:true,
    incomeDiv:true
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    if (!!options.openId || !!options.lecturerId){
      if(options.type == 1){
        //收入明细
        this.loadIncome(options.lecturerId);
      }else if(options.type == 2){
        //支出明细
        this.loadShopping(options.openId);
      } else if (options.type == 3){
        //总明细
        this.loadAll(options.openId);
      }
    }
  },
  loadIncome: function (lecturerInfo){
    var that = this;
    wx.request({
      url: app.globalData.serverPath + '/wallet/getLecturerIncome',
      data: {
        lecturerId: lecturerInfo
      },
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        if (!!res.data) {
          for (var i = 0; i < res.data.length; i++) {
            console.log(res.data[i].createTime);
            res.data[i].createTime = res.data[i].createTime.substring(8, 21);
            if (res.data[i].incomeType == 'live'){
              res.data[i].incomeType = '直播';
            }
            res.data[i].amount = '+' + res.data[i].amount;
          }
          that.setData({
            list: res.data,
            incomeDiv:false
          })
        }
      }
    })
  },
  loadShopping:function(openId){
    var that = this;
    wx.request({
      url: app.globalData.serverPath + '/wallet/getUserTradePayRecord',
      data: {
        openId: openId
      },
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        console.log(res.data);
        if (!!res.data) {
          for(var i = 0 ;i < res.data.length ;i ++){
            res.data[i].createTime = res.data[i].createTime.substring(8,21);
            if (res.data[i].tradeType == 0) {
              res.data[i].tradeType = '收入';
              res.data[i].tradeAmount = '+'+res.data[i].tradeAmount;
            }else{
              res.data[i].tradeType = '支出';
              res.data[i].tradeAmount = '-' + res.data[i].tradeAmount;
            }
          }
          that.setData({
            list: res.data,
            tradeRecordDiv:false
          })
        }
      }
    })
  },
  loadAll: function (openId) {
    var that = this;
    wx.request({
      url: app.globalData.serverPath + '/wallet/getUserTradeRecord',
      data: {
        openId: openId
      },
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        if (!!res.data) {
          for (var i = 0; i < res.data.length; i++) {
            res.data[i].createTime = res.data[i].createTime.substring(8, 21);
            if (res.data[i].tradeType == 0) {
              res.data[i].tradeType = '收入';
              res.data[i].tradeAmount = '+' + res.data[i].tradeAmount;
            } else {
              res.data[i].tradeType = '支出';
              res.data[i].tradeAmount = '-' + res.data[i].tradeAmount;
            }
          }
          that.setData({
            list: res.data,
            tradeRecordDiv:false
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
  
  }
})