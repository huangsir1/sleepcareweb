# 钛铱睡眠管家后端接口说明
## Confidential 请勿在公共场所传播此文件
### 用户相关接口
#### 上传用户 or 用户注册
**简要描述：** 

- 用户注册接口

**请求URL：** 
- ` http://taiir.com/web/api/user/register`
  
**请求方式：**
- POST 

**请求头 **
- Content-Type:application/json

**请求体示例**
``` 
{  
   "birthday":-28800000,
   "essrank":0,
   "gender":"男",
   "height":160,
   "name":"姓名",
   "weight":50
}
``` 
**参数：** 

|参数名|必选|类型|说明|
|:-------|:-------|:-------|:-------|
| birthday |是| number| Epoch Time，January 1, 1970, 00:00:00 GMT到今天的**毫秒**数 |
| essRank |是| number| ess评分 |
| gender | 是|string| 男／女 |
| height | 是|number| 厘米 |
| name| 是| string| 姓名 |
| weight | 是|number| 千克 |


 **返回示例**
成功:
``` 
{  
   "code":2000,
   "message":"7f915b5d-172d-4c50-84c5-126dc276193a"
}
```
失败
``` 
{  
   "code":5000,
   "message":"未知错误"
}
```

 **返回参数说明** 

|参数名|类型|说明|
|:-------|:-------|:-------|
| code | number| 状态码，2000为成功，5000为失败 |
| message | string| 当状态码为2000时返回用户id，其他时返回错误信息 |


 **备注** 

- 更多返回错误代码请看错误代码描述

------------

#### 判断用户是否注册

**简要描述：** 

- 判断用户是否注册(上传)成功

**请求URL：** 
- ` http://taiir.com/web/api/user/register`
  
**请求方式：**
- POST 

**请求头 **
- Content-Type:application/json

**请求体示例**
``` 
{  
   "id":"92dfd39a-0e54-49b0-b82e-6c1abefad9cd"
}
``` 
**参数：** 

|参数名|必选|类型|说明|
|:-------|:-------|:-------|:-------|
| id |是| string| 用户id |


 **返回示例**
用户已注册
``` 
{
	"code":5002,
	"message":"用户已存在"
}

```
用户未注册
``` 
{  
   "code":5001,
   "message":"用户未注册"
}
```

 **返回参数说明** 

|键|类型|说明|
|:-------|:-------|:-------|
| code | number| 状态码，2000为成功，5000为失败 |
| message | string| 当状态码为2000时返回用户id，其他时返回错误信息 |


 **备注** 

- 更多返回错误代码请看错误代码描述

------------

#### 更新用户信息
**简要描述：** 

- 更新用户信息

**请求URL：** 
- ` http://taiir.com/web/api/user/update`
  
**请求方式：**
- POST 

**请求头 **
- Content-Type:application/json

**请求体示例**
``` 
{  
   "birthday":-28800000,
   "height":160,
   "weight":50
}
``` 
**参数：** 

|参数名|必选|类型|说明|
|:-------|:-------|:-------|:-------|
| birthday |是| number| Epoch Time，January 1, 1970, 00:00:00 GMT到今天的**毫秒**数 |
| height | 是|number| 厘米 |
| weight | 是|number| 千克 |


 **返回示例**
成功:
``` 
{  
   "code":2000,
   "message":"Id :  xxxx的用户修改成功"
}
```
失败
``` 
{  
   "code":5000,
   "message":"未知错误"
}
```

 **返回参数说明** 

|参数名|类型|说明|
|:-------|:-------|:-------|
| code | number| 状态码，2000为成功，5000为失败 |
| message | string| 返回提示信息 |


 **备注** 

- 更多返回错误代码请看错误代码描述

------------

#### 上传用户ess状态
**简要描述：** 

- 上传ess

**请求URL：** 
- ` http://taiir.com/web/api/user/uploadEss
  
**请求方式：**
- POST 

**请求头 **
- Content-Type:application/json

**请求体示例**
``` 
{  
   "essUsers":[  
      {  
         "essId":1,
         "rank":1,
         "userId":"213"
      },
      {  
         "essId":2,
         "rank":1,
         "userId":"213"
      },
      {  
         "essId":3,
         "rank":1,
         "userId":"213"
      },
      {  
         "essId":4,
         "rank":1,
         "userId":"213"
      },
      {  
         "essId":5,
         "rank":1,
         "userId":"213"
      },
      {  
         "essId":6,
         "rank":1,
         "userId":"213"
      },
      {  
         "essId":7,
         "rank":1,
         "userId":"213"
      },
      {  
         "essId":8,
         "rank":1,
         "userId":"213"
      }
   ]
}
``` 
**参数：** 

|参数名|必选|类型|说明|
|:-------|:-------|:-------|:-------|
| essUsers |是| object|ess对象数组|
| essId | 是|number| ess的id，id参照如下，坐着阅读时：1，看电视时：2，在公共场所坐着不动时( 如在剧场或开会)：3，长时间坐车时中间不休息( 超过 ! 5)：4，坐着与人谈话时：5，饭后休息时( 未饮酒时)：6，开车等红绿灯时：7，下午静卧休息时：8 |
| rank | 是|number| ess状态下的评分，0，1，2，3 |
| userId | 是|string| 用户id |


 **返回示例**
成功:
``` 
{  
   "code":2000,
   "message":"成功"
}
```
失败
``` 
{  
   "code":5000,
   "message":"未知错误"
}
```

 **返回参数说明** 

|参数名|类型|说明|
|:-------|:-------|:-------|
| code | number| 状态码，2000为成功，5000为失败 |
| message | string| 返回提示信息 |


 **备注** 

- 更多返回错误代码请看错误代码描述

------------

#### 上传用户疾病历史
**简要描述：** 

- 上传疾病史

**请求URL：** 
- ` http://taiir.com/web/api/user/uploadDiseaseHistory
  
**请求方式：**
- POST 

**请求头 **
- Content-Type:application/json

**请求体示例**
``` 
{  
   "diseaseHistoryIds":[  
      1,
      2,
      3,
      4,
      5,
      6,
      7
   ],
   "userId":"213"
}
``` 

**参数：** 

|参数名|必选|类型|说明|
|:-------|:-------|:-------|:-------|
| diseaseHistoryIds |是| object|disease id数组，在数组内的id即为有该疾病史，id参照如下，失眠：1，糖尿病：2，高血压：3，冠心病：4，心力衰竭：5，心律失常：6， 鼻腔阻塞：7，长期吸烟：8，悬雍垂粗大：9，OSAHS的家族史：10，脑血管疾病：11，肾功能损害：12，用镇静剂／药物：13，长期大量饮酒：14，绝经：15|
| userId | 是|string| 用户id |


 **返回示例**
成功:
``` 
{  
   "code":2000,
   "message":"成功"
}
```
失败
``` 
{  
   "code":5000,
   "message":"未知错误"
}
```

 **返回参数说明** 

|参数名|类型|说明|
|:-------|:-------|:-------|
| code | number| 状态码，2000为成功，5000为失败 |
| message | string| 返回提示信息 |


 **备注** 

- 更多返回错误代码请看错误代码描述

------------

#### 上传用户疾病历史和ess
**简要描述：** 

- 上传疾病史和ess

**请求URL：** 
- ` http://taiir.com/web/api/user/uploadEssAndDiseaseHistory
  
**请求方式：**
- POST 

**请求头 **
- Content-Type:application/json

**请求体示例**
``` 
{  
   "diseaseHistoryDto":{  
      "diseaseHistoryIds":[  
         2,
         3,
         4,
         5
      ],
      "userId":"213"
   },
   "essDto":{  
      "essUsers":[  
         {  
            "essId":1,
            "rank":1,
            "userId":"213"
         },
         {  
            "essId":2,
            "rank":2,
            "userId":"213"
         },
         {  
            "essId":3,
            "rank":3,
            "userId":"213"
         },
         {  
            "essId":4,
            "rank":1,
            "userId":"213"
         },
         {  
            "essId":5,
            "rank":2,
            "userId":"213"
         },
         {  
            "essId":6,
            "rank":3,
            "userId":"213"
         },
         {  
            "essId":7,
            "rank":2,
            "userId":"213"
         },
         {  
            "essId":8,
            "rank":1,
            "userId":"213"
         }
      ]
   }
}
``` 

**参数：** 

|参数名|必选|类型|说明|
|:-------|:-------|:-------|:-------|
| - diseaseHistoryDto |是| object  | disease 对象 |
| - - diseaseHistoryIds |是| object  | disease id数组，在数组内的id即为有该疾病史，id参照如下，失眠：1，糖尿病：2，高血压：3，冠心病：4，心力衰竭：5，心律失常：6， 鼻腔阻塞：7，长期吸烟：8，悬雍垂粗大：9，OSAHS的家族史：10，脑血管疾病：11，肾功能损害：12，用镇静剂／药物：13，长期大量饮酒：14，绝经：15|
| userId | string|是|  用户id |
| - essDto |object  |是|  ess 对象 |
| - - essUsers |object  |是|  ess对象数组 |
| essId | number|是|  ess的id，id参照如下，坐着阅读时：1，看电视时：2，在公共场所坐着不动时( 如在剧场或开会)：3，长时间坐车时中间不休息( 超过 ! 5)：4，坐着与人谈话时：5，饭后休息时( 未饮酒时)：6，开车等红绿灯时：7，下午静卧休息时：8 |
| rank | 是|number| ess状态下的评分，0，1，2，3 |
| userId | 是|string| 用户id |


 **返回示例**
成功:
``` 
{  
   "code":2000,
   "message":"成功"
}
```
失败
``` 
{  
   "code":5000,
   "message":"未知错误"
}
```

 **返回参数说明** 

|参数名|类型|说明|
|:-------|:-------|:-------|
| code | number| 状态码，2000为成功，5000为失败 |
| message | string| 返回提示信息 |


 **备注** 

- 更多返回错误代码请看错误代码描述

------------

#### 上传报告
**简要描述：** 

- 上传报告

**请求URL：** 
- ` http://taiir.com/web/api/baseReport/upload
  
**请求方式：**
- POST 

**请求头**
- Content-Type:application/json

**请求体示例**
``` 
{  
   "advice":"2333",
   "apneaHypopneaIndex":27.19,
   "apneaTimes":123,
   "averageOxygenSaturation":97.02,
   "averagePulse":76.03,
   "bloodOxygenHazardIndex":10.32,
   "brathe_score":0,
   "deepSleepSeconds":3300,
   "endTime":1458165949000,
   "hyponeaHappenDate":1458143870000,
   "hypopneaTimes":101,
   "id":"2e87291b-2394-4ea5-ae68-79c487e8690b",
   "lightSleepSeconds":25567,
   "longestApneaSeconds":135,
   "longestApneaTime":1458143870000,
   "longestOxygenReduceSeconds":120,
   "longestOxygenReduceTime":1458159274000,
   "macAddress":"8C:DE:52:64:98:BC",
   "maxHyponeaSeconds":60,
   "maxOxygenReduceSeconds":46,
   "maxOxygenReduceTime":1458164436000,
   "maxOxygenSaturation":0,
   "maxPulse":179,
   "maxPulseTime":1458136763000,
   "minOxygenSaturation":69,
   "minPulse":47,
   "minPulseTime":1458159274000,
   "odi":0,
   "oxygenReductionIndex":27.31,
   "oxygenSaturationEightyToEightyNinePercentHyponea":600,
   "oxygenSaturationEightyToEightyNinePercentTimes":41,
   "oxygenSaturationFiftyToFiftyNinePercentHyponea":0,
   "oxygenSaturationFiftyToFiftyNinePercentTimes":0,
   "oxygenSaturationLessthanFiftyPercentHyponea":0,
   "oxygenSaturationLessthanFiftyPercentTimes":0,
   "oxygenSaturationLessthanNinetyPercent":2.3,
   "oxygenSaturationNinetyToHundredPercentHyponea":28988,
   "oxygenSaturationNinetyToHundredPercentTimes":180,
   "oxygenSaturationSeventyToSeventyNinePercentHyponea":0,
   "oxygenSaturationSeventyToSeventyNinePercentTimes":3,
   "oxygenSaturationSixtyToSixtyNinePercentHyponea":0,
   "oxygenSaturationSixtyToSixtyNinePercentTimes":1,
   "reducedOxygenTimes":0,
   "sleep_score":0,
   "startTime":1458136226000,
   "totalHyponeaSeconds":0,
   "totalSeconds":29648,
   "userId":"b6d24c67-e121-4405-b53a-eaa4119c9656"
}
``` 

**参数：** 

|键|类型|说明|
|:-------|:-------|:-------|
| advice | string| 医生建议 |
| apneaHypopneaIndex | number| 呼吸暂停低通气指数 |
| apneaTimes | number| 呼吸暂停次数 |
| averageOxygenSaturation | number| 平均血氧饱和度 |
| averagePulse | number| 平均脉率 |
| bloodOxygenHazardIndex | number| 血氧危害指数 |
| brathe_score | number| 呼吸评分 |
| deepSleepSeconds | number| 深度睡眠时长（秒） |
| endTime | number| 睡眠结束时间 |
| hyponeaHappenDate | number| 低通气发生时间 |
| hypopneaTimes | number| 低通气次数 |
| id | string| 报告id，由客户端生成16进制 8-4-4-4-12位的uuid，数值为1-F，请参照示例，该id为标准的UUID。ios: NSUUID().UUIDString ,java: UUID.randomUUID().toString() |
| lightSleepSeconds | number| 浅睡眠时长（秒） |
| longestApneaSeconds | number| 最长呼吸暂停时长（秒） |
| longestApneaTime | number| 最长呼吸暂停发生时间 |
| longestOxygenReduceSeconds | number| 最长氧降时长（秒） |
| longestOxygenReduceTime | number|  最长氧降发生时间 |
| macAddress | string| mac地址 |
| maxHyponeaSeconds | number| 最长低通气秒数 |
| maxOxygenReduceSeconds | number| 最大氧降秒数 |
| maxOxygenReduceTime | number| 最大氧降发生时间 |
| maxOxygenSaturation | number| 最高血氧饱和度 |
| maxPulse | number| 最大脉率 |
| maxPulseTime | number| 最大脉率发生时间 |
| minOxygenSaturation | number| 最低血氧饱和度 |
| minPulse | number| 最小脉率 |
| minPulseTime | number| 最小脉率发生时间 |
| odi | number| odi |
| oxygenReductionIndex | number| OxygenReductionIndex |
| oxygenSaturationEightyToEightyNinePercentHyponea | number| 血氧80%-89%的持续时间秒数 |
| oxygenSaturationEightyToEightyNinePercentTimes | number| 血氧80%-89%的次数 |
| oxygenSaturationFiftyToFiftyNinePercentHyponea | number| 血氧50%-59%的持续时间秒数 |
| oxygenSaturationFiftyToFiftyNinePercentTimes | number| 血氧50%-59%的次数 |
| oxygenSaturationLessthanFiftyPercentHyponea | number| 血氧<50%的持续时间秒数 |
| oxygenSaturationLessthanFiftyPercentTimes | number|  血氧<50%的次数 |
| oxygenSaturationLessthanNinetyPercent | number| 血氧低于90%的百分比 |
| oxygenSaturationNinetyToHundredPercentHyponea | number| 血氧90%-100%的持续时间秒数 |
| oxygenSaturationNinetyToHundredPercentTimes | number| 血氧90%-100%的次数 |
| oxygenSaturationSeventyToSeventyNinePercentHyponea | number| 血氧70%-79%的持续时间秒数 |
| oxygenSaturationSeventyToSeventyNinePercentTimes | number| 血氧70%-79%的次数 |
| oxygenSaturationSixtyToSixtyNinePercentHyponea | number| 血氧60%-69%的持续时间秒数 |
| oxygenSaturationSixtyToSixtyNinePercentTimes | number| 血氧60%-69%的次数 |
| reducedOxygenTimes | number| 氧减次数 |
| sleep_score | number| 睡眠评分 |
| startTime | number| 睡眠开始时间 |
| totalHyponeaSeconds | number| 低通气总时间秒数 |
| totalSeconds | number| 睡眠总时长秒数 |
| userId | string| 用户id |



 **返回示例**
成功:
``` 
{  
   "code":2000,
   "message":"成功"
}
```
失败
``` 
{  
   "code":5000,
   "message":"未知错误"
}
```

 **返回参数说明** 

|参数名|类型|说明|
|:-------|:-------|:-------|
| code | number| 状态码，2000，2006，2002，2001为成功，5000为失败，5001用户未注册失败|
| message | string| 返回提示信息 |


 **备注** 

- 更多返回错误代码请看错误代码描述

------------

#### 上传报告文件
**简要描述：** 

- 上传报告文件

**请求URL：** 
- ` http://taiir.com/web/api/report/file/upload
  
**请求方式：**
- POST 

**请求头 **
- Content-Type: application/x-www-form-urlencoded

**普通post请求**


**参数：** 

|参数名|必选|类型|说明|
|:-------|:-------|:-------|:-------|
| file | 是|file| 文件 |
| reportId | 是|string| 报告id |


 **返回示例**
成功:
``` 
{  
   "code":2000,
   "message":"成功"
}
```
失败
``` 
{  
   "code":5000,
   "message":"未知错误"
}
```

 **返回参数说明** 

|参数名|类型|说明|
|:-------|:-------|:-------|
| code | number| 状态码，2000，2003，2004为成功，5000为失败，6003报告不存在， |
| message | string| 返回提示信息 |


 **备注** 

- 更多返回错误代码请看错误代码描述

------------

#### 上传报告意见
**简要描述：** 

- 上传报告意见

**请求URL：** 
- ` http://taiir.com/web/api/advice/upload
  
**请求方式：**
- POST 

**请求头 **
- Content-Type:application/json

**请求示例**

```
{  
   "advice":"advice",
   "id":"id"
}
```

**参数：** 

|参数名|必选|类型|说明|
|:-------|:-------|:-------|:-------|
| advice | 是|string| 医生建议 |
| id | 是|string| 报告id |


 **返回示例**
成功:
``` 
{  
   "code":2000,
   "message":"成功"
}
```
失败
``` 
{  
   "code":5000,
   "message":"未知错误"
}
```

 **返回参数说明** 

|参数名|类型|说明|
|:-------|:-------|:-------|
| code | number| 状态码，2000-3000为成功，其他为失败|
| message | string| 返回提示信息 |


 **备注** 

- 更多返回错误代码请看错误代码描述


------------


 **错误代码详情** 
 
|参数名|说明|
|:-------|:-------|
|2000|成功|
|2001|报告上传成功|
|2002|报告上传成功，并且已经开始生成报告|
|2003|文件上传成功|
|2004|文件上传成功，并且已经开始生成报告|
|2005|请求显示报告，但是报告文件不存在，正在生成|
|2006|报告覆盖成功|
|5000|未知错误|
|5001|用户未注册|
|5002|用户已存在|
|5003|参数错误|
|5004|无法删除用户|
|5005|在该条目的所有子条目删除之前，无法删除该条目。|
|5100|验证码错误|
|5200|用户注册成功|
|6001|报告已存在|
|6002|生成报告的条件不齐全|
|6003|报告不存在|
|6004|无法删除报告|
|7000|密码不匹配|
|7001|新密码与原密码相同|



