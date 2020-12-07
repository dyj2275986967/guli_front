import request from '@/utils/request'
export default {
//查询前2条banner数据
  getListBanner() {
    return request({
      url: `/educms/crm-banner-front/getAllBanner`,
      method: 'get'
    })
  }
}