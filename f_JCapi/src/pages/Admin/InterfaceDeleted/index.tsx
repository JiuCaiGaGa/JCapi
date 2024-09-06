import type { ActionType, ProColumns, ProDescriptionsItemProps } from '@ant-design/pro-components';
import {
  PageContainer,
  ProDescriptions,
  ProTable,
} from '@ant-design/pro-components';
import '@umijs/max';
import {Drawer, Tooltip} from 'antd';
import React, { useRef, useState } from 'react';
import {
  listDeletedInterfaceInfoUsingGet,
} from "@/services/f_JCapi/interfaceInfoController";

const TableList: React.FC = () => {
  /**
   * @en-US Pop-up window of new window
   * @zh-CN 新建窗口的弹窗
   *  */

  /**
   * @en-US The pop-up window of the distribution update window
   * @zh-CN 分布更新窗口的弹窗
   * */

  const [showDetail, setShowDetail] = useState<boolean>(false);
  const actionRef = useRef<ActionType>();
  const [currentRow, setCurrentRow] = useState<API.InterfaceInfo>();

  const columns: ProColumns<API.InterfaceInfo>[] = [
    {
      title: 'id',
      dataIndex: 'id',
      valueType: "index",
    },
    {
      title: '接口名称',
      dataIndex: 'name',
      valueType: "text",
      formItemProps:{
        rules: [{
          required: true,
          message: "此项必填!",
        }],
      },
      render: (text)=><Tooltip placement="topLeft" title={text}>{text}</Tooltip>,
    },
    {
      title: '接口描述',
      dataIndex: 'description',
      valueType: 'textarea',
      ellipsis: true,
      render: (text)=><Tooltip placement="topLeft">{text}</Tooltip>,
    },
    {
      title: '接口类型',
      dataIndex: 'method',
      valueType: 'text',
      render: (text)=><Tooltip placement="topLeft" title={text}>{text}</Tooltip>,
      onCell:()=>{
        return {
          style:{
            overflow: 'auto',
            whiteSpace: 'nowrap',
            textOverflow: 'ellipsis',
            cursor: 'pointer',
          }
        }
      }
    },
    {
      title: 'url',
      dataIndex: 'url',
      valueType: 'text',
      ellipsis: true,
      render: (text)=><Tooltip placement="topLeft" title={text}>{text}</Tooltip>,
      onCell:()=>{
        return {
          style:{
            overflow: 'auto',
            whiteSpace: 'nowrap',
            textOverflow: 'ellipsis',
            cursor: 'pointer',
          }
        }
      }
    },
    {
      title: '请求参数',
      dataIndex: 'requestParams',
      valueType: 'jsonCode',
      onCell:()=>{
        return {
          style:{
            maxWidth: 150,
            overflow: 'auto',
            whiteSpace: 'nowrap',
            textOverflow: 'ellipsis',
            cursor: 'pointer',
          }
        }
      }
    },
    {
      title: '请求头',
      dataIndex: 'requestHeader',
      valueType: 'jsonCode',
      onCell:()=>{
        return {
          style:{
            maxWidth: 150,
            overflow: 'auto',
            whiteSpace: 'nowrap',
            textOverflow: 'ellipsis',
            cursor: 'pointer',
          }
        }
      }
    },
    {
      title: '响应头',
      dataIndex: 'responseHeader',
      valueType: 'jsonCode',
      onCell:()=>{
        return {
          style:{
            maxWidth: 150,
            overflow: 'auto',
            whiteSpace: 'nowrap',
            textOverflow: 'ellipsis',
            cursor: 'pointer',
          }
        }
      }
    },
    {
      title: '接口创建时间',
      sorter: true,
      dataIndex: 'createTime',
      valueType: 'dateTime',
      hideInForm: true,
    },
    {
      title: '接口更新时间',
      sorter: true,
      dataIndex: 'updateTime',
      valueType: 'dateTime',
      hideInForm: true,
    },
  ];
  return (
    <PageContainer>
      <ProTable<API.InterfaceInfo, API.PageParams>
        headerTitle={'已弃用接口'}
        actionRef={actionRef}
        rowKey="key"
        search={{
          labelWidth: 120,
        }}
        request={ async (params) => {
          const res = await listDeletedInterfaceInfoUsingGet({
            ...params
          });
          if(res.data) {
            console.log(res.data);
            return {
              data: res.data || [],
              success: true,
              total: res.data.length,
            }
          }
          return {
            data: [],
            success: false,
            total: 0,
          };

        }}
        columns={columns}
      />
      <Drawer
        width={600}
        open={showDetail}
        onClose={() => {
          setCurrentRow(undefined);
          setShowDetail(false);
        }}
        closable={false}
      >
        {currentRow?.name && (
          <ProDescriptions<API.InterfaceInfo>
            column={2}
            title={currentRow?.name}
            request={async () => ({
              data: currentRow || {},
            })}
            params={{
              id: currentRow?.name ,
            }}
            columns={columns as ProDescriptionsItemProps<API.InterfaceInfo>[]}
          />
        )}
      </Drawer>

    </PageContainer>
  );
};
export default TableList;
