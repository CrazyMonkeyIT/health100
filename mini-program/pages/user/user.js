
var that = null;
Page({
  data: {
    userInfo:{},
    animationData: {},
    circleDeg : -20,
    leftBBtop :345,//345
    cleftright:240,//234
    clefttop:0,
    crightright :130,
    crightBottom : 200,
  },
  onLoad: function () {
    that = this;
    var animation = wx.createAnimation({
      duration: 1000,
      timingFunction: 'ease-in-out',
    })
    this.animation = animation

    animation.bottom(50).step().bottom(20).step()
    this.setData({
      animationData: animation.export()
    })
    this.continuousDay(68);
    setInterval(function () {
      animation.bottom(20).step().bottom(50).step()
      this.setData({
        animationData: animation.export()
      })
    }.bind(this), 1000)
    
    this.setData({
      userInfo:{
        userId:'1',
        nickName:'天老大我老二',
        point:9999,
        avatar:'https://wx.qlogo.cn/mmopen/vi_32/GGHSy6Xf1ia9JyqRn3DNH1ib7U4eviadichuBPTlAibBy5TqEfnD4jqUBRwT7Ck5LNdHznVkDET5KlkRY09eYPksevw/0',
        badge1: '../images/user/badge_1_y.png',
        badge2: '../images/user/badge_2_y.png',
        badge3: '../images/user/badge_3_n.png',
        badge4: '../images/user/badge_4_n.png',
        signDay:100,
        contSignDay:89,
        corpsName:'天大地大我最大',
        corpsPoint:9999,
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
      url: "/pages/achieve/achieve?userId="+this.data.userInfo.userId
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

  }
})