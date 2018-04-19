/**
 * 小程序配置文件
 */

// 此处主机域名修改成腾讯云解决方案分配的域名
var host = 'http://localhost:9090/health';
var wsshost = 'wss://dujiaoshouzhiku.com/unicorn/websocket/endpointChat';//'ws://localhost:9090/unicorn/websocket/endpointChat';

var config = {
  // 下面的地址配合云端 Demo 工作
  service: {
    host,
    //保存用户信息
    saveUser:host + '/minigram/saveUser',
    //获取用户加密信息的解密文档
    getauth: host + '/minigram/getauth',
    //上传文件URL
    upUrl : host + '/import/up/',
    pankingCorps: host +"/minigram/corpsPanking",
    socketUrl: wsshost
  }
};

module.exports = config;