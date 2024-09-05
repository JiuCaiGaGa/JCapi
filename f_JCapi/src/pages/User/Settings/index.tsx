import { createStyles } from 'antd-style';
import React, { useEffect, useState } from 'react';
import { ProCard } from '@ant-design/pro-components';
import { Avatar, Button, Col, Descriptions, Divider, Row, Tabs } from 'antd';
import { useModel } from '@@/exports';
import { InfoCircleOutlined, KeyOutlined, SettingOutlined, UserOutlined } from '@ant-design/icons';
import { getUserAkSkUsingGet, userUpdateKeyUsingPost } from '@/services/f_JCapi/userController';

const useStyles = createStyles(({ token }) => {
  return {
    action: {
      marginLeft: '8px',
      color: 'rgba(0, 0, 0, 0.2)',
      fontSize: '24px',
      verticalAlign: 'middle',
      cursor: 'pointer',
      transition: 'color 0.3s',
      '&:hover': {
        color: token.colorPrimaryActive,
      },
    },
    lang: {
      width: 42,
      height: 42,
      lineHeight: '42px',
      position: 'fixed',
      right: 16,
      borderRadius: token.borderRadius,
      ':hover': {
        backgroundColor: token.colorBgTextHover,
      },
    },
    container: {
      display: 'flex',
      flexDirection: 'column',
      height: '100vh',
      overflow: 'auto',
      backgroundImage:
        "url('https://mdn.alipayobjects.com/yuyan_qk0oxh/afts/img/V-_oS6r-i7wAAAAAAAAAAAAAFl94AQBr')",
      backgroundSize: '100% 100%',
    },
    name: {
      display: 'none',
    },
  };
});



const MyInfo: React.FC = () => {

  const { styles } = useStyles();
  const [ data ,setData] = useState<API.User>();
  const { initialState } = useModel('@@initialState');
  const { loginUser } = initialState || {};
  const { TabPane } = Tabs;
  const [Ak,setAk] = useState<string>("");
  const [Sk,setSk] = useState<string>("");
  const [showSk, setShowSk] = useState<boolean>(false);


  const  getAk = async () =>{
    const res = await getUserAkSkUsingGet({
      id: data?.id,
    });
    if(res.data){
      // @ts-ignore
      setAk(res.data.accessKey);
      // @ts-ignore
      setSk(res.data.secretKey);
    }
  };
  const loadData = async () => {
    // todo Ak 绑定触发事件 特定TAB下获取AK
    setData(loginUser);
    getAk();
  }

  const changeKey = async () => {

    await userUpdateKeyUsingPost({
      id : data?.id,
    });
      getAk();
  }

  useEffect(() => {
    loadData();
  })

  return (

    <div className={styles.container}>
      <div
        style={{
          flex: '1',
          padding: '32px 0',
        }}
      >
        <ProCard split="vertical">
          {/*左侧卡片栏*/}
          <ProCard title={<div style={{textAlign:"center"}}>个人信息</div>} colSpan="20%">
            <Row justify="center">
              <Col >
                <Avatar
                  size={{ xs: 24, sm: 32, md: 40, lg: 64, xl: 80, xxl: 100 }}
                  src={data?.userAvatar}
                  alt={loginUser?.userName}
                />
              </Col>
              <Divider />
            </Row>
            <Row justify="center">
              <Col span={6} >
                  <UserOutlined />
              </Col>

              <Col>
                <span className="acss-rbfzfg">{loginUser?.userName ?? '匿名用户'}</span>
              </Col>
            </Row>
            <Divider />
          </ProCard>
          {/*右侧卡片栏*/}
          <Tabs defaultActiveKey="1">
            <TabPane tab={
              <span>
                <InfoCircleOutlined />
                <span>基本信息</span>
              </span>
            } key="1">
              <ProCard headerBordered>
                {loginUser ? (
                  <Descriptions title={'我的信息'} column={2}>
                    <Descriptions.Item label="用户名"><span>{data?.userName}</span></Descriptions.Item>
                    <Descriptions.Item label="创建时间">{data?.createTime}</Descriptions.Item>
                    <Descriptions.Item label="性别">{data?.gender ? '男' : '女'}</Descriptions.Item>
                    <Descriptions.Item label="用户角色">
                      {data?.userRole ? (data?.userRole === 'admin' ? '管理员' : '用户') : null}
                    </Descriptions.Item>
                  </Descriptions>
                ) : (
                  <>用户错误</>
                )}
              </ProCard>
            </TabPane>
            <TabPane
              tab={
              <span>
                <KeyOutlined />
                <span>Ak</span>
              </span>
            } key="2" >
              <Descriptions title={'我的信息'} column={1} extra={  <><Button danger onClick={changeKey}>重置访问密钥 </Button> <Button onClick={()=>setShowSk(!showSk)}>显示Key</Button></>}>
                  <Descriptions.Item label="访问密钥">{Ak}</Descriptions.Item>
                  <Descriptions.Item label="私密密钥">
                    { showSk ? Sk : '*********' }
                  </Descriptions.Item>
              </Descriptions>
            </TabPane>
            <TabPane tab={
              <span>
                <SettingOutlined />
                <span>账号设置</span>
              </span>
            } key="3">
              Content of Tab Pane 3
            </TabPane>
          </Tabs>
        </ProCard>
      </div>
    </div>
  );
};
export default MyInfo;
