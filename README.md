# JCapi
本项目基于 React + Spring Boot + Dubbo + Gateway 的 API 接口开放平台 。可以实现接口的接入以及发布，可视化接口的调用信息。用户具有接口的浏览、在线测试、调用权限，通过客户端SDK调用接口。

---

## 项目模块简述：

- f_JCapi : 本项目的前端
- b_JCapi ：项目主体，主要包括用户相关、接口相关等功能
- JCapi-common ：公共封装类（如公共返回对象）
- JCapi-gateway ：网关服务，涉及到统一鉴权等
- JCapi-Test：接口服务，主要用于实现接口调用的功能
- jcgg-client-sdk

---

*注意事项*：

- 本项目使用了自建SDK,请自行打包到本地.



---

