export default [
  { path: '/', name: '主页', icon: 'smile', component: './Index/index' },
  {  path: '/interface_info/:id', name: '查看接口',   component: './InterfaceInfo', hideInMenu: true },
  {
    path: '/user',
    layout: false,
    routes: [{ name: '登录', path: '/user/login', component: './User/Login' }],
  },

  {
    path: '/admin',
    name: '管理页',
    icon: 'crown',
    access: 'canAdmin',
    routes: [
      // { path: '/admin', redirect: '/admin/sub-page' },
      { name: '接口管理', icon: 'table', path: '/admin/interface_info', component: './Admin/InterfaceInfo' },
      { name: '接口分析', icon: 'cry', path: '/admin/interface_analysis', component: './Admin/InterfaceAnalysis' },
      { name: '废弃接口', icon: 'deleted', path: '/admin/interface_deleted', component: './Admin/InterfaceDeleted' },
    ],
  },
  {
    path: '/account',
    name: '个人中心',
    icon: 'crown',
    access: 'canUser',
    routes: [
      {name: '我的', icon: 'center', path: '/account/center', component: './User/Settings' },
    ]
  },
  // { path: '/', redirect: '/welcome' },
  { path: '*', layout: false, component: './404' },
];
