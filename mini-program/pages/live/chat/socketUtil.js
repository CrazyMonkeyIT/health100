const Stomp = require('stomp-2.3.3.js').Stomp;
const config = require('../../../config.js');
const stomClient = () => {
  var socketOpen = false
  var socketMsgQueue = []
  function sendSocketMessage(msg) {
    if (socketOpen) {
      wx.sendSocketMessage({
        data: msg
      })
    } else {
      socketMsgQueue.push(msg)
    }
  }
  var ws = {
    send: sendSocketMessage,
    onopen: null,
    onmessage: null
  }
  wx.connectSocket({
    url: config.service.socketUrl,
    data: {},
    header: { 'content-type': 'application/json' },
    method: "get"
  })
  wx.onSocketOpen(function (res) {
    console.log('WebSocket连接已打开！');
    socketOpen = true;
    ws.onopen && ws.onopen();
  })
  wx.onSocketError(function (res) {
    console.log('WebSocket连接打开失败，请检查！')
  })
  wx.onSocketMessage(function (res) {
    console.log('收到onmessage事件:', res)
    ws.onmessage && ws.onmessage(res)
  }) 
  Stomp.setInterval = function () { }
  Stomp.clearInterval = function () { }
  var client = Stomp.over(ws);
  return client;
}

module.exports = {
  stomClient: stomClient
}