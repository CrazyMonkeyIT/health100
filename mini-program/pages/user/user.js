const config = require("../../config.js")
const userUtil = require("../../global-js/userUtil.js")
var that = null;
Page({
  data: {
    miniUser:{},
    userSignImages:[],
    userId:'',
    animationData: {},
    circleDeg : -20,
    leftBBtop :345,//345
    cleftright:240,//234
    clefttop:0,
    crightright :130,
    crightBottom : 200,
  },
  onShow: function () {
    that = this;
    userUtil.login(function () {
      var userInfo = wx.getStorageSync('userInfo');
      that.setData({
        userId: userInfo.id
      })
      that.getUser(userInfo.id);
      that.userSignImages();
    });
    var animation = wx.createAnimation({
      duration: 1000,
      timingFunction: 'ease-in-out',
    })
    this.animation = animation
    animation.bottom(50).step().bottom(20).step()
    this.setData({
      animationData: animation.export()
    })
    
    setInterval(function () {
      animation.bottom(20).step().bottom(50).step()
      this.setData({
        animationData: animation.export()
      })
    }.bind(this), 1000)
  },
  getUser:function (userId) {
    wx.request({
      url: config.service.getMiniUser,
      data: { userId: userId },
      method: 'GET',
      dataType: 'json',
      responseType: 'text',
      success: function (resp) {
        console.log(resp)
        if (resp.data.result == true) {
          that.setData({
            miniUser: resp.data.obj
          })
          that.continuousDay(resp.data.obj.signDay);
        } else {
          wx.showModal({
            title: '提示',
            content: '系统错误'
          })
        }
      }
    })
  },
  userSignImages:function(){
    wx.request({
      url: config.service.getSignImage,
      data: { userId: that.data.userId },
      method: 'GET',
      dataType: 'json',
      responseType: 'text',
      success: function (resp) {
        console.log(resp)
        that.setData({
          userSignImages: resp.data.obj
        })
        }
    })
  },
  scrollBottom:function(e){
    console.log(e.target)
    if (wx.pageScrollTo) {
      wx.pageScrollTo({
        scrollTop: e.target.offsetTop + 200
      })
    } else {
      wx.showModal({
        title: '提示',
        content: '当前微信版本过低，无法使用该功能，请升级到最新微信版本后重试。'
      })
    }
  },
  user_achieve_lick:function(){
    wx.navigateTo({
      url: "/pages/achieve/achieve?userId="+this.data.miniUser.userId
    })
  },
  continuousDay : function(day){
    let initDeg = -20, step = 2.4, cleftright = 120,clefttop=0,
    crightright = 130,crightBottom = 200;
    if(day < 30){
      clefttop = 345-30 - 9 * day;
    }
    if(day >= 30 && day < 50){
      clefttop = 0;
      cleftright = 100 + (day-30) * 9
    }
    if(day >=50 && day <= 65){
      cleftright = 240;
      crightright = 240 + (day - 50) * 9
    } else if (day > 65 && day <=80){
      cleftright = 240;
      crightright = 480;
      crightBottom = 105 + (day - 65) * 5.2
    }else if(day > 80){
      cleftright = 240;
      crightright = 480;
      crightBottom = 105 + (day - 65) * 7.4
    }
    this.setData({
      circleDeg: (step * day) + initDeg,
      cleftright: cleftright,
      clefttop: clefttop,
      crightright: crightright,
      crightBottom: crightBottom
    })
  },
  inv_card_lick :function(){
    wx.showLoading({
      title: '生成中请稍候',
      mask: true
    })
    wx.request({
      url: config.service.host + '/minigram/pic/invite/' + this.data.userId,
      method: 'GET',
      dataType: 'json',
      responseType: 'text',
      success: function(res) {
        if (res.data.result == true){
          wx.previewImage({
            current: res.data.message,
            urls: [res.data.message],
            success: function () {
              wx.hideLoading();
            }
          })
        } else {
          wx.showToast({
            title: '我的邀请卡图片生成失败',
            mask: true,
          })
        }
      },
    })
  },
  achive_click: function () {
    wx.showLoading({
      title: '生成中请稍候',
      mask: true
    })
    wx.request({
      url: config.service.host + '/minigram/pic/achive/' + this.data.userId,
      method: 'GET',
      dataType: 'json',
      responseType: 'text',
      success: function (res) {
        if (res.data.result == true) {
          wx.previewImage({
            current: res.data.message,
            urls: [res.data.message],
            success: function () {
              wx.hideLoading();
            }
          })
        }else{
          wx.showToast({
            title: '个人成就图片生成失败',
            mask: true,
          })
        }
      },
    })
  }
})