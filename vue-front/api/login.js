import request from '@/utils/request'
export default {
  //根据token值获取用户信息
  getLoginUserInfo(){
    return request({
      url: `/educenter/ucenter-member/getMemberInfo`,
      method: 'get'
    })
  },
  //登录
  submitLogin(userInfo) {
    return request({
      url: `/educenter/ucenter-member/login`,
      method: 'post',
      data: userInfo
    })
  }
}