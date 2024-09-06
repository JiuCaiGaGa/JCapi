import { PageContainer } from '@ant-design/pro-components';
import {List, message} from 'antd';
import React, {useEffect, useState} from 'react';
import {listInterfaceInfoByPageUsingGet} from "@/services/f_JCapi/interfaceInfoController";


const Index: React.FC = () => {
  const [loading,setLoading] = useState(true);
  const [list,setList] = useState<API.InterfaceInfo[]>([]);
  const [total, setTotal] = useState<number>(0);
  const [apiSize] = useState<number>(10);


  const loadData = async (current = 1 , pageSize = apiSize) => {
    setLoading(true);
    try {
      const res = await listInterfaceInfoByPageUsingGet({
        current, pageSize
      });
      setList(res.data?.records ?? []);
      setTotal(res.data?.total ?? 0);
    }catch (e){
      message.error("数据获取异常," + e);
    }
    setLoading(false);
  }

  useEffect(() =>{
    loadData();
  },[]);

  return (
    <PageContainer title="欢迎使用JCapi">
      <span>我喜欢玩黑神话悟空</span>
      <List
        className="API-list"
        loading={loading}
        itemLayout="horizontal"
        dataSource={list}
        renderItem={(item) => {
          const apilink = `/interface_info/${item.id}`;
          return (
            <List.Item
              actions={[<a key={item.id} href={apilink}>查看</a>]}
            >
                <List.Item.Meta
                  title={<a href={apilink}>{item.name}</a>}
                  description={item.description}
                />
            </List.Item>
            )
        }
      }

        pagination={
          {
            hideOnSinglePage: true,
            defaultCurrent: 1,
            pageSize: apiSize,
            total: total,
            onChange: (current, pageSize) => {
              loadData(current, pageSize);
            },
            showTotal: (total) => {
              return "当前API总数为:" + total;
            },
          }
        }
      />

    </PageContainer>
  );
};

export default Index;
