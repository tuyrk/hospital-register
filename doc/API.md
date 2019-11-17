# API

## 用户

### 患者信息列表√

```
GET /register/patient/patient/list
```

参数

```json
{
    "openid": "oYnw56OEcTV8oekci1lk-ss-YvoQ"
}
```

返回

```json
{
    "code": 0,
    "msg": "成功",
    "data": [
        {
            "patientId": "1547362583672852456",
            "patientName": "张三",
            "patientCard": "510726198512302568"
        }
    ]
}
```

### 医院简介√

```
GET /register/clinic/synopsis
```

参数

```
无
```

返回

```json
{
    "code": 0,
    "msg": "成功",
    "data": "成都中医药大学温江门诊部座落于成都中医药大学温江校区内，建筑面约2600平方米。..."
 }
```

### 医院导航√

```
GET /register/patient/clinic/nav
```

参数

```
无
```

返回

```json
{
    "code": 0,
    "msg": "成功",
    "data": {
        "floorSynopsis": "一楼:...",
        "clinicPosition": "成都市温江区学府路北段..."
    }
 }
```

### 医生列表√

```
GET /register/patient/doctor/list
```

参数

```json
{
    "departmentId": "中医科"
 }
```

返回

```json
{
    "code": 0,
    "msg": "成功",
    "data": [
        {
            "doctorId": "1547362583672852456",
            "doctorPhoto": "123.jpg",
            "doctorName": "杨川",
            "doctorPost": "副主任医师",
            "doctorAdept": "师从四川省十大名中医艾儒棣教授，省中医药学会中医协....",
            "remainder": 7
        }
    ]
 }
```

### 医生简介√

```
GET /register/patient/doctor/detail
```

参数

```json
{
    "doctorId": "1547362583672852456"
 }
```

返回

```json
{
    "code": 0,
    "msg": "成功",
    "data": {
        "doctorPhoto": "123.jpg",
        "doctorName": "杨川",
        "doctorPost": "副主任医师",
        "doctorAdept": "师从四川省十大名中医艾儒棣教授，省中医药学会中医协....",
        "doctorDetail": "硕士研究生导师,四川省中医药管理局学术技术带头人后备人选,四川省卫生和计划生育委员会学术技术带头人后备人选,成都中医药大学优势学科学术技术带头人后备人选。 毕业于成都中医药大学，长期从事中医药临床、教学与科研工作，先后主持、主研国家自然科学基金、部省级、厅局级科研课题20余项，出版国家级规划教材7部。临床上以辨证施治为指导，结合历代医家临床经验，师古而不泥古，力主中西汇通，将现代科学技术与中医经典理论结合互用，擅治内科、妇科、儿科、皮肤科常见病及疑难杂症，尤其擅长治疗各类良、恶性肿瘤疾病。"
    }
 }
```

### 解除绑定√

```
PUT /register/patient/patient/remove
```

参数

```json
{
    "patientId": "1547362583672852456"
 }
```

返回

```json
{
    "code": 0,
    "msg": "成功"
 }
```

### 绑定信息√

```
POST /register/patient/patient/bind
```

参数

```json
{
    "openid": "123456",
    "patientName": "张三",
    "patientCard": "510726198512302568",
    "patientPhone": "18382554758",
    "patientMail": "7665646162qq.com"
 }
```

返回

```json
{
    "code": 0,
    "msg": "成功"
 }
```

### 医生主页√

```
GET /register/patient/schedule/detail
```

参数

```json
{
    "doctorId": "1547362583672852456"
 }
```

返回

```json
{
    "code": 0,
    "msg": "成功",
    "data": [
        {
            "scheduleId": "1547362583672852456",
            "scheduleTime": "2019-1-2 上午",
            "scheduleMoney": 20,
            "remainder": 7,
            "sourceNumber":[
                {
                    "numberId": 1,
                    "isSchedule": false,
                    "numberTime":"08:30"
                }
            ]
        }
    ]
 }
```

### 点击号源（下订单）√

```
POST /register/patient/order/place
```

参数

```json
{
    "patientId": "123",
    "scheduleId": "1547362583672852456",
    "numberId": "1"
}
```

返回

```json
{
    "code": 0,
    "msg": "成功",
    "data": "20190113151628196777085"
 }
```

### 立即支付√

```
POST /register/patient/order/pay
```

参数

```json
{
    "openid": "oYnw56OEcTV8oekci1lk-ss-YvoQ",
    "orderId": "1547362583672852456"
 }
```

返回

```json
{
    "code": 0,
    "msg": "成功"
 }
```

### 缴费记录√

```
GET /register/patient/order/pay_list
```

参数

```json
{
    "openid": "oYnw56OEcTV8oekci1lk-ss-YvoQ",
    "orderStatus": ""
}
```

返回

```json
{
    "code": 0,
    "msg": "成功",
    "data": [
        {
            "orderId": "20190113151628196777085",
            "patientName": "张三",
            "department": "中医科",
            "payTime": "2019-01-05 21:30",
            "orderMoney": "520",
            "orderStatus": "待缴费"
        }
     ]
 }
```

### 我的缴费√

```
GET /register/patient/order/paying
```

参数

```json
{
    "openid": "oYnw56OEcTV8oekci1lk-ss-YvoQ",
}
```

返回

```json
{
    "code": 0,
    "msg": "成功",
    "data": [
        {
            "orderId": "20190113151628196777085",
            "patientName": "张三",
            "department": "中医科",
            "payTime": "2019-01-05 21:30",
            "orderMoney": "520",
            "orderStatus": "待缴费"
        }
     ]
 }
```

### 挂号记录√

```
GET /register/patient/order/list
```

参数

```json
{
    "openid": "oYnw56OEcTV8oekci1lk-ss-YvoQ"
}
```

返回

```json
{
    "code": 0,
    "msg": "成功",
    "data": [
        {
            "orderId": "20190113151628196777085",
            "patientName": "张三",
            "department": "中医科",
            "scheduleTime": "2019-01-05上午 9:30",
            "doctorName": "李四"
        }
     ]
 }
```

### 确认支付/挂号详情√

```
GET /register/patient/order/detail
```

参数

```json
{
    "orderId": "20190113151628196777085"
}
```

返回

```json
{
    "code": 0,
    "msg": "成功",
    "data": {
            "doctorName": "张三",
            "department": "中医科",
            "scheduleTime": "2019-01-05上午 9:30",
            "patientName": "张三",
            "patientCard": "510726198512302568",
            "patientPhone": "18382554758"
        }
     
 }
```

### 排班表√

```
GET /register/patient/schedule/list
```

参数

```
无
```

返回

```json
{
"code": 0,
"msg": "成功",
"data": [
    {
        "departmentName": "中医科",
        "scheduleWeek": "2019-02-14",
        "scheduleTime": "上午",
        "doctors":["王五","李四"]
    }
]
}
```
## 管理员

### 管理员列表√

```
GET /register/admin/admin/list
```

参数

```json
{
    "username": "",
    "adminName": "",
    "adminType": ""
 }
```

返回

```json
{
    "code": 0,
    "msg": "成功",
    "data": [
        {
            "adminId": "",
            "username": "admin",
            "adminName": "管理员",
            "adminSex": "男",
            "adminPhone": "联系方式",
            "adminType": "系统管理员"
        }
    ]
}
```

### 管理员新增√

```
POST /register/admin/admin/save
```

参数

```json
{
    "username": "admin",
    "adminName": "管理员",
    "adminSex": "男",
    "adminPhone": "联系方式",
    "adminType": "系统管理员"
}
```

返回

```json
{
    "code": 0,
    "msg": "成功"
}
```

### 管理员删除√

```
POST /register/admin/admin/delete
```

参数

```json
{
    "adminId": ""
}
```

返回

```json
{
    "code": 0,
    "msg": "成功"
}
```

### 医生列表

```
GET /register/admin/doctor/list
```

参数

```json
{
    "username": "",
    "doctorName": "",
    "department": "",
    "doctorCard": ""
}
```

返回

```json
{
    "code": 0,
    "msg": "成功",
    "data": [
        {
            "doctorId": "123",
            "username": "201801",
            "doctorName": "张三",
            "doctorSex": "男",
            "doctorCard": "530723198466327463",
            "department": "中医科",
            "doctorPhone": "18373666666"
        }
    ]
}
```

### 医生新增

```
POST /register/admin/doctor/save
```

参数

```json
{
    "username": "201801",
    "doctorName": "张三",
    "doctorSex": "男",
    "doctorCard": "530723198466327463",
    "department": "中医科",
    "doctorPhone": "18373666666",
    "doctorMail": "123"
}
```

返回

```json
{
    "code": 0,
    "msg": "成功"
}
```

### 医生删除

```
POST /register/admin/doctor/delete
```

参数

```json
{
    "doctorId": "123"
}
```

返回

```json
{
    "code": 0,
    "msg": "成功"
}
```

### 患者列表

```
GET /register/admin/patient/list
```

参数

```json
{
    "patientName": "",
    "patientCard": "",
    "patientPhone": ""
}
```

返回

```json
{
    "code": 0,
    "msg": "成功",
    "data": [
        {
            "patientId": "123",
            "patientName": "王艳",
            "patientCard": "514733199610257864",
            "patientPhone": "18362647222",
            "patientMail": "111@qq.com"
        }
    ]
}
```

### 科室列表

```
GET /register/admin/department/list
```

参数

```json
{
    "departmentName": ""
}
```

返回

```json
{
    "code": 0,
    "msg": "成功",
    "data": [
        {
            "departmentId": "01",
            "departmentName": "中医科",
            "principal": "张三",
            "principalPhone": "18362647222"
        }
    ]
}
```

### 科室新增

```
POST /register/admin/department/save
```

参数

```json
{
    "departmentId": "01",
    "departmentName": "中医科",
    "principal": "张三",
    "principalPhone": "18362647222"
}
```

返回

```json
{
    "code": 0,
    "msg": "成功"
}
```

### 医院简介

```
GET /register/admin/clinic/synopsis/modify
```

参数

```json
{
    "clinicSynopsis": ""
}
```

返回

```json
{
    "code": 0,
    "msg": "成功"
 }
```

### 医生列表

```
GET /register/admin/doctor/list
```

参数

```json
{
    "doctorName": "",
    "department": ""
}
```

返回

```json
{
    "code": 0,
    "msg": "成功",
    "data": [
        {
            "doctorId": "123",
            "username": "01",
            "doctorName": "张三",
            "department": "中医科",
            "doctorPhone": "18373666666"
        }
    ]
}
```

### 医生维护

```
POST /register/admin/doctor/modify
```

参数

```json
{
    "doctorId": "",
    "doctorPost": "",
    "doctorAdept": "",
    "doctorDetail": ""
}
```

返回

```json
{
    "code": 0,
    "msg": "成功"
}
```

### 医院导航

```
POST /register/admin/clinic/nav/modify
```

参数

```json
{
    "floorSynopsis": "一楼:...",
    "clinicPosition": "成都市温江区学府路北段..."
}
```

返回

```json
{
    "code": 0,
    "msg": "成功"
 }
```

### 医生排班表

```
POST /register/admin/admin/list
```

参数

```json
{
    "doctorId": "",
    "scheduleTime": "",
    "scheduleMoney": "",
    "intervalTime": ""
}
```

返回

```json
{
    "code": 0,
    "msg": "成功"
}
```

### 挂号信息管理

```
GET /register/admin/order/list
```

参数

```json
{
    "doctorName": "",
    "department": ""
}
```

返回

```json
{
    "code": 0,
    "msg": "成功",
    "data": [
        {
            "orderId": "20190113151628196777085",
            "patientId": "admin",
            "patientName": "王艳",
            "patientCard": "514733199610257864",
            "department": "中医科",
            "doctorName": "张三",
            "scheduleTime": "2019-01-05上午 9:30"
        }
     ]
 }
```

### 挂号信息统计

```
GET /register/admin/order/show
```

参数

```json
{
    "scheduleTime": "yyyy-MM",
}
```

返回

```json
{
    "code": 0,
    "msg": "成功",
    "data": [
        {
            "department": "中医科",
            "doctorCount": "120"
        }
     ]
 }
```