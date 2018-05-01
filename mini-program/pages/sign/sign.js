const config = require("../../config.js")
const userUtil = require("../../global-js/userUtil.js")
var that = null;
Page({
  data: {
    imageSign:true, //一键打卡不可用
    clickSign:false,//一键打卡可用
    userId:'',
    miniSign:[],
  },
  onShow: function () {
    that = this;
    userUtil.login(function () {
      var userInfo = wx.getStorageSync('userInfo');
      that.setData({
        userId:userInfo.id
      })
      that.checkUserSign(userInfo.id);
    });
    
  },
  checkUserSign:function(param){
    wx.request({
      url: config.service.checkSign,
      data: { userId: param },
      method: 'GET',
      dataType: 'json',
      responseType: 'text',
      success: function (resp) {
        if (!resp.data.obj){
          that.setData({
            imageSign: false,
            clickSign: true
          })
        } else if (resp.data.obj.countDays==100){
          that.setData({
            miniSign: resp.data.obj,
            imageSign: false,
            clickSign: true
          })
        }else{
          that.setData({
            miniSign: resp.data.obj
          })
        }
      }
    })
  },
  // 一键打卡
  sign_click:function(){
    var hours = new Date().getHours();
    if (hours < 5 || hours > 21) {
      wx.showModal({
        title: '提示',
        content: '亲！每天打卡时间为5：00～21：00哦'
      })
      return;
    }
    wx.request({
      url: config.service.userSign,
      data: { userId: that.data.userId},
      method: 'GET',
      dataType: 'json',
      responseType: 'text',
      success: function (resp) {
        if (resp.data.result == false) {
          wx.showModal({
            title: '提示',
            content: resp.data.message
          })
        } else {
          wx.navigateTo({
            url: "/pages/user/user"
          })
        }
      }
    })
  },
  // 传图打卡
  image_sign_click:function(){
    var hours = new Date().getHours();
    if(hours<5 || hours>21){
      wx.showModal({
        title: '提示',
        content: '亲！每天打卡时间为5：00～21：00哦'
      })
      return;
    }
   
    wx.chooseImage({ 
      count:1,
      sizeType: ['compressed'], 
      sourceType: ['album', 'camera'], 
      success: function (res) {  
        // 返回选定照片的本地文件路径列表，tempFilePath可以作为img标签的src属性显示图片  
        var tempFilePaths = res.tempFilePaths; 
        wx.showToast({
          title: '正在上传...',
          icon: 'loading',
          mask: true,
          duration: 10000
        })
        wx.uploadFile({
          url: config.service.upUrl,
          filePath: tempFilePaths[0],
          name:'file',
          success: function (resp) {
            wx.hideToast();
            var obj = JSON.parse(resp.data)
            that.userImageSign(obj[0].filePath);
          },
          fail: function (res) { 
          },
        })
      }
    })
  },
  userImageSign:function(filePath){
    wx.request({
      url: config.service.imageSign,
      data: { userId: that.data.userId ,filePath:filePath},
      method: 'GET',
      dataType: 'json',
      responseType: 'text',
      success: function (resp) {
        if (resp.data.result==false){
          wx.hideToast();
          wx.showModal({
            title: '提示',
            content: resp.data.message
          })
        }else{
          wx.navigateTo({
            url: "/pages/user/user"
          })
        }
      }
    })
  }
})