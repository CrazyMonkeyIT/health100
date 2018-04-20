Page({
  data: {
    userInfo: {},
    animationData: {}
  },
  onLoad: function (userId) {
    console.log(userId);
    this.setData({
      userInfo: {
        nickName: '天老大我老二',
        avatar: 'https://wx.qlogo.cn/mmopen/vi_32/GGHSy6Xf1ia9JyqRn3DNH1ib7U4eviadichuBPTlAibBy5TqEfnD4jqUBRwT7Ck5LNdHznVkDET5KlkRY09eYPksevw/0',
        contSignDay: 100,
        pointPanking:1000,
        donate:50
      }
    })
  },
  bindViewTap:function(){
    var _this = this;
    　　var times = _this.data.timeend - _this.data.timestart;
    　　if (times > 300) {
        console.log("长按");
        
       }
  },
  //点击开始时的时间
  timestart: function (e) {
    　　var _this = this;
    　　_this.setData({ timestart: e.timeStamp });
  },

  //点击结束的时间
  timeend: function (e) {
    　　var _this = this;
    　　_this.setData({ timeend: e.timeStamp });
  },
})