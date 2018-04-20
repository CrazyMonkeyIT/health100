
var that = null;
Page({
  data: {
    userInfo:{},
    animationData: {}
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
        percent:'../images/user/percent/1.png'
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
  }
})