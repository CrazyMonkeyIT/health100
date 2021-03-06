/**
 * 小程序配置文件
 */

// 此处主机域名修改成腾讯云解决方案分配的域名
//var host = 'https://health100.cnsoc.org/health';
var host = 'http://localhost:9090/health';

var config = {
  // 下面的地址配合云端 Demo 工作
  service: {
    host,
    //保存用户信息
    saveUser:host + '/minigram/saveUser',
    //获取用户加密信息的解密文档
    getauth: host + '/minigram/getauth',
    //获取用户信息
    getMiniUser: host + '/minigram/getMiniUser',
    //获取用户战队信息
    getMiniUserCorps:host+'/minigram/getMiniUserCorps',
    //战队排行
    pankingCorps: host + "/minigram/corpsPanking",
    //战队成员排行
    getCropsUserRanking: host + "/minigram/getCropsUserRanking",
    //个人排行
    pankingUsers:host + '/minigram/usersPanking',
    //校验用户打卡类型
    checkSign:host+'/minigram/checkSign',
    //分享得积分
    sharePoint: host +'/minigram/sharePointInvoke',
    //传图打卡
    imageSign: host +'/minigram/imageSign',
    // 一键打卡
    userSign:host+'/minigram/userSign',
    //获取所有的战队信息
    getCorpsList: host +'/minigram/corpsList',
    //加入战队
    joinCorps: host + '/minigram/joinCorps',
    //获取积分top1的战队
    getTop1Corps: host +'/minigram/getTop1Corps',
    //获取用户打卡图片
    getSignImage: host +'/minigram/getSignImage',
    //上传文件URL
    upUrl : host + '/import/up/temp'
  }
};

module.exports = config;