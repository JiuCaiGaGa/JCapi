import { PageContainer } from '@ant-design/pro-components';
import {Button, Card, Descriptions, Divider, Form, Input, message} from 'antd';
import React, {useEffect, useState} from 'react';
import {
  getInterfaceInfoByIdUsingGet, invokeInterfaceInfoUsingPost,
} from "@/services/f_JCapi/interfaceInfoController";
import {useParams} from "@@/exports";


const Index: React.FC = () => {
  const [loading,setLoading] = useState(false);
  const [invokeloading,setInvokeLoading] = useState(false);
  const [data ,setData] = useState<API.InterfaceInfo>();
  const parms = useParams();// 获取url参数
  const [invokeRes,setInvokeRes] = useState<any>();

  const loadData = async () => {

    if(!parms.id){//
      message.error("参数不存在");
      return;
    }

    setLoading(true);
    try {
      const res = await getInterfaceInfoByIdUsingGet({
        id : Number(parms.id),
      });
      setData(res.data);
    }catch (e){
      message.error("数据获取异常," + e);
    }
    setLoading(false);
  }

  useEffect(() =>{
    loadData();
  },[]);

  const onFinish = async (values :any) => {
    if(!parms.id){//
      message.error("接口不存在");
      return;
    }

    setInvokeLoading(true);
    try {
      const res = await invokeInterfaceInfoUsingPost({
        id : parms.id,
        ...values,
      });
      setInvokeRes(res.data);
      message.success('操作成功!');
    } catch (error : any) {
      message.error('操作失败!\n错误信息'+error);
    }
    setInvokeLoading(false);
  };
  return (
    <PageContainer title="查看接口文档">
      <Card>
        {
          data ? <Descriptions title={data?.name+" API Info"} column={1} extra={<Button>调用</Button>}>
              <Descriptions.Item label="接口状态">{data.status ? "运行中..." : "关闭"} </Descriptions.Item>
              <Descriptions.Item label="描述">{data.description}</Descriptions.Item>
              <Descriptions.Item label="请求地址">{data.url}</Descriptions.Item>
              <Descriptions.Item label="请求方法">{data.method}</Descriptions.Item>
              <Descriptions.Item label="请求参数">{data.requestParams}</Descriptions.Item>
              <Descriptions.Item label="请求头">{data.requestHeader}</Descriptions.Item>
              <Descriptions.Item label="响应头">{data.responseHeader}</Descriptions.Item>
              <Descriptions.Item label="创建时间">{data.createTime}</Descriptions.Item>
              <Descriptions.Item label="更新时间">{data.updateTime}</Descriptions.Item>
            </Descriptions>
            : <>接口不存在</>
        }
      </Card>
      <Divider type="vertical" />
      <Card title="在线测试">
        <Form
          name="invoke"
          layout="vertical"
          onFinish={onFinish}
        >
          {/*todo 不同请求方法对应不同的页面 接口测试限制 如 测试次数 测试频率等*/}
          <Form.Item
            label="请求参数"
            name="userRequestParams"
          >
            <Input.TextArea />
          </Form.Item>
          <Form.Item wrapperCol={{ offset: 8, span: 16 }}>
            <Button type="primary" htmlType="submit">
              调用
            </Button>
          </Form.Item>
        </Form>
      </Card>
      <Divider type="vertical" />
      <Card loading={invokeloading} title="调用结果">
        {invokeRes}
      </Card>


    </PageContainer>
  );
};

export default Index;
