# Bank API Docs


**简介**:Bank API Docs


**HOST**:http://localhost:8080


**联系人**:Vaulka


**Version**:${application.version}


**接口路径**:/v3/api-docs/default


[TOC]






# 登录


## 登录


**接口地址**:`/login`


**请求方式**:`POST`


**请求数据类型**:`application/x-www-form-urlencoded,application/json`


**响应数据类型**:`*/*`


**接口描述**:


**请求示例**:


```javascript
{
  "username": "",
  "password": ""
}
```


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|loginDTO|LoginDTO|body|true|LoginDTO|LoginDTO|
|&emsp;&emsp;username|||true|string||
|&emsp;&emsp;password|||true|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK|LoginVO|


**响应参数**:


| 参数名称 | 参数说明 | 类型 | schema |
| -------- | -------- | ----- |----- | 
|id||integer(int64)|integer(int64)|
|name||string||
|username||string||
|role|可用值:EMPLOYEES,USER|string||
|authorization||string||


**响应示例**:
```javascript
{
	"id": 0,
	"name": "",
	"username": "",
	"role": "",
	"authorization": ""
}
```


# 银行利率查询


## 查询单位用户银行利率


**接口地址**:`/bank-deposit/unit/user`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|time||query|true|string(date)||
|bankType|可用值:GONGSHANG,ZHONGGUO,JIANSHE,JIAOTONG,NONGYE|query|true|string||
|field||query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK||


**响应参数**:


暂无


**响应示例**:
```javascript

```


## 查询单位员工银行利率


**接口地址**:`/bank-deposit/unit/employees`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|time||query|true|string(date)||
|bankType|可用值:GONGSHANG,ZHONGGUO,JIANSHE,JIAOTONG,NONGYE|query|true|string||
|field||query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK||


**响应参数**:


暂无


**响应示例**:
```javascript

```


## 查询个人用户银行利率


**接口地址**:`/bank-deposit/person/user`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|time||query|true|string(date)||
|bankType|可用值:GONGSHANG,ZHONGGUO,JIANSHE,JIAOTONG,NONGYE|query|true|string||
|field||query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK||


**响应参数**:


暂无


**响应示例**:
```javascript

```


## 查询个人员工银行利率


**接口地址**:`/bank-deposit/person/employees`


**请求方式**:`GET`


**请求数据类型**:`application/x-www-form-urlencoded`


**响应数据类型**:`*/*`


**接口描述**:


**请求参数**:


| 参数名称 | 参数说明 | 请求类型    | 是否必须 | 数据类型 | schema |
| -------- | -------- | ----- | -------- | -------- | ------ |
|time||query|true|string(date)||
|bankType|可用值:GONGSHANG,ZHONGGUO,JIANSHE,JIAOTONG,NONGYE|query|true|string||
|field||query|false|string||


**响应状态**:


| 状态码 | 说明 | schema |
| -------- | -------- | ----- | 
|200|OK||


**响应参数**:


暂无


**响应示例**:
```javascript

```