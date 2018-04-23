//用户登录util
const config = require("../config.js")

const login = function(callback){
  var userData = wx.getStorageSync("userInfo");
  if(!userData){
    loginOper(callback);
  }
  if(callback){
    callback();
  }
  return userData;
}
//异步方法
const asynchronous = function (result, callback) {
  wx.request({
    url: config.service.saveUser,
    data: {
      openId: result.data.openId,
      gender: result.data.gender,
      country: result.data.country,
      avatarUrl: result.data.avatarUrl,
      city: result.data.city,
      province: result.data.province,
      nickName: result.data.nickName,
    },
    method: 'POST',
    dataType: 'json',
    responseType: 'text',
    success: function (saveRes) {
      if (saveRes.data.result == true) {
        saveUserStorage(result, saveRes, callback);
      }
    }
  })
}
//保存用户信息到缓存
const saveUserStorage = function (result, saveRes, callback) {
  wx.setStorageSync('userInfo', {
    id: saveRes.data.obj.id,
    openId: result.data.openId,
    gender: result.data.gender,
    country: result.data.country,
    avatarUrl: result.data.avatarUrl,
    city: result.data.city,
    province: result.data.province,
    nickName: result.data.nickName
  });
  if (callback) {
    callback();
  }
}
const loginOper = function (callback) {
  wx.login({
    success: function (res) {
      if (res.code) {
        wx.getUserInfo({
          success: function (userRes) {
            wx.request({
              url: config.service.getauth,
              data: {
                code: res.code,
                iv: userRes.iv,
                encryptedData: userRes.encryptedData
              },
              header: {},
              method: 'GET',
              dataType: 'json',
              success: function (result) {
                asynchronous(result, callback);
              }
            })
          }
        })
      }
    },
    fail: function (res) { },
    complete: function (res) { },
  });
}

module.exports = {
  login: login
}