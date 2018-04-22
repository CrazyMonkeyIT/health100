const config = require("../config.js")

/**
 * 请求支付
 * @productDetail 商品描述
 * @totalFee      金额
 * @callback      支付结束后的回调函数
 */
const pay = function(productDetail, totalFee, payType, callback){
  var user = wx.getStorageSync('miniUser');
  let openId = user.openId;
  wx.request({
      url: config.service.host +'/pay/create', 
      dataType:'json',
      data: {
        productDetail: productDetail,
        totalFee: totalFee,
        openId: openId
      },
      header: {
        'content-type': 'application/json'
      },
      success: function (res) {
        if (res.data.result){
          openPay(res.data.obj, callback);
        }else{
          showAlter(res.data.message);
        }
      }
  })
}
const openPay = function (obj, callback){
  wx.requestPayment({
    'timeStamp': obj.timeStamp,
    'nonceStr': obj.nonceStr,
    'package': obj.package,
    'signType': 'MD5',
    'paySign': obj.paySign,
    'success': function (res) {
        showAlter("恭喜您支付成功");
        callback();
     },
    'fail': function (res) {
        showAlter("可能支付遇到了异常：" + res.data.message);
    }
  })
}

const showAlter = function (content){
  wx.showToast({
    title: content,
    icon: 'none',
    duration: 2000
  })
}
module.exports = {
  pay: pay
}