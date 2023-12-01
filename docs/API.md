### API specification

`BASE_URL=https://security.eldes.lt`

<details><summary>Login</summary>

`POST: /api1?gatelogin=1`

Headers:
`Content-Type	application/x-www-form-urlencoded`

Body:
```
login=79000000000
psw=password
uuid=random uid (optional)
```
Response 200 (Success):
```
[
    {
        "id": srting,
        "key": srting,
        "name": srting,
        "user_id": srting,
        "owner_id": srting,
        "state": number
    }
]
```

Response 200 (Error):
```
{
    "error": {
        "code": number,
        "msg": string
    }
}
```
</details>


<details><summary>Open barrier</summary>

`POST: /api1?update=device&id=<id barrier>&key=<key barrier>`

Headers:
`Content-Type	application/x-www-form-urlencoded`

Body:
```
json={"vars":{"OPN":"1;79000000000"}}
```
Response 200 (Success):
```
{
	"id": string,
	"SEQ": string,
	"state": number
}
```

Response 200 (Error):
```
{
    "error": {
        "code": number,
        "msg": string
    }
}
```
</details>

