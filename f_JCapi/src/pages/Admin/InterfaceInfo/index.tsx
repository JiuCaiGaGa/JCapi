import { PlusOutlined } from '@ant-design/icons';
import type { ActionType, ProColumns, ProDescriptionsItemProps } from '@ant-design/pro-components';
import {
  PageContainer,
  ProDescriptions,
  ProTable,
} from '@ant-design/pro-components';
import '@umijs/max';
import {Button, Drawer, message, Tooltip} from 'antd';
import React, { useRef, useState } from 'react';
import {
  addInterfaceInfoUsingPost, deleteInterfaceInfoUsingPost,
  listInterfaceInfoByPageUsingGet, offlineInterfaceInfoUsingPost, onlineInterfaceInfoUsingPost,
  updateInterfaceInfoUsingPost
} from "@/services/f_JCapi/interfaceInfoController";


import CreateModal from "@/pages/Admin/InterfaceInfo/components/CreateModal";
import UpdateModal from "@/pages/Admin/InterfaceInfo/components/UpdateModal";

const TableList: React.FC = () => {
  /**
   * @en-US Pop-up window of new window
   * @zh-CN 新建窗口的弹窗
   *  */
  const [createModalVisible, handleModalVisible] = useState<boolean>(false);
  /**
   * @en-US The pop-up window of the distribution update window
   * @zh-CN 分布更新窗口的弹窗
   * */
  const [updateModalVisible, handleUpdateModalVisible] = useState<boolean>(false);
  const [showDetail, setShowDetail] = useState<boolean>(false);
  const actionRef = useRef<ActionType>();
  const [currentRow, setCurrentRow] = useState<API.InterfaceInfo>();
  // const [setSelectedRows] = useState<API.InterfaceInfo[]>([]);

  /**
   * @en-US Add node
   * @zh-CN 添加接口
   * @param fields
   */
  const handleAdd = async (fields: API.InterfaceInfo) => {
    const hide = message.loading('正在添加');
    try {
      await addInterfaceInfoUsingPost({
        ...fields,
      });
      hide();
      message.success('创建成功！');
      // 使 模态框 可视
      handleModalVisible(false);
      return true;
    } catch (error: any) {
      hide();
      message.error('创建失败,请稍后重试。'+ error.message);
      return false;
    }
  };

  /**
   * @en-US Update interface
   * @zh-CN 更新接口信息
   *
   * @param fields
   */
  const handleUpdate = async (fields: API.InterfaceInfo) => {
    if(!currentRow) return;
    const hide = message.loading('修改中.....');
    try {
      await updateInterfaceInfoUsingPost({
        id : currentRow.id,
        ...fields,
      });
      hide();
      message.success('修改成功!');
      return true;
    } catch (error) {
      hide();
      message.error('修改失败,请重试!\n错误信息'+error);
      return false;
    }
  };

  /**
   *  Delete interface
   * @zh-CN 删除接口
   *
   * @param record
   */
  const handleRemove = async (record: API.InterfaceInfo) => {
    const hide = message.loading('正在删除');
    if (!record) return true;
    try {
      await deleteInterfaceInfoUsingPost({
        id: record.id,
      });
      hide();
      message.success('删除成功,即将刷新数据');
      actionRef.current?.reload();
      return true;
    } catch (error : any) {
      hide();
      message.error('删除失败，'+ error);
      return false;
    }
  };

  /**
   *  API Online
   * @zh-CN 接口上线
   * @param record
   */
  const handleOnline = async (record: API.InterfaceInfo) => {
    const hide = message.loading('正在上线');
    if (!record) return true;
    try {
      await onlineInterfaceInfoUsingPost({
        id: record.id,
      });
      hide();
      message.success('上线成功,即将刷新数据');
      actionRef.current?.reload();
      return true;
    } catch (error : any) {
      hide();
      message.error('上线失败，'+ error);
      return false;
    }
  };

  /**
   *  API Online
   * @zh-CN 接口下线
   * @param record
   */
  const handleOffline = async (record: API.InterfaceInfo) => {
    const hide = message.loading('正在下线');
    if (!record) return true;
    try {
      await offlineInterfaceInfoUsingPost({
        id: record.id,
      });
      hide();
      message.success('下线成功,即将刷新数据');
      actionRef.current?.reload();
      return true;
    } catch (error : any) {
      hide();
      message.error('下线失败，'+ error);
      return false;
    }
  };




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
      title: '接口状态',
      dataIndex: 'status',
      hideInForm: true,
      valueEnum: {
        0: {
          text: '关闭',
          status: 'Default',
        },
        1: {
          text: '运行中',
          status: 'Processing',
        },
      },
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
    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      width : 150,
      render: (_, record) => [
        <a
          key="config"
          onClick={() => {
            handleUpdateModalVisible(true);
            setCurrentRow(record);
          }}
        >
          修改
        </a>,
        record.status === 0? (
          <Button
            // type="text"

            key="APIstatus"
            onClick={() => {
              handleOnline(record);
            }}
          >
            开启
          </Button>

        ): null,
        record.status === 1 ?(
          <Button
            // danger
            key="APIstatus"
            onClick={() => {
              handleOffline(record);
            }}
          >
            关闭
          </Button>
        ): null,
        <Button
          danger
          key="delete"
          onClick={() => {
            handleRemove(record);
          }}
        >
          删除
        </Button>,
      ],
    },
  ];
  return (
    <PageContainer>
      <ProTable<API.InterfaceInfo, API.PageParams>
        headerTitle={'查询表格'}
        actionRef={actionRef}
        rowKey="key"
        search={{
          labelWidth: 120,
        }}
        toolBarRender={() => [
          <Button
            type="primary"
            key="primary"
            onClick={() => {
              handleModalVisible(true);
            }}
          >
            <PlusOutlined /> 新建
          </Button>,
        ]}
        request={ async (params) => {
          const res = await listInterfaceInfoByPageUsingGet({
            ...params
          });
          if(res.data) {
            return {
              data: res.data.records || [],
              success: true,
              total: res.data.total,
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
      <UpdateModal
        columns = {columns}
        onSubmit={async (value) => {

          const success = await handleUpdate(value);
          if (success) {
            handleUpdateModalVisible(false);
            setCurrentRow(undefined);
            if (actionRef.current) {
              actionRef.current.reload();
            }
          }
        }}
        onCancel={() => {
          handleUpdateModalVisible(false);
          if (!showDetail) {
            setCurrentRow(undefined);
          }
        }}
        visible={updateModalVisible}
        values={currentRow || {}}
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
              id: currentRow?.name,
            }}
            columns={columns as ProDescriptionsItemProps<API.InterfaceInfo>[]}
          />
        )}
      </Drawer>

      <CreateModal
        columns={columns}
        onCancel={ () => handleModalVisible(false) }
        visible={createModalVisible}
        onSubmit={
          async (values) => {
            handleAdd(values);
            actionRef.current?.reload();
          }
        }
      />

    </PageContainer>
  );
};
export default TableList;
