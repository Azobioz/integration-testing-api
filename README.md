
# Integration-Testing-API

Данный API был создан с целью закрыть предмет "Интеграционное тестирование. Основые автоматизации тестирования" на 100 баллов. 

## Что нужно для работы с API
* Скачать Docker Desktop [здесь](https://www.docker.com/products/docker-desktop/)
* Скачать файл docker-compose.yml из репозитория 
* Скачать [postman](https://www.postman.com/downloads/) для тестирования API
* Скачать все json файлы из директории [integration-testing-api/src/main/resources](https://github.com/Azobioz/integration-testing-api/tree/main/src/main/resources)

## Как запустить API 

### На Windows
1. Скачайте файл docker-compose.yml 
2. В командой строке перейдите на директорию с файлом docker-compose.yml
3. Пропишите в этой директории:
```
docker compose up
```
чтобы запустить работу API

4. Чтобы выключить работу API пропишите в той же директории:
```
docker compose down
```

## Структура коллекции в postman
После того как импортитруете "My API Testing Collection.postman_collection.json" в postman будет такая коллекция:

![](https://i.ibb.co/JjBC0bFp/image.png)


## Инструкция по настройке
Чтобы коллекция работала нужно импортировать "MainEnv.postman_environment.json" в postman и указать переменное окружение MainEnv

Запросы в папке "Chained requests" нужны для запуска цепных запросов и для работы с ней нужно импортировать файл в postman "ChainReqEnv.postman_environment.json" и выбрать переменное окружение ChainReqEnv, а также импортировать "test-data.json" и использовать как тестовые данные 

## Описание всех запросов

### Создать пользователя 
```
 POST api/auth/register  
```
Тело запроса:
```
{
    "name": String,
    "email": String,
    "password" String
}
```
Ответ:
```
User is registered successfully
```


### Аутентификация и получение токена 
```
 POST api/auth/login  
```
Тело запроса:
```
{
    "email": String,
    "password" String
}
```
Ответ:
```
{
    "id": Number,
    "accessToken": String,
    "refreshToken": String
}
```

### Создать новый токен 
```
 POST api/auth/refresh  
```
Тело запроса:
```
{
    "refreshToken": {{refresh_token}}
}
```
Ответ:
```
{
    "id": Number,
    "accessToken": String,
    "refreshToken": String // Тот же самый так как срок не истек
}
```

### Сделать logout 
```
 POST api/auth/logout  
```

Ответ:
```
{
    "id": Number,
    "accessToken": String,
    "refreshToken": String // Тот же самый так как срок не истек
}
```

### Получить всех пользователей
```
GET api/users
```
Ответ:
```
[
    {
        "id": Number,
        "name": String,
        "email": String,
        "createAt": Time
    },
    {
        "id": Number,
        "name": String,
        "email": String,
        "createAt": Time
    },
    {
        ....
    }
]
```

### Получить пользователя по ID
```
 GET users/{тут указать id}
```

Ответ:
```
{
    "id": Number,
    "name": String,
    "email": String,
    "createAt": Time
}
```

### Обновить данные пользователя
```
 PUT api/users/{тут указать id}/update
```
Тело запроса:
```
{
    //Можно указаывать не все данные 
    "name": String 
    "email": String,
    "password" String
}
```
Ответ:
```
{
    "id": Number,
    "name": String,
    "email": String
    "createdAt": Time
}
```

### Создать продукт
```
 POST api/users/{тут указать id}/delete
```
Ответ:
```
User has been deleted
```

### Обновить данные пользователя
```
 PUT api/products/create
```
Тело запроса:
```
{
    "name": String
    "price": Double
}
```
Ответ:
```
{
    "id": Number,
    "name": String,
    "price": Double
}
```

### Получить все продукты
```
 GET api/products
```

Ответ:
```
[
    {
        "id": Number,
        "name": String,
        "price": Double
    },
    {
        "id": Number,
        "name": String,
        "price": Double
    },
    {
        ....
    }
]
```

### Получить все продукты (Max price filter)
```
 GET api/products?maxPrice={указать значение}
```

Ответ:
```
[
    {
        "id": Number,
        "name": String,
        "price": Double
    },
    {
        "id": Number,
        "name": String,
        "price": Double
    },
    {
        ....
    }
]
```

### Получить все продукты (Min price filter)
```
 GET api/products?minPrice={указать значение}
```

Ответ:
```
[
    {
        "id": Number,
        "name": String,
        "price": Double
    },
    {
        "id": Number,
        "name": String,
        "price": Double
    },
    {
        ....
    }
]
```

### Получить все продукты (Min and max price filter)
```
 GET api/products?minPrice={указать значение}&maxPrice={указать значение}
```

Ответ:
```
[
    {
        "id": Number,
        "name": String,
        "price": Double
    },
    {
        "id": Number,
        "name": String,
        "price": Double
    },
    {
        ....
    }
]
```
### Обновить данные продукта
```
 PUT api/products/{указать id продукта}/update
```
Тело запроса:
```
{
    //Можно указаывать не все данные 
    "name": String
    "price": Double
}
```
Ответ:
```
{
    "id": Number,
    "name": String,
    "price": Double
}
```

### Сделать заказ
```
 POST api/users/{указать id пользователя}/orders/create
```
Тело запроса:

либо
```
{
    "products": [
        {
            //Создать новый продукт прямо тут
            "name": String
            "price": Double
        },
        {
             "name": String
             "price": Double
        },
        {
            ....
        }
    ]
}
```

либо
```
{
    "products": [
        {
            //Указать id существующего продукта
            "id": Number
        },
        {
             "id": Number
        },
        {
            ....
        }
    ]
}
```

Ответ:
```
{
    "id": Number, //номер заказа
    "user": {
        "id": Number,
        "name": String,
        "email": String
    },
    "products": [
        {
            "id": Number,
            "name": String,
            "price": Double
        },
        {
            "id": Number,
            "name": String,
            "price": Double
        },
        {
            ...
        }
    ]
}
```

### Получить все заказы пользователя
```
 GET api/users/{указать id пользователя}/orders
```

Ответ:
```
[
    {
        "id": Number, //номер заказа
        "user": {
            "id": Number,
            "name": String,
            "email": String
        },
        "products": [
            {
                "id": Number,
                "name": String,
                "price": Double
            },
            {
                "id": Number,
                "name": String,
                "price": Double
            },
            {
                ...
            }
        ]
    },
    {
        ...
    }
]
```

## Примеры
![1](https://i.ibb.co/pBqvDs2R/image.png)

![2](https://i.ibb.co/YBBv3tMd/image.png)

![3](https://i.ibb.co/bMGwcGYw/image.png)

![4](https://i.ibb.co/HLZsrb3B/image.png)

![5](https://i.ibb.co/MkwZrFWv/image.png)

![6](https://i.ibb.co/7JPsB7Cy/image.png)

![7](https://i.ibb.co/9khMQ2ct/image.png)

![8](https://i.ibb.co/Q7TCr6cZ/image.png)

![9](https://i.ibb.co/5VXR28c/image.png)

## Успешное выполнение цепочку запросов из папки Chained requests

![](https://i.ibb.co/FqLLFSQC/image.png)
