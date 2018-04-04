## demo-oauth2-spring-security

- 简单的 Password Flow demo with Spring Security OAuth 2，只有 AuthorizationServer 和 ResourceServer 没有 Client 服务
- 授权模式为 password 模式，直接通过 postman 实现

#### 直接访问资源

1. 直接访问资源 http://localhost:8080/products 报出"unauthorized exception message"

#### 要想正确访问资源，需首先获取授权码 access token

- 创建一个获取 token 的 post 请求 http://localhost:8080/oauth/token
- post 请求选择 Authorization 种的 Basic Auth, 输入 username 和 password， 此案例中 username 是 "client"， password 是 "clientpassword" (无引号) 
Authorization 是一个 Client credentials 但不是 user, 当发送请求的时候该 Authorization 自动变成 Http Header
authorization header 形式如下 
Authorization : Basic Y2xpZW50OmNsaWVudHBhc3N3b3Jk
- 设定Header  Content-Type 为 application/x-www-form-urlencoded

![alt text](https://snag.gy/FSs0Cw.jpg)

- 设定 body, 选择body 格式： x-www-form-urlencoded 设定如下key-value:

    - client_id   client

    - username    user

    - password    user

    - grant_type  password

![alt text](https://snag.gy/HlJZRq.jpg)

- 发送结果

```json
{
  "access_token": "bd999429-898b-4201-908e-40e846ec0105",
  "token_type": "bearer",
  "expires_in": 3599,
  "scope": "read write"
}
```

#### 重新访问受限资源

- 此次访问受限资源携带 access token

- 创建 get 请求 URL http://localhost:8080/products

- 直接设定 Authorization， 这次选择 Bearer Token, 直接填入上一步获得的access token ,点击send 会自动产生如下 header

    - Key: Authorization

    - Value: Bearer bd999429-898b-4201-908e-40e846ec0105

- 访问结果

```json
[
  {
    "name": "Mug for Coffee",
    "value": 12.99
  },
  {
    "name": "Coffee cup",
    "value": 4.21
  }
]
```

![alt text](https://snag.gy/WFjXt5.jpg)
 