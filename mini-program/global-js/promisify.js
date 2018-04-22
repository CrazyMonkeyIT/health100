var Promise = require('es6-promise.min.js')

function httpsPromisify(fn) {
  return function (obj = {}) {
    return new Promise((resolve, reject) => {
      obj.success = function (res) {
        resolve(res)
      }

      obj.fail = function (res) {
        reject(res)
      }

      fn(obj)
    })
  }
}
/**
 * promisify.httpsPromisify(wx.request)({
    url: config.service.saveUser,
    method: "POST",
    dataType: 'json',
    data: {
      openId: result.data.openId,
      gender: result.data.gender,
      country: result.data.country,
      avatarUrl: result.data.avatarUrl,
      city: result.data.city,
      province: result.data.province,
      nickName: result.data.nickName
    }
  }).then(function (saveRes) {
    console.log("同步方法执行完毕");
    saveUserStorage(result, saveRes);
  })
 * 
 */
module.exports = {
  httpsPromisify: httpsPromisify
}